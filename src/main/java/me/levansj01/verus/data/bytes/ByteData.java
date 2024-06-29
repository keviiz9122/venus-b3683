package me.levansj01.verus.data.bytes;

import java.util.function.Function;
import me.levansj01.verus.data.state.Releasable;
import me.levansj01.verus.util.jpountz.lz4.LZ4Compressor;
import me.levansj01.verus.util.jpountz.lz4.LZ4FastDecompressor;

public interface ByteData extends Releasable {
   default ByteData compress(LZ4Compressor var1) {
      int var2 = var1.maxCompressedLength(this.getLength());
      byte[] var3 = new byte[var2];
      var2 = var1.compress(this.getArray(), this.getOffset(), this.getLength(), var3, 0, var2);
      byte[] var4 = new byte[var2];
      System.arraycopy(var3, 0, var4, 0, var2);
      return new BasicByteData(var4);
   }

   int getLength();

   default void retain() {
   }

   default ByteData slice(int var1, int var2) {
      return new OffsetByteData(this.getArray(), this.getOffset() + var1, var2);
   }

   int getOffset();

   default void setBytePair(int var1, int var2) {
      this.set(var1, (byte)(var2 & 255));
      this.set(var1 + 1, (byte)(var2 >> 8 & 255));
   }

   void set(int var1, byte var2);

   byte get(int var1);

   Function<byte[], ByteData> creator();

   default ByteData decompress(LZ4FastDecompressor var1, int var2) {
      return new BasicByteData(var1.decompress(this.getArray(), this.getOffset(), var2));
   }

   byte[] getArray();

   default int getBytePair(int var1) {
      int var2 = this.get(var1) & 255;
      int var3 = this.get(var1 + 1) & 255;
      return var3 << 8 | var2;
   }
}
