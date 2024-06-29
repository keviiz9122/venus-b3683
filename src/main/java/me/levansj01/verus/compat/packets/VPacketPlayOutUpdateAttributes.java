package me.levansj01.verus.compat.packets;

import java.util.ArrayList;
import java.util.List;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutUpdateAttributes extends VPacket {
   protected final List<VPacketPlayOutUpdateAttributes.Snapshot> snapshots = new ArrayList();
   protected int entityId;
   private static final int count = count();

   public List<VPacketPlayOutUpdateAttributes.Snapshot> getSnapshots() {
      return this.snapshots;
   }

   public int ordinal() {
      return count;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public static class Snapshot {
      private final List<VPacketPlayOutUpdateAttributes.Snapshot.Modifier> modifiers = new ArrayList();
      private final double baseValue;
      private final String name;

      public double getBaseValue() {
         return this.baseValue;
      }

      public Snapshot(String var1, double var2) {
         this.name = var1;
         this.baseValue = var2;
      }

      public String getName() {
         return this.name;
      }

      public List<VPacketPlayOutUpdateAttributes.Snapshot.Modifier> getModifiers() {
         return this.modifiers;
      }

      public static class Modifier {
         private final String name;
         private final double amount;
         private final int operation;

         public int getOperation() {
            return this.operation;
         }

         public Modifier(String var1, double var2, int var4) {
            this.name = var1;
            this.amount = var2;
            this.operation = var4;
         }

         public double getAmount() {
            return this.amount;
         }

         public String getName() {
            return this.name;
         }
      }
   }
}
