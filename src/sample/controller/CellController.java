package sample.controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Database.DBHandler;
import sample.model.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CellController extends JFXListCell<Task> {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ImageView iconImageView;

    @FXML
    private Label TaskLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView deleteButton;

    private FXMLLoader fxmlLoader;

    private DBHandler dbHandler;

    @FXML
    void initialize() {

    }

    @Override
    protected void updateItem(Task myTask, boolean empty) {
        super.updateItem(myTask, empty);
        if(empty || myTask == null) {
            setText(null);
            setGraphic(null);
        } else {
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/cell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            TaskLabel.setText(myTask.getTask());
            dateLabel.setText(myTask.getDataecreated().toString());
            descriptionLabel.setText(myTask.getDescription());

            // Delete a selected task
            deleteButton.setOnMouseClicked(event -> {
               dbHandler = new DBHandler();
               dbHandler.deletetask(LoginController.userId, myTask.getTaskId());
                getListView().getItems().remove(getItem());
            });

            setText(null);
            setGraphic(rootAnchorPane);
        }
    }
}
