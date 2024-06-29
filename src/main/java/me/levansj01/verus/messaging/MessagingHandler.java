package me.levansj01.verus.messaging;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.messaging.listener.BrandListener;
import me.levansj01.verus.storage.StorageEngine;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;

public class MessagingHandler {
   private static final String verusChannel;
   private ThreadLocal<Cipher> cipher;
   private static MessagingHandler instance;
   private BrandListener brandListener;
   private VerusPlugin plugin;

   public BrandListener getBrandListener() {
      return this.brandListener;
   }

   public void disable() {
      if (this.plugin != null) {
         this.unregisterChannels();
         if (this.brandListener != null) {
            this.brandListener = null;
         }

      }
   }

   private void registerChannels() {
      Messenger var1 = this.plugin.getServer().getMessenger();
      VerusLauncher var2 = this.plugin.getPlugin();
      String var3 = StorageEngine.getInstance().getVerusConfig().getMessagingChannel();
      if (!var3.equals("BungeeCord")) {
         label35: {
            try {
               var1.registerOutgoingPluginChannel(var2, "BungeeCord");
            } catch (Throwable var7) {
               break label35;
            }
         }
      }

      label31: {
         try {
            var1.registerOutgoingPluginChannel(var2, verusChannel);
         } catch (Throwable var6) {
            break label31;
         }
      }

      label27: {
         try {
            var1.registerOutgoingPluginChannel(var2, var3);
         } catch (Throwable var5) {
            this.plugin.getPlugin().getLogger().log(Level.SEVERE, "Failed to register bungeecord messaging channel " + var3 + ", ", var5);
            break label27;
         }
      }

      this.registerBrandListener(var1, var2);
   }

   static {
      String var10000;
      if (NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_13_R2)) {
         var10000 = "bungeecord:verus";
      } else {
         var10000 = "VerusBungee";
      }

      verusChannel = var10000;
   }

   public void enable(VerusPlugin var1) {
      this.plugin = var1;
      this.registerChannels();
      String var2 = StorageEngine.getInstance().getVerusConfig().getSecretKeyPath();
      Path var3 = Paths.get(var2);

      byte[] var4;
      try {
         if (Files.notExists(var3, new LinkOption[0])) {
            var4 = new byte[16];
            SecureRandom.getInstanceStrong().nextBytes(var4);
            Files.write(var3, var4, new OpenOption[0]);
            var1.getPlugin().getLogger().log(Level.INFO, "Secret key " + var2 + " not found, generating...");
         } else {
            var4 = Files.readAllBytes(var3);
         }

         var1.getPlugin().getLogger().log(Level.WARNING, "Please configure VerusLink so it has access to this secret key (" + var2 + ").");
      } catch (NoSuchAlgorithmException | IOException var6) {
         var1.getPlugin().getLogger().log(Level.SEVERE, "Failed to find or generate secret key for bungeecord messaging. ", var6);
         return;
      }

      this.cipher = ThreadLocal.withInitial(() -> {
         Cipher var2 = null;

         try {
            var2 = Cipher.getInstance("AES");
            var2.init(1, new SecretKeySpec(var4, "AES"));
         } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException var4x) {
            var1.getPlugin().getLogger().log(Level.SEVERE, "Failed to initialize Cipher for bungeecord messaging. ", var4x);
         }

         return var2;
      });
   }

   public void handleBan(Player var1, String var2) {
      ByteArrayDataOutput var3 = ByteStreams.newDataOutput();
      var3.writeUTF("HandleBan");
      var3.writeUTF(var2);
      byte[] var4 = var3.toByteArray();
      var1.sendPluginMessage(this.plugin.getPlugin(), "BungeeCord", var4);

      byte[] var5;
      try {
         var5 = ((Cipher)this.cipher.get()).doFinal(var4);
      } catch (BadPaddingException | IllegalBlockSizeException var7) {
         this.plugin.getPlugin().getLogger().log(Level.SEVERE, "Failed to encrypt data for bungeecord messaging. ", var7);
         return;
      }

      ByteArrayDataOutput var6 = ByteStreams.newDataOutput();
      var6.writeUTF("verus");
      var6.writeInt(var5.length);
      var6.write(var5);
      var1.sendPluginMessage(this.plugin.getPlugin(), StorageEngine.getInstance().getVerusConfig().getMessagingChannel(), var6.toByteArray());
   }

   public static MessagingHandler getInstance() {
      MessagingHandler var10000;
      if (instance == null) {
         var10000 = instance = new MessagingHandler();
      } else {
         var10000 = instance;
      }

      return var10000;
   }

   private void registerBrandListener(Messenger var1, VerusLauncher var2) {
      try {
         String var10002;
         if (NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_13_R2)) {
            var10002 = "minecraft:brand";
         } else {
            var10002 = "MC|Brand";
         }

         var1.registerIncomingPluginChannel(var2, var10002, this.brandListener = new BrandListener());
      } catch (Throwable var4) {
         this.plugin.getPlugin().getLogger().log(Level.SEVERE, "Failed to register Brand listener", var4);
         return;
      }
   }

   private void unregisterChannels() {
      this.plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(this.plugin.getPlugin());
      this.plugin.getServer().getMessenger().unregisterIncomingPluginChannel(this.plugin.getPlugin());
   }
}
