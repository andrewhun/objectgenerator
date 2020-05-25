package com.andrewhun.object.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum AccessRight {
    PRIVATE ("Private"),
    READ_ONLY ("Read-Only"),
    WRITE_ONLY("Write-Only"),
    READ_WRITE ("Read/Write");

    private final String name;

    AccessRight(String name) {

        this.name = name;
    }

    public boolean equalsName(String otherName) {

        return name.equals(otherName);
    }

    public static ObservableList<String> getAccessRights() {

        ObservableList<String> rights = FXCollections.observableArrayList("");
        for(AccessRight right : AccessRight.values()) {

            rights.add(right.toString());
        }
        return rights;
    }

    public static AccessRight getSelectedAccessRight(String name) throws Exception {

        for(AccessRight right : AccessRight.values()) {

            if(right.equalsName(name)) {

                return right;
            }
        }
        throw new Exception("The selected access right is not recognised");
    }

    public String toString() {
        return this.name;
    }
}
