package si.zitnik.dr.mmr.ui

import javax.swing.JPanel
import java.awt.{Color, Graphics}
import collection.mutable.ArrayBuffer
import si.zitnik.dr.mmr.domain.Point

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 8/27/12
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
class GraphPanel(val points: Array[Point], showCoords: Boolean) extends JPanel {
  this.setBackground(Color.WHITE)
  val padding = 45
  val pointSize = 7

  val xMin = points.minBy(_.x).x
  val xMax = points.maxBy(_.x).x

  val yMin = points.minBy(_.y).y
  val yMax = points.maxBy(_.y).y

  def getPosition(point: Point) = {
    val width = this.getWidth()-2*padding
    val height = this.getHeight()-2*padding

    val xUnit = width*1./(xMax-xMin)
    val yUnit = height*1./(yMax-yMin)

    //interpolate x
    val x = (point.x-xMin)*xUnit + padding/2

    //interpolate y
    val y = this.getHeight() - (point.y-yMin)*yUnit - padding

    (x.round.toInt ,y.round.toInt)
  }

  override def paintComponent(g: Graphics) {
    super.paintComponent(g)

    val interpolatedPoints = points.map(getPosition(_))

    //draw lines
    g.setColor(Color.BLACK)
    for (i <- 0 until interpolatedPoints.size-1) {
      g.drawLine(interpolatedPoints(i)._1, interpolatedPoints(i)._2, interpolatedPoints(i+1)._1, interpolatedPoints(i+1)._2)
    }
    g.drawLine(interpolatedPoints(0)._1, interpolatedPoints(0)._2, interpolatedPoints.last._1, interpolatedPoints.last._2)

    //draw points
    g.setColor(Color.RED)
    for ((point, realPoint) <- interpolatedPoints.zip(points)) {
      g.fillOval(point._1-pointSize/2, point._2-pointSize/2, pointSize, pointSize)
    }

    if (showCoords) {
      g.setColor(Color.RED)
      for ((point, realPoint) <- interpolatedPoints.zip(points)) {
        g.drawString(realPoint.toString, point._1-15, point._2+20)
      }
    }
  }
}
