package me.levansj01.verus.api.wrapper;

public enum BanResult {
   CANCEL(false, false),
   SILENT(true, false),
   ANNOUNCE(true, true);

   private final boolean announce;
   private final boolean ban;

   public boolean isAnnounce() {
      return this.announce;
   }

   public boolean isBan() {
      return this.ban;
   }

   private BanResult(boolean var3, boolean var4) {
      this.ban = var3;
      this.announce = var4;
   }
}
