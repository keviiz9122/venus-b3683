package me.levansj01.verus.verus2.data.player;

import java.util.Arrays;

public enum TickerType {
   TOTAL(true),
   ELYTRA_EXIT(true, 1200),
   HOOKED(true, 1200),
   WATER_BUCKET(true, 1200),
   SPRINT(true),
   TELEPORT(true),
   EXPLOSION(true, 1200),
   VEHICLE,
   SLIME_PUSH(true, 1200),
   WINDOW_CLICK(true, 1200),
   LEVITATION(true, 1200),
   SUFFOCATING(true, 100),
   LEGIT_SWING(true),
   ELYTRA_BOOST(true, 1200),
   MANUAL_TELEPORT(true),
   RIPTIDE(true, 1200),
   POSSIBLE_SWINGS,
   PLACED_BLOCK_UNDER(true, 1200),
   PLACED_BLOCK(true, 1200),
   LAST_RECEIVED_TRANSACTION(false, 0),
   MOVES_SINCE_TELEPORT(false, 0),
   NOT_MOVING(true),
   ATTACKS,
   DOUBLE_SWING(true),
   ATTACKS_IN_LAST,
   GLIDING(true, 1200),
   LAST_SENT_TRANSACTION(false, 0),
   VELOCITY(true),
   FALL_FLYING(true, 1200),
   SPEED_BOOST(true, 1200),
   ATTRIBUTES(true, 1200),
   JUMP_BOOST(true, 1200);

   private final boolean auto;
   private final int starting;
   private static final TickerType[] autos = (TickerType[])Arrays.stream(values()).filter(TickerType::isAuto).toArray((var0) -> {
      return new TickerType[var0];
   });

   private TickerType(boolean var3, int var4) {
      this.auto = var3;
      this.starting = var4;
   }

   private TickerType(int var3) {
      this(false, var3);
   }

   private TickerType(boolean var3) {
      this(var3, 0);
   }

   public int getStarting() {
      return this.starting;
   }

   private TickerType() {
      this(false, 0);
   }

   public boolean isAuto() {
      return this.auto;
   }

   public static TickerType[] getAutos() {
      return autos;
   }
}
