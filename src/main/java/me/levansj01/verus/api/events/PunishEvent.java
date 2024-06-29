package me.levansj01.verus.api.events;

import java.util.Collection;
import java.util.List;
import me.levansj01.verus.api.check.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PunishEvent extends BanEvent {
   private static final HandlerList handlers = new HandlerList();
   private final List<String> commands;
   private boolean announce;

   public PunishEvent(Player player, Check check, boolean announce, List<String> commands) {
      super(player, check);
      this.announce = announce;
      this.commands = commands;
   }

   public boolean isAnnounce() {
      return this.announce;
   }

   public void setAnnounce(boolean announce) {
      this.announce = announce;
   }

   public List<String> getCommands() {
      return this.commands;
   }

   public void setCommands(Collection<String> commands) {
      this.commands.clear();
      this.commands.addAll(commands);
   }

   public void addCommand(String command) {
      this.commands.add(command);
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public HandlerList getHandlers() {
      return handlers;
   }
}
