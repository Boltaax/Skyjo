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
    private ArrayList<Playable> playables = new ArrayList<>();
    private ArrayList<MenuButton> labels = new ArrayList<>();
    private MenuButton buttonPlusBot = new MenuButton("add_bot_button1.png", 25*width/50 ,20*height/50, width/5, width/15);
    private MenuButton buttonPlusPlayer = new MenuButton("add_player_button1.png", 15*width/50 ,20*height/50, width/5, width/15);
    private MenuButton buttonMinusRed = new MenuButton("Button minus red0.png");
    private MenuButton buttonCrossBlue = new MenuButton("Button cross blue0.png");
    private MenuButton buttonCrossRed = new MenuButton("Button cross red0.png");
    private MenuButton buttonExitRed = new MenuButton("Button exit red0.png", 45*width/50 ,5*height/50, width/15, width/15);
    private MenuButton buttonExitBlue = new MenuButton("Button exit blue0.png");
    private MenuButton buttonMenu = new MenuButton("Button menu0.png");
    private MenuButton buttonOkBlue = new MenuButton("Button ok blue0.png");
    private MenuButton buttonOkGreen = new MenuButton("Button ok green0.png", 40*width/50 ,20*height/50, width/15, width/15);
    private MenuButton background = new MenuButton("MainMenuInterface0.png", 0 , 0, width, height);

    public MainMenu(){
    }


    public void draw(GraphicsContext gc){
        background.draw(gc);
        buttonExitRed.draw(gc);
        buttonPlusBot.draw(gc);
        buttonPlusPlayer.draw(gc);
        buttonOkGreen.draw(gc);
        display_labels(gc);
    }

    public void display_labels(GraphicsContext gc){
        for (int i = 0; i< labels.size(); i++){
            labels.get(i).setHeight(width/15);
            labels.get(i).setWidth(width/5);
            switch (i){
                case 0:
                    labels.get(i).setX(3*width/50);
                    labels.get(i).setY(30*height/50);
                    break;
                case 1:
                    labels.get(i).setX(14*width/50);
                    labels.get(i).setY(30*height/50);
                    break;
                case 2:
                    labels.get(i).setX(25*width/50);
                    labels.get(i).setY(30*height/50);
                    break;
                case 3:
                    labels.get(i).setX(36*width/50);
                    labels.get(i).setY(30*height/50);
                    break;
                case 4:
                    labels.get(i).setX(3*width/50);
                    labels.get(i).setY(40*height/50);
                    break;
                case 5:
                    labels.get(i).setX(14*width/50);
                    labels.get(i).setY(40*height/50);
                    break;
                case 6:
                    labels.get(i).setX(25*width/50);
                    labels.get(i).setY(40*height/50);
                    break;
                case 7:
                    labels.get(i).setX(36*width/50);
                    labels.get(i).setY(40*height/50);
                    break;
            }
            labels.get(i).draw(gc);
        }
    }



    public void add_player(){
        playables.add(new Player());
        labels.add(new MenuButton("player_label1.png"));
    }
    public void add_bot(){
        playables.add(new Bot());
        labels.add(new MenuButton("bot_label1.png"));
    }


    public MenuButton getButtonExitRed() {
        return buttonExitRed;
    }

    public MenuButton getButtonOkGreen() {
        return buttonOkGreen;
    }

    public MenuButton getButtonPlusPlayer() {
        return buttonPlusPlayer;
    }

    public MenuButton getButtonPlusBot() {
        return buttonPlusBot;
    }

    public List<MenuButton> getAllButtons(){
        List<MenuButton> allButtons = new ArrayList<>();
        allButtons.addAll(Arrays.asList(background,  buttonExitRed, buttonPlusPlayer,  buttonOkGreen, buttonPlusBot));
        return allButtons;
    }

    public ArrayList<Playable> getPlayables() {
        return playables;
    }
}
