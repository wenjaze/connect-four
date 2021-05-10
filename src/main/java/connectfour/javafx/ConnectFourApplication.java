package connectfour.javafx;


import connectfour.javafx.utils.SceneHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectFourApplication extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        log.info("Running applicaton...");
        SceneHandler.switchToStartup(stage);
//        fxmlLoader.setLocation(getClass().getResource("/fxml/startup.fxml"));
//        Parent root = fxmlLoader.load();
//        stage.setTitle("Connect Four");
//        stage.setResizable(false);
//        stage.setScene(new Scene(root));
//        stage.show();
    }

}
