// The purpose of this object is to act as an interface between business logic and file operations

package com.andrewhun.object.models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.andrewhun.object.util.NamedConstants;
import com.google.gson.Gson;

public class JavaObject {

    public static final String OBJECT_DIRECTORY = "src/main/resources/objects/";
    public static final String JAVA_EXTENSION = ".java";

    private String name;
    private List<Variable> variables;
    private List<Method> methods;

    private static JavaObject currentObject;

    private JavaObject() {

        variables = new ArrayList<>();
        methods = new ArrayList<>();
    }

    public static JavaObject getCurrentObject() {

        if(currentObject == null) {

            currentObject = new JavaObject();
        }
        return currentObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public static JavaObject getObject(String name) throws IOException {

        String path = OBJECT_DIRECTORY + name + NamedConstants.METADATA_FILE_TYPE;

        File file = new File(path);
        if(file.exists()) {

            String json = Files.readString(Paths.get(path));
            return new Gson().fromJson(json, JavaObject.class);
        }
        return new JavaObject();
    }

    public void create() throws Exception {

        String json = new Gson().toJson(this);
        createFile(json, NamedConstants.METADATA_FILE_TYPE);
        createFile(new JavaContentFactory(this).generateContent(), JAVA_EXTENSION);
        DataType.addDataType(getName());
    }


    private void createFile(String content, String fileExtension) throws IOException {

        File file = new File(OBJECT_DIRECTORY + getName() + fileExtension);
        if(file.exists()) {

            file.delete();
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.flush();
        writer.close();
    }
}
