package com.andrewhun.object.models;

public class Project {

    private String name;
    private String path;

    public static final String PROJECT_DIRECTORY = "src/resources/projects";

    public Project(String path) {

        this.name = path.substring(path.lastIndexOf("/"));
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
