package connectfour.javafx.main;


import connectfour.javafx.utils.SceneHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Extends {@link javafx.application.Application}, has an overridden
 * start function that starts the StartUpScene.
 */

@Slf4j
public class ConnectFourApplication extends Application {

    /**
     * Overridden start function of the {@link Application}.
     *
     * @param stage The stage to put scenes to.
     * @throws Exception Inhetired from {@link Application}.
     */
    @Override
    public void start(Stage stage) throws Exception {
        SceneHandler.commenceWithStartUpScene(stage);
    }
}
