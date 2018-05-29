package UI;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class UIController extends AnimationTimer {

    @FXML
    private Label fpsLbl;

    @FXML
    private Circle moveMeCircle;

    @FXML
    private AnchorPane root;

    private ArrayList<Rectangle> rectangles = new ArrayList<>();

    int fps = 0;

    int deltaX = -5;
    int deltaY = 5;

    long start = 0;

    @FXML
    public void initialize() {
        super.start();
        root.setOnKeyPressed(event -> {
            System.out.println(event.getCode());
            switch (event.getCode()) {
                case W:
                    moveMeCircle.setCenterY(moveMeCircle.getCenterY() - deltaY*3);
                    break;
                case S:
                    moveMeCircle.setCenterY(moveMeCircle.getCenterY() + deltaY*3);
                    break;
                case A:
                    moveMeCircle.setCenterX(moveMeCircle.getCenterX() + deltaX*3);
                    break;
                case D:
                    moveMeCircle.setCenterX(moveMeCircle.getCenterX() - deltaX*3);
                    break;
                case SPACE:
                    restartGame();
                    break;
            }
        });
    }

    private void restartGame() {
        root.getChildren().removeAll(rectangles);
        rectangles.clear();
        super.start();
        moveMeCircle.setCenterX(50);
        moveMeCircle.setCenterY(360);
    }

    @Override
    public void handle(long l) {
        root.requestFocus();
        if (start == 0) {
            start = l;
        } else {
            if (l >= start + 1000000000) {
                fpsLbl.setText("FPS: " + ((l - start) / 1000000000) * fps);
                System.out.println(fps);
                renderShape();
                if (fps < 59) {
                    System.out.println(rectangles.size());
                }
                fps = 0;
                start = l;
            }
        }
        updateElements();
        handleCollisions();

        fps++;
    }

    private void handleCollisions() {
        for (Rectangle rectangle : rectangles) {
            if (moveMeCircle.getLayoutBounds().intersects(rectangle.getLayoutBounds())) {
                System.out.println("COLLISION");
                super.stop();
                Label lblGameOver = new Label("Game over!\n Press space to restart");
                lblGameOver.setLayoutX(400);
                lblGameOver.setLayoutY(100);
                lblGameOver.setStyle("-fx-font: 72px 'Agency FB'; -fx-text-fill: blue; -fx-text-alignment: center");
                root.getChildren().add(lblGameOver);
            }
        }
    }

    private void renderShape() {
        for (Iterator<Rectangle> iterator = rectangles.listIterator(); iterator.hasNext(); ) {
            Rectangle rectangle = iterator.next();
            if (rectangle.getX() + rectangle.getWidth() < 0) {
                iterator.remove();
                root.getChildren().remove(rectangle);
            }
        }
        Random rand = new Random();
        int posX = 1280;
        int posY = rand.nextInt(720);
        rectangles.add(new Rectangle(posX, posY, 100, 50));


        for (Rectangle rect : rectangles) {
            if (!root.getChildren().contains(rect)) {
                rect.setWidth(rand.nextInt(300 + 650));
                root.getChildren().add(rect);
            }
        }
    }

    private void updateElements() {
        for (Rectangle rect : rectangles) {
            rect.setX(rect.getX() + deltaX);
        }
    }


}
