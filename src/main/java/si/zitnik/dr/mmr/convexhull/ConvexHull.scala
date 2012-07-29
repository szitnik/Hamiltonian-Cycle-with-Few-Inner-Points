package si.zitnik.dr.mmr.convexhull

import si.zitnik.dr.mmr.domain.Point
import collection.mutable.ArrayBuffer

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 7/11/12
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */

abstract class ConvexHull {
  def calculateConvexHull(points: ArrayBuffer[Point]): ArrayBuffer[Point]
}
