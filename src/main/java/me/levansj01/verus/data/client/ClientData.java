package me.levansj01.verus.data.client;

public class ClientData {
   private ClientType type;
   private String brand;

   public void setType(ClientType var1) {
      this.type = var1;
   }

   public String getBrand() {
      return this.brand;
   }

   public void setBrand(String var1) {
      this.brand = var1;
   }

   public ClientType getType() {
      return this.type;
   }

   public ClientData(String var1, ClientType var2) {
      this.brand = var1;
      this.type = var2;
   }
}
