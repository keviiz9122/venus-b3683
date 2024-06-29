package me.levansj01.verus.data.transaction.velocity;

import java.util.Queue;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityVelocity;
import me.levansj01.verus.compat.packets.VPacketPlayOutExplosion;
import me.levansj01.verus.data.CheckLocalQueue;

public interface IVelocityHandler {
   int getTicks();

   void handle(VPacketPlayOutEntityVelocity<?> var1);

   boolean possibleVelocity();

   Queue<BasicVelocity> getQueue();

   CheckLocalQueue<TickVelocity> getTickData();

   void handle(VPacketPlayOutExplosion<?> var1);
}
