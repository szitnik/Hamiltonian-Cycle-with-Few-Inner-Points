package si.zitnik.dr.mmr.algorithms

import org.junit.Test
import si.zitnik.dr.mmr.util.RandomGraph
import collection.mutable.ArrayBuffer
import si.zitnik.dr.mmr.domain.Point
import si.zitnik.dr.mmr.ui.GraphFrame

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 8/29/12
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
class SpeedTest {


  def test1() {


    val allPoints = ArrayBuffer[ArrayBuffer[Point]]()
    for (n <- 10 to 20) {
      for (k <- 3 to 5) {
        allPoints.append(RandomGraph.create(n,k))
      }
    }

    for (i <- 1 to 1) {
      var secondBetter = 0
      var firstBetter  =0
      var equal = 0
      var ac = 0

      for (points <- allPoints) {
        var time1 = System.currentTimeMillis()
        val result1 = new Algorithm1(points).compute()
        time1 = System.currentTimeMillis() - time1

        var time2 = System.currentTimeMillis()
        val result2 = new Algorithm2(points).compute()
        time2 = System.currentTimeMillis() - time2

        if (result2._1 < result1._1) {
          secondBetter += 1
          new GraphFrame(result1._2, "First")
          new GraphFrame(result2._2, "Second")
          return
        } else if (result2._1 > result1._1) {
          firstBetter += 1
          //new GraphFrame(result1._2, "First")
          //new GraphFrame(result2._2, "Second")
        } else {
          equal += 1
        }
        ac +=1
        //println("times: %4.2f %4.2f, lengths: %4.2f %4.2f".format(time1/1000.0,time2/1000.0, result1._1, result2._1))
      }

      println("all: %d, first better: %d, second better: %d, equal: %d".format(ac, firstBetter, secondBetter, equal))
    }

  }
}

object SpeedTest {
  def main(args: Array[String]) {
    new SpeedTest().test1()
  }
}
