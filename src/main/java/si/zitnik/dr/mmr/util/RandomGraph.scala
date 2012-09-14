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
    if (n<3) {
      println("ERROR: n must be >= 3")
      System.exit(-1)
    }

    val retVal = ArrayBuffer[Point]()
    val r = 10 //radius
    val random = new Random()

    //outer points
    val fi = 2*math.Pi / n
    for (ni <- 0 until n) {
      retVal.append((r * math.cos(ni*fi), r * math.sin(ni*fi)))  //radians :)
    }
    //inner points

    val maxOuterPointRadius =
      math.sqrt(math.pow((0,0).distanceTo(retVal(0)), 2) - math.pow(retVal(0).distanceTo(retVal(1))/2, 2)) //easy pitagora
    (1 to k).foreach(_ => {
      val randomFi = random.nextDouble()*2*math.Pi
      val randomR = random.nextDouble()*maxOuterPointRadius
      retVal.append((randomR * math.cos(randomFi), randomR * math.sin(randomFi)))
    })

    retVal
  }

}
