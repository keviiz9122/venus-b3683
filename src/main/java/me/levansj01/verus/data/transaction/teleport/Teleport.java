package me.levansj01.verus.data.transaction.teleport;

import java.util.Set;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;

public class Teleport extends BasicTeleport {
   private final long time;
   private final int ticks;

   public long getTime() {
      return this.time;
   }

   public int getTicks() {
      return this.ticks;
   }

   public Teleport(double var1, double var3, double var5, float var7, float var8, Set<VPacketPlayOutPosition.TeleportFlag> var9, int var10, long var11) {
      super(var1, var3, var5, var7, var8, var9);
      this.ticks = var10;
      this.time = var11;
   }
}
