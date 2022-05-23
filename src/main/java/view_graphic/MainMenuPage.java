package view_graphic;

import controller.MainMenuController;
import controller.RegisterController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class MainMenuPage {
    private MainMenuController mainMenuController;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Text title;
    private Text text;
    @FXML
    private VBox vbox;
    public void initialize() {
        Platform.runLater(() -> borderPane.requestFocus());
        title.setFill(Color.rgb(1, 231, 212));
        mainMenuController=MainMenuController.getInstance();
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource("/images/backgrounds/loginBackground.png").toExternalForm()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                backgroundSize);
        borderPane.setBackground(new Background(backgroundImage));
        text = new Text();
        vbox.getChildren().add(text);
    }

    public void profileMenu(){
        //TODO change scene to profilePage
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void logout (MouseEvent mouseEvent) {
        mainMenuController.logout();
        App.changeMenu("LoginPage");
    }

    public void scoreMenu (MouseEvent mouseEvent) {
        //TODO change scene to scoreMenu
    }

    public void chatMenu (MouseEvent mouseEvent) {
        //TODO change scene to chatMenu
    }
}