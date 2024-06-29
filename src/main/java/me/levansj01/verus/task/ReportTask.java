package me.levansj01.verus.task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.type.Loader;
import me.levansj01.verus.type.VerusType;
import me.levansj01.verus.type.VerusTypeLoader;
import org.bukkit.Bukkit;

public class ReportTask extends Thread {
   private boolean running = true;
   private final SocketAddress socketAddress;

   public ReportTask() {
      super("Verus Report Thread");

      try {
         InetAddress var10003 = InetAddress.getByName("session.verusdev.xyz");
         short var10004;
         if (Loader.getUsername().equals("Test")) {
            var10004 = 10312;
         } else {
            var10004 = 10311;
         }

         this.socketAddress = new InetSocketAddress(var10003, var10004);
      } catch (Throwable var2) {
         throw new IllegalArgumentException("Failed to connect to Verus server ", var2);
      }
   }

   public void run() {
      try {
         Thread.sleep(TimeUnit.SECONDS.toMillis(30L));
      } catch (InterruptedException var9) {
         Thread.currentThread().interrupt();
         return;
      }

      do {
         if (!this.running) {
            return;
         }

         label87: {
            try {
               Socket var1 = new Socket();

               try {
                  var1.connect(this.socketAddress);
                  DataOutputStream var2 = new DataOutputStream(var1.getOutputStream());
                  var2.writeByte(3);
                  var2.writeInt(Bukkit.getServer().getPort());
                  var2.writeInt(Bukkit.getOnlinePlayers().size());
                  StorageEngine var3 = StorageEngine.getInstance();
                  boolean var10000;
                  if (this.running && var3.isConnected()) {
                     var10000 = true;
                  } else {
                     var10000 = false;
                  }

                  boolean var4 = var10000;
                  var2.writeBoolean(var4);
                  if (var4) {
                     VerusConfiguration var5 = var3.getVerusConfig();
                     var2.writeUTF(var5.getMongoHost());
                     var2.writeInt(var5.getMongoPort());
                     var2.writeUTF(var5.getMongoDatabase());
                     var2.writeInt(Math.toIntExact((long)var3.getDatabase().getTotalBans()));
                     var2.writeInt(Math.toIntExact((long)var3.getDatabase().getTotalLogs()));
                  }

                  var2.writeBoolean(true);
                  var2.writeInt(2);
                  var2.writeInt((int)VerusPlugin.getBuild());
                  DataInputStream var13 = new DataInputStream(var1.getInputStream());
                  int var6 = var13.readInt();
                  if (var6 <= 2) {
                     if (var13.read() == 1) {
                        var10000 = true;
                     } else {
                        var10000 = false;
                     }

                     boolean var7 = var10000;
                     if (var7) {
                        NMSManager.getInstance().postToMainThread(VerusPlugin::restart);
                     }
                  }
               } catch (Throwable var11) {
                  try {
                     var1.close();
                  } catch (Throwable var10) {
                     var11.addSuppressed(var10);
                     throw var11;
                  }

                  throw var11;
               }

               var1.close();
            } catch (Throwable var12) {
               if (VerusTypeLoader.getVerusType() == VerusType.DEV) {
                  var12.printStackTrace();
               }
               break label87;
            }
         }

         try {
            Thread.sleep(TimeUnit.MINUTES.toMillis(3L));
         } catch (InterruptedException var8) {
            Thread.currentThread().interrupt();
            return;
         }
      } while (true);
   }

   public void end() {
      this.running = false;
      this.interrupt();
   }

   public boolean isRunning() {
      return this.running;
   }

   public SocketAddress getSocketAddress() {
      return this.socketAddress;
   }

   public void setRunning(boolean var1) {
      this.running = var1;
   }
}
