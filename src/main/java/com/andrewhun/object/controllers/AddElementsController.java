package com.andrewhun.object.controllers;

import com.andrewhun.object.models.Page;
import com.andrewhun.object.services.InputVerificationService;
import com.andrewhun.object.util.WindowUtil;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;

import java.util.List;


public abstract class AddElementsController implements PageController {

    public void initialize() throws Exception {

        setUpFields();
        getNameField().setTextFormatter(InputVerificationService.createNameTextFormatter());
    }

    public void backToPreviousPage(ActionEvent event) throws Exception {
        saveElements();
        Page previousPage = getPage().getPreviousPage();
        if(previousPage != null) {
            new WindowUtil(event).displayPage(previousPage);
        }
    }

    public void submitElements(ActionEvent event) throws Exception {
        saveElements();
        Page nextPage = getPage().getNextPage();
        if(nextPage != null) {
            new WindowUtil(event).displayPage(nextPage);
        }
    }

    public void addElement() throws Exception {

        clearErrorDisplay();
        InputVerificationService service = new InputVerificationService(this);
        if(service.inputIsValid()) {

            addElementToDisplay();
        }
        else {
            service.showErrorMessage();
        }
    }

    public void removeElements() {

        ListView<String> displayedElements = getDisplayedElements();
        List<String> selectedElements = displayedElements.getSelectionModel().getSelectedItems();
        displayedElements.getItems().removeAll(selectedElements);
    }

    protected abstract void saveElements() throws Exception;
    protected abstract void clearErrorDisplay();
    protected abstract void addElementToDisplay() throws Exception;
    protected abstract ListView<String> getDisplayedElements();
    protected abstract void setUpFields() throws Exception;
}
