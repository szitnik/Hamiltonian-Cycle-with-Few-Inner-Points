package si.zitnik.dr.mmr.ui.views

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.control.Button
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.layout.StackPane
import javafx.scene.{Parent, Scene}
import javafx.fxml
import fxml.FXMLLoader

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 7/10/12
 * Time: 7:26 PM
 * To change this template use File | Settings | File Templates.
 */

class MainWindow extends Application {
  private val title = "Hamiltonian Cycle with Few Inner Points"


  override
  def start(primaryStage: Stage) {
    val root: Parent = FXMLLoader.load(this.getClass().getClassLoader().getResource("fxml_mainWindow.fxml"))
    primaryStage.setTitle(title)
    primaryStage.setScene(new Scene(root))
    primaryStage.show()
  }
}

object MainWindow {
  def main(args: Array[String]) {
    Application.launch(classOf[MainWindow], args: _*)
  }
}
