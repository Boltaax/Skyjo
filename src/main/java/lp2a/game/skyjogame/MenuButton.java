package lp2a.game.skyjogame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class MenuButton {
    // Attributes
    private Image img;
    private int width;
    private int height;
    private int x;
    private int y;
    private boolean clicked = false;

    // Methods
    public MenuButton(String path, int x, int y, int width, int height){
        this.img = new Image(getClass().getResourceAsStream(path));
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }
    public MenuButton(String path){
        this.img = new Image(getClass().getResourceAsStream(path));
        this.width = (int) img.getWidth();
        this.height = (int) img.getHeight();
    }


    public Image getImg() {
        return img;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void clicked(MouseEvent e){
        // Taking coordinates of where the mouse have been clicked
        double mouseX = e.getX();
        double mouseY = e.getY();
        if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height) {
            this.clicked = true;
        }
    }
    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(img, x, y, width, height);
    }
}
