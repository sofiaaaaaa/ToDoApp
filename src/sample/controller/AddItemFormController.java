package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Database.DBHandler;
import sample.model.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddItemFormController {

    private int userId;
    private DBHandler dbHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField taskField;

    @FXML
    private JFXTextField descriptionField;

    @FXML
    private JFXButton saveTaskButton;

    @FXML
    private Label successLabel;

    @FXML
    private JFXButton todoButton;

    @FXML
    void initialize() {

        dbHandler = new DBHandler();
        saveTaskButton.setOnAction(event -> {
            Task task = new Task();
            String taskText = taskField.getText().trim();
            String taskDescription = descriptionField.getText().trim();

            if(!taskText.equals("") || !taskDescription.equals("")){
                task.setUserId(LoginController.userId);
                task.setDataecreated(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                task.setDescription(taskDescription);
                task.setTask(taskText);
                dbHandler.insertTask(task);

                successLabel.setVisible(true);
                todoButton.setVisible(true);
                int taskNumber = 0;
                try {
                    taskNumber = dbHandler.getAllTasks(LoginController.userId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                todoButton.setText("My 2Do's: "+ taskNumber);

                taskField.setText("");
                descriptionField.setText("");
                todoButton.setOnAction(event1 -> {
                    // send users to the list screen
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/sample/view/list.fxml"));
                    try {
                        fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Parent root = fxmlLoader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                });

                System.out.println("Task Added Successfully!");

            } else {
                System.out.println("Nothing added!");
            }

        });

    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId(){
        return this.userId;
    }
}
