package lp2a.game.skyjogame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMenu {
    private int width = Skyjo.XMAX;
    private int height = Skyjo.YMAX;
    private MenuButton buttonPlus = new MenuButton("Button plus0.png");
    private MenuButton buttonMinusBlue = new MenuButton("Button minus blue0.png");
    private MenuButton buttonMinusRed = new MenuButton("Button minus red0.png");
    private MenuButton buttonCrossBlue = new MenuButton("Button cross blue0.png");
    private MenuButton buttonCrossRed = new MenuButton("Button cross red0.png");
    private MenuButton buttonExitRed = new MenuButton("Button exit red0.png", 45*width/50 ,5*height/50, width/15, width/15);
    private MenuButton buttonExitBlue = new MenuButton("Button exit blue0.png");
    private MenuButton buttonMenu = new MenuButton("Button menu0.png");
    private MenuButton buttonOkBlue = new MenuButton("Button ok blue0.png");
    private MenuButton buttonOkGreen = new MenuButton("Button ok green0.png");
    private MenuButton background = new MenuButton("MainMenuInterface0.png", 0 , 0, width, height);

    public MainMenu(){
    }


    public void draw(GraphicsContext gc){
        gc.drawImage(background.getImg(), background.getX(), background.getY(), background.getWidth(), background.getHeight());
        gc.drawImage(buttonExitRed.getImg(), buttonExitRed.getX(), buttonExitRed.getY(), buttonExitRed.getWidth(),buttonExitRed.getHeight());

    }
    public MenuButton getButtonExitRed() {
        return buttonExitRed;
    }

    public List<MenuButton> getAllButtons(){
        List<MenuButton> allButtons = new ArrayList<>();
        allButtons.addAll(Arrays.asList(background, buttonCrossBlue, buttonCrossRed, buttonMenu, buttonExitBlue, buttonExitRed, buttonMinusBlue, buttonMinusRed, buttonOkBlue, buttonOkGreen, buttonPlus));
        return allButtons;
    }
}
