package example

import io.netty.util.concurrent.DefaultEventExecutorGroup

import scala.concurrent.JavaConversions.asExecutionContext

object DefaultEventExecutorGroupStudy extends App {
  Demo.run()
}

class FruitTask(val name: String) extends Runnable {
  override def run(): Unit = {
    val t = Thread.currentThread().getName
    println(s"[$t] $name task")
  }
}
object Demo {
  def run(): Unit = {
    val defaultEventExecutorGroup = new DefaultEventExecutorGroup(5)
    Range(1,10).foreach(
      i => defaultEventExecutorGroup.submit(new FruitTask(s"$i"))
    )
    defaultEventExecutorGroup.shutdownGracefully()
  }


}
