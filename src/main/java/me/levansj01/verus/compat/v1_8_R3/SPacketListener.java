package me.levansj01.verus.compat.v1_8_R3;

import java.lang.reflect.Method;
import me.levansj01.verus.compat.api.VPacketListener;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInAbilities;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInArmAnimation;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInBlockDig;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInBlockPlace;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInClientCommand;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInCloseWindow;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInCustomPayload;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInEntityAction;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInFlying;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInHeldItemSlot;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInKeepAlive;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInSetCreativeSlot;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInSteerVehicle;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInTransaction;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInUseEntity;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayInWindowClick;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutAbilities;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutAttachEntity;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutBlockChange;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutEntity;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutEntityEffect;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutEntityTeleport;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutEntityVelocity;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutExplosion;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutGameStateChange;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutKeepAlive;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutMapChunk;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutMapChunkBulk;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutMultiBlockChange;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutOpenWindow;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutPosition;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutRespawn;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutSetSlot;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutSpawnEntity;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutSpawnEntityLiving;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutSpawnPosition;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutTransaction;
import me.levansj01.verus.compat.v1_8_R3.packets.SPacketPlayOutUpdateAttributes;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketHandshakingInListener;
import net.minecraft.server.v1_8_R3.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_8_R3.PacketLoginInListener;
import net.minecraft.server.v1_8_R3.PacketLoginInStart;
import net.minecraft.server.v1_8_R3.PacketLoginOutDisconnect;
import net.minecraft.server.v1_8_R3.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_8_R3.PacketLoginOutListener;
import net.minecraft.server.v1_8_R3.PacketLoginOutSetCompression;
import net.minecraft.server.v1_8_R3.PacketLoginOutSuccess;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayInEnchantItem;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_8_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import net.minecraft.server.v1_8_R3.PacketPlayInSpectate;
import net.minecraft.server.v1_8_R3.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_8_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayInTransaction;
import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;
import net.minecraft.server.v1_8_R3.PacketPlayOutAbilities;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutBed;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayOutCollect;
import net.minecraft.server.v1_8_R3.PacketPlayOutCombatEvent;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_8_R3.PacketPlayOutExperience;
import net.minecraft.server.v1_8_R3.PacketPlayOutExplosion;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_8_R3.PacketPlayOutLogin;
import net.minecraft.server.v1_8_R3.PacketPlayOutMap;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunkBulk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutRemoveEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutResourcePackSend;
import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.PacketPlayOutServerDifficulty;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetCompression;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityWeather;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutStatistic;
import net.minecraft.server.v1_8_R3.PacketPlayOutTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTransaction;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateEntityNBT;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateSign;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateTime;
import net.minecraft.server.v1_8_R3.PacketPlayOutWindowData;
import net.minecraft.server.v1_8_R3.PacketPlayOutWindowItems;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PacketStatusInListener;
import net.minecraft.server.v1_8_R3.PacketStatusInPing;
import net.minecraft.server.v1_8_R3.PacketStatusInStart;
import net.minecraft.server.v1_8_R3.PacketStatusOutListener;
import net.minecraft.server.v1_8_R3.PacketStatusOutPong;
import net.minecraft.server.v1_8_R3.PacketStatusOutServerInfo;

public class SPacketListener extends VPacketListener implements PacketListenerPlayIn, PacketListenerPlayOut, PacketStatusInListener, PacketStatusOutListener, PacketLoginInListener, PacketLoginOutListener, PacketHandshakingInListener {
   private static final Method method = SafeReflection.access(new String[]{"net.minecraft.server.v1_8_R3.Packet"}, "a", PacketListener.class);

   public void a(PacketPlayInKeepAlive var1) {
      this.handleIn(var1, new SPacketPlayInKeepAlive());
   }

   public void a(PacketPlayOutOpenWindow var1) {
      this.handleOut(var1, new SPacketPlayOutOpenWindow());
   }

   public void a(PacketStatusInPing var1) {
   }

   public void a(PacketPlayOutScoreboardTeam var1) {
   }

   public void a(PacketPlayInSpectate var1) {
   }

   public void a(PacketPlayInEntityAction var1) {
      this.handleIn(var1, new SPacketPlayInEntityAction());
   }

   public <T extends PacketListener> void processTyped(Packet<T> var1) {
      var1.a(this);
   }

   public void a(PacketStatusInStart var1) {
   }

   public void a(PacketPlayOutSpawnPosition var1) {
      this.handleOut(var1, new SPacketPlayOutSpawnPosition());
   }

   public void a(PacketPlayOutMap var1) {
   }

   public void a(PacketPlayOutSpawnEntityLiving var1) {
      this.handleOut(var1, new SPacketPlayOutSpawnEntityLiving());
   }

