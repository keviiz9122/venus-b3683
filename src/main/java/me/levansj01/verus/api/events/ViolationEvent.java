package me.levansj01.verus.api.events;

import me.levansj01.verus.api.check.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ViolationEvent extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private final Check check;
   private final int violations;
   private boolean cancelled;

   /** @deprecated */
   @Deprecated
   public ViolationEvent(Player player, String checkType, String checkSubType, int violations) {
      this(player, new Check(checkType, checkSubType, (String)null), violations);
   }

   public ViolationEvent(Player player, Check check, int violations) {
      super(true);
      this.player = player;
      this.check = check;
      this.violations = violations;
   }

   public Player getPlayer() {
      return this.player;
   }

   public Check getCheck() {
      return this.check;
   }

   public int getViolations() {
      return this.violations;
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
   public String getSubType() {
      return this.check.getSubType();
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public HandlerList getHandlers() {
      return handlers;
   }
}
