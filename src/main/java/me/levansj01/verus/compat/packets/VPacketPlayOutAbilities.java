package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutAbilities extends VPacket {
   protected float flySpeed;
   private static final int count = count();
   protected boolean isInvulnerable;
   protected float walkSpeed;
   protected boolean canFly;
   protected boolean isFlying;
   protected boolean canInstantlyBuild;

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public boolean isFlying() {
      return this.isFlying;
   }

   public boolean isCanFly() {
      return this.canFly;
   }

   public float getFlySpeed() {
      return this.flySpeed;
   }

   public boolean isCanInstantlyBuild() {
      return this.canInstantlyBuild;
   }

   public float getWalkSpeed() {
      return this.walkSpeed;
   }

   public int ordinal() {
      return count;
   }

   public boolean isInvulnerable() {
      return this.isInvulnerable;
   }
}
