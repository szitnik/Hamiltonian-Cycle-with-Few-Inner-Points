package si.zitnik.dr.mmr.algorithms.abst

import collection.mutable.ArrayBuffer
import si.zitnik.dr.mmr.domain.Point

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 8/2/12
 * Time: 11:20 PM
 * To change this template use File | Settings | File Templates.
 */

abstract class Algorithm(private val points: ArrayBuffer[Point]) {

  /**
   * Hamiltonian Cycle computation
   * @return (points, distance)
   */
  def compute(): (Double, Array[Point])

}
