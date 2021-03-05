package example

import java.lang

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.{ChannelFuture, ChannelInitializer, ChannelOption, EventLoopGroup}
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

object HelloMore extends App {
  NettyStudy.run(8080)
}

// https://github.com/newca12/scala-netty-examples/blob/master/src/main/scala/org/edla/netty/example/discard/DiscardServer.scala
object NettyStudy {
  def run(port: Int) = {
    println("Hello!")
    val bossGroup = new NioEventLoopGroup()
    val workerGroup = new NioEventLoopGroup()
    try {
      val b = new ServerBootstrap()
      b.group(bossGroup, workerGroup)
        .channel(classOf[NioServerSocketChannel])
        .childHandler(
          new ChannelInitializer[SocketChannel] {
            override def initChannel(ch: SocketChannel): Unit = {
              ch.pipeline().addLast(new DiscardServerHandler())
            }
          }
        )
        .option(ChannelOption.SO_BACKLOG, new Integer(128))
        .childOption(ChannelOption.SO_KEEPALIVE,new java.lang.Boolean(true))
      val f = b.bind(port).sync()
      f.channel().closeFuture().sync()
    } finally {
      workerGroup.shutdownGracefully()
      bossGroup.shutdownGracefully()
    }

  }
  def main(args: Array[String]): ChannelFuture = {
    println("Hello, world")
    run(8080)
  }
}


