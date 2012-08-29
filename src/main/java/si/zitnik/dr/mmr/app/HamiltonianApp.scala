package si.zitnik.dr.mmr.app

import io.Source
import java.util.Scanner
import collection.mutable.ArrayBuffer
import si.zitnik.dr.mmr.domain.Point
import si.zitnik.dr.mmr.util.MMRConversions._
import si.zitnik.dr.mmr.util.RandomGraph
import si.zitnik.dr.mmr.algorithms.abst.Algorithm
import si.zitnik.dr.mmr.algorithms.{Algorithm2, Algorithm1SpaceOptimized, Algorithm1}
import si.zitnik.dr.mmr.ui.GraphFrame

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 8/29/12
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
object HamiltonianApp {
  var end = false
  val sc = new Scanner(Source.stdin.reader())

  var points: ArrayBuffer[Point] = null
  var algorithm: Algorithm = null

  def main(args: Array[String]) {
    printMainMenu()
  }

  def printMainMenu() {
    try {
      println("\n\n#### MAIN MENU ####\n\nHamiltonian Cycle finder for Graphs with Few Inner Points!\n")
      println(" 1 - Read points from file")
      println(" 2 - Read points from standard input")
      println(" 3 - Use point random generator")
      println(" 4 - Exit")
      print("Selection: ")

      while (!end) {
        sc.nextLine() match {
          case "1" => inputFromFile()
          case "2" => inputByStdin()
          case "3" => randomGenerate()
          case "4" => end = true
        }
      }
    } catch {
      case e: Exception => {
        println("Error, please try again!")
        printMainMenu()
      }
    }
  }

  //// STEP 2

  def inputFromFile() {
    println("\n\nReading points from file. Each line in file should contain two double values, separated with space (e.g. \"1.0 2.1\").")
    print("Enter filename: ")
    val file = sc.nextLine()

    points = new ArrayBuffer[Point]()
    Source.fromFile(file).getLines().foreach(line => {
      val splitLine = line.split(" ")
      points.append((splitLine(0).toDouble, splitLine(1).toDouble))
    })
    algorithmSelection()
  }

  def inputByStdin() {
    println("\n\nReading points from standard input. Each line in file should contain two double values, separated with space (e.g. \"1.0 2.1\"). Input is finished by line \"EOP\".")
    println("Enter points: ")
    points = new ArrayBuffer[Point]()

    var pointsEnd = false
    while (!pointsEnd) {
      val line = sc.nextLine()
      line match {
        case "EOP" => pointsEnd = true
        case _ => {
          val splitLine = line.split(" ")
          points.append((splitLine(0).toDouble, splitLine(1).toDouble))
        }
      }
    }
    algorithmSelection()
  }

  def randomGenerate() {
    println("\n\nCreate random graph.")
    print("Enter n (# of convex hull points): ")
    val n = sc.nextLine().toInt
    print("Enter k (# of inner points): ")
    val k = sc.nextLine().toInt

    points = RandomGraph.create(n,k)
    algorithmSelection()
  }

  /// STEP 3

  def algorithmSelection() {
    println("\n\nSelect algorithm\n")

    println(" 1 - Algorithm1")
    println(" 2 - Algorithm1SpaceOptimized")
    println(" 3 - Algorithm2")
    print("Selection: ")
    sc.nextLine() match {
      case "1" => algorithm = new Algorithm1(points)
      case "2" => algorithm = new Algorithm1SpaceOptimized(points)
      case "3" => algorithm = new Algorithm2(points)
    }

    println("Running selected algorithm ...")
    val result = algorithm.compute()
    println("Calculation finished!")
    println("\n\nCycle length: %.2f".format(result._1))
    println("Points chain: %s".format(result._2.mkString("("," -> ", ")")))

    print("\n\nDo you want to visualize the result? [yes]: ")
    if (!sc.nextLine().toLowerCase.contains("no")) {
      new GraphFrame(result._2)
    }

    printMainMenu()
  }
}
