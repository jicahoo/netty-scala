package example

import java.util.concurrent.TimeUnit

import io.netty.channel.nio.{NioEventLoop, NioEventLoopGroup}
import io.netty.channel.{DefaultEventLoop, EventLoop}

object Main extends App {
  EventLoopExample.scheduleTaskInNioEventLoop()
}

object EventLoopExample {
  def defaultEventLoop(): Unit = {
    println("Enter demo!")
    val eLoop = new DefaultEventLoop();
    val future = eLoop.schedule(
      new Runnable {
        override def run(): Unit = {
          val threadName = Thread.currentThread().getName
          println(s"[$threadName] 10 secs later")
        }
      },
      10,
      TimeUnit.SECONDS
    )
    println("Task scheduled... just wait")

    println("Shutdown event loop")
    eLoop.shutdownGracefully()

  }

  def scheduleTaskInNioEventLoop(): Unit = {
    val workerGroup = new NioEventLoopGroup()
    workerGroup.scheduleAtFixedRate(
      new Runnable {
        override def run(): Unit =  {
          val threadName = Thread.currentThread().getName
          println(s"[$threadName] zhangj52 scheduled task")
        }
      },
      10,
      10,
      TimeUnit.SECONDS
    )
    println("After schedule task!")
    workerGroup.awaitTermination(1, TimeUnit.DAYS)
    workerGroup.shutdownGracefully()
  }
}
