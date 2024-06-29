package me.levansj01.verus.gui.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.impl.CheckGUI;
import me.levansj01.verus.gui.impl.MainGUI;
import me.levansj01.verus.gui.impl.TypeGUI;

public class GUIManager {
   private static GUIManager instance;
   private final Map<CheckType, GUI> typeGuis = new HashMap();
   private MainGUI mainGui;
   private CheckGUI checkGui;

   public static GUIManager getInstance() {
      GUIManager var10000;
      if (instance == null) {
         var10000 = instance = new GUIManager();
      } else {
         var10000 = instance;
      }

      return var10000;
   }

   public void disable() {
      if (this.mainGui != null) {
         this.mainGui.clear();
      }

      if (this.checkGui != null) {
         this.checkGui.clear();
      }

      this.typeGuis.values().forEach(GUI::clear);
   }

   public CheckGUI getCheckGui() {
      return this.checkGui;
   }

   public GUI getTypeGui(CheckType var1) {
      return (GUI)this.typeGuis.get(var1);
   }

   public void enable(VerusPlugin var1) {
      Check[] var2 = var1.getTypeLoader().loadChecks();
      CheckType[] var3 = CheckType.values();
      int var4 = var3.length;
      int var5 = 0;

      do {
         if (var5 >= var4) {
            this.mainGui = new MainGUI();
            this.checkGui = new CheckGUI();
            return;
         }

         CheckType var6 = var3[var5];
         if (var6.ignore()) {
         } else {
            this.typeGuis.put(var6, new TypeGUI(var6, (List)Arrays.stream(var2).filter((var1x) -> {
               boolean var10000;
               if (var1x.getType() == var6) {
                  var10000 = true;
               } else {
                  var10000 = false;
               }

               return var10000;
            }).collect(Collectors.toList())));
         }

         ++var5;
      } while (true);
   }

   public MainGUI getMainGui() {
      return this.mainGui;
   }

   public Map<CheckType, GUI> getTypeGuis() {
      return this.typeGuis;
   }
}
