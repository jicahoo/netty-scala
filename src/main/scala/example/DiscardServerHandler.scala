package example

import io.netty.buffer.ByteBuf
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.util.ReferenceCountUtil

class DiscardServerHandler extends SimpleChannelInboundHandler[AnyRef] {
  override def channelRead0(ctx: ChannelHandlerContext, msg: Object): Unit =  {
    println("Hello")
    val in =  msg.asInstanceOf[ByteBuf];
    try {
      while (in.isReadable()) { // (1)
        println( "Received:" + in.readByte());
        System.out.flush();
      }
    } finally {
      ReferenceCountUtil.release(msg); // (2)
    }
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    // Close the connection when an exception is raised.
    cause.printStackTrace();
    ctx.close();
  }

}

