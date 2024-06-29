package me.levansj01.verus.storage.database.pool;

public enum ConnectionType {
   MYSQL("com.mysql.jdbc.Driver", "com.mysql.jdbc.jdbc2.optional.MysqlDataSource", "port", "mysql"),
   POSTGRESQL("org.postgresql.ds.PGSimpleDataSource", "org.postgresql.ds.PGSimpleDataSource", "portNumber", "postgresql");

   private final String className;
   private final String uri;
   private final String hikariCP;
   private final String port;

   public String getUri() {
      return this.uri;
   }

   public String getPort() {
      return this.port;
   }

   private ConnectionType(String var3, String var4, String var5, String var6) {
      this.className = var3;
      this.hikariCP = var4;
      this.port = var5;
      this.uri = var6;
   }

   public String getHikariCP() {
      return this.hikariCP;
   }

   public String getClassName() {
      return this.className;
   }
}
