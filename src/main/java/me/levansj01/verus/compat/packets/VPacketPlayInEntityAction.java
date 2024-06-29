package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInEntityAction extends VPacket {
   private static final int count = count();
   protected int value;
   protected VPacketPlayInEntityAction.PlayerAction action;

   public VPacketPlayInEntityAction.PlayerAction getAction() {
      return this.action;
   }

   public int ordinal() {
      return count;
   }

   public int getValue() {
      return this.value;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public static enum PlayerAction {
      START_SNEAKING(VPacketPlayInEntityAction.PlayerAction.Type.SNEAK, true),
      START_SPRINTING(VPacketPlayInEntityAction.PlayerAction.Type.SPRINT, true),
      START_FALL_FLYING(true),
      STOP_SPRINTING(VPacketPlayInEntityAction.PlayerAction.Type.SPRINT, false),
      OPEN_INVENTORY(true),
      STOP_SLEEPING(false),
      STOP_SNEAKING(VPacketPlayInEntityAction.PlayerAction.Type.SNEAK, false),
      RIDING_JUMP(true);

      private final boolean value;
      private final VPacketPlayInEntityAction.PlayerAction.Type type;

      public VPacketPlayInEntityAction.PlayerAction.Type getType() {
         return this.type;
      }
      
      public boolean isSneak() {
         return this.type == VPacketPlayInEntityAction.PlayerAction.Type.SNEAK;
      }

      private PlayerAction(VPacketPlayInEntityAction.PlayerAction.Type var3, boolean var4) {
         this.type = var3;
         this.value = var4;
      }

      public boolean isValue() {
         return this.value;
      }

      public boolean isSprint() {
         return this.type == VPacketPlayInEntityAction.PlayerAction.Type.SPRINT;
      }

      private PlayerAction(boolean var3) {
         this(VPacketPlayInEntityAction.PlayerAction.Type.OTHER, var3);
      }

      public static enum Type {
         SPRINT,
         OTHER,
         SNEAK;
      }
   }
}
