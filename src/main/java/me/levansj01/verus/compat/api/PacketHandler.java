package me.levansj01.verus.compat.api;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VClientboundPingPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInChat;
import me.levansj01.verus.compat.packets.VPacketPlayInClientCommand;
import me.levansj01.verus.compat.packets.VPacketPlayInCloseWindow;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import me.levansj01.verus.compat.packets.VPacketPlayInEntityAction;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import me.levansj01.verus.compat.packets.VPacketPlayInSetCreativeSlot;
import me.levansj01.verus.compat.packets.VPacketPlayInSteerVehicle;
import me.levansj01.verus.compat.packets.VPacketPlayInTeleportAccept;
import me.levansj01.verus.compat.packets.VPacketPlayInTransaction;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.compat.packets.VPacketPlayInUseItem;
import me.levansj01.verus.compat.packets.VPacketPlayInVehicleMove;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.compat.packets.VPacketPlayOutAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayOutAttachEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutBlockChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityEffect;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityMetadata;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityTeleport;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityVelocity;
import me.levansj01.verus.compat.packets.VPacketPlayOutExplosion;
import me.levansj01.verus.compat.packets.VPacketPlayOutGameStateChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutKeepAlive;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunk;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunkBulk;
import me.levansj01.verus.compat.packets.VPacketPlayOutMultiBlockChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.compat.packets.VPacketPlayOutOpenWindow;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.compat.packets.VPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.compat.packets.VPacketPlayOutRespawn;
import me.levansj01.verus.compat.packets.VPacketPlayOutSetSlot;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntityLiving;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnPosition;
import me.levansj01.verus.compat.packets.VPacketPlayOutTransaction;
import me.levansj01.verus.compat.packets.VPacketPlayOutUnloadChunk;
import me.levansj01.verus.compat.packets.VPacketPlayOutUpdateAttributes;
import me.levansj01.verus.compat.packets.VServerboundPongPacket;
import me.levansj01.verus.util.java.SafeReflection;

public interface PacketHandler {
   default void handle(VPacketPlayOutPosition<?> var1) {
   }

   default void handle(VPacketPlayOutUnloadChunk<?> var1) {
   }

   default void handle(VPacketPlayInAbilities<?> var1) {
   }

   default int[] parse() {
      LinkedList var1 = new LinkedList();
      Method[] var2 = this.getClass().getDeclaredMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method var5 = var2[var4];
         if (var5.getParameterCount() == 1) {
            Class var6 = var5.getParameterTypes()[0];
            if (var6 == Transactionable.class) {
               String var7 = var5.getName();
               byte var8 = -1;
               switch(var7.hashCode()) {
               case 2067821:
                  if (var7.equals("handleIn")) {
                     var8 = 0;
                  }
                  break;
               case 64108550:
                  if (var7.equals("handleOut")) {
                     var8 = 1;
                  }
               }

               switch(var8) {
               case 0:
                  var1.add(VPacketPlayInTransaction.class);
                  var1.add(VServerboundPongPacket.class);
                  break;
               case 1:
                  var1.add(VPacketPlayOutTransaction.class);
                  var1.add(VClientboundPingPacket.class);
               }
            } else if (VPacket.class.isAssignableFrom(var6) && var5.getName().equals("handle")) {
               var1.add(var6);
            }
         }
      }

      int[] var9 = new int[var1.size()];
      var3 = 0;

      Class var11;
      for(Iterator var10 = var1.iterator(); var10.hasNext(); var9[var3++] = (Integer)SafeReflection.getLocalField((Class)var11, (Object)null, (String[])("count"))) {
         var11 = (Class)var10.next();
      }

      return var9;
   }

   default void handle(VPacketPlayOutOpenWindow<?> var1) {
   }

   default void handle(VPacketPlayOutGameStateChange<?> var1) {
   }

   default void handle(VPacketPlayInEntityAction<?> var1) {
   }

   default void handle(VPacketPlayInClientCommand<?> var1) {
   }

   default void handle(VPacketPlayInCustomPayload<?> var1) {
   }

   default void handle(VPacketPlayOutSpawnPosition<?> var1) {
   }

   default void handle(VPacketPlayOutEntityVelocity<?> var1) {
   }

   default void handle(VPacketPlayInBlockDig<?> var1) {
   }

   default void handle(VPacketPlayOutUpdateAttributes<?> var1) {
   }

   default void handle(VPacketPlayOutAttachEntity<?> var1) {
   }

   default void handle(VPacketPlayOutMapChunk<?> var1) {
   }

   default void handle(VPacketPlayOutAbilities<?> var1) {
   }

   default void handle(VPacketPlayInTeleportAccept<?> var1) {
   }

   default void handle(VPacketPlayOutExplosion<?> var1) {
   }

   default void handle(VPacketPlayOutEntityTeleport<?> var1) {
   }

   default void handle(VPacketPlayInHeldItemSlot<?> var1) {
   }

   default void handle(VPacketPlayInArmAnimation<?> var1) {
   }

   default void handle(VPacketPlayInUseEntity<?> var1) {
   }

   default void handle(VPacketPlayInCloseWindow<?> var1) {
   }

   default void handle(VPacketPlayOutKeepAlive<?> var1) {
   }

   default void handle(VPacketPlayOutNamedEntitySpawn<?> var1) {
   }

   default void handle(VPacketPlayInVehicleMove<?> var1) {
   }

   default void handleOut(Transactionable var1) {
   }

   default void handle(VPacketPlayOutBlockChange<?> var1) {
   }

   default void handle(VPacketPlayOutEntityMetadata<?> var1) {
   }

   default void handle(VPacketPlayInKeepAlive<?> var1) {
   }

   default void handle(VPacketPlayInChat<?> var1) {
   }

   default void handle(VPacketPlayOutMultiBlockChange<?> var1) {
   }

   default void handle(VPacketPlayOutMapChunkBulk<?> var1) {
   }

   default void handle(VPacketPlayOutRespawn<?> var1) {
   }

   default void handle(VPacketPlayOutEntityDestroy<?> var1) {
   }

   default void handle(VPacketPlayInBlockPlace<?> var1) {
   }

   default void handle(VPacketPlayOutEntity<?> var1) {
   }

   default void handleIn(Transactionable var1) {
   }

   default void handle(VPacketPlayInFlying<?> var1) {
   }

   default void handle(VPacketPlayInSetCreativeSlot<?> var1) {
   }

   default void handle(VPacketPlayOutSpawnEntity<?> var1) {
   }

   default void handle(VPacketPlayOutRemoveEntityEffect<?> var1) {
   }

   default void handle(VPacketPlayOutSetSlot<?> var1) {
   }

   default void handle(VPacketPlayOutEntityEffect<?> var1) {
   }

   default void handle(VPacketPlayInWindowClick<?> var1) {
   }

   default void handle(VPacketPlayInSteerVehicle<?> var1) {
   }

   default void handle(VPacketPlayOutSpawnEntityLiving<?> var1) {
   }

   default void handle(VPacketPlayInUseItem<?> var1) {
   }
}
