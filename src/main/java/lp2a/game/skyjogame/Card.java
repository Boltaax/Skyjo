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
    private int width = Skyjo.XMAX/25;
    private int height = Skyjo.YMAX/10;
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
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
    }

    public void draw(GraphicsContext gc){
        if(this.clicked){
            gc.setFill(Color.ORANGE);
            gc.fillRoundRect(x-3, y-3, width+6 , height+6, width/4, width/4 );
        }
        if (!this.isVisible()) {
            gc.setFill(Color.RED);
            gc.fillRoundRect(x, y, width , height, width/4, width/4 );
        }
        else {
            gc.setFill(Color.CYAN);
            gc.fillRoundRect(x, y, width , height, width/4, width/4 );
        }
        gc.setFill(Color.BLACK);
        if (this.isVisible()) {
            gc.fillText(String.valueOf(this.value), x+width/2, y+height/2);
        }
    }

}
