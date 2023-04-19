package lp2a.game.skyjogame;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class MenuButton {
    private Image img;
    private int width;
    private int height;
    private int x;
    private int y;
    private boolean clicked = false;

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
}
