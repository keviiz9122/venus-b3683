package me.levansj01.verus.storage.database.check;

import java.util.ArrayList;
import java.util.List;
import me.levansj01.verus.check.Check;

public class CheckValues {
   private boolean punish;
   private List<String> commands;
   private int maxViolation;
   private final String checkId;
   private boolean alert;

   public CheckValues(String var1) {
      this.punish = true;
      this.alert = true;
      this.maxViolation = Integer.MAX_VALUE;
      this.commands = new ArrayList();
      this.checkId = var1;
   }

   public void setCommands(List<String> var1) {
      this.commands = var1;
   }

   public boolean isPunish() {
      return this.punish;
   }

   public boolean hasCommand(String var1) {
      return this.commands.contains(var1);
   }

   public void setMaxViolation(int var1) {
      this.maxViolation = var1;
   }

   public void setPunish(boolean var1) {
      this.punish = var1;
   }

   public CheckValues(Check var1) {
      this(var1.identifier());
      this.maxViolation = var1.getMaxViolation();
   }

   public String getCheckId() {
      return this.checkId;
   }

   public void removeCommand(int var1) {
      this.commands.remove(var1);
   }

   public boolean isAlert() {
      return this.alert;
   }

   public void setAlert(boolean var1) {
      this.alert = var1;
   }

   public boolean hasCommands() {
      boolean var10000;
      if (!this.commands.isEmpty()) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public List<String> getCommands() {
      return this.commands;
   }

   public String getCommand(int var1) {
      return (String)this.commands.get(var1);
   }

   public int getMaxViolation() {
      return this.maxViolation;
   }

   public void addCommand(String var1) {
      this.commands.add(var1);
   }

   public int getCommandsSize() {
      return this.commands.size();
   }
}
