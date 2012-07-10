package si.zitnik.dr.mmr.ui

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.control.Button
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.layout.StackPane
import javafx.scene.Scene

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
    primaryStage.setTitle(title)

    val btn = new Button("Say Hello World!")
    btn.setOnAction(new EventHandler[ActionEvent] {
      override
      def handle(event: ActionEvent) {
        println("Hello World!")
      }
    })

    val root = new StackPane()
    root.getChildren().add(btn)
    primaryStage.setScene(new Scene(root, 300, 250))
    primaryStage.show()
  }
}

object MainWindow {
  def main(args: Array[String]) {
    Application.launch(classOf[MainWindow], args: _*)
  }
}
