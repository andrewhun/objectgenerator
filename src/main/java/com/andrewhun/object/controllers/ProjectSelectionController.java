package com.andrewhun.object.controllers;

import com.andrewhun.object.util.WindowUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class ProjectSelectionController {

    static final String SOURCE = "src";
    static final String MAIN = "main";
    static final String JAVA = "java";
    //static final String PROJECTS_PATH = "src/resources/projects";

    @FXML Button addNewProject;

    public void addNewProject(ActionEvent event) throws Exception {

        Stage stage = WindowUtil.getStageFromActionEvent(event);
        File rootDir = new DirectoryChooser().showDialog(stage);
        createProject(rootDir);
    }

    void createProject(File rootDir) throws Exception {


        File sourceDir = getSubDirectory(rootDir, SOURCE);
        File mainDir = getSubDirectory(sourceDir, MAIN);
        File javaDir = getSubDirectory(mainDir, JAVA);
    }

    private File getSubDirectory(File rootDir, String folderName) throws Exception {

        File subDir = new File(rootDir.getAbsolutePath() + "/" + folderName);

        if(subDir.exists() && subDir.isDirectory()) {

            return subDir;
        }

        else throw new Exception("The directory " + rootDir.getAbsolutePath() +
                " does not contain a subdirectory called " + folderName);
    }
}