   public void a(PacketPlayOutWorldParticles var1) {
   }

   public void a(IChatBaseComponent var1) {
   }

   public void a(PacketPlayInChat var1) {
   }

   public void a(PacketPlayOutExperience var1) {
   }

   public void a(PacketPlayOutAttachEntity var1) {
      this.handleOut(var1, new SPacketPlayOutAttachEntity());
   }

   public void a(PacketPlayInAbilities var1) {
      this.handleIn(var1, new SPacketPlayInAbilities());
   }

   public void a(PacketLoginInStart var1) {
   }

   public void a(PacketPlayOutMapChunkBulk var1) {
      this.handleOut(var1, new SPacketPlayOutMapChunkBulk());
   }

   public void a(PacketStatusOutServerInfo var1) {
   }

   public void a(PacketPlayOutCustomPayload var1) {
   }

   public void a(PacketPlayOutResourcePackSend var1) {
   }

   public void a(PacketPlayOutNamedEntitySpawn var1) {
      this.handleOut(var1, new SPacketPlayOutNamedEntitySpawn());
   }

   public void a(PacketPlayOutCombatEvent var1) {
   }

   public void a(PacketPlayOutStatistic var1) {
   }

   public void a(PacketPlayOutEntity var1) {
      this.handleOut(var1, new SPacketPlayOutEntity());
   }

   public void a(PacketPlayOutUpdateAttributes var1) {
      this.handleOut(var1, new SPacketPlayOutUpdateAttributes());
   }

   public void a(PacketPlayOutUpdateTime var1) {
   }

   public void a(PacketPlayOutPlayerListHeaderFooter var1) {
   }

   public void a(PacketLoginOutSuccess var1) {
   }

   public void a(PacketPlayOutWorldBorder var1) {
   }

   public void a(PacketPlayOutCollect var1) {
   }

   public void a(PacketLoginOutEncryptionBegin var1) {
   }

   public void a(PacketPlayInClientCommand var1) {
      this.handleIn(var1, new SPacketPlayInClientCommand());
   }

   public void a(PacketPlayOutWindowData var1) {
   }

   public void a(PacketPlayInEnchantItem var1) {
   }

   public void a(PacketPlayOutServerDifficulty var1) {
   }

   public void a(PacketPlayOutBed var1) {
   }

   public void a(PacketPlayOutScoreboardDisplayObjective var1) {
   }

   public void a(PacketLoginInEncryptionBegin var1) {
   }

   public void a(PacketPlayOutKickDisconnect var1) {
   }

   public void a(PacketPlayOutSetSlot var1) {
      this.handleOut(var1, new SPacketPlayOutSetSlot());
   }

   public void a(PacketPlayOutAbilities var1) {
      this.handleOut(var1, new SPacketPlayOutAbilities());
   }

   public void a(PacketPlayInTabComplete var1) {
   }

   public void a(PacketPlayOutAnimation var1) {
   }

   public void a(PacketPlayOutBlockChange var1) {
      this.handleOut(var1, new SPacketPlayOutBlockChange());
   }

   public void a(PacketStatusOutPong var1) {
   }

   public void a(PacketPlayInUseEntity var1) {
      this.handleIn(var1, new SPacketPlayInUseEntity());
   }

   public void a(PacketPlayOutEntityHeadRotation var1) {
   }

   public void process(Object var1) {
      try {
         this.processTyped((Packet)var1);
      } catch (IncompatibleClassChangeError var3) {
         SafeReflection.execute(method, var1, this);
      }

   }

   public void a(PacketLoginOutDisconnect var1) {
   }

   public void a(PacketPlayOutScoreboardObjective var1) {
   }

   public void a(PacketHandshakingInSetProtocol var1) {
   }

   public void a(PacketPlayOutEntityVelocity var1) {
      this.handleOut(var1, new SPacketPlayOutEntityVelocity());
   }

   public void a(PacketPlayInCloseWindow var1) {
      this.handleIn(var1, new SPacketPlayInCloseWindow());
   }

   public void a(PacketPlayOutSpawnEntityPainting var1) {
   }

   public void a(PacketPlayOutUpdateSign var1) {
   }

   public void a(PacketPlayOutGameStateChange var1) {
      this.handleOut(var1, new SPacketPlayOutGameStateChange());
   }

   public void a(PacketPlayInHeldItemSlot var1) {
      this.handleIn(var1, new SPacketPlayInHeldItemSlot());
   }

   public void a(PacketPlayOutTileEntityData var1) {
   }

   public void a(PacketPlayInBlockDig var1) {
      this.handleIn(var1, new SPacketPlayInBlockDig());
   }

