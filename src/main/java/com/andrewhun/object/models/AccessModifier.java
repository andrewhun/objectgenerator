package com.andrewhun.object.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum AccessModifier {

    PRIVATE("Private"),
    PACKAGE_PRIVATE("Package-private"),
    PROTECTED("Protected"),
    PUBLIC("Public");

    private String name;

    AccessModifier(String name) {

        this.name = name;
    }

    public boolean equalsName(String otherName) {

        return name.equals(otherName);
    }

    public static ObservableList<String> getAccessModifiers() {

        ObservableList<String> modifiers = FXCollections.observableArrayList("");
        for(AccessModifier modifier : AccessModifier.values()) {

            modifiers.add(modifier.toString());
        }
        return modifiers;
    }

    public static AccessModifier getSelectedAccessModifier(String name) throws Exception {

        for(AccessModifier modifier : AccessModifier.values()) {

            if(modifier.equalsName(name)) {

                return modifier;
            }
        }
        throw new Exception("The selected access modifier is not recognised");
    }

    public String toString() {
        return this.name;
    }
}
