package lp2a.game.skyjogame;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MainMenu {
    // Attributes
    private int width = Skyjo.XMAX;
    private int height = Skyjo.YMAX;
    private List<Playable> playables = new ArrayList<>();
    private ArrayList<MenuButton> labels = new ArrayList<>();
    private ArrayList<MenuButton> suppr_buttons = new ArrayList<>();
    private MenuButton buttonPlusBot = new MenuButton("add_bot_button1.png", 25*width/50 ,20*height/50, width/5, width/15);
    private MenuButton buttonPlusPlayer = new MenuButton("add_player_button1.png", 15*width/50 ,20*height/50, width/5, width/15);
    private MenuButton buttonExitRed = new MenuButton("Button exit red0.png", 45*width/50 ,5*height/50, width/15, width/15);
    private MenuButton buttonOkGreen = new MenuButton("Button ok green0.png", 40*width/50 ,20*height/50, width/15, width/15);
    private MenuButton background = new MenuButton("MainMenuInterface0.png", 0 , 0, width, height);

    // Methods
    public MainMenu(){
    }


    public void draw(GraphicsContext gc){
        background.draw(gc);
        buttonExitRed.draw(gc);
        buttonPlusBot.draw(gc);
        buttonPlusPlayer.draw(gc);
        buttonOkGreen.draw(gc);
        displayWinner(gc);
        display_labels(gc);
        display_playables(gc);
        display_suppr(gc);
    }

    public void display_labels(GraphicsContext gc){
        for (int i = 0; i< playables.size(); i++){
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

    public void displayWinner(GraphicsContext gc){
        if (Skyjo.lastWinner != null){
            gc.setFill(Color.BLACK);
            gc.setFont(Font.loadFont(getClass().getResourceAsStream("Gameria.ttf"),5*width/100));
            gc.fillText("The last winner was " +Skyjo.lastWinner, 41*width/200 ,77*height/200 );
            gc.setFill(Color.DARKGOLDENROD);
            gc.setFont(Font.loadFont(getClass().getResourceAsStream("Gameria.ttf"),5*width/100));
            gc.fillText("The last winner was " +Skyjo.lastWinner, 10*width/50 ,19*height/50 );
        }
        gc.setFill(Color.BLACK);
        gc.setFont(Font.loadFont(getClass().getResourceAsStream("Gameria.ttf"),width/60));

    }

    public void display_suppr(GraphicsContext gc){
        for (int i = 0; i< playables.size(); i++){
            suppr_buttons.get(i).setHeight(width/40);
            suppr_buttons.get(i).setWidth(width/40);

            switch (i){
                case 0:
                    suppr_buttons.get(i).setX(3*width/50);
                    suppr_buttons.get(i).setY(34*height/50);

                    break;
                case 1:
                    suppr_buttons.get(i).setX(14*width/50);
                    suppr_buttons.get(i).setY(34*height/50);
                    break;
                case 2:
                    suppr_buttons.get(i).setX(25*width/50);
                    suppr_buttons.get(i).setY(34*height/50);
                    break;
                case 3:
                    suppr_buttons.get(i).setX(36*width/50);
                    suppr_buttons.get(i).setY(34*height/50);
                    break;
                case 4:
                    suppr_buttons.get(i).setX(3*width/50);
                    suppr_buttons.get(i).setY(44*height/50);
                    break;
                case 5:
                    suppr_buttons.get(i).setX(14*width/50);
                    suppr_buttons.get(i).setY(44*height/50);
                    break;
                case 6:
                    suppr_buttons.get(i).setX(25*width/50);
                    suppr_buttons.get(i).setY(44*height/50);
                    break;
                case 7:
                    suppr_buttons.get(i).setX(36*width/50);
                    suppr_buttons.get(i).setY(44*height/50);
                    break;
            }
            suppr_buttons.get(i).draw(gc);
        }
    }

    public void display_playables(GraphicsContext gc){
        for (int i = 0; i< playables.size(); i++){
            gc.setFont(Font.loadFont(getClass().getResourceAsStream("Gameria.ttf"), width/50));
            gc.setFill(playables.get(i).getPlayercolor());
            switch (i){
                case 0:
                    gc.fillText(playables.get(i).getName(), 5*width/50, 34*height/50);
                    break;
                case 1:
                    gc.fillText(playables.get(i).getName(), 16*width/50, 34*height/50);
                    break;
                case 2:
                    gc.fillText(playables.get(i).getName(), 27*width/50, 34*height/50);
                    break;
                case 3:
                    gc.fillText(playables.get(i).getName(), 38*width/50, 34*height/50);
                    break;
                case 4:
                    gc.fillText(playables.get(i).getName(), 5*width/50, 44*height/50);
                    break;
                case 5:
                    gc.fillText(playables.get(i).getName(), 16*width/50, 44*height/50);
                    break;
                case 6:
                    gc.fillText(playables.get(i).getName(), 27*width/50, 44*height/50);
                    break;
                case 7:
                    gc.fillText(playables.get(i).getName(), 38*width/50, 44*height/50);
                    break;
            }
        }
    }

    public void remove_playable(int id){
        labels.remove(id);
        suppr_buttons.remove(id);
        playables.remove(id);
    }

    public void add_player(){
        // Opening a new thread to overpass the main user interface and still display the Menu while opeing a dialog box in a showAndWait method.
        Platform.runLater(() ->{
            // Creating a dialog box to add the name of the new player
            TextInputDialog dialog = new TextInputDialog("New Player");
            dialog.setTitle("New Player");
            dialog.setHeaderText("What will be the name of the new player ?");
            dialog.setContentText("Pseudo:");

            // Adding a filter for user input
            TextField editor = dialog.getEditor();
            editor.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 10) { // Text can't be longer than 10 caracters
                    editor.setText(oldValue);
                }
                if (!newValue.matches("[a-zA-Z0-9- ]*")) { // Players name can only have letters and numbers (It protects the code from possible bugs with name with symbols)
                    editor.setText(newValue.replaceAll("[^a-zA-Z0-9- ]", ""));
                }
            });

            // Display the box
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newPlayer -> {
                //Creating the new player with the name entered
                playables.add(new Player(newPlayer));
            });
        });

        labels.add(new MenuButton("player_label1.png"));
        suppr_buttons.add(new MenuButton("Button cross1.png"));
    }
    public void add_bot(){
        playables.add(new Bot());
        labels.add(new MenuButton("bot_label1.png"));
        suppr_buttons.add(new MenuButton("Button cross1.png"));
    }

    public ArrayList<MenuButton> getSuppr_buttons() {
        return suppr_buttons;
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
        allButtons.addAll(suppr_buttons);
        return allButtons;
    }

    public List<Playable> getPlayables() {
        return playables;
    }
}
