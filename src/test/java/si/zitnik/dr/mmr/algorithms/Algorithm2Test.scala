package si.zitnik.dr.mmr.algorithms

import org.junit.Test
import collection.mutable.ArrayBuffer
import si.zitnik.dr.mmr.domain.Point
import si.zitnik.dr.mmr.util.MMRConversions._
import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 8/1/12
 * Time: 12:11 AM
 * To change this template use File | Settings | File Templates.
 */

class Algorithm2Test {

  @Test
  def test0() {
    var input = new ArrayBuffer[Point]()
    val rand = new Random()
    (1 to 100).foreach(_ => {input += ((rand.nextDouble()*1000, rand.nextDouble()*1000))})


    val shortestTour = new Algorithm2(input).compute()
    println("Length: %.3f\nPath: %s".format(shortestTour._1, shortestTour._2.mkString(" -> ")))
  }

  @Test
  def test1() {
    var input = new ArrayBuffer[Point]()
    var chInput = Array[Point]((1, 1), (2, 1), (1, 2))
    input ++= chInput
    input += ((1.5, 1.1))
    input += ((1.3, 1.1))
    input += ((1.2, 1.2))
    input += ((1.3, 1.3))
    input += ((1.4, 1.4))

    val shortestTour = new Algorithm2(input).compute()
    println("Length: %.3f\nPath: %s".format(shortestTour._1, shortestTour._2.mkString(" -> ")))
  }


}
