package me.levansj01.verus.compat.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.DefaultThreadFactory;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
import me.levansj01.verus.data.bytes.ByteData;

public class UnshadedNettyHandler implements NettyHandler {
   public ByteData of(int var1) {
      return new UnshadedByteData(Unpooled.directBuffer(var1, var1).setZero(0, var1));
   }

   public ThreadFactory newThreadFactory(String var1, boolean var2, int var3) {
      return new DefaultThreadFactory(var1, var2, var3);
   }

   public void uninject(Channel var1) {
      var1.eventLoop().execute(() -> {
         ChannelPipeline var1x = var1.pipeline();
         if (var1x.get("verus_handler_in") != null) {
            var1x.remove("verus_handler_in");
         }

         if (var1x.get("verus_handler_out") != null) {
            var1x.remove("verus_handler_out");
         }

      });
   }

   public ByteData wrap(byte[] var1, int var2, int var3) {
      return new UnshadedByteData(Unpooled.wrappedBuffer(var1, var2, var3));
   }

   public void inject(Channel var1, Consumer<Object> var2) {
      var1.eventLoop().execute(() -> {
         ChannelPipeline var2x = var1.pipeline();
         if (var2x.get("decoder") != null && var2x.get("packet_handler") != null) {
            var2x.addAfter("decoder", "verus_handler_in", new UnshadedNettyHandler.PacketHandlerIn(var2));
            var2x.addBefore("packet_handler", "verus_handler_out", new UnshadedNettyHandler.PacketHandlerOut(var2));
         }

      });
   }

   public static class PacketHandlerIn extends ChannelInboundHandlerAdapter {
      private final Consumer<Object> listener;

      public void channelRead(ChannelHandlerContext var1, Object var2) throws Exception {
         super.channelRead(var1, var2);
         this.listener.accept(var2);
      }

      public PacketHandlerIn(Consumer<Object> var1) {
         this.listener = var1;
      }
   }

   public static class PacketHandlerOut extends ChannelOutboundHandlerAdapter {
      private final Consumer<Object> listener;

      public void write(ChannelHandlerContext var1, Object var2, ChannelPromise var3) throws Exception {
         super.write(var1, var2, var3);
         this.listener.accept(var2);
      }

      public PacketHandlerOut(Consumer<Object> var1) {
         this.listener = var1;
      }
   }
}
