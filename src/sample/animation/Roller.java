package sample.animation;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Roller {
    private RotateTransition  rotateTransition ;

    public Roller(Node node) {
        rotateTransition  = new RotateTransition(Duration.millis(1000), node);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
    }

    public void rolling(){
        rotateTransition.playFromStart();
    }
}
