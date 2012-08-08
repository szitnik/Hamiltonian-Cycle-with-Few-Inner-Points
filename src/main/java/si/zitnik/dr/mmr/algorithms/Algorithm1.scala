package si.zitnik.dr.mmr.algorithms

import abst.Algorithm
import collection.mutable.ArrayBuffer
import si.zitnik.dr.mmr.domain.Point
import si.zitnik.dr.mmr.convexhull.IncrementalConvexHull
import si.zitnik.dr.mmr.util.MMRConversions._

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 7/31/12
 * Time: 11:12 PM
 * To change this template use File | Settings | File Templates.
 */

class Algorithm1(points: ArrayBuffer[Point]) extends Algorithm(points) {

  private var F1: Array[Array[Array[(Double, Array[Point])]]] = null
  private val n = points.size
  private var k = 0

  override
  def compute(): (Double, Array[Point]) = {
    val OutP = IncrementalConvexHull.calculateConvexHull(points)
    val InnP = (points -- OutP).sorted
    k = InnP.size

    //add blinds
    OutP.insert(0,((0.,0.)))
    InnP.insert(0, ((Double.PositiveInfinity, Double.PositiveInfinity)))


    val i = n-k+1 //zero index will not be used
    val j = k+1
    val m = 2 // Inn = 0, Out = 1
    F1 = Array.ofDim(i,j,m)


    //set defaults
    F1(1)(0)(1) = (0., Array(OutP(1)))
    (1 until j).foreach(F1(1)(_)(1) = (Double.PositiveInfinity, Array(new Point(Double.PositiveInfinity,Double.PositiveInfinity))))
    (1 until i).foreach(F1(_)(0)(0) = (Double.PositiveInfinity, Array(new Point(Double.PositiveInfinity,Double.PositiveInfinity))))

    for (iCtr <- 1 to n-k) {
      for (jCtr <- 0 to k) {
        if (jCtr > 0) {
          val dist1 = F1(iCtr)(jCtr-1)(1)._1+OutP(iCtr).distanceTo(InnP(jCtr))
          val dist2 = F1(iCtr)(jCtr-1)(0)._1+InnP(jCtr-1).distanceTo(InnP(jCtr))
          F1(iCtr)(jCtr)(0) = if (dist1 < dist2) {
            (dist1, F1(iCtr)(jCtr-1)(1)._2 ++ Array(InnP(jCtr)))
          } else {
            (dist2, F1(iCtr)(jCtr-1)(0)._2 ++ Array(InnP(jCtr)))
          }
        }


        if (iCtr > 1) {
          val dist1 = F1(iCtr-1)(jCtr)(1)._1+OutP(iCtr-1).distanceTo(OutP(iCtr))
          val dist2 = F1(iCtr-1)(jCtr)(0)._1+InnP(jCtr).distanceTo(OutP(iCtr))
          F1(iCtr)(jCtr)(1) = if (dist1 < dist2) {
            (dist1, F1(iCtr-1)(jCtr)(1)._2 ++ Array(OutP(iCtr)))
          } else {
            (dist2, F1(iCtr-1)(jCtr)(0)._2 ++ Array(OutP(iCtr)))
          }
        }
      }
    }

    val dist1 = F1(i-1)(k)(1)._1 + OutP(n - k).distanceTo(OutP(1))
    val dist2 = F1(i-1)(k)(0)._1 + InnP(k).distanceTo(OutP(1))
    val shortestTour = if (dist1 < dist2) {
      (dist1, F1(i-1)(k)(1)._2 ++ Array(OutP(1)))
    } else {
      (dist2, F1(i-1)(k)(0)._2 ++ Array(OutP(1)))
    }

    shortestTour
  }

}
