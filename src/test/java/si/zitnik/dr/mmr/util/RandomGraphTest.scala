package si.zitnik.dr.mmr.util

import org.junit.Test


/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 8/27/12
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
class RandomGraphTest {

  @Test
  def test1() {
    println(RandomGraph.create(10, 0).mkString(", "))
    println(RandomGraph.create(10, 1).mkString(", "))
    println(RandomGraph.create(10, 2).mkString(", "))
    println(RandomGraph.create(10, 3).mkString(", "))
  }

}
