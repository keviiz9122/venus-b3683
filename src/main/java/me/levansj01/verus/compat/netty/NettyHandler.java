package me.levansj01.verus.compat.netty;

import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
import me.levansj01.verus.data.bytes.ByteData;

public interface NettyHandler {
   ByteData of(int var1);

   ByteData wrap(byte[] var1, int var2, int var3);

   ThreadFactory newThreadFactory(String var1, boolean var2, int var3);

   default ThreadFactory newThreadFactory(String var1, int var2) {
      return this.newThreadFactory(var1, false, var2);
   }

   default ThreadFactory newThreadFactory(String var1) {
      return this.newThreadFactory(var1, 5);
   }

   void inject(C var1, Consumer<Object> var2);

   void uninject(C var1);
}
