package com.andrewhun.object.models;

public abstract class Element {

    protected Boolean classElement;
    protected Boolean constant;
    protected DataType dataType;
    protected String name;

    protected abstract String getAccessDescription();

    public Boolean getClassElement() {
        return classElement;
    }

    public void setClassElement(Boolean classElement) {
        this.classElement = classElement;
    }

    public Boolean getConstant() {
        return constant;
    }

    public void setConstant(Boolean constant) {
        this.constant = constant;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public String toString() {

        String description = "";
        description += getAccessDescription() + " ";
        if(getClassElement()) {
            description += "Static ";
        }
        if(getConstant()) {
            description += "Final ";
        }
        description += getDataType().toString() + " ";
        description += getName();
        return description;
    }

    protected static Element populateElement(Element element, String description) throws Exception {

        boolean classElement = isClassElement(description);
        boolean constant = isConstant(description);
        DataType dataType = getDataType(description);
        String name = getName(description);

        element.setClassElement(classElement);
        element.setConstant(constant);
        element.setDataType(dataType);
        element.setName(name);
        return element;
    }

    private static boolean isClassElement(String description) {

        return description.contains("Static");
    }

    private static boolean isConstant(String description) {

        return description.contains("Final");
    }

    private static DataType getDataType(String description) throws Exception {

        for(DataType type : DataType.getAllDataTypes()) {

            if (description.contains(type.toString())) {

                return type;
            }
        }
        return null;
    }

    private static String getName (String description) {

        String[] values = description.split(" ");
        return values[values.length - 1];
    }
}
