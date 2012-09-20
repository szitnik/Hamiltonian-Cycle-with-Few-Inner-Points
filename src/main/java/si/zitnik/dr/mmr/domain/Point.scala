package si.zitnik.dr.mmr.domain

import si.zitnik.dr.mmr.util.MMRConversions._



class Point(val x: Double,
            val y: Double) extends Ordered[Point] {

  def distanceTo(p: Point) = math.sqrt(math.pow(this.x-p.x, 2)+math.pow(this.y-p.y, 2))

  override def equals(p: Any) = {
    (p.isInstanceOf[Point] && this.x == p.asInstanceOf[Point].x && this.y == p.asInstanceOf[Point].y) ||
    (p.isInstanceOf[Tuple2[Double, Double]] && this.equals(new Point(p.asInstanceOf[Tuple2[Double, Double]]._1, p.asInstanceOf[Tuple2[Double, Double]]._2)))
  }

  override
  def compare(p: Point) = {
    if (this.x != p.x) {
      (this.x - p.x).signum
    } else {
      (this.y - p.y).signum
    }
  }

  override
  def toString = "(%.2f,%.2f)".format(this.x, this.y)

}

object Point {

  /**
   * D(p1, p2, p3) =
   *   | 1  p1x  p1y |
   *   | 1  p2x  p2y |
   *   | 1  p3x  p3y |
   *
   * D < 0 => RIGHT, D == 0 => LINE, D > 0 => LEFT
   * @param p1
   * @param p2
   * @param p3
   * @return
   */
  def getSide(p1: Point, p2: Point, p3: Point) = {
    val D = 1*(p2.x*p3.y-p3.x*p2.y) -1*p1.x*(p3.y-p2.y) +p1.y*(p3.x-p2.x)

    if (D > 0) {
      Side.LEFT
    } else if (D < 0) {
      Side.RIGHT
    } else {
      Side.LINE
    }
  }


}
