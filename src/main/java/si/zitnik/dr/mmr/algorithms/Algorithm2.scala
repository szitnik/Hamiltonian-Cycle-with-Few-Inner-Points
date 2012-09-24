package si.zitnik.dr.mmr.algorithms

import abst.Algorithm
import collection.mutable.ArrayBuffer
import scala.collection.immutable.Set._
import si.zitnik.dr.mmr.domain.Point
import si.zitnik.dr.mmr.convexhull.IncrementalConvexHull
import si.zitnik.dr.mmr.util.MMRConversions._
import scala.collection.JavaConversions._
import scala.Array
import collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 8/2/12
 * Time: 11:19 PM
 * To change this template use File | Settings | File Templates.
 */

class Algorithm2(points: ArrayBuffer[Point]) extends Algorithm(points) {

  private var F2: Array[Array[Array[(Double, Array[Point])]]] = null
  private val n = points.size
  private var k = 0

  override
  def compute(): (Double, Array[Point]) = {
    val OutP = IncrementalConvexHull.calculateConvexHull(points)
    val InnP = (points -- OutP).sorted
    k = InnP.size
    val SAll = (0 to k).map(InnP.combinations(_).toArray).flatten.map(_.toSet).toArray.sortWith(_.size < _.size)

    //add blind
    OutP.insert(0,((0.,0.)))

    val i = n-k+1 //zero index will not be used
    val s = SAll.size
    val m = k+1 // ending element = one of InnP or pi, last index represents pi
    F2 = Array.ofDim(i,s,m)


    //set defaults
    (0 until s).foreach(sCtr =>
      (0 until m).foreach(mCtr =>
          F2(1)(sCtr)(mCtr) = (Double.PositiveInfinity, Array(new Point(Double.PositiveInfinity,Double.PositiveInfinity)))))
    F2(1)(0)(k) = (0., Array(OutP(1)))
    //bootstrap
    (0 until s).foreach(sCtr =>
      (0 until k).foreach(mCtr =>
        F2(1)(sCtr)(mCtr) = {
          val S: Set[Point] = SAll(sCtr)
          val lastPoint = InnP(mCtr)
          if (S.contains(lastPoint)) {
            var retPath: (Double, Array[Point]) = null
            val buffer = S.filter(_ != lastPoint).toBuffer.permutations
            while (buffer.hasNext) {
              val Sperm = buffer.next()
              var currentPath: (Double, Array[Point]) = (0., Array(OutP(1)))
              Sperm.foreach(point => {
                currentPath = (currentPath._1 + currentPath._2.last.distanceTo(point), currentPath._2 ++ Array(point))
              })
              currentPath = (currentPath._1 + currentPath._2.last.distanceTo(lastPoint), currentPath._2 ++ Array(lastPoint))
              if (retPath == null) {
                retPath = currentPath
              } else if (currentPath._1 < retPath._1) {
                retPath = currentPath
              }
            }
            retPath

          } else {
            (Double.PositiveInfinity, Array(new Point(Double.PositiveInfinity,Double.PositiveInfinity)))
          }
        }))


    for (iCtr <- 2 to n-k) {
      for (sCtr <- 0 until s) {
        val S = SAll(sCtr)
        //first sentence
        F2(iCtr)(sCtr)(m-1) =  (S ++ Set(OutP(iCtr-1))).map(t => {
          val tempVal = if (S.contains(t)) {F2(iCtr-1)(sCtr)(InnP.indexOf(t))} else {F2(iCtr-1)(sCtr)(m-1)}
          (tempVal._1 + t.distanceTo(OutP(iCtr)), tempVal._2 ++ Array(OutP(iCtr)))
        }).minBy(_._1)
      }


      for (sCtr <- 0 until s) {
        val S = SAll(sCtr)
        //second sentence
        for (r <- S) { //implicitly avoid condition S!={}
          val rIdx = InnP.indexOf(r)
          val SwrIdx = SAll.indexOf(S -- Array(r))
          F2(iCtr)(sCtr)(rIdx) = (S.filter(_ != r) ++ Array(OutP(iCtr))).map(t => {
            val tempVal = if (SAll(SwrIdx).contains(t)) {F2(iCtr)(SwrIdx)(InnP.indexOf(t))} else {F2(iCtr)(SwrIdx)(m-1)}
            (tempVal._1 + t.distanceTo(r), tempVal._2 ++ Array(r))
          }).minBy(_._1)
        }
      }
    }

    val shortestTour = (SAll.last ++ Array(OutP(n-k))).map(r => {
      val tempVal = if (SAll.last.contains(r)) {F2(n-k)(s-1)(InnP.indexOf(r))} else {F2(n-k)(s-1)(m-1)}
      (tempVal._1 + r.distanceTo(OutP(1)), tempVal._2 ++ Array(OutP(1)))
    }).minBy(_._1)

    shortestTour
  }
}
