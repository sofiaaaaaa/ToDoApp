package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Database.DBHandler;
import sample.model.User;

import java.io.IOException;

public class SignUpController {
    @FXML
    private JFXTextField ffirstName;

    @FXML
    private JFXButton signUpButton;

    @FXML
    private JFXTextField flastName;

    @FXML
    private JFXTextField fuserName;

    @FXML
    private JFXTextField flocation;

    @FXML
    private JFXCheckBox chuman;

    @FXML
    private JFXCheckBox clion;

    @FXML
    private JFXPasswordField fpassword;

    @FXML
    void initialize() {
        signUpButton.setOnAction(actionEvent -> {
            createdUser();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/login.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

        });
    }

    private void createdUser() {
        DBHandler databaseHandler = new DBHandler();

        String firstName = ffirstName.getText();
        String lastName = flastName.getText();
        String userName = fuserName.getText();
        String password = fpassword.getText();
        String location = flocation.getText();
        String gender = "Lion";

        if(chuman.isSelected()){
            gender = "Human";
        }
        User user = new User(firstName, lastName, userName, password , location , gender);
        databaseHandler.signUpUser(user);

    }
}
