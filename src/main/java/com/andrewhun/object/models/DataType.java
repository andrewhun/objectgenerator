package com.andrewhun.object.models;

import com.andrewhun.object.util.NamedConstants;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataType {

    private String name;
    private String returnValue;

    public static List<DataType> getAllDataTypes() throws Exception {

        File file = new File("src/main/resources/datatypes.json");

        if(!file.exists()) {

            createBasicDataTypes();
        }

        String json = Files.readString(Paths.get(file.getPath()));
        DataType[] types = new Gson().fromJson(json, DataType[].class);
        return new ArrayList<>(Arrays.asList(types));
    }

    private static void createBasicDataTypes() throws Exception {

        List<DataType> types = new ArrayList<>();
        types.add(new DataType(NamedConstants.INTEGER, "0"));
        types.add(new DataType(NamedConstants.DOUBLE, "0.0"));
        types.add(new DataType(NamedConstants.STRING, "\"\""));
        types.add(new DataType(NamedConstants.BOOLEAN, "false"));
        types.add(new DataType(NamedConstants.VOID, ""));
        storeDataTypesInFile(types);
    }

    public static void addDataType(String name) throws Exception {

        List<DataType> types = new ArrayList<>(getAllDataTypes());
        types.add(new DataType(name));

        storeDataTypesInFile(types);
    }

    public static void removeDataType(String name) throws Exception {

        List<DataType> types = new ArrayList<>(getAllDataTypes());
        for(DataType type : types) {

            if(type.getName().equals(name)) {

                types.remove(type);
                break;
            }
        }
        storeDataTypesInFile(types);
    }

    public static DataType getDataType(String name) throws Exception {

        List<DataType> types = getAllDataTypes();
        DataType selectedType = null;

        for(DataType type : types) {

            if (type.getName().equals(name)) {
                selectedType = type;
                break;
            }
        }
        if(selectedType == null) {

            throw new Exception("Selected type not found.");
        }
        return selectedType;
    }

    private static void storeDataTypesInFile(List<DataType> types) throws Exception {

        String json = new Gson().toJson(types.toArray());
        File file = new File("src/main/resources/datatypes.json");
        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    public DataType(String name) {

        this.name = name;
        this.returnValue = "new " + name + "()";
    }


    private DataType(String name, String returnValue) {

        this.name = name;
        this.returnValue = returnValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnValue() {
        return returnValue;
    }

    @Override public String toString() {return name;}
}
