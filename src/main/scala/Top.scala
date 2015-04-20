import Chisel._

class Top extends Module {
  val io = new Bundle {
    val sw = UInt(INPUT, 8)
    val btn = UInt(INPUT, 5)
    val led = UInt(OUTPUT, 8)
    val ss_pin = new SevenSegmentPin()
  }

  val ss = Module(new SevenSegment())

  io.ss_pin <> ss.io.pin

  val count = Reg(UInt(width = 16))

  def risingedge(x: Bool) = x && !Reg(next = x)

  // TODO: risingedge vs io.btn, both correct in simulation. But io.btn is wrong on the board
  when (risingedge(io.btn(0))) {
    count := count + UInt(1)
  }
//  when (io.btn(0)) {
//    count := count + UInt(1)
//  }

  ss.io.disp_data := count
  io.led := io.sw | io.btn
}

class TopTester(c: Top) extends Tester(c) {
  reset(100)
  for(i <- 0 until 16) {
    step(1)
    poke(c.io.btn, 0)
    step(100)
    poke(c.io.btn, 1)
    expect(c.count, i)
  }

//
//  poke(c.io.btn, 0)
//
//  for(i <- 0 until 256) {
//    poke(c.io.sw, i)
//    step(1)
//    expect(c.io.led, i)
//  }
//
//  reset()
//
//  for(i <- 0 until 10) {
//    var rnd0 = rnd.nextInt()
//    var rnd1 = rnd.nextInt()
//    var sw_in = rnd0 & 0xff
//    var btn_in = rnd1 & 0x1f
//    poke(c.io.sw, sw_in)
//    poke(c.io.btn, btn_in)
//    step(1)
//    expect(c.io.led, sw_in | btn_in)
//  }
}

object Top {
  def main(args: Array[String]): Unit = {
    args.foreach(arg => println(arg))
    chiselMainTest(args, () => Module(new Top())) {
      c => new TopTester(c) }
  }
}