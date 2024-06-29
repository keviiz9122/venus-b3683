package me.levansj01.verus.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerInitEvent extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private boolean cancelled;

   public PlayerInitEvent(Player player) {
      super(true);
      this.player = player;
   }

   public Player getPlayer() {
      return this.player;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public HandlerList getHandlers() {
      return handlers;
   }
}
