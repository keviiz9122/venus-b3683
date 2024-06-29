package me.levansj01.verus.discord;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.client.ClientData;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.util.webhook.WebhookClient;
import me.levansj01.verus.util.webhook.send.WebhookEmbed;
import me.levansj01.verus.util.webhook.send.WebhookEmbedBuilder;
import org.bukkit.ChatColor;

public class DiscordManager implements Runnable {
   private final Queue<WebhookEmbed> logsQueue = new LinkedList();
   private static DiscordManager instance;
   private static final WebhookEmbedBuilder BUILDER = new WebhookEmbedBuilder();
   private static final ScheduledExecutorService DISCORD_SERVICE = Executors.newSingleThreadScheduledExecutor();
   private final Queue<WebhookEmbed> bansQueue = new LinkedList();
   private WebhookClient logsClient;
   private WebhookClient bansClient;
   private static final String AVATAR_URL = "https://minotar.net/helm/%s/100.png";

   public void run() {
      int var2 = 0;

      do {
         if (var2 >= 10) {
            return;
         }

         WebhookEmbed var1;
         if (!this.bansQueue.isEmpty()) {
            while((var1 = (WebhookEmbed)this.bansQueue.poll()) != null) {
               this.bansClient.send(var1, new WebhookEmbed[0]);
            }
         }

         if (!this.logsQueue.isEmpty()) {
            while((var1 = (WebhookEmbed)this.logsQueue.poll()) != null) {
               this.logsClient.send(var1, new WebhookEmbed[0]);
            }
         }

         ++var2;
      } while (true);
   }

   public void stop() {
      if (this.logsClient != null) {
         this.logsClient.close();
      }

      if (this.bansClient != null) {
         this.bansClient.close();
      }

      this.logsQueue.clear();
      this.bansQueue.clear();
   }

   public void sendLog(PlayerData var1, String var2) {
      if (this.logsClient != null) {
         ClientData var3 = var1.getClientData();
         String var4 = EnumMessage.DISCORD_LOG.get().replace("{log}", ChatColor.stripColor(var2)).replace("{client}", var3.getType().getDisplay()).replace("{brand}", var3.getBrand()).replace("{version}", var1.getVersion().getName());
         this.logsQueue.add(BUILDER.setColor(3553598).setThumbnailUrl(String.format("https://minotar.net/helm/%s/100.png", var1.getName())).setDescription(var4).build());
      }

   }

   public void sendBan(PlayerData var1, Check var2) {
      if (this.bansClient != null) {
         ClientData var3 = var1.getClientData();
         String var4 = EnumMessage.DISCORD_BAN.get().replace("{player}", var1.getName()).replace("{subType}", var2.getSubType()).replace("{client}", var3.getType().getDisplay()).replace("{brand}", var3.getBrand()).replace("{version}", var1.getVersion().getName()).replace("{checkType}", var2.getType().getName());
         this.bansQueue.add(BUILDER.setColor(3553598).setThumbnailUrl(String.format("https://minotar.net/helm/%s/100.png", var1.getName())).setDescription(var4).build());
      }

   }

   public static DiscordManager getInstance() {
      DiscordManager var10000;
      if (instance == null) {
         var10000 = instance = new DiscordManager();
      } else {
         var10000 = instance;
      }

      return var10000;
   }

   public void start() {
      VerusConfiguration var1 = StorageEngine.getInstance().getVerusConfig();
      String var2 = var1.getDiscordLogsUrl();
      if (var1.isDiscordLogs() && !var2.isEmpty()) {
         this.logsClient = WebhookClient.withUrl(var2);
      }

      String var3 = var1.getDiscordBansUrl();
      if (var1.isDiscordBans() && !var3.isEmpty()) {
         this.bansClient = WebhookClient.withUrl(var3);
      }

      if (this.logsClient != null || this.bansClient != null) {
         DISCORD_SERVICE.scheduleAtFixedRate(this, 0L, 5L, TimeUnit.SECONDS);
      }

   }
}
