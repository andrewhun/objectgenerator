package com.andrewhun.object.util;

import com.andrewhun.object.models.Page;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowUtil {

    private Stage stage;

    public WindowUtil(ActionEvent event) {

        //this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        this.stage = getStageFromActionEvent(event);
    }

    public WindowUtil (Stage stage) {
        this.stage = stage;
    }

    public void displayPage(Page page) throws Exception {

        Parent root = new FXMLLoader(getClass().getResource(page.getPath())).load();

        /*
        * The prefHeight and prefWidth methods on the Parent object are somewhat unusual.
        * Even though they require a double parameter, what we enter does not seem to
        * influence the returned values (which are the preferred properties we set in the FXML files)
        */
        stage.setMinHeight(root.prefHeight(0.0));
        stage.setMinWidth(root.prefWidth(0.0));
        stage.hide();
        stage.setTitle(page.getTitle());
        Scene scene = new Scene(root);

        String css = getClass().getResource("/assets/styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStageFromActionEvent(ActionEvent event) {

        return (Stage) ((Node)event.getSource()).getScene().getWindow();
    }
}
