package me.levansj01.verus.data;

public interface PingHandler {
   void handleKeepAlive(long var1, long var3);

   void handleTransaction(long var1, long var3);
}
