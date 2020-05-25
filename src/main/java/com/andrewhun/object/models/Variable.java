package com.andrewhun.object.models;

import java.util.ArrayList;
import java.util.List;

public class Variable extends Element {

    private AccessRight accessRight;

    private Variable() {}

    public AccessRight getAccessRight() {
        return accessRight;
    }

    void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }

    public static Variable createVariable(AccessRight accessRight, Boolean classElement, Boolean constant,
                                          DataType dataType, String name) {

        Variable variable = new Variable();
        variable.setAccessRight(accessRight);
        variable.setClassElement(classElement);
        variable.setConstant(constant);
        variable.setDataType(dataType);
        variable.setName(name);
        assertVariableValues(variable);
        return variable;
    }

    public static List<Variable> generateVariablesFromDescription(List<String> descriptions) throws Exception {

        List<Variable> variables = new ArrayList<>();
        for(String description : descriptions) {

            Variable variable = (Variable)populateElement(new Variable(), description);
            for(AccessRight right : AccessRight.values()) {

                if (description.contains(right.toString())) {

                    variable.setAccessRight(right);
                    break;
                }
            }
            assertVariableValues(variable);
            variables.add(variable);
        }
        return variables;
    }

    protected String getAccessDescription() {

        return accessRight.toString();
    }

    private static void assertVariableValues(Variable variable) {

        assert variable.getAccessRight() != null : "Access right is missing";
        assert variable.getClassElement() != null : "Class variable flag is missing";
        assert variable.getConstant() != null : "Constant flag is missing";
        assert variable.getDataType() != null : "Data type is missing";
        assert variable.getName() != null : "Name is missing";

        assert !variable.getConstant() || (variable.getAccessRight() != AccessRight.WRITE_ONLY &&
                variable.getAccessRight() != AccessRight.READ_WRITE) : "Constants cannot have write access";
    }
}
