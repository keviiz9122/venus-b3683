package me.levansj01.verus.data.transaction.teleport;

import java.util.Set;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.util.location.ILocationGround;

public class BasicTeleport implements ILocationGround {
   private final Set<VPacketPlayOutPosition.TeleportFlag> flags;
   private final double x;
   private final float yaw;
   private final double y;
   private final float pitch;
   private final double z;

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof BasicTeleport)) {
         return false;
      } else {
         BasicTeleport var2 = (BasicTeleport)var1;
         if (!var2.canEqual(this)) {
            return false;
         } else if (Double.compare(this.getX(), var2.getX()) != 0) {
            return false;
         } else if (Double.compare(this.getY(), var2.getY()) != 0) {
            return false;
         } else if (Double.compare(this.getZ(), var2.getZ()) != 0) {
            return false;
         } else if (Float.compare(this.getYaw(), var2.getYaw()) != 0) {
            return false;
         } else if (Float.compare(this.getPitch(), var2.getPitch()) != 0) {
            return false;
         } else {
            Set var3 = this.getFlags();
            Set var4 = var2.getFlags();
            if (var3 == null) {
               if (var4 != null) {

                  return false;
               }
            } else if (!var3.equals(var4)) {
               return false;
            }

            return true;
         }
      }
   }

   public String toString() {
      return "BasicTeleport(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", yaw=" + this.getYaw() + ", pitch=" + this.getPitch() + ", flags=" + this.getFlags() + ")";
   }

   public boolean matches(VPacketPlayInFlying<?> var1) {
      boolean var10000;
      if (!var1.isGround() && var1.isPos() && var1.isLook() && var1.getX() == this.x && var1.getY() == this.y && var1.getZ() == this.z && var1.getYaw() == this.yaw && var1.getPitch() == this.pitch) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public double getX() {
      return this.x;
   }

   public boolean isGround() {
      return false;
   }

   public double getZ() {
      return this.z;
   }

   public double getY() {
      return this.y;
   }

   public BasicTeleport(double var1, double var3, double var5, float var7, float var8, Set<VPacketPlayOutPosition.TeleportFlag> var9) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.yaw = var7;
      this.pitch = var8;
      this.flags = var9;
   }

   public Set<VPacketPlayOutPosition.TeleportFlag> getFlags() {
      return this.flags;
   }

   public float getPitch() {
      return this.pitch;
   }

   public int hashCode() {
      boolean var1 = true;
      byte var2 = 1;
      long var3 = Double.doubleToLongBits(this.getX());
      int var10 = var2 * 59 + (int)(var3 >>> 32 ^ var3);
      long var5 = Double.doubleToLongBits(this.getY());
      var10 = var10 * 59 + (int)(var5 >>> 32 ^ var5);
      long var7 = Double.doubleToLongBits(this.getZ());
      var10 = var10 * 59 + (int)(var7 >>> 32 ^ var7);
      var10 = var10 * 59 + Float.floatToIntBits(this.getYaw());
      var10 = var10 * 59 + Float.floatToIntBits(this.getPitch());
      Set var9 = this.getFlags();
      int var10000 = var10 * 59;
      int var10001;
      if (var9 == null) {
         var10001 = 43;
      } else {
         var10001 = var9.hashCode();
      }

      var10 = var10000 + var10001;
      return var10;
   }

   protected boolean canEqual(Object var1) {
      return var1 instanceof BasicTeleport;
   }

   public float getYaw() {
      return this.yaw;
   }
}
