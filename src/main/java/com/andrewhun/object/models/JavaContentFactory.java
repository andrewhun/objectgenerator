package com.andrewhun.object.models;

import java.util.List;

public class JavaContentFactory {

    private static final String PUBLIC = "public";
    private static final String NEWLINE = "\n";
    private static final String TAB = "\t";
    private static final String PRIVATE = "private";
    private static final String STATIC = "static";
    private static final String FINAL = "final";

    private JavaObject object;

    public JavaContentFactory(JavaObject object) {

        this.object = object;
    }

    String generateContent() {

        String content = createClassHeader();
        content += createVariableDeclarations(object.getVariables());
        content += createMethodBodies(object.getMethods());
        content += createGettersAndSetters(object.getVariables());

        content = content.stripTrailing();
        content += addClosingBracket();
        return content;
    }

    private String createClassHeader() {

        String header = "";
        header += PUBLIC + " class " + object.getName() + " {";
        header += NEWLINE;
        header += NEWLINE;
        return header;
    }

    private String createVariableDeclarations(List<Variable> variables) {

        StringBuilder declarations = new StringBuilder();
        if (variables.size() > 0) {

            for (Variable variable : variables) {

                declarations.append(generateDeclaration(variable));
            }
            declarations.append(NEWLINE);

        }
        return declarations.toString();
    }

    private String generateDeclaration(Variable variable) {

        String declaration = "";
        declaration += TAB;
        declaration += PRIVATE + " ";
        if (variable.getClassElement()) {

            declaration += STATIC + " ";
        }

        if (variable.getConstant()) {

            declaration += FINAL + " ";
        }

        declaration += variable.getDataType().toString() + " ";
        declaration += variable.getName() + ";";
        declaration += NEWLINE;
        return declaration;
    }

    private String createMethodBodies(List<Method> methods) {

        StringBuilder methodBodies = new StringBuilder();
        for(Method method : methods) {

            methodBodies.append(generateMethodBody(method));

        }
        return methodBodies.toString();
    }


    private String generateMethodBody(Method method) {

        String body = "";
        body += TAB;
        body += getAccessModifier(method);

        if (method.getClassElement()) {

            body += STATIC + " ";
        }

        if (method.getConstant()) {

            body += FINAL + " ";
        }

        body += method.getDataType().toString() + " ";
        body += method.getName() + "() {";
        body += NEWLINE;
        body += NEWLINE;
        body += TAB;
        body += TAB;
        body += "return " + method.getDataType().getReturnValue() + ";";
        body += NEWLINE;
        body += TAB;
        body += "}";
        body += NEWLINE;
        body += NEWLINE;
        return body;
    }

    private String getAccessModifier(Method method) {

        if (method.getAccessModifier() == AccessModifier.PACKAGE_PRIVATE) {

            return "";
        }

        else {

            return method.getAccessModifier().toString().toLowerCase() + " ";
        }
    }

    private String createGettersAndSetters(List<Variable> variables) {

        StringBuilder gettersAndSetters = new StringBuilder();

        for (Variable variable : variables) {

            if(variable.getAccessRight() == AccessRight.READ_ONLY ||
                    variable.getAccessRight() == AccessRight.READ_WRITE) {
                gettersAndSetters.append(createGetter(variable));
                gettersAndSetters.append(NEWLINE);
                gettersAndSetters.append(NEWLINE);
            }

            if(variable.getAccessRight() == AccessRight.WRITE_ONLY ||
                    variable.getAccessRight() == AccessRight.READ_WRITE) {

                gettersAndSetters.append(createSetter(variable));
                gettersAndSetters.append(NEWLINE);
                gettersAndSetters.append(NEWLINE);
            }
        }
        return gettersAndSetters.toString();
    }

    private String createGetter(Variable variable) {

        String getter = "";
        getter += TAB;
        getter += PUBLIC + " ";
        if(variable.getClassElement()) {

            getter += STATIC + " ";
        }

        getter += variable.getDataType().toString() + " ";
        getter += "get" + capitalizeWord(variable.getName()) + "() {";
        getter += NEWLINE;
        getter += NEWLINE;
        getter += TAB;
        getter += TAB;
        getter += "return " + variable.getName() + ";";
        getter += NEWLINE;
        getter += TAB;
        getter += "}";
        return getter;
    }

    private String createSetter(Variable variable) {

        String setter = "";
        setter += TAB;
        setter += PUBLIC + " ";
        if(variable.getClassElement()) {

            setter += STATIC + " ";
        }
        setter += "void set" + capitalizeWord(variable.getName());
        setter += "(" + variable.getDataType().toString() + " " + variable.getName() + ") {";
        setter += NEWLINE;
        setter += NEWLINE;
        setter += TAB;
        setter += TAB;
        setter += "this." + variable.getName() + " = "  + variable.getName() + ";";
        setter += NEWLINE;
        setter += TAB;
        setter += "}";
        return setter;
    }

    private String addClosingBracket() {

        String content = "";
        content += NEWLINE;
        content += "}";
        return content;
    }

    private String capitalizeWord(String word) {

        assert word.length() > 0;
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }
}