package si.zitnik.dr.mmr.ui

import javax.swing.{WindowConstants, JFrame}
import java.awt.{Color, BorderLayout}
import si.zitnik.dr.mmr.util.RandomGraph
import si.zitnik.dr.mmr.domain.Point
import collection.mutable.ArrayBuffer
import si.zitnik.dr.mmr.algorithms.Algorithm1SpaceOptimized

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 8/27/12
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
class GraphFrame extends JFrame {

  def this(hamiltonianCycle: Array[Point], showCoords: Boolean = true, title: String = "") = {
    this()

    this.setTitle("Hamiltonian Cycle Visualizer" + " - " + title)
    this.setSize(600,600)

    val content = this.getContentPane()
    content.setLayout(new BorderLayout())
    content.setBackground(Color.WHITE)

    val panel = new GraphPanel(hamiltonianCycle, showCoords)
    content.add(panel, BorderLayout.CENTER)

    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
    this.setVisible(true)

    this
  }


}

object GraphFrame {
  def main(args: Array[String]) {
    val result = new Algorithm1SpaceOptimized(RandomGraph.create(60, 10)).compute()
    new GraphFrame(result._2)
  }
}
