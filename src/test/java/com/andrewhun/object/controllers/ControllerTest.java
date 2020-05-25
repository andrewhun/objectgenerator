package com.andrewhun.object.controllers;

import com.andrewhun.object.TestFXBaseClass;
import com.andrewhun.object.models.Page;
import com.andrewhun.object.util.TestUtil;
import org.junit.jupiter.api.Test;

public abstract class ControllerTest extends TestFXBaseClass {

    @Test void testNoDetailsEntered() {

        bypassFrontendValidation();
        clickOn(getSubmitButtonId());
        assertErrorMessage(getPage().getDetailMissingError());
    }

    @Test void testUsingReservedWordForName() {

        submitInput(TestUtil.getRandomReservedWord());
        assertErrorMessage(getPage().getInvalidNameError());
    }

    @Test void testSpecialCharacterInName() {

        submitInput(TestUtil.generateNameWithSpecialCharacter());
        assertSuccessfulInput(TestUtil.TEST_NAME);
    }

    @Test void testInvalidFirstCharacterInName() {

        submitInput(TestUtil.createNameWithInvalidFirstCharacter());
        assertSuccessfulInput(TestUtil.TEST_NAME);
    }

    @Test void testValidInput() {

        String name = TestUtil.generateValidName();
        submitInput(name);
        assertSuccessfulInput(name);
    }

    protected abstract String getSubmitButtonId();
    protected abstract void assertErrorMessage(String errorMessage);
    protected abstract Page getPage();
    protected abstract void submitInput(String name);
    protected abstract void assertSuccessfulInput(String name);
    protected abstract void bypassFrontendValidation();
}
