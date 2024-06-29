package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;

public class SPacketPlayInUseEntity extends VPacketPlayInUseEntity {
   private boolean fetchedEntity;
   private static final Field id_field = SafeReflection.access(PacketPlayInUseEntity.class, "a", "id");

   public void accept(PacketPlayInUseEntity var1) {
      this.id = (Integer)SafeReflection.fetch(id_field, var1);
      this.action = VPacketPlayInUseEntity.EntityUseAction.values()[var1.a().ordinal()];
      if (this.action == VPacketPlayInUseEntity.EntityUseAction.INTERACT_AT) {
         this.bodyX = var1.b().a;
         this.bodyY = var1.b().b;
         this.bodyZ = var1.b().c;
      } else {
         this.bodyX = this.bodyY = this.bodyZ = 0.0D;
      }

      this.fetchedEntity = false;
   }

   public Entity getEntity(World var1) {
      if (!this.fetchedEntity) {
         net.minecraft.server.v1_8_R3.Entity var2 = ((CraftWorld)var1).getHandle().a(this.id);
         if (var2 == null) {
            this.entity = new WeakReference((Object)null);
         } else {
            this.entity = new WeakReference(var2.getBukkitEntity());
         }

         this.fetchedEntity = true;
      }

      return this.entity == null ? null : (Entity)this.entity.get();
   }
}
