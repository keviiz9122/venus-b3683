package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class SPacketPlayInFlying extends VPacketPlayInFlying {
   public void accept(PacketPlayInFlying var1) {
      this.x = var1.a();
      this.y = var1.b();
      this.z = var1.c();
      this.yaw = var1.d();
      this.pitch = var1.e();
      this.ground = var1.f();
      this.pos = var1.g();
      this.look = var1.h();
   }
}
