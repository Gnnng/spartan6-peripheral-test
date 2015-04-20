package sp6

import Chisel._

class Top extends Module {
  val io = new Bundle {
    var sw = UInt(INPUT, 8)
    var btn = Bits(INPUT, 5)
    var led = UInt(OUTPUT, 8)
  }

  io.led := io.sw | io.btn;

}

class TopTester(c: Top) extends Tester(c) {
  poke(c.io.btn, 0)

  for(i <- 0 until 256) {
    poke(c.io.sw, i)
    step(1)
    expect(c.io.led, i)
  }

  reset()

  for(i <- 0 until 10) {
    var rnd0 = rnd.nextInt()
    var rnd1 = rnd.nextInt()
    var sw_in = rnd0 & 0xff
    var btn_in = rnd1 & 0x1f
    poke(c.io.sw, sw_in)
    poke(c.io.btn, btn_in)
    step(1)
    expect(c.io.led, sw_in | btn_in)
  }
}

object Top {
  def main(args: Array[String]): Unit = {
    args.foreach(arg => println(arg))
    chiselMainTest(args, () => Module(new Top())) {
      c => new TopTester(c) }
  }
}