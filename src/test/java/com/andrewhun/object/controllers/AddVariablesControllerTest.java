package com.andrewhun.object.controllers;

import com.andrewhun.object.models.AccessRight;
import com.andrewhun.object.models.DataType;
import com.andrewhun.object.models.Page;
import com.andrewhun.object.util.GuiElementIds;
import com.andrewhun.object.util.TestUtil;
import javafx.scene.AccessibleAttribute;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

public class AddVariablesControllerTest extends ControllerTest {

    // @BeforeAll protected static void setUpHeadlessMode() {}

    @BeforeEach void navigateToVariablePage() {

        clickOn(GuiElementIds.OBJECT_NAME).write(TestUtil.TEST_NAME);
        clickOn(GuiElementIds.SUBMIT_OBJECT_NAME);
    }

    @Test void testBackToName() {

        clickOn(GuiElementIds.BACK_TO_NAME);
        // The entered name should be displayed when users return to the starting page
        verifyThat(GuiElementIds.OBJECT_NAME, hasText(TestUtil.TEST_NAME));
    }

    @Test void testSubmitVariables() {

        clickOn(GuiElementIds.SUBMIT_VARIABLES);
        assertPageWasChanged(GuiElementIds.BACK_TO_VARIABLES, Page.ADD_METHODS);
}

    @Test void testChoiceBoxValues() throws Exception {

        ChoiceBox<String> accessRights = lookup(GuiElementIds.ACCESS_RIGHT).query();
        assertTrue(accessRights.getItems().containsAll(AccessRight.getAccessRights()));

        ChoiceBox<String> dataTypes = lookup(GuiElementIds.DATA_TYPE).query();
        // The list should not contain the void basic type
        assertEquals(DataType.getAllDataTypes().size() - 1, dataTypes.getItems().size());
    }

    @Test void testAccessRightForConstants() {

        // Ticking the constant box
        clickOn(GuiElementIds.CONSTANT);
        ChoiceBox<String> accessRights = lookup(GuiElementIds.ACCESS_RIGHT).query();
        for(String right : accessRights.getItems()) {

            assertFalse(right.contains("Write"));
        }

        // Un-ticking the box
        clickOn(GuiElementIds.CONSTANT);

        assertTrue(accessRights.getItems().containsAll(AccessRight.getAccessRights()));
    }

    @Test void testMissingAccessRights() {

        bypassFrontendValidation();
        selectIntegerDataType();
        enterName(TestUtil.TEST_NAME);
        clickOn(GuiElementIds.ADD_VARIABLE);
        sleep(5000);
        assertVariableDetailError(Page.ADD_VARIABLES.getDetailMissingError());
    }

    @Test void testMissingDataType() {

        bypassFrontendValidation();
        selectPrivateAccessRight();
        enterName(TestUtil.TEST_NAME);
        clickOn(GuiElementIds.ADD_VARIABLE);
        assertVariableDetailError(Page.ADD_VARIABLES.getDetailMissingError());
    }

    @Test void testMissingVariableName() {

        bypassFrontendValidation();
        selectPrivateAccessRight();
        selectIntegerDataType();
        clickOn(GuiElementIds.ADD_VARIABLE);
        assertVariableDetailError(Page.ADD_VARIABLES.getDetailMissingError());
    }

    @Test void testDuplicateVariableName() {

        addVariable(TestUtil.generateValidName());
        clickOn(GuiElementIds.ADD_VARIABLE);
        assertVariableDetailError(Page.ADD_VARIABLES.getNameTakenError());
    }
    @Test void testRemoveVariable() {

        addVariable(TestUtil.generateValidName());
        ListView<String> displayedVariables = getDisplayedVariables();
        Node firstItem = (Node)displayedVariables.queryAccessibleAttribute(AccessibleAttribute.ITEM_AT_INDEX, 0);
        clickOn(firstItem);
        clickOn(GuiElementIds.REMOVE_VARIABLES);

        assertNumberOfDisplayedVariables(0);
    }

    @Test void testSavingVariableWhenBackButtonIsClicked() {

        addVariable(TestUtil.generateValidName());
        clickOn(GuiElementIds.BACK_TO_NAME);
        clickOn(GuiElementIds.SUBMIT_OBJECT_NAME);
        assertNumberOfDisplayedVariables(1);
    }

    @Test void testSavingVariableWhenNextButtonIsClicked() {

        addVariable(TestUtil.generateValidName());
        clickOn(GuiElementIds.SUBMIT_VARIABLES);
        clickOn(GuiElementIds.BACK_TO_VARIABLES);
        assertNumberOfDisplayedVariables(1);
    }

    @Test void testAddVariableValidation() {
        Button addVariable = lookup(GuiElementIds.ADD_VARIABLE).query();
        assertTrue(addVariable.isDisable());

        selectPrivateAccessRight();
        assertTrue(addVariable.isDisable());

        selectIntegerDataType();
        assertTrue(addVariable.isDisable());

        enterName(TestUtil.TEST_NAME);
        assertFalse(addVariable.isDisable());
    }


    // Implementations of abstract base class methods
    protected void assertErrorMessage(String message) {

        assertVariableDetailError(message);
    }

    protected Page getPage() {

        return Page.ADD_VARIABLES;
    }

    protected String getSubmitButtonId() {

        return GuiElementIds.ADD_VARIABLE;
    }


    protected void submitInput(String name) {

        addVariable(name);
    }

    protected void assertSuccessfulInput(String name) {

        assertNewVariableDetails(name);
    }

    protected void bypassFrontendValidation() {

        ChoiceBox<String> accessRights = lookup(GuiElementIds.ACCESS_RIGHT).query();
        accessRights.setOnAction(null);

        ChoiceBox<String> dataTypes = lookup(GuiElementIds.DATA_TYPE).query();
        dataTypes.setOnAction(null);

        TextField variableName = lookup(GuiElementIds.VARIABLE_NAME).query();
        variableName.setOnKeyTyped(null);

        Button addVariable = lookup(GuiElementIds.ADD_VARIABLE).query();
        addVariable.setDisable(false);
    }


    // Internal methods
    private void addVariable(String name) {

        selectPrivateAccessRight();
        selectIntegerDataType();
        enterName(name);
        clickOn(GuiElementIds.ADD_VARIABLE);
    }

    private void selectPrivateAccessRight() {

        clickOn(GuiElementIds.ACCESS_RIGHT);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
    }

    private void selectIntegerDataType() {

        clickOn(GuiElementIds.DATA_TYPE);
        type(KeyCode.ENTER);
    }

    private void enterName(String name) {

        clickOn(GuiElementIds.VARIABLE_NAME).write(name);
    }

    private void assertVariableDetailError(String errorMessage) {

        final String VARIABLE_DETAIL_ERROR_ID = GuiElementIds.VARIABLE_DETAIL_ERROR;
        verifyThat(VARIABLE_DETAIL_ERROR_ID,
                hasText(errorMessage));
    }

    private void assertNewVariableDetails(String name) {

        assertNumberOfDisplayedVariables(1);
        String variableDescription = getDisplayedVariables().getItems().get(0);
        assertEquals("Private Integer " + name, variableDescription);
    }

    private void assertNumberOfDisplayedVariables(int expected) {

        assertEquals(expected, getDisplayedVariables().getItems().size());
    }

    private ListView<String> getDisplayedVariables() {

        return lookup(GuiElementIds.DISPLAYED_VARIABLES).query();
    }
}