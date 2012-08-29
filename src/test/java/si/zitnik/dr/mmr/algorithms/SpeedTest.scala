package si.zitnik.dr.mmr.algorithms

import org.junit.Test
import si.zitnik.dr.mmr.util.RandomGraph

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 8/29/12
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
class SpeedTest {

  @Test
  def test1() {
    for (n <- 20 to 100) {
      for (k <- 1 to 10) {
        val points = RandomGraph.create(n,k)

        var time1 = System.currentTimeMillis()
        new Algorithm1(points).compute()
        time1 = System.currentTimeMillis() - time1

        var time2 = System.currentTimeMillis()
        new Algorithm2(points).compute()
        time2 = System.currentTimeMillis() - time2

        println("%3d, %3d - %4.2f %4.2f".format(n,k,time1/1000.0,time2/1000.0))
      }
    }
  }
}
