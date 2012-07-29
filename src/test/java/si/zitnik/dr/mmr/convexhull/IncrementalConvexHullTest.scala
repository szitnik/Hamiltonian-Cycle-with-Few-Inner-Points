package si.zitnik.dr.mmr.convexhull

import org.junit.{Ignore, Test}
import collection.mutable.ArrayBuffer
import si.zitnik.dr.mmr.domain.Point
import si.zitnik.dr.mmr.util.MMRConversions._
import util.Random


/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 7/28/12
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */

class IncrementalConvexHullTest {

  def doCHTest(chInput: Array[Point], chOutput: ArrayBuffer[Point]) {
    assert(chOutput.intersect(chInput).size == chInput.size)
    assert(chOutput.forall(chInput.contains(_)) && chInput.forall(chOutput.contains(_)))
  }

  @Test
  def simpleTest()  {
    var input = new ArrayBuffer[Point]()
    var chInput = Array[Point]((1,1), (2,1), (2,2), (1,2))
    input ++= chInput
    input += ((1.5, 1.5))
    input += ((1.1, 1.1))
    input += ((1.2, 1.2))
    input += ((1.3, 1.3))
    input += ((1.4, 1.4))

    val chOutput = IncrementalConvexHull.calculateConvexHull(input)
    doCHTest(chInput, chOutput)
  }


  @Test
  def fullTest()  {
    var input = new ArrayBuffer[Point]()
    var chInput = Array[Point]((0,0), (0,100), (100,100), (100,0))
    input ++= chInput
    val rand = new Random()
    (1 to 10).foreach(_ => input += ((rand.nextDouble()*100, rand.nextDouble()*100)))

    val chOutput = IncrementalConvexHull.calculateConvexHull(input)
    doCHTest(chInput, chOutput)
  }
}
