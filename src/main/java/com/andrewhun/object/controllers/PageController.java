package com.andrewhun.object.controllers;

import com.andrewhun.object.models.Page;
import javafx.scene.control.TextField;

public interface PageController {

    Page getPage();
    Boolean inputDetailMissing();
    Boolean nameIsTaken(String name) throws Exception;
    void displayErrorMessage(String message);
    TextField getNameField();
}
