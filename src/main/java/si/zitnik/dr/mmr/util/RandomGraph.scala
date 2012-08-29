package si.zitnik.dr.mmr.util

import collection.mutable.ArrayBuffer
import si.zitnik.dr.mmr.domain.Point
import MMRConversions._
import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 8/27/12
 * Time: 5:10 PM
 * To change this template use File | Settings | File Templates.
 */
object RandomGraph {

  def create(n: Int, k: Int): ArrayBuffer[Point] = {
    val retVal = ArrayBuffer[Point]()
    val r = 10 //radius
    val random = new Random()

    //outer points
    val fi = 360.0 / n
    for (ni <- 0 until n) {
      retVal.append((r * math.cos(ni*fi), r * math.sin(ni*fi)))
    }
    //inner points
    (1 to k).foreach(_ => {
      val randomFi = random.nextDouble()*360
      val randomR = random.nextInt(r)
      retVal.append((randomR * math.cos(randomFi), randomR * math.sin(randomFi)))
    })

    retVal
  }

}
