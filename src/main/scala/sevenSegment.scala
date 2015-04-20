import Chisel._

class SevenSegmentPin extends Bundle {
  val select = UInt(OUTPUT, 4)
  val segment = UInt(OUTPUT, 7)
  val dot = UInt(OUTPUT, 1)
}

class SevenSegment extends Module {
  val io = new Bundle {
    val disp_data = UInt(INPUT, 16)
    val pin = new SevenSegmentPin()
  }

  io.pin.select := UInt("b1111")
  io.pin.segment := UInt("b111_1111")
  io.pin.dot := UInt("b1")

  val counter = Reg(init = UInt(0, 19))
  counter := counter + UInt(1)

  val digit = UInt()
  digit := UInt("h_f")

  switch(counter(18, 17)) {
    is(UInt(0)) { digit := io.disp_data( 3, 0); io.pin.select := UInt("b1110")}
    is(UInt(1)) { digit := io.disp_data( 7, 4); io.pin.select := UInt("b1101")}
    is(UInt(2)) { digit := io.disp_data(11, 8); io.pin.select := UInt("b1011")}
    is(UInt(3)) { digit := io.disp_data(15,12); io.pin.select := UInt("b0111")}
  }

  switch(digit) {
    is(UInt("h_0")) { io.pin.segment := UInt("b1000000") }
    is(UInt("h_1")) { io.pin.segment := UInt("b1111001") }
    is(UInt("h_2")) { io.pin.segment := UInt("b0100100") }
    is(UInt("h_3")) { io.pin.segment := UInt("b0110000") }
    is(UInt("h_4")) { io.pin.segment := UInt("b0011001") }
    is(UInt("h_5")) { io.pin.segment := UInt("b0010010") }
    is(UInt("h_6")) { io.pin.segment := UInt("b0000010") }
    is(UInt("h_7")) { io.pin.segment := UInt("b1111000") }
    is(UInt("h_8")) { io.pin.segment := UInt("b0000000") }
    is(UInt("h_9")) { io.pin.segment := UInt("b0010000") }
    is(UInt("h_a")) { io.pin.segment := UInt("b0001000") }
    is(UInt("h_b")) { io.pin.segment := UInt("b0000011") }
    is(UInt("h_c")) { io.pin.segment := UInt("b1000110") }
    is(UInt("h_d")) { io.pin.segment := UInt("b0100001") }
    is(UInt("h_e")) { io.pin.segment := UInt("b0000110") }
    is(UInt("h_f")) { io.pin.segment := UInt("b0001110") }
  }
}


class SSTester(c: SevenSegment) extends Tester(c) {

  for(i <- 0 until 16) {
    val disp = (i << 12) + (i << 8) + (i << 4) + i
    poke(c.io.disp_data, disp)
    step((1 << 19))
    var seg_out: Int = 0
    i match {
      case  0 => seg_out = Integer.parseInt("1000000", 2)
      case  1 => seg_out = Integer.parseInt("1111001", 2)
      case  2 => seg_out = Integer.parseInt("0100100", 2)
      case  3 => seg_out = Integer.parseInt("0110000", 2)
      case  4 => seg_out = Integer.parseInt("0011001", 2)
      case  5 => seg_out = Integer.parseInt("0010010", 2)
      case  6 => seg_out = Integer.parseInt("0000010", 2)
      case  7 => seg_out = Integer.parseInt("1111000", 2)
      case  8 => seg_out = Integer.parseInt("0000000", 2)
      case  9 => seg_out = Integer.parseInt("0010000", 2)
      case 10 => seg_out = Integer.parseInt("0001000", 2)
      case 11 => seg_out = Integer.parseInt("0000011", 2)
      case 12 => seg_out = Integer.parseInt("1000110", 2)
      case 13 => seg_out = Integer.parseInt("0100001", 2)
      case 14 => seg_out = Integer.parseInt("0000110", 2)
      case 15 => seg_out = Integer.parseInt("0001110", 2)
    }

    expect(c.io.pin.segment, seg_out)
  }
}

object SevenSegment {
  def main(args: Array[String]): Unit = {
    args.foreach(arg => println(arg))
    chiselMainTest(args, () => Module(new SevenSegment())) {
      c => new SSTester(c) }
  }
}