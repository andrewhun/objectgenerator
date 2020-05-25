package com.andrewhun.object;

import com.andrewhun.object.models.JavaObject;
import com.andrewhun.object.models.Page;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.EmptyNodeQueryException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class TestFXBaseClass extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {

        new App().start(stage);
    }

    @BeforeAll
    protected static void setUpHeadlessMode() {

        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
    }

    @AfterEach
    protected void tearDown() throws Exception {

        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        JavaObject object = JavaObject.getCurrentObject();
        object.setName("");
        object.setVariables(new ArrayList<>());
        object.setMethods(new ArrayList<>());
    }

    protected void assertPageWasChanged(String elementId, Page page) {

        try {
            lookup(elementId).query();
        }
        catch (EmptyNodeQueryException e) {

            fail("The "+ page.getTitle() + " page was not loaded: " + e.getMessage());
        }
    }
}
