package com.andrewhun.object.controllers;

import com.andrewhun.object.models.JavaObject;
import com.andrewhun.object.models.Page;
import com.andrewhun.object.util.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;
import javafx.scene.control.Button;

import java.io.File;

public class StartingPageControllerTest extends ControllerTest {

    // @BeforeAll protected static void setUpHeadlessMode() {}

    @Test void testTakenObjectName() throws Exception {

        File file = new File(JavaObject.OBJECT_DIRECTORY +
                TestUtil.TEST_NAME + NamedConstants.METADATA_FILE_TYPE);
        file.createNewFile();

        submitObjectName(TestUtil.TEST_NAME);
        file.delete();
        assertNotEquals(TestUtil.TEST_NAME, JavaObject.getCurrentObject().getName());
        verifyThat(GuiElementIds.NAME_TAKEN_ERROR, hasText(Page.STARTING_PAGE.getNameTakenError()));
    }

    @Test void testSubmitButtonStatus() {

        Button submitButton = lookup(GuiElementIds.SUBMIT_OBJECT_NAME).query();

        assertTrue(submitButton.isDisable());

        clickOn(GuiElementIds.OBJECT_NAME).write(TestUtil.TEST_NAME);

        assertFalse(submitButton.isDisable());

    }

    protected Page getPage() {

        return Page.STARTING_PAGE;
    }

    protected void assertErrorMessage(String message) {

        verifyThat(GuiElementIds.NAME_TAKEN_ERROR, hasText(message));
    }

    protected String getSubmitButtonId() {

        return GuiElementIds.SUBMIT_OBJECT_NAME;
    }

    protected void submitInput(String name) {

        submitObjectName(name);
    }

    protected void assertSuccessfulInput(String name) {

        assertEquals(name, JavaObject.getCurrentObject().getName());
        // Check that the user was navigated to the "Add variables" page
        assertPageWasChanged(GuiElementIds.BACK_TO_NAME, Page.ADD_VARIABLES);
    }

    protected void bypassFrontendValidation() {

        Button submitButton = lookup(GuiElementIds.SUBMIT_OBJECT_NAME).query();
        submitButton.setDisable(false);
    }

    private void submitObjectName(String name) {

        clickOn(GuiElementIds.OBJECT_NAME).write(name);
        clickOn(GuiElementIds.SUBMIT_OBJECT_NAME);
    }
}
