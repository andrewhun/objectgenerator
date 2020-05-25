package com.andrewhun.object.controllers;

import com.andrewhun.object.models.JavaObject;
import com.andrewhun.object.models.Page;
import com.andrewhun.object.services.InputVerificationService;
import com.andrewhun.object.util.NamedConstants;
import com.andrewhun.object.util.WindowUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;

import java.io.File;

public class StartingPageController implements PageController {

    @FXML Button submitObjectName;
    @FXML TextField objectName;
    @FXML Label nameTakenError;

    @FXML private void initialize() {

        addNameEventHandler();
        objectName.setTextFormatter(InputVerificationService.createNameTextFormatter());
        objectName.setText(JavaObject.getCurrentObject().getName());
        submitObjectName.setDisable(noNameIsEntered());
    }

    private void addNameEventHandler() {

        EventHandler<InputEvent> handler = event -> {

            TextField nameField = (TextField)event.getSource();
            boolean isEmptyField = nameField.getText() == null || nameField.getText().equals("");
            submitObjectName.setDisable(isEmptyField);
        };

        objectName.addEventHandler(InputEvent.ANY, handler);
    }

    private boolean noNameIsEntered() {

        return objectName.getText() == null || objectName.getText().equals("");
    }

    public void checkObjectName(ActionEvent event) throws Exception {

        nameTakenError.setText("");
        InputVerificationService service = new InputVerificationService(this);
        if(service.inputIsValid()) {

            saveName(event);
        }
        else {
            service.showErrorMessage();
        }
    }

    private void saveName(ActionEvent event) throws Exception {

        JavaObject object = JavaObject.getCurrentObject();
        object.setName(objectName.getText());
        new WindowUtil(event).displayPage(Page.ADD_VARIABLES);
    }

    public Page getPage() {

        return Page.STARTING_PAGE;
    }

    public Boolean inputDetailMissing() {

        return objectName.getText() == null || objectName.getText().equals("");
    }

    public TextField getNameField() {

        return objectName;
    }

    public Boolean nameIsTaken(String name) {

        File file = new File(JavaObject.OBJECT_DIRECTORY + name + NamedConstants.METADATA_FILE_TYPE);
        return file.exists();
    }

    public void displayErrorMessage(String message) {

        nameTakenError.setText(message);
    }
}
