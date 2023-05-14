package lp2a.game.skyjogame;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

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

    /**
     * This method display the main menu
     * @param gc GraphicsContext
     */
    public void draw(GraphicsContext gc){
        background.draw(gc);
        buttonExitRed.draw(gc);
        buttonPlusBot.draw(gc);
        buttonPlusPlayer.draw(gc);
        buttonOkGreen.draw(gc);
        displayWinner(gc);
        displayLabels(gc);
        displayPlayables(gc);
        displaySuppr(gc);
    }

    /**
     * This method display the labels of the players
     * @param gc GraphicsContext
     */
    public void displayLabels(GraphicsContext gc){
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

    /**
     * This method display the winner of the last game
     * @param gc GraphicsContext
     */
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

    /**
     * This method display the suppr buttons of the playables
     * @param gc GraphicsContext
     */
    public void displaySuppr(GraphicsContext gc){
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

    /**
     * This method display the names of the playables
     * @param gc GraphicsContext
     */
    public void displayPlayables(GraphicsContext gc){
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

    /**
     * This method remove a playable from the menu
     * @param id id of the playable to remove
     */
    public void removePlayable(int id){
        labels.remove(id);
        suppr_buttons.remove(id);
        playables.remove(id);
    }

    /**
     * This method add a playable to the menu by a dialog box
     */
    public void addPlayer(){
        // Opening a new thread to overpass the main user interface and still display the Menu while opeing a dialog box in a showAndWait method.
        Platform.runLater(() ->{
            // Creating a dialog box to add the name and color of the new player
            Dialog<Pair<String, Color>> dialog = new Dialog<>();
            dialog.setTitle("New Player");
            dialog.setHeaderText("What will be the name and color of the new player ?");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Creating a GridPane to hold the input fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            // Adding a Text field for player name
            TextField nameField = new TextField("New Player");
            nameField.setPromptText("Pseudo");
            nameField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 10) { // Text can't be longer than 10 characters
                    nameField.setText(oldValue);
                }
                if (!newValue.matches("[a-zA-Z0-9- ]*")) { // Players name can only have letters and numbers (It protects the code from possible bugs with name with symbols)
                    nameField.setText(newValue.replaceAll("[^a-zA-Z0-9- ]", ""));
                }
            });

            // Adding a Color picker for player color
            ColorPicker colorPicker = new ColorPicker(Color.BLUE);

            // Adding the input fields to the GridPane
            grid.add(new Label("Name:"), 0, 0);
            grid.add(nameField, 1, 0);
            grid.add(new Label("Color:"), 0, 1);
            grid.add(colorPicker, 1, 1);

            // Adding the GridPane to the dialog box
            dialog.getDialogPane().setContent(grid);

            // Focusing the name field by default
            Platform.runLater(() -> nameField.requestFocus());

            // Handling the OK button click
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    return new Pair<>(nameField.getText(), colorPicker.getValue());
                }
                return null;
            });

            // Display the box
            Optional<Pair<String, Color>> result = dialog.showAndWait();
            result.ifPresent(newPlayer -> {
                //Creating the new player with the name and color entered
                playables.add(new Player(newPlayer.getKey(), newPlayer.getValue()));
            });
        });

        labels.add(new MenuButton("player_label1.png"));
        suppr_buttons.add(new MenuButton("Button cross1.png"));
    }

    /**
     * This method add a bot to the menu
     */
    public void addBot(){
        playables.add(new Bot());
        labels.add(new MenuButton("bot_label1.png"));
        suppr_buttons.add(new MenuButton("Button cross1.png"));
    }

    public ArrayList<MenuButton> getSupprButtons() {
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
