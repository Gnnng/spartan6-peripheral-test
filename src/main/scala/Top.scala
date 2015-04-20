package sp6

import Chisel._

class Top extends Module {
  val io = new Bundle {
    var sw = UInt(INPUT, 8)
    var led = UInt(OUTPUT, 8)
  }

  io.led := io.sw
}

class TopTester(c: Top) extends Tester(c) {
  poke(c.io.sw, 8)
  step(1)
  expect(c.io.led, 8)
}

object Top {
  def main(args: Array[String]): Unit = {
    args.foreach(arg => println(arg))
    chiselMainTest(args, () => Module(new Top())) {
      c => new TopTester(c) }
  }
}