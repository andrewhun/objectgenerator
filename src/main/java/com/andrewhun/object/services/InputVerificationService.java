package com.andrewhun.object.services;

import com.andrewhun.object.controllers.PageController;
import com.andrewhun.object.models.Page;
import javafx.scene.control.TextFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class InputVerificationService {

    private PageController controller;

    public InputVerificationService(PageController controller) {

        this.controller = controller;
    }

    // Source: https://en.wikipedia.org/wiki/List_of_Java_keywords
    /* Added "Private" and the Data Type keywords ("Integer", etc.), because they are used
    by the application. Leaving them available is error-prone due to the way variables and methods are generated from
    their descriptions.*/
    public static final List<String> RESERVED_WORDS = new ArrayList<>(
            Arrays.asList("abstract", "assert", "boolean", "break", "byte", "case",
                    "catch", "char", "class", "continue", "default", "do", "double", "else", "enum", "exports", "extends",
                    "final", "finally", "float", "for", "if", "implements", "import", "instanceof", "int", "interface",
                    "long", "module", "native", "new", "package", "private", "protected", "public", "requires", "return",
                    "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient",
                    "try", "void", "volatile", "while", "true", "null", "false", "var", "const", "goto",
                    "Private", "Integer", "Double", "String", "Boolean"));

    public static TextFormatter<String> createNameTextFormatter() {

        UnaryOperator<TextFormatter.Change> filter = change -> {

            String text = change.getControlNewText();
            if (text.equals("") ||
                    text.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$")) {

                return change;
            }
            return null;
        };

        return new TextFormatter<>(filter);
    }

    public Boolean inputIsValid() throws Exception {

        String name = controller.getNameField().getText();
        return !controller.inputDetailMissing() &&
                !RESERVED_WORDS.contains(name) &&
                !controller.nameIsTaken(name);
    }

    public void showErrorMessage() throws Exception {

        controller.displayErrorMessage(getErrorMessage());
    }

    private String getErrorMessage() throws Exception {

        Page page = controller.getPage();
        String name = controller.getNameField().getText();

        if(controller.inputDetailMissing()) {

            return page.getDetailMissingError();
        }
        else if(RESERVED_WORDS.contains(name)) {

            return page.getInvalidNameError();
        }
        else if(controller.nameIsTaken(name)) {

            return page.getNameTakenError();
        }
        else {

            return "";
        }
    }
}
