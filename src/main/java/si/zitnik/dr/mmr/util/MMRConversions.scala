package si.zitnik.dr.mmr.util

import si.zitnik.dr.mmr.domain.Point

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 7/28/12
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */

object MMRConversions {

  implicit def tupleToPoint(tuple: (Double, Double)) = new Point(tuple._1, tuple._2)
  implicit def intTupleToPoint(tuple: (Int, Int)) = new Point(tuple._1, tuple._2)

}
