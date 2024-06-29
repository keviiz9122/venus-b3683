package me.levansj01.verus.storage;

import java.util.logging.Logger;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.storage.database.DatabaseType;

public class StorageEngine {
   private VerusConfiguration verusConfig;
   private static StorageEngine engine;
   private DatabaseType type;
   private Database database;

   public DatabaseType getType() {
      return this.type;
   }

   public VerusConfiguration getVerusConfig() {
      return this.verusConfig;
   }

   public void startConfig() {
      this.verusConfig = new VerusConfiguration();
      this.verusConfig.enable();
   }

   public static StorageEngine getInstance() {
      StorageEngine var10000;
      if (engine == null) {
         var10000 = engine = new StorageEngine();
      } else {
         var10000 = engine;
      }

      return var10000;
   }

   public void stopConfig() {
      if (this.verusConfig != null) {
         this.verusConfig.disable();
      }

   }

   public boolean isConnected() {
      boolean var10000;
      if (this.database != null && this.database.isConnected()) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public void stop() {
      if (this.database != null) {
         this.database.stop();
      }

   }

   public void start() {
      if (this.verusConfig.isMongoEnabled()) {
         this.type = DatabaseType.MONGO;
      } else if (this.verusConfig.isSqlEnabled()) {
         this.type = DatabaseType.SQL;
      }

      if (this.type == null) {
         Logger var1 = VerusLauncher.getPlugin().getLogger();
         var1.warning("No storage method found, please enable database storage in the configuration to access important features");
      } else {
         this.database = this.type.create();
         VerusLauncher.getPlugin().getLogger().info("Using storage method " + this.type.name() + " handled by " + this.database.getClass().getName());
         this.database.start();
         this.database.connect(this.verusConfig);
      }

   }

   public Database getDatabase() {
      return this.database;
   }
}
