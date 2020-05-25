package com.andrewhun.object.controllers;

import com.andrewhun.object.models.*;
import com.andrewhun.object.util.GuiElementIds;
import com.andrewhun.object.util.NamedConstants;
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

public class AddMethodsControllerTest extends ControllerTest {



    // @BeforeAll protected static void setUpHeadlessMode() {}

    @BeforeEach void navigateToMethodPage() {

        clickOn(GuiElementIds.OBJECT_NAME).write(TestUtil.TEST_NAME);
        clickOn(GuiElementIds.SUBMIT_OBJECT_NAME);
        clickOn(GuiElementIds.SUBMIT_VARIABLES);
    }

    @Test void testBackToVariables() {

        assertPageWasChanged(GuiElementIds.BACK_TO_VARIABLES, Page.ADD_VARIABLES);
    }

    @Test void testChoiceBoxValues() throws Exception {

        ChoiceBox<String> accessModifier = lookup(GuiElementIds.ACCESS_MODIFIER).query();
        assertTrue(accessModifier.getItems().containsAll(AccessModifier.getAccessModifiers()));

        ChoiceBox<String> dataTypes = lookup(GuiElementIds.DATA_TYPE).query();
        assertEquals(DataType.getAllDataTypes().size(), dataTypes.getItems().size());
    }

    @Test void testMissingAccessModifier() {

        bypassFrontendValidation();
        selectIntegerDataType();
        enterName(TestUtil.TEST_NAME);
        clickOn(GuiElementIds.ADD_METHOD);
        assertErrorMessageIsShown(Page.ADD_METHODS.getDetailMissingError());
    }

    @Test void testMissingDataType() {

        bypassFrontendValidation();
        selectPrivateAccessModifier();
        enterName(TestUtil.TEST_NAME);
        clickOn(GuiElementIds.ADD_METHOD);
        assertErrorMessageIsShown(Page.ADD_METHODS.getDetailMissingError());
    }

    @Test void testMissingName() {

        bypassFrontendValidation();
        selectPrivateAccessModifier();
        selectIntegerDataType();
        clickOn(GuiElementIds.ADD_METHOD);
        assertErrorMessageIsShown(Page.ADD_METHODS.getDetailMissingError());
    }

    @Test void testDuplicateMethodName() {

        addMethod(TestUtil.generateValidName());
        clickOn(GuiElementIds.ADD_METHOD);
        assertErrorMessageIsShown(Page.ADD_METHODS.getNameTakenError());
    }

    @Test void testRemovingMethod() {

        addMethod(TestUtil.generateValidName());
        ListView<String> displayedMethods = getDisplayedMethods();
        Node firstItem = (Node)displayedMethods.queryAccessibleAttribute(AccessibleAttribute.ITEM_AT_INDEX, 0);
        clickOn(firstItem);
        clickOn(GuiElementIds.REMOVE_METHODS);
        assertNumberOfDisplayedMethods(0);
    }

    @Test void testSavingMethodWhenBackButtonIsClicked() {

        addMethod(TestUtil.generateValidName());
        clickOn(GuiElementIds.BACK_TO_VARIABLES);
        clickOn(GuiElementIds.SUBMIT_VARIABLES);

        assertNumberOfDisplayedMethods(1);
    }

    @Test void testCreatingObject() throws Exception {

        String name = TestUtil.generateValidName();
        addMethod(name);
        clickOn(GuiElementIds.CREATE_OBJECT);

        JavaObject object = JavaObject.getObject(TestUtil.TEST_NAME);
        assertNotNull(object);

        assertEquals(1, object.getMethods().size());

        Method method = object.getMethods().get(0);
        assertEquals(AccessModifier.PRIVATE, method.getAccessModifier());
        assertEquals(NamedConstants.INTEGER, method.getDataType().getName());
        assertEquals(name, method.getName());
        assertFalse(method.getClassElement());
        assertFalse(method.getConstant());

        TestUtil.deleteObjectData(object);

        assertObjectHasNoValues(JavaObject.getCurrentObject());
        assertPageWasChanged(GuiElementIds.SUBMIT_OBJECT_NAME, Page.STARTING_PAGE);
    }

    @Test void testAddMethodValidation() {

        Button addMethod = lookup(GuiElementIds.ADD_METHOD).query();
        assertTrue(addMethod.isDisable());

        selectPrivateAccessModifier();
        assertTrue(addMethod.isDisable());

        selectIntegerDataType();
        assertTrue(addMethod.isDisable());

        enterName(TestUtil.TEST_NAME);
        assertFalse(addMethod.isDisable());
    }


    // Implementations of abstract base class methods
    protected Page getPage() {

        return Page.ADD_METHODS;
    }

    protected void assertErrorMessage(String message) {

        assertErrorMessageIsShown(message);
    }

    protected String getSubmitButtonId() {

        return GuiElementIds.ADD_METHOD;
    }

    protected void submitInput(String name) {

        addMethod(name);
    }

    protected void assertSuccessfulInput(String name) {

        assertNewMethodDetails(name);
    }

    protected void bypassFrontendValidation() {

        ChoiceBox<String> accessModifier = lookup(GuiElementIds.ACCESS_MODIFIER).query();
        accessModifier.setOnAction(null);

        ChoiceBox<String> dataType = lookup(GuiElementIds.DATA_TYPE).query();
        dataType.setOnAction(null);

        TextField methodName = lookup(GuiElementIds.METHOD_NAME).query();
        methodName.setOnKeyTyped(null);

        Button addMethod = lookup(GuiElementIds.ADD_METHOD).query();
        addMethod.setDisable(false);
    }


    // Internal methods
    private void addMethod(String name) {

        selectPrivateAccessModifier();
        selectIntegerDataType();
        enterName(name);
        clickOn(GuiElementIds.ADD_METHOD);
    }

    private void selectPrivateAccessModifier() {

        clickOn(GuiElementIds.ACCESS_MODIFIER);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
    }

    private void selectIntegerDataType() {

        clickOn(GuiElementIds.DATA_TYPE);
        type(KeyCode.ENTER);
    }

    private void enterName(String name) {

        clickOn(GuiElementIds.METHOD_NAME).write(name);
    }

    private void assertErrorMessageIsShown(String message) {

        verifyThat(GuiElementIds.METHOD_ERROR, hasText(message));
    }

    private void assertNewMethodDetails(String name) {

        assertNumberOfDisplayedMethods(1);
        String methodDescription = getDisplayedMethods().getItems().get(0);
        assertEquals("Private Integer " + name, methodDescription);
    }

    private void assertObjectHasNoValues(JavaObject object) {

        assertEquals("", object.getName());
        assertEquals(0, object.getVariables().size());
        assertEquals(0, object.getMethods().size());
    }

    private void assertNumberOfDisplayedMethods(int expected) {

        assertEquals(expected, getDisplayedMethods().getItems().size());
    }

    private ListView<String> getDisplayedMethods() {

        return lookup(GuiElementIds.DISPLAYED_METHODS).query();
    }
}