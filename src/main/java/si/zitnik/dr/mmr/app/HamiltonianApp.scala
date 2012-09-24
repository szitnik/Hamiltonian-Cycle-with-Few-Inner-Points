package si.zitnik.dr.mmr.app

import io.Source
import java.util.Scanner
import collection.mutable.ArrayBuffer
import si.zitnik.dr.mmr.domain.Point
import si.zitnik.dr.mmr.util.MMRConversions._
import si.zitnik.dr.mmr.util.RandomGraph
import si.zitnik.dr.mmr.algorithms.abst.Algorithm
import si.zitnik.dr.mmr.algorithms.{Algorithm2, Algorithm1SpaceOptimized, Algorithm1}
import si.zitnik.dr.mmr.ui.{GraphFrame}
import java.io.{FileWriter, File}

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 8/29/12
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
object HamiltonianApp {

  def main(args: Array[String]) {
    if (args.length > 0 && args.map(_.toLowerCase()).contains("cmd")) { //command-based
      CMDRunner.processArguments(args)
    } else { //interactive
      InteractiveRunner.printMainMenu()
    }
  }


}

object InteractiveRunner {
  var end = false
  val sc = new Scanner(Source.stdin.reader())

  var points: ArrayBuffer[Point] = null
  var algorithm: Algorithm = null

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
      print("\n\nDo you want to draw point coordinates? [yes]: ")
      if (!sc.nextLine().toLowerCase.contains("no")) {
        new GraphFrame(result._2)
      } else {
        new GraphFrame(result._2, false)
      }
    }

    printMainMenu()
  }
}

object CMDRunner {
  val sc = new Scanner(Source.stdin.reader())

  def savePoints(filename: String, points: ArrayBuffer[Point]) {
    val fw = new FileWriter(filename)
    points.foreach(p => {fw.write("%.2f %.2f\n".format(p.x, p.y))})
    fw.close()
  }

  def processArguments(args: Array[String]) {
    val config = new Config()
    val str = "cmd"
    var h = false
    val parser = new scopt.mutable.OptionParser("HamiltonianApp", "0.1") {
      arg("cmd", "Command line type of execution. If not set, interactive shell is started.", { v: String =>  str })
      argOpt("help", "Prints help.", { v: String =>  h = true })

      opt("p", "points", "[FILE, STDIN, RANDOM]", "STDIN end line = EOP, Default: RANDOM", { v: String => config.points = v })

      opt("f", "file", "<file>", "File if FILE point selected. Default: null", { v: String => config.filename = v })
      intOpt("n", "N", "Number of convex hull points if RANDOM selected. Default: 10", { v: Int => config.n = v })
      intOpt("k", "K", "Number of inner points if RANDOM selected. Default: 3", { v: Int => config.k = v })

      intOpt("a", "alg", "[1,2,3]", "1- Algorithm1, 2- Space Optimized Algorithm1, 3-Algorithm2, Default: 1", { v: Int => config.alg = v })

      booleanOpt("v", "vis", "Visualize result, Default: true", { v: Boolean => config.visualize = v })
      booleanOpt("c", "coords", "Show coordinates, Default: false", { v: Boolean => config.coords = v })
      opt("s", "save", "<file>", "Save graph to a file.", { v: String => config.saveFile = v })

    }
    if (parser.parse(args) && !h) {
      // do stuff

      //get points
      var points = new ArrayBuffer[Point]()
      config.points match {
        case "FILE" => {
          if (config.filename == null) {println("ERROR: filename not set!")}
          Source.fromFile(config.filename).getLines().foreach(line => {
            val splitLine = line.split(" ")
            points.append((splitLine(0).toDouble, splitLine(1).toDouble))
          })
        }
        case "STDIN" => {
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
        }
        case "RANDOM" => { points = RandomGraph.create(config.n,config.k) }
        case _ => println("ERROR: Unsupported points input type!")
      }

      if (config.saveFile != null) {
        savePoints(config.saveFile, points)
      }

      //algorithm selection
      var algorithm: Algorithm = null
      config.alg match {
        case 1 => algorithm = new Algorithm1(points)
        case 2 => algorithm = new Algorithm1SpaceOptimized(points)
        case 3 => algorithm = new Algorithm2(points)
        case _ => println("ERROR: Unsupported algorithm type!")
      }

      println("Running selected algorithm ...")
      val result = algorithm.compute()
      println("Calculation finished!")
      println("\n\nCycle length: %.2f".format(result._1))
      println("Points chain: %s".format(result._2.mkString("("," -> ", ")")))

      //results visualization
      if (config.visualize) {
        new GraphFrame(result._2, config.coords)

        println("Press ENTER to exit ...")
        while (sc.hasNextLine()) {
          val line = sc.nextLine()
          if (line.equals("")) {
            System.exit(0)
          }
        }
      }

    }
    else {
      // arguments are bad, usage message will have been displayed
      parser.showUsage
    }
  }

}

case class Config(var points: String = "RANDOM", var filename: String = null,
                  var n: Int = 10, var k: Int = 3, var alg: Int = 1,
                  var visualize: Boolean = true, var coords: Boolean = false,
                  var saveFile: String = null)
