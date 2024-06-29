package me.levansj01.verus.compat.packets;

import java.lang.ref.WeakReference;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public abstract class VPacketPlayInUseEntity extends VPacket {
   protected WeakReference<Entity> entity;
   protected double bodyX;
   protected double bodyY;
   protected VPacketPlayInUseEntity.EntityUseAction action;
   private static final int count = count();
   protected double bodyZ;
   protected int id;

   public abstract Entity getEntity(World var1);

   public double getBodyX() {
      return this.bodyX;
   }

   public int ordinal() {
      return count;
   }

   public VPacketPlayInUseEntity.EntityUseAction getAction() {
      return this.action;
   }

   public int getId() {
      return this.id;
   }

   public PlayerData getPlayerData() {
      DataManager var1 = DataManager.getInstance();
      return var1 == null ? null : var1.getPlayer(this.id);
   }

   public double getBodyZ() {
      return this.bodyZ;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public boolean isPlayer() {
      DataManager var1 = DataManager.getInstance();
      return var1 == null ? false : var1.isPlayer(this.id);
   }

   public double getBodyY() {
      return this.bodyY;
   }

   public static enum EntityUseAction {
      INTERACT,
      INTERACT_AT,
      ATTACK;
      
      public boolean isInteract() {
         return this == INTERACT;
      }

      public boolean isInteractAt() {
         return this == INTERACT_AT;
      }

      public boolean isAttack() {
         return this == ATTACK;
      }
   }
}