   public void a(PacketPlayInCustomPayload var1) {
      this.handleIn(var1, new SPacketPlayInCustomPayload());
   }

   public void a(PacketPlayOutUpdateHealth var1) {
   }

   public void a(PacketPlayInFlying var1) {
      this.handleIn(var1, new SPacketPlayInFlying());
   }

   public void a(PacketPlayOutScoreboardScore var1) {
   }

   public void a(PacketPlayOutEntityEffect var1) {
      this.handleOut(var1, new SPacketPlayOutEntityEffect());
   }

   public void a(PacketPlayOutPosition var1) {
      this.handleOut(var1, new SPacketPlayOutPosition());
   }

   public void a(PacketPlayOutCamera var1) {
   }

   public void a(PacketPlayOutPlayerInfo var1) {
   }

   public void a(PacketPlayOutLogin var1) {
   }

   public void a(PacketPlayInSetCreativeSlot var1) {
      this.handleIn(var1, new SPacketPlayInSetCreativeSlot());
   }

   public void a(PacketPlayOutBlockBreakAnimation var1) {
   }

   public void a(PacketPlayOutBlockAction var1) {
   }

   public void a(PacketPlayInSteerVehicle var1) {
      this.handleIn(var1, new SPacketPlayInSteerVehicle());
   }

   public void a(PacketPlayInResourcePackStatus var1) {
   }

   public void a(PacketPlayOutEntityTeleport var1) {
      this.handleOut(var1, new SPacketPlayOutEntityTeleport());
   }

   public void a(PacketPlayInTransaction var1) {
      this.handleIn(var1, new SPacketPlayInTransaction());
   }

   public void a(PacketPlayInBlockPlace var1) {
      this.handleIn(var1, new SPacketPlayInBlockPlace());
   }

   public void a(PacketPlayOutOpenSignEditor var1) {
   }

   public void a(PacketPlayOutSpawnEntity var1) {
      this.handleOut(var1, new SPacketPlayOutSpawnEntity());
   }

   public void a(PacketPlayInWindowClick var1) {
      this.handleIn(var1, new SPacketPlayInWindowClick());
   }

   public void a(PacketPlayOutWorldEvent var1) {
   }

   public void a(PacketPlayOutUpdateEntityNBT var1) {
   }

   public void a(PacketPlayOutNamedSoundEffect var1) {
   }

   public void a(PacketPlayOutEntityEquipment var1) {
   }

   public void a(PacketPlayOutHeldItemSlot var1) {
   }

   public void a(PacketPlayOutMultiBlockChange var1) {
      this.handleOut(var1, new SPacketPlayOutMultiBlockChange());
   }

   public void a(PacketPlayOutExplosion var1) {
      this.handleOut(var1, new SPacketPlayOutExplosion());
   }

   public void a(PacketLoginOutSetCompression var1) {
   }

   public void a(PacketPlayOutWindowItems var1) {
   }

   public void a(PacketPlayOutCloseWindow var1) {
   }

   public void a(PacketPlayOutTabComplete var1) {
   }

   public void a(PacketPlayOutTransaction var1) {
      this.handleOut(var1, new SPacketPlayOutTransaction());
   }

   public void a(PacketPlayInUpdateSign var1) {
   }

   public void a(PacketPlayOutRemoveEntityEffect var1) {
      this.handleOut(var1, new SPacketPlayOutRemoveEntityEffect());
   }

   public void a(PacketPlayOutSpawnEntityExperienceOrb var1) {
   }

   public void a(PacketPlayOutTitle var1) {
   }

   public void a(PacketPlayInArmAnimation var1) {
      this.handleIn(var1, new SPacketPlayInArmAnimation());
   }

   public void a(PacketPlayInSettings var1) {
   }

   public void a(PacketPlayOutMapChunk var1) {
      this.handleOut(var1, new SPacketPlayOutMapChunk());
   }

   public void a(PacketPlayOutKeepAlive var1) {
      this.handleOut(var1, new SPacketPlayOutKeepAlive());
   }

   public SPacketListener(PlayerData var1) {
      super(var1);
   }

   public void a(PacketPlayOutEntityDestroy var1) {
      this.handleOut(var1, new SPacketPlayOutEntityDestroy());
   }

   public void a(PacketPlayOutSetCompression var1) {
   }

   public void a(PacketPlayOutEntityStatus var1) {
   }

   public void a(PacketPlayOutChat var1) {
   }

   public void a(PacketPlayOutRespawn var1) {
      this.handleOut(var1, new SPacketPlayOutRespawn());
   }

   public void a(PacketPlayOutEntityMetadata var1) {
   }

   public void a(PacketPlayOutSpawnEntityWeather var1) {
   }
}
