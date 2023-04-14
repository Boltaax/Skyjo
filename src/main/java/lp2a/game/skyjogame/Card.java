package lp2a.game.skyjogame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Card {
    private int value;
    private String name;
    private boolean visible;
    private int x = 10000;
    private int y = 10000;
    private int width = 60;
    private int height = 80;
    private boolean clicked = false;

    public Card(int value, String name) {
        this.value = value;
        this.name = name;
        this.visible = false;
        this.clicked = false;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public int getValue() {
        return value;
    }
    public String getName() {
        return name;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public String toString() {
        return name;
    }

    public void clicked(MouseEvent e){
        // Taking coordinates of where the mouse have been clicked
        double mouseX = e.getX();
        double mouseY = e.getY();
        if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height) {
            this.clicked = true;
        }

        if (!this.visible && this.clicked) {
            System.out.println("Retournes ta mère !");
            this.clicked = false;
        } else {

        }
    }

    public void draw(GraphicsContext gc){
        if (!this.isVisible()) {
            gc.setFill(Color.RED);
            gc.fillRoundRect(x, y, width , height, width/4, width/4 );
        }
        else {
            gc.setFill(Color.BLUE);
            gc.fillRoundRect(x, y, width , height, width/4, width/4 );
        }
        gc.setFill(Color.BLACK);
        switch (this.value){
            case -2:
                gc.fillText("-2", x+width/2, y+height/2);
                break;
            case -1:
                gc.fillText("-1", x+width/2, y+height/2);
                break;
            case 0:
                gc.fillText("0", x+width/2, y+height/2);
                break;
            case 1:
                gc.fillText("1", x+width/2, y+height/2);
                break;
            case 2:
                gc.fillText("2", x+width/2, y+height/2);
                break;
            case 3:
                gc.fillText("3", x+width/2, y+height/2);
                break;
            case 4:
                gc.fillText("4", x+width/2, y+height/2);
                break;
            case 5:
                gc.fillText("5", x+width/2, y+height/2);
                break;
            case 6:
                gc.fillText("6", x+width/2, y+height/2);
                break;
            case 7:
                gc.fillText("7", x+width/2, y+height/2);
                break;
            case 8:
                gc.fillText("8", x+width/2, y+height/2);
                break;
            case 9:
                gc.fillText("9", x+width/2, y+height/2);
                break;
            case 10:
                gc.fillText("10", x+width/2, y+height/2);
                break;
            case 11:
                gc.fillText("11", x+width/2, y+height/2);
                break;
            case 12:
                gc.fillText("12", x+width/2, y+height/2);
                break;
        }
    }

}
