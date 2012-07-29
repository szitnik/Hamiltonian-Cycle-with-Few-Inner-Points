package si.zitnik.dr.mmr.domain

import org.junit.Test
import si.zitnik.dr.mmr.domain.Point
import collection.mutable.ArrayBuffer
import si.zitnik.dr.mmr.util.MMRConversions._

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 7/11/12
 * Time: 6:53 PM
 * To change this template use File | Settings | File Templates.
 */


class PointTest {

  @Test
  def sortTest() {
    var list = new ArrayBuffer[Point]()
    list += ((10, 10))
    list += ((3, 10))
    list += ((3, 3))
    list += ((4, 5))
    list += ((4, 4))
    list = list.sorted
    assert(list.mkString(", ").equals("(3.00,3.00), (3.00,10.00), (4.00,4.00), (4.00,5.00), (10.00,10.00)"))
  }


}
