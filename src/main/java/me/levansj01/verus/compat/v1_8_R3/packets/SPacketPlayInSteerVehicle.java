package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInSteerVehicle;
import net.minecraft.server.v1_8_R3.PacketPlayInSteerVehicle;

public class SPacketPlayInSteerVehicle extends VPacketPlayInSteerVehicle {
   public void accept(PacketPlayInSteerVehicle var1) {
      this.forward = (double)var1.a();
      this.strafe = (double)var1.b();
   }
}
