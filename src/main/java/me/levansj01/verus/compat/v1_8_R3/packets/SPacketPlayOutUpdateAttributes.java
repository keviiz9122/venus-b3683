package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import me.levansj01.verus.compat.packets.VPacketPlayOutUpdateAttributes;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateAttributes.AttributeSnapshot;

public class SPacketPlayOutUpdateAttributes extends VPacketPlayOutUpdateAttributes {
   private static final Field b_field = SafeReflection.access(PacketPlayOutUpdateAttributes.class, "b");
   private static final Field a_field = SafeReflection.access(PacketPlayOutUpdateAttributes.class, "a");

   public void accept(PacketPlayOutUpdateAttributes var1) {
      this.entityId = (Integer)SafeReflection.fetch(a_field, var1);
      List var2 = (List)SafeReflection.fetch(b_field, var1);
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         AttributeSnapshot var4 = (AttributeSnapshot)var3.next();
         VPacketPlayOutUpdateAttributes.Snapshot var5 = new VPacketPlayOutUpdateAttributes.Snapshot(var4.a(), var4.b());
         Collection var6 = var4.c();
         Iterator var7 = var6.iterator();

         while(var7.hasNext()) {
            AttributeModifier var8 = (AttributeModifier)var7.next();
            var5.getModifiers().add(new VPacketPlayOutUpdateAttributes.Snapshot.Modifier(var8.b(), var8.d(), var8.c()));
         }

         this.snapshots.add(var5);
      }

   }
}
