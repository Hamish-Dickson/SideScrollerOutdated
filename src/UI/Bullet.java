package UI;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by hdd00002 on 29/05/2018.
 */
public class Bullet extends Circle{
    private double destinationX;
    private double destinationY;


    public Bullet(double startX, double startY, double destinationX, double destinationY){
        super(startX,startY, 5, Color.RED);
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        correctTrajectory(destinationX, destinationY);
    }

    private void correctTrajectory(double destinationX, double destinationY) {
        /*while(!(destinationX>=1280) || (destinationX<=0) || (destinationY>=720) || destinationY<=0){
            this.destinationX+=10;
            this.destinationY+=10;
            destinationX+=10;
            destinationY+=10;
        }*/
    }

    public double getDestinationX() {
        return destinationX;
    }

    public double getDestinationY() {
        return destinationY;
    }

}
