/*
This enumerated type represents the pages which make up the application's view. The order of the elements below
determines in what order the pages appear to users. This is because the ordinal values of the elements are part of the
business logic (please see the AddElementsController abstract class). For further documentation on ordinal values,
please refer to https://docs.oracle.com/javase/7/docs/api/java/lang/Enum.html.
*/
package com.andrewhun.object.models;

public enum Page {

    STARTING_PAGE("Object Creator", "/fxml/StartingPage.fxml", "object"),
    ADD_VARIABLES("Add Variables","/fxml/AddVariables.fxml", "variable"),
    ADD_METHODS("Add Methods", "/fxml/AddMethods.fxml", "method"),
    PROJECT_SELECTION("Project Selection", "/fxml/ProjectSelection.fxml", "");

    private final String title;
    private final String path;
    private final String relatedObject;

    Page(String title, String path, String relatedObject) {

        this.title = title;
        this.path = path;
        this.relatedObject = relatedObject;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public String getInvalidNameError() {

        return "The " + relatedObject + " name you have entered is invalid.";
    }

    public String getDetailMissingError() {

        return "Please enter all " + relatedObject + " details before proceeding.";
    }

    public String getNameTakenError() {

        return "The " + relatedObject + " name you have entered is taken.";
    }

    public Page getPreviousPage() {

        if(ordinal() == 0) {
            return null;
        }
        return Page.values()[ordinal() - 1];
    }

    public Page getNextPage() {

        if(Page.values().length - 1 == ordinal()) {

            return null;
        }
        return Page.values()[ordinal() + 1];
    }
}