package UI;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.util.Duration;

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

    Label lblGameOver = new Label("Game over!\n Press space to restart");

    private ArrayList<Rectangle> rectangles = new ArrayList<>();

    private ArrayList<Bullet> bullets = new ArrayList<>();

    int fps = 0;

    int deltaX = -5;
    int deltaY = 5;

    int bulletSpeed = 10;

    double spawnRate = 1;

    long start = 0;

    @FXML
    public void initialize() {
        lblGameOver.setLayoutX(400);
        lblGameOver.setLayoutY(100);
        lblGameOver.setStyle("-fx-font: 72px 'Agency FB'; -fx-text-fill: blue; -fx-text-alignment: center");
        lblGameOver.setVisible(false);
        root.getChildren().add(lblGameOver);
        super.start();

        root.setOnMousePressed(mouseEvent -> {
            //System.out.println(mouseEvent.getButton());
            switch (mouseEvent.getButton()) {
                case SECONDARY:
                    fire(mouseEvent);
            }
        });
        root.setOnKeyPressed(event -> {
            //System.out.println(event.getCode());
            switch (event.getCode()) {
                case W:
                    moveMeCircle.setCenterY(moveMeCircle.getCenterY() - deltaY * 3);
                    break;
                case S:
                    moveMeCircle.setCenterY(moveMeCircle.getCenterY() + deltaY * 3);
                    break;
                case A:
                    moveMeCircle.setCenterX(moveMeCircle.getCenterX() + deltaX * 3);
                    break;
                case D:
                    moveMeCircle.setCenterX(moveMeCircle.getCenterX() - deltaX * 3);
                    break;
                case SPACE:
                    restartGame();
                    break;
            }
        });
    }

    private void fire(MouseEvent mouseEvent) {
        Bullet bullet = new Bullet(moveMeCircle.getCenterX(), moveMeCircle.getCenterY(), mouseEvent.getX(), mouseEvent.getY());
        PathTransition transition = new PathTransition();
        transition.setNode(bullet);
        transition.setPath(new Line(moveMeCircle.getCenterX(),moveMeCircle.getCenterY(), bullet.getDestinationX(), bullet.getDestinationY()));
        transition.setCycleCount(1);
        transition.setDuration(Duration.seconds(3));
        transition.play();
        bullets.add(bullet);

    }

    private void restartGame() {
        root.getChildren().removeAll(rectangles);
        rectangles.clear();
        super.start();
        lblGameOver.setVisible(false);
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
                //System.out.println(fps);
                renderShape();
                if (fps < 59) {
                    System.out.println(rectangles.size());
                }
                fps = 0;
                start = l;
                spawnRate += 0.2;
            }
        }
        updateElements();
        handleCollisions();

        fps++;

    }

    private void handleCollisions() {
        for (Rectangle rectangle : rectangles) {
            if (moveMeCircle.getLayoutBounds().intersects(rectangle.getLayoutBounds())) {
                //System.out.println("COLLISION");
                super.stop();
                lblGameOver.setVisible(true);
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

        for (Iterator<Bullet> iterator = bullets.listIterator(); iterator.hasNext(); ) {
            Bullet bullet = iterator.next();
            if (bullet.getCenterX() < 0 || bullet.getCenterX() > 1280 || bullet.getCenterY() < 0 || bullet.getCenterY() > 720) {
                iterator.remove();
                root.getChildren().remove(bullet);
            }
        }

        Random rand;
        for (int i = 0; i < spawnRate; i++) {
            rand = new Random();
            int posX = 1280;
            int posY = rand.nextInt(720);
            //rectangles.add(new Rectangle(posX, posY, 100, 50));
        }


    }

    private void updateElements() {
        Random rand = new Random();
        for (Rectangle rect : rectangles) {
            if (!root.getChildren().contains(rect)) {
                rect.setWidth(rand.nextInt(300 + 650));
                root.getChildren().add(rect);
            }
        }



        for (Circle bullet : bullets) {
            if (!root.getChildren().contains(bullet)) {
                root.getChildren().add(bullet);
            }
        }

        for (Rectangle rect : rectangles) {
            rect.setX(rect.getX() + deltaX);
        }
        for (Bullet bullet : bullets) {
            /*if (bullet.isMoving()) {
                if(bullet.getCenterX()>bullet.getDestinationX()){
                    bullet.setCenterX(bullet.getCenterX() - 0.1*(bullet.getDestinationX()/1280*100));
                }else{
                    bullet.setCenterX(bullet.getCenterX() + 0.1*(bullet.getDestinationX()/1280*100));
                }
                if (bullet.getCenterY()>bullet.getDestinationY()){
                    bullet.setCenterY(bullet.getCenterY() - 0.1*(bullet.getDestinationY()/1280*100));
                }
                else{
                    bullet.setCenterY(bullet.getCenterY() + 0.1*(bullet.getDestinationY()/1280*100));
                }
            }*/
            /*if(bullet.isHorizontal()){
                bullet.setTranslateX(bullet.getTranslateX()+bulletSpeed);
            }
            if(bullet.isVertical()){
                bullet.setTranslateY(bullet.getTranslateY()+bulletSpeed);
            }
            if(bullet.isRight()){
                bullet.setTranslateX(bullet.getTranslateX()+bulletSpeed+bullet.getDestinationX()/1280*100);
            }
            else{
                bullet.setTranslateX(bullet.getTranslateX()-bulletSpeed-bullet.getDestinationX()/1280*100);
            }
            if(bullet.isUp()){
                bullet.setTranslateY(bullet.getTranslateY()-bulletSpeed-bullet.getDestinationY()/720*100);
            }
            else {
                bullet.setTranslateY(bullet.getTranslateY()+bulletSpeed+bullet.getDestinationY()/720*100);
            }*/
        }
    }


}
