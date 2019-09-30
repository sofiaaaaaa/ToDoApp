package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sample.Database.DBHandler;
import sample.animation.Shaker;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField loginUserName;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXPasswordField loginPassword;

    @FXML
    private JFXButton loginSignupButton;

    private DBHandler dbHandler;

    public static int userId;
    public static void setUserId(int userId) {
        LoginController.userId = userId;
    }


    @FXML
    void initialize(){
        dbHandler = new DBHandler();

        loginPassword.setOnKeyPressed( event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                loginClickEvent();
            }

        });

        loginButton.setOnAction(event -> {
            loginClickEvent();
        });

        loginSignupButton.setOnAction(event -> {
            // Take users to signup screen
            loginSignupButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/signUp.fxml"));
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

    private void loginClickEvent(){

        String loginText = loginUserName.getText().trim();
        String loginPwd = loginPassword.getText().trim();

        if( loginText.isEmpty() || loginPwd.isEmpty() ){
            shakeLoginFields();
            return;
        }
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPwd);

        ResultSet userRow = dbHandler.getUser(user);

        int counter = 0;
        try {
            while(userRow.next()){
                counter++;
                String name = userRow.getString("firstname");
                setUserId(userRow.getInt("userid"));
            }

            if(counter == 1){
                showAddItemScreen();
            } else {
                shakeLoginFields();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void shakeLoginFields(){
        Shaker shaker = new Shaker(loginUserName);
        Shaker shaker2 = new Shaker(loginPassword);
        shaker.shake();
        shaker2.shake();
    }

    private void showAddItemScreen() {

        try {
            String url = "";
            // Get the user's total task count
            int taskCount = dbHandler.getAllTasks(userId);
            if(taskCount > 0){
                url = "/sample/view/list.fxml";
            } else {
                url = "/sample/view/addItem.fxml";
            }

            // Take users to AddItem screen
            loginSignupButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(url));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
