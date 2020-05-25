package com.andrewhun.object.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class ProjectSelectionControllerTest extends ApplicationTest {

    private static final String TEST_DIR_PATH = "src/test/resources/testdir/";

    @BeforeEach void createTestDirectory() {

        File testDir = new File(TEST_DIR_PATH);
        assert !testDir.exists() || deleteDirectory(testDir);
        assert testDir.mkdir();
    }

    @AfterEach void deleteTestDirectory() {

        assert deleteDirectory(new File(TEST_DIR_PATH));
    }

    @Test void testMissingSourceDir() {

        assertMissingDirException(new File(TEST_DIR_PATH), ProjectSelectionController.SOURCE);
    }

    @Test void testMissingMainDir() {

        File sourceDir = new File(TEST_DIR_PATH + "/" + ProjectSelectionController.SOURCE);
        assert sourceDir.mkdir();
        assertMissingDirException(sourceDir, ProjectSelectionController.MAIN);
    }

    @Test void testMissingJavaDir() {

        File mainDir = new File(TEST_DIR_PATH + "/" + ProjectSelectionController.SOURCE +
                "/" + ProjectSelectionController.MAIN);
        assert mainDir.mkdirs();
        assertMissingDirException(mainDir, ProjectSelectionController.JAVA);
    }

    private void assertMissingDirException(File parentDir, String folderName) {

        try {
            new ProjectSelectionController().createProject(new File(TEST_DIR_PATH));
            fail(getFailureMessage(folderName));
        }
        catch (Exception e) {
            assertTrue(e.getMessage().contains(createExpectedErrorMessage(parentDir, folderName)));
        }

    }

    @Test void testProjectCreation() {

        assert new File(TEST_DIR_PATH + "/" + ProjectSelectionController.SOURCE +
                "/" + ProjectSelectionController.MAIN + "/" + ProjectSelectionController.JAVA).mkdirs();

        try{

            new ProjectSelectionController().createProject(new File(TEST_DIR_PATH));


        }

        catch (Exception e) {

            fail("A project was meant to be created, but the process failed due the following error: "
                    + e.getMessage());

        }
    }

    private String getFailureMessage(String directoryName) {

        return "Excepted exception due to missing " + directoryName + " directory";
    }

    private String createExpectedErrorMessage(File testDir, String folderName) {

        return "The directory " + testDir.getAbsolutePath() +
                " does not contain a subdirectory called " + folderName;
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {

        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
