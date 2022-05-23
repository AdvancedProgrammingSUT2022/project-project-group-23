package view_graphic;

import controller.RegisterController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import view.RegisterMenu;

public class LoginPage {
    private RegisterController registerController;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField password;
    @FXML
    private TextField username;
    @FXML
    private TextField nickname;
    @FXML
    private VBox vbox;
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    private Text text;
    @FXML
    private Text title;
    @FXML
    Label usernameLabel;
    @FXML
    Label nicknameLabel;
    @FXML
    Label passwordLabel;

    public void initialize() {
        Platform.runLater(() -> borderPane.requestFocus());
        usernameLabel.setTextFill(Color.rgb(232, 200, 22));
        nicknameLabel.setTextFill(Color.rgb(232, 200, 22));
        passwordLabel.setTextFill(Color.rgb(232, 200, 22));
        title.setFill(Color.rgb(1, 231, 212));
        registerController = RegisterController.getInstance();
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource("/images/backgrounds/loginBackground.png").toExternalForm()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                backgroundSize);
        borderPane.setBackground(new Background(backgroundImage));

        registerButton.setDisable(true);
        loginButton.setDisable(true);
        text = new Text();
        vbox.getChildren().add(text);
    }

    public void type(KeyEvent keyEvent) {
        int strength = password.getText().length();
        if (strength < 5) {
            password.setStyle("-fx-border-color: #ff0066;");
            registerButton.setDisable(true);
            loginButton.setDisable(true);
        } else {
            password.setStyle("-fx-border-width: 0");
            registerButton.setDisable(false);
            loginButton.setDisable(false);
        }
    }

    public void register() {
        String output = registerController.addUser(username.getText(), nickname.getText(), password.getText());
        if (output.equals("user created successfully!")) {
            text.setFill(Color.GREEN);
            text.setText(output);
        } else {
            text.setFill(Color.RED);
            text.setText(output);
        }
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        String output = registerController.login(username.getText(), password.getText());
        if (output.equals("user logged in successfully!")) {
            App.changeMenu("MainMenuPage");
        } else {
                text.setFill(Color.RED);
                text.setText(output);
        }
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }
}