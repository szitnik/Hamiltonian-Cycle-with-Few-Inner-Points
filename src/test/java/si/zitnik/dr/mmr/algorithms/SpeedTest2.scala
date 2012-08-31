package si.zitnik.dr.mmr.algorithms

import org.junit.Test
import collection.mutable.ArrayBuffer
import si.zitnik.dr.mmr.domain.Point
import si.zitnik.dr.mmr.util.RandomGraph
import si.zitnik.dr.mmr.ui.GraphFrame

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 8/31/12
 * Time: 9:55 AM
 * To change this template use File | Settings | File Templates.
 */
class SpeedTest2 {

  @Test
  def test1() {

    val runtimes1 = Array.ofDim[Double](11)
    val runtimes2 = Array.ofDim[Double](11)
    for (r <- 1 to 10) {
      for (n <- 10 to 10) {
        for (k <- 0 to 10) {
          val points = RandomGraph.create(n,k)

          val alg1 = new Algorithm1(points)
          var time1 = System.currentTimeMillis()
          alg1.compute()
          time1 = System.currentTimeMillis() - time1

          val alg2 = new Algorithm2(points)
          var time2 = System.currentTimeMillis()
          alg2.compute()
          time2 = System.currentTimeMillis() - time2

          println("n: %3d, k: %3d, times: %4.2f %4.2f".format(n,k,time1/1000.0,time2/1000.0))

          runtimes1(k) += time1/1000.0
          runtimes2(k) += time2/1000.0
        }
      }
    }

    println("Alg1 overall times: "+ runtimes1.map(_/11).mkString(", "))
    println("Alg2 overall times: "+ runtimes2.map(_/11).mkString(", "))
  }

  @Test
  def test2() {
    val f1 = (k: Int) => {1L*factorial(k)*k}
    val f2 = (k: Int) => {1L*math.pow(2, k).toLong*k*k}

    for (k <- 1 to 100) {
      val f1res = f1(k)
      val f2res = f2(k)
      println(k+": " + f1res + " - " + f2res)
    }
  }

  def factorial(k: Int) = {
    var retVal: Long = 1
    for (i <- 1 to k) {
      retVal *= i
    }
    retVal
  }

}
