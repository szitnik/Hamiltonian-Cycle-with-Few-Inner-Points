package si.zitnik.dr.mmr.ui.controllers

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.text.Text

/**
 * Created with IntelliJ IDEA.
 * User: slavkoz
 * Date: 7/11/12
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */

class FXMLExampleController {
  @FXML var actiontarget: Text = null

  @FXML
  protected def handleSubmitButtonAction(event: ActionEvent) {
    actiontarget.setText("Sign in button pressed")
  }
}
