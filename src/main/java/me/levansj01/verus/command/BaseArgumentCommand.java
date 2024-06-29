package me.levansj01.verus.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import me.levansj01.verus.VerusPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class BaseArgumentCommand extends BaseCommand {
   protected Map<String, BaseArgumentCommand.CommandArgument> arguments = new ConcurrentHashMap();

   public BaseArgumentCommand(String var1, String var2, String var3, List<String> var4) {
      super(var1, var2, var3, var4);
   }

   public BaseArgumentCommand(String var1) {
      super(var1);
   }

   public void execute(CommandSender var1, String[] var2) {
      if (var2.length == 0) {
         this.sendHelp(var1);
      } else {
         BaseArgumentCommand.CommandArgument var3 = (BaseArgumentCommand.CommandArgument)this.arguments.get(var2[0]);
         if (var3 == null) {
            this.sendHelp(var1);
         } else {
            var3.run(var1, var2);
         }
      }
   }

   protected void addArgument(BaseArgumentCommand.CommandArgument var1) {
      this.arguments.put(var1.argument, var1);
   }

   protected void sendHelp(CommandSender var1) {
      String var2 = VerusPlugin.COLOR + "Help Command for /" + this.getName() + ":\n";
      Iterator var3 = this.arguments.values().iterator();

      do {
         if (!var3.hasNext()) {
            var1.sendMessage(var2);
            return;
         }

         BaseArgumentCommand.CommandArgument var4 = (BaseArgumentCommand.CommandArgument)var3.next();
         var2 = var2 + String.format(ChatColor.GRAY + "- " + VerusPlugin.COLOR + "/%s %s %s: %s\n", this.getName(), var4.argument, var4.usage, ChatColor.WHITE + var4.description);
      } while (true);
   }

   public class CommandArgument {
      private final BiConsumer<CommandSender, String[]> consumer;
      protected final String argument;
      private String usage = "";
      protected final String description;

      public CommandArgument(String var2, String var3, BiConsumer<CommandSender, String[]> var4) {
         this.argument = var2;
         this.description = var3;
         this.consumer = var4;
      }

      public CommandArgument(String var2, String var3, String var4, BiConsumer<CommandSender, String[]> var5) {
         this.argument = var2;
         this.description = var3;
         this.usage = var4;
         this.consumer = var5;
      }

      public void run(CommandSender var1, String[] var2) {
         if (this.consumer != null) {
            this.consumer.accept(var1, var2);
         }

      }
   }
}
