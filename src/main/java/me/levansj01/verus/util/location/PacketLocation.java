package me.levansj01.verus.util.location;

public class PacketLocation extends BasicPacketLocation implements IPacketLocation {
   private final boolean ground;
   private final boolean look;
   private final boolean teleport;
   private final boolean pos;

   public PacketLocation(double var1, double var3, double var5, float var7, float var8, boolean var9, boolean var10, boolean var11, boolean var12) {
      super(var1, var3, var5, var7, var8);
      this.ground = var9;
      this.pos = var10;
      this.look = var11;
      this.teleport = var12;
   }

   public boolean isTeleport() {
      return this.teleport;
   }

   public boolean isGround() {
      return this.ground;
   }

   public boolean isLook() {
      return this.look;
   }

   public PacketLocation add(double var1, double var3, double var5) {
      return new PacketLocation(this.x + var1, this.y + var3, this.z + var5, this.yaw, this.pitch, this.ground, true, this.look, this.teleport);
   }

   public boolean isPos() {
      return this.pos;
   }

   public PacketLocation up() {
      return this.add(0.0D, 1.0D, 0.0D);
   }
}
