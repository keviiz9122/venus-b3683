package me.levansj01.verus.compat;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketTracker;
import java.lang.reflect.Field;
import java.util.UUID;
import me.levansj01.verus.util.java.SafeReflection;

public class ViaHandler {
   private final Field PacketTracker_intervalPackets = SafeReflection.access(PacketTracker.class, "intervalPackets");
   private final Field PacketTracker_receivedPackets = SafeReflection.access(PacketTracker.class, "receivedPackets");

   public ViaHandler.PlayerHandler get(UUID var1) {
      return new ViaHandler.PlayerHandler(var1, null);
   }

   public class PlayerHandler {
      private UserConnection connection;
      private final UUID uuid;

      private PlayerHandler(UUID var2) {
         this.uuid = var2;
      }

      public void decrease() {
         if (this.connection == null) {
            this.connection = Via.getAPI().getConnection(this.uuid);
         }

         if (this.connection != null) {
            PacketTracker var1 = this.connection.getPacketTracker();
            long var2 = (Long)SafeReflection.fetch(ViaHandler.this.PacketTracker_receivedPackets, var1);
            SafeReflection.set(ViaHandler.this.PacketTracker_receivedPackets, var1, var2 - 1L);
            long var4 = (Long)SafeReflection.fetch(ViaHandler.this.PacketTracker_intervalPackets, var1);
            SafeReflection.set(ViaHandler.this.PacketTracker_intervalPackets, var1, var4 - 1L);
         }
      }
   }
}
