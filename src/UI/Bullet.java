package UI;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by hdd00002 on 29/05/2018.
 */
public class Bullet extends Circle {
    private double destinationX;
    private double destinationY;
    private boolean moving;
    boolean right;
    boolean up;
    private boolean vertical;

    public boolean isVertical() {
        return vertical;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    private boolean horizontal;


    public Bullet(double startX, double startY, double destinationX, double destinationY) {
        super(startX, startY, 5, Color.BLACK);
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.moving = true;
        setdirections();
        correctTrajectory();
    }

    private void setdirections() {
        if (destinationX > getCenterX()) {
            right = true;
        } else {
            right = false;
        }
        if(destinationX == getCenterX()){
            horizontal=true;
        }
        if(destinationY == getCenterY()){
            vertical=true;
        }
        if (destinationY > getCenterY()) {
            up = false;
        } else {
            up = true;
        }
    }

    private void correctTrajectory() {
        if(right){
            if(destinationX<1280){
                double a = destinationX-getCenterX();
                double b = destinationY-getCenterY();
                double c = Math.sqrt((a*a) + (b*b));
                destinationY=destinationY*(destinationX/1280);
                destinationX=1280;

            }
        }
        /*if(right){
            while(getDestinationX()<1280){
                this.destinationX+=1;
                if(up){
                    this.destinationY+=-1;
                }
                else{
                    this.destinationY+=1;
                }
            }
        }
        else{
            while(getDestinationX()>0){
                this.destinationX+= -1;
                if(up){
                    this.destinationY+=-1;
                }
                else{
                    this.destinationY+=1;
                }
            }
        }
        if(up){
            while(getDestinationY()>0){
                this.destinationY+= -1;
                if(right){
                    this.destinationX+=1;
                }
                else{
                    this.destinationX+=-1;
                }
            }
        }
        else{
            while(getDestinationY()<720){
                this.destinationY+= 1;
                if(right){
                    this.destinationX+=1;
                }
                else{
                    this.destinationX+=-1;
                }
            }
        }
        /*while (!((destinationX >= 1280) || (destinationX <= 0) || (destinationY >= 720) || (destinationY <= 0))) {
            if (this.destinationX < 360) {
                destinationX += -1;
            }
            if (this.destinationY < 640) {
                destinationY += -1;
            } else {
                //roughly keeps the bullets moving until the edge of screen on given trajectory
                destinationX += 1;
                destinationY += 1;
            }
        }*/
    }

    public double getDestinationX() {
        return destinationX;
    }

    public double getDestinationY() {
        return destinationY;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }
}
