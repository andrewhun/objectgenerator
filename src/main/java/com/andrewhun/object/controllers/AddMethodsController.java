package com.andrewhun.object.controllers;

import com.andrewhun.object.models.*;
import com.andrewhun.object.util.WindowUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class AddMethodsController extends AddElementsController {

    @FXML ChoiceBox<String> accessModifier;
    @FXML ChoiceBox<DataType> dataType;
    @FXML TextField methodName;
    @FXML CheckBox classMethod;
    @FXML CheckBox constant;
    @FXML Label methodError;
    @FXML ListView<String> displayedMethods;
    @FXML Button addMethod;

    protected void setUpFields() throws Exception {

        addMethod.setDisable(true);
        accessModifier.setItems(AccessModifier.getAccessModifiers());
        dataType.setItems(FXCollections.observableArrayList(DataType.getAllDataTypes()));
        displayedMethods.setItems(FXCollections.observableArrayList());
        List<Method> methods = JavaObject.getCurrentObject().getMethods();
        for(Method method : methods) {

            displayedMethods.getItems().add(method.toString());
        }
        setFieldContentHandlers();
    }

    private void setFieldContentHandlers() {

        EventHandler<ActionEvent> actionHandler = event -> {

            addMethod.setDisable(requiredFieldsAreMissing());
            event.consume();
        };

        EventHandler<KeyEvent> keyHandler = event -> {

            addMethod.setDisable(requiredFieldsAreMissing());
            event.consume();
        };

        accessModifier.setOnAction(actionHandler);
        dataType.setOnAction(actionHandler);
        methodName.setOnKeyTyped(keyHandler);
    }

    private Boolean requiredFieldsAreMissing() {

        return (accessModifierIsMissing() ||
                dataTypeIsMissing() ||
                methodNameIsMissing());
    }

    private Boolean accessModifierIsMissing() {

        return accessModifier.getValue() == null || accessModifier.getValue().equals("");
    }

    private Boolean dataTypeIsMissing() {

        return dataType.getSelectionModel().getSelectedItem() == null;
    }

    private Boolean methodNameIsMissing() {

        return methodName.getText() == null || methodName.getText().equals("");
    }

    public TextField getNameField() {

        return methodName;
    }

    public void createObject(ActionEvent event) throws Exception {

        saveElements();
        JavaObject object = JavaObject.getCurrentObject();
        object.create();
        clearObjectValues(object);

        new WindowUtil(event).displayPage(Page.STARTING_PAGE);
    }

    public void saveElements() throws Exception {

        JavaObject currentObject = JavaObject.getCurrentObject();
        List<Method> methods = Method.generateMethodsFromDescription(displayedMethods.getItems());
        currentObject.setMethods(methods);
    }

    private void clearObjectValues(JavaObject object) {

        object.setName("");
        object.setVariables(new ArrayList<>());
        object.setMethods(new ArrayList<>());
    }

    public void clearErrorDisplay() {

        methodError.setText("");
    }
    public void addElementToDisplay() throws Exception {

        displayedMethods.getItems().add(createMethod().toString());
    }
    public Page getPage() {return Page.ADD_METHODS;}
    public Boolean inputDetailMissing() {

        return methodName.getText() == null ||
                methodName.getText().equals("") ||
                accessModifier.getValue() == null ||
                accessModifier.getValue().equals("") ||
                dataType.getSelectionModel().getSelectedItem() == null;
    }

    public Boolean nameIsTaken(String name) throws Exception {

        for(Method method : Method.generateMethodsFromDescription(displayedMethods.getItems())) {

            if (method.getName().equals(methodName.getText())) {

                return true;
            }
        }
        return false;
    }
    public void displayErrorMessage(String message) {

        methodError.setText(message);
    }
    public ListView<String> getDisplayedElements() {

        return displayedMethods;
    }

    private Method createMethod() throws Exception {

        String name = methodName.getText();
        AccessModifier modifier = AccessModifier.getSelectedAccessModifier(accessModifier.getValue());
        DataType type = dataType.getSelectionModel().getSelectedItem();
        boolean classMethodFlag = classMethod.isSelected();
        boolean constantFlag = constant.isSelected();
        return Method.createMethod(modifier, classMethodFlag, constantFlag, type, name);
    }
}

