package lp2a.game.skyjogame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Card {
    // Attributes
    private int value;
    private String name;
    private boolean visible;
    private int x = 10000;
    private int y = 10000;
    private int width = Skyjo.XMAX/25;
    private int height = Skyjo.YMAX/10;
    private boolean clicked = false; // If the card is clicked

    // Methods
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

    /**
     * This method check if the card is clicked
     * @param e MouseEvent
     */
    public void clicked(MouseEvent e){
        // Taking coordinates of where the mouse have been clicked
        double mouseX = e.getX();
        double mouseY = e.getY();
        // Checking if the mouse is in the card
        if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height) {
            this.clicked = true;
        }
    }

    /**
     * This method draw the card
     * @param gc GraphicsContext
     */
    public void draw(GraphicsContext gc) {
        Image background = new Image(getClass().getResourceAsStream("Cards/Skyjo card back0.png"));
        Image front = img_nb();
        // Fill the border of the card with orange if the card is clicked
        if (this.clicked) {
            gc.setFill(Color.RED);
            gc.fillRoundRect(x - 3, y - 3, width + 6, height + 6, width / 4, width / 4);
        }
        // Fill the card with red if the card is not visible
        if (!this.isVisible()) {
            gc.drawImage(background, x, y, width, height);

        } else { // Fill the card with cyan if the card is visible
            gc.drawImage(front, x, y, width, height);
        }
    }

    public Image img_nb(){
        switch(this.value){
            case -2:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card -2.png"));
            case -1:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card -1.png"));
            case 0:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 0.png"));
            case 1:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 1.png"));
            case 2:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 2.png"));
            case 3:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 3.png"));
            case 4:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 4.png"));
            case 5:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 5.png"));
            case 6:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 6.png"));
            case 7:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 7.png"));
            case 8:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 8.png"));
            case 9:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 9.png"));
            case 10:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 10.png"));
            case 11:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 11.png"));
            case 12:
                return new Image(getClass().getResourceAsStream("Cards/Skyjo card 12.png"));
        }
        return null;
    }

}
