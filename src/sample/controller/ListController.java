package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sample.Database.DBHandler;
import sample.animation.Roller;
import sample.animation.Shaker;
import sample.model.Task;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXListView<Task> listTask;

    @FXML
    private JFXTextField taskField;

    @FXML
    private JFXTextField descriptionField;

    @FXML
    private JFXButton saveTaskButton;

    @FXML
    private ImageView refreshButton;

    @FXML
    private Label TitleLabel;

    private DBHandler dbHandler;

    private Task selectedTask;

    @FXML
    void initialize() throws SQLException {
        dbHandler = new DBHandler();
        selectedTask = null;

        // Initialize a user's Task List
        setMyTaskList();

        // Save a task
        saveTaskButton.setOnAction(event -> {
            saveTask();
        });

        // Click refresh button
        refreshButton.setOnMouseClicked(event -> {
            try {
                setMyTaskList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Click a cell in the list and show the task in the left side
        listTask.getSelectionModel().selectedItemProperty().addListener(event -> {
            selectedTask = listTask.getSelectionModel().getSelectedItem();
            if(selectedTask != null) {
                taskField.setText(selectedTask.getTask());
                descriptionField.setText(selectedTask.getDescription());
            }
        });

    }

    public void setMyTaskList() throws SQLException {

        Roller roller = new Roller(refreshButton);
        roller.rolling();

        ObservableList<Task> list = FXCollections.observableArrayList();
        ResultSet resultSet = dbHandler.getTasksByUser(LoginController.userId);

        int rowcount = 0;
        if (resultSet.last()) {
            rowcount = resultSet.getRow();
            resultSet.beforeFirst();
        }

        // Set Title
        TitleLabel.setText( "My 2Do's "+rowcount);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDataecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            list.addAll(task);
        }

        listTask.setItems(list);
        listTask.setCellFactory(CellController -> new CellController());
    }

    public void saveTask() {

        if (!taskField.getText().equals("") || !descriptionField.getText().equals("")) {

            if(selectedTask != null){
                updateTask();
            } else {
                addNewTask();
            }

            taskField.setText("");
            descriptionField.setText("");

            // Update the user's task list
            try {
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            Shaker shaker = new Shaker(taskField);
            Shaker shaker2 = new Shaker(descriptionField);
            shaker.shake();
            shaker2.shake();
        }
    }

    public void updateTask(){
        selectedTask.setTask(taskField.getText().trim());
        selectedTask.setDescription(descriptionField.getText().trim());
        selectedTask.setUserId(LoginController.userId);
        selectedTask.setDataecreated(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        System.out.println("selected taskId == > "+selectedTask.getTaskId());
        dbHandler.updateTask(selectedTask);

        // initialize a global variable
        selectedTask = null;

    }

    public void addNewTask(){

        Task myNewTask = new Task();
        myNewTask.setTask(taskField.getText().trim());
        myNewTask.setDescription(descriptionField.getText().trim());
        myNewTask.setDataecreated(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        myNewTask.setUserId(LoginController.userId);
        dbHandler.insertTask(myNewTask);


    }

}