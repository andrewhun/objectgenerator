package com.andrewhun.object;

import com.andrewhun.object.models.Page;
import com.andrewhun.object.util.WindowUtil;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application
{

    @Override
    public void start(Stage stage) throws Exception {

        new WindowUtil(stage).displayPage(Page.STARTING_PAGE);

    }
    public static void main( String[] args )
    {
        Application.launch(args);
    }
}
