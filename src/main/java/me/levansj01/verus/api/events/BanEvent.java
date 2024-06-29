package me.levansj01.verus.api.events;

import me.levansj01.verus.api.check.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** @deprecated */
@Deprecated
public class BanEvent extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private final Check check;
   private boolean cancelled;

   /** @deprecated */
   @Deprecated
   public BanEvent(Player player, String checkType, String checkSubType) {
      this(player, new Check(checkType, checkSubType, (String)null));
   }

   public BanEvent(Player player, Check check) {
      this.player = player;
      this.check = check;
   }

   public Player getPlayer() {
      return this.player;
   }

   public Check getCheck() {
      return this.check;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   /** @deprecated */
   @Deprecated
   public String getCheckType() {
      return this.check.getType();
   }

   /** @deprecated */
   @Deprecated
   public String getCheckSubType() {
      return this.check.getSubType();
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public HandlerList getHandlers() {
      return handlers;
   }
}
