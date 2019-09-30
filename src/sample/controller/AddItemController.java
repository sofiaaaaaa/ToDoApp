package sample.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.animation.Shaker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private URL location;

    @FXML
    private ImageView addItemButton;

    @FXML
    private Label noTaskLabel;

    @FXML
    void initialize() {

        addItemButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Shaker buttonShaker = new Shaker(addItemButton);
            buttonShaker.shake();
            Node node;

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), addItemButton);
            FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(500), noTaskLabel);

            addItemButton.relocate(0, 20);
            noTaskLabel.relocate(0, 100);
            addItemButton.setOpacity(0);
            noTaskLabel.setOpacity(0);

            fadeTransition.setFromValue(1f);
            fadeTransition.setToValue(0f);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);
            fadeTransition.play();

            fadeTransition2.setFromValue(1f);
            fadeTransition2.setToValue(0f);
            fadeTransition2.setCycleCount(1);
            fadeTransition2.setAutoReverse(false);
            fadeTransition2.play();

            try {
                AnchorPane formPane = FXMLLoader.load(getClass().getResource("/sample/view/addItemForm.fxml"));

//                AddItemFormController addItemFormController = new AddItemFormController();
//
//                AddItemController.userId = getUserId();

                FadeTransition rootTransition = new FadeTransition(Duration.millis(4000), formPane);
                rootTransition.setFromValue(0f);
                rootTransition.setToValue(1f);
                rootTransition.setCycleCount(1);
                rootTransition.setAutoReverse(false);
                rootTransition.play();

                rootAnchorPane.getChildren().setAll(formPane);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

}