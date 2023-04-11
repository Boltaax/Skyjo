package lp2a.game.skyjogame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Card {

    private Runnable runnable;
    private int value;
    private String name;
    private boolean visible;
    private int x = 0;
    private int y = 0;
    private int width = 60;
    private int height = 100;
    private boolean clicked = false;

    public Card(int value, String name) {
        this.value = value;
        this.name = name;
        this.visible = false;
        this.runnable = () -> this.setClicked(true);
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

    public void draw(GraphicsContext gc){
        if (!this.isVisible()) {
            gc.setFill(Color.RED);
            gc.fillRoundRect(x, y, width , height, width/4, width/4 );
        }
        else {
            gc.setFill(Color.BLUE);
            gc.fillRoundRect(x, y, width , height, width/4, width/4 );
        }
    }
}
