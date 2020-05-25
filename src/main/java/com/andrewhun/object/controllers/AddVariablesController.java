package com.andrewhun.object.controllers;

import com.andrewhun.object.models.*;
import com.andrewhun.object.util.NamedConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class AddVariablesController extends AddElementsController {

    @FXML ChoiceBox<String> accessRight;
    @FXML ChoiceBox<DataType> dataType;
    @FXML TextField variableName;
    @FXML CheckBox classVariable;
    @FXML CheckBox constant;
    @FXML Label variableDetailError;
    @FXML ListView<String> displayedVariables;
    @FXML Button addVariable;

    protected void setUpFields() throws Exception {

        addVariable.setDisable(true);
        accessRight.setItems(AccessRight.getAccessRights());
        List<DataType> types = DataType.getAllDataTypes();
        types.removeIf(type -> type.getName().equals(NamedConstants.VOID));
        dataType.setItems(FXCollections.observableArrayList(types));
        displayedVariables.setItems(FXCollections.observableArrayList());
        List<Variable> variables = JavaObject.getCurrentObject().getVariables();
        for(Variable variable : variables) {
            displayedVariables.getItems().add(variable.toString());
        }
        displayedVariables.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setConstantBoxHandler();
        setFieldContentHandlers();
    }

    private void setConstantBoxHandler() {

        EventHandler<ActionEvent> constantHandler = event -> {

            CheckBox box = (CheckBox) event.getSource();
            if(box.isSelected()) {

                removeWriteRights();
            }

            else {

                restoreAllRights();
            }
        };

        constant.addEventFilter(ActionEvent.ANY, constantHandler);
    }

    private void setFieldContentHandlers() {

        EventHandler<KeyEvent> handler = event -> {

                addVariable.setDisable(requiredFieldsAreEmpty());
                event.consume();
        };

        EventHandler<ActionEvent> actionHandler = event -> {

            addVariable.setDisable(requiredFieldsAreEmpty());
            event.consume();
        };

        accessRight.setOnAction(actionHandler);
        dataType.setOnAction(actionHandler);
        variableName.setOnKeyTyped(handler);
    }

    private Boolean requiredFieldsAreEmpty() {

        return (accessRightIsMissing() ||
                dataTypeIsMissing() ||
                nameIsMissing());
    }

    private Boolean accessRightIsMissing() {

        return (accessRight.getValue() == null || accessRight.getValue().equals(""));
    }

    private Boolean dataTypeIsMissing() {

        return dataType.getSelectionModel().getSelectedItem() == null;
    }

    private Boolean nameIsMissing() {

        return (variableName.getText() == null || variableName.getText().equals(""));
    }

    private void removeWriteRights() {

        ObservableList<String> originalRights = accessRight.getItems();
        ObservableList<String> allowedRights = FXCollections.observableArrayList();
        for(String originalRight : originalRights) {

            if(originalRight.contains("Write")) {
                continue;
            }
            allowedRights.add(originalRight);
        }
        accessRight.setValue("");
        accessRight.setItems(allowedRights);
    }

    private void restoreAllRights() {

        String selectedRight = accessRight.getValue();
        accessRight.setItems(AccessRight.getAccessRights());
        accessRight.setValue(selectedRight);
    }

    public TextField getNameField() {

        return variableName;
    }

    @Override
    protected void saveElements() throws Exception {

        JavaObject currentObject = JavaObject.getCurrentObject();
        List<Variable> variables = Variable.generateVariablesFromDescription(displayedVariables.getItems());
        currentObject.setVariables(variables);
    }

    @Override
    protected void clearErrorDisplay() {

        variableDetailError.setText("");
    }

    @Override
    public Boolean inputDetailMissing() {

        return (accessRight.getValue() == null ||
                accessRight.getValue().equals("") ||
                dataType.getSelectionModel().getSelectedItem() == null ||
                variableName.getText().equals(""));
    }

    @Override
    public Boolean nameIsTaken(String name) throws Exception {

        for (Variable variable : Variable.generateVariablesFromDescription(displayedVariables.getItems())) {

            if (variable.getName().equals(name)) {

                return true;
            }
        }
        return false;
    }

    @Override
    protected void addElementToDisplay() throws Exception {

        displayedVariables.getItems().add(createVariable().toString());
    }

    @Override
    public Page getPage() {return Page.ADD_VARIABLES;}

    public void displayErrorMessage(String message) {

        variableDetailError.setText(message);
    }

    protected ListView<String> getDisplayedElements() {

        return displayedVariables;
    }


    private Variable createVariable() throws Exception {

        String name = variableName.getText();
        AccessRight right = AccessRight.getSelectedAccessRight(accessRight.getValue());
        DataType type = dataType.getSelectionModel().getSelectedItem();
        Boolean constantFlag = constant.isSelected();
        Boolean classVariableFlag = classVariable.isSelected();
        return Variable.createVariable(right, classVariableFlag, constantFlag, type, name);
    }
}