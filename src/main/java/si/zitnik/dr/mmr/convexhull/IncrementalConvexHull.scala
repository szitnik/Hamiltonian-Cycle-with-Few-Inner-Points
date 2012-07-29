package si.zitnik.dr.mmr.convexhull

import si.zitnik.dr.mmr.domain.{Side, Point}
import collection.mutable.ArrayBuffer


/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 7/11/12
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */

object IncrementalConvexHull extends ConvexHull {

  def calculateConvexHull(pointsParam: ArrayBuffer[Point]): ArrayBuffer[Point] = {
    val Z = new ArrayBuffer[Point]()

    if (pointsParam.size == 0) {
      return Z
    } else if (pointsParam.size == 1) {
      Z += pointsParam(0)
      return Z
    }

    val points = pointsParam.sorted
    Z += points(0)
    Z += points(1)

    for (i <- 2 to points.size-1) {
      Z += points(i)
      while (Z.size >= 3 && Point.getSide(Z.reverse(2), Z.reverse(1), Z.last).equals(Side.RIGHT)) {
        Z.remove(Z.size-2)
      }
    }

    val S = new ArrayBuffer[Point]()
    S += points.last
    S += points.reverse(1)
    for (i <- (0 to points.size-3).reverse) {
      S += points(i)
      while (S.size >= 3 && Point.getSide(S.reverse(2), S.reverse(1), S.last).equals(Side.RIGHT)) {
        S.remove(S.size-2)
      }
    }

    Z ++ S.slice(1, S.size-1)
  }


}
