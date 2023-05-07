package lp2a.game.skyjogame;

import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class Menu {
    // Attributes
    private Stage stage;
    private ListView<String> playersList = new ListView<>();
    private ListView<Bot> botslist =new ListView<>();
    private TextField addPlayerField = new TextField();
    private boolean start = false;

    // Methods
    public Menu() {

        // Créer une fenêtre pour afficher le menu
        stage = new Stage();
        playersList.getItems().add("Player 1");

        // Créer un bouton pour ajouter un joueur
        Button addButton = new Button("Ajouter Joueur");
        addButton.setDisable(false);
        addButton.setOnAction(e -> {
            // Ajouter le joueur à la liste des joueurs
            if (!addPlayerField.getText().isEmpty()) playersList.getItems().add(addPlayerField.getText());
            // Effacer le champ de texte pour ajouter un joueur
            addPlayerField.clear();
        });

        // Créer un bouton pour supprimer un joueur
        Button removeButton = new Button("Supprimer Joueur");
        removeButton.setDisable(true);
        removeButton.setOnAction(e -> {
            // Obtenir l'index du joueur sélectionné
            int selectedIndex = playersList.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                playersList.getItems().remove(selectedIndex);
            }
        });

        // Créer un bouton pour modifier le pseudo d'un joueur sélectionné
        Button editButton = new Button("Modifier Pseudo");
        editButton.setDisable(false);
        editButton.setOnAction(e -> {
            // Obtenir l'index du joueur sélectionné
            int selectedIndex = playersList.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                // Obtenir le pseudo du joueur sélectionné
                String selectedPlayer = playersList.getSelectionModel().getSelectedItem();

                // Créer une boîte de dialogue pour modifier le pseudo du joueur sélectionné
                TextInputDialog dialog = new TextInputDialog(selectedPlayer);
                dialog.setTitle("Modifier le pseudo");
                dialog.setHeaderText("Modifier le pseudo de " + selectedPlayer);
                dialog.setContentText("Nouveau pseudo:");

                // Afficher la boîte de dialogue pour modifier le pseudo du joueur sélectionné
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(newPlayer -> {
                    // Modifier le pseudo du joueur sélectionné dans la liste des joueurs
                    playersList.getItems().set(selectedIndex, newPlayer);
                });
            }
        });

        // Créer un bouton pour ajouter un bot
        Button addBotButton = new Button("Ajouter Bot");
        addBotButton.setDisable(false);
        addBotButton.setOnAction(e -> {
            // Ajouter le bot à la liste des bots
            Bot bot = new Bot();
            botslist.getItems().add(bot);
        });

        // Créer un bouton pour supprimer un bot
        Button removeBotButton = new Button("Supprimer Bot");
        removeBotButton.setDisable(true);
        removeBotButton.setOnAction(e -> {
            // Supprimer le dernier bot ajouté de la liste des bots
            int lastIndex = botslist.getItems().size() - 1;
            botslist.getItems().remove(lastIndex);
        });

        // Créer un bouton pour lancer la partie
        Button startButton = new Button("Lancer la partie");
        startButton.setDisable(true);
        startButton.setOnAction(e -> {
            start = true;
            stage.close();
        });

        // Vérifier si on peut activer le bouton "Lancer la partie"
        playersList.getItems().addListener((ListChangeListener<String>) c -> {
            if (playersList.getItems().size() >= 1) {
                editButton.setDisable(false);
                startButton.setDisable(false);
            }
            if (playersList.getItems().size() >= 2){
                removeButton.setDisable(false);
            }
            else {
                editButton.setDisable(true);
                removeButton.setDisable(true);
                startButton.setDisable(true);
            }
            if (playersList.getItems().size() + botslist.getItems().size() >= 8){
                addButton.setDisable(true);
            } else {
                addButton.setDisable(false);
            }
        });

        botslist.getItems().addListener((ListChangeListener<Bot>) c -> {
            if (botslist.getItems().size() >= 1){
                removeBotButton.setDisable(false);
            }
            else {
                removeBotButton.setDisable(true);
            }
            if ( playersList.getItems().size() + botslist.getItems().size() < 8){
                addBotButton.setDisable(false);
            }
            else {
                addBotButton.setDisable(true);
            }
        });

        // Modifier la ListView des bots pour afficher le nom de chaque bot
        botslist.setCellFactory(param -> new ListCell<Bot>() {
            @Override
            protected void updateItem(Bot bot, boolean empty) {
                super.updateItem(bot, empty);
                if (empty || bot == null) {
                    setText(null);
                } else {
                    setText(bot.getName());
                }
            }
        });

        // Ajouter les éléments à une VBox
        VBox menuBox = new VBox();

        // Créer une étiquette pour les joueurs
        Label playersLabel = new Label("Joueurs:");
        // Ajouter la liste des joueurs et l'étiquette à la VBox
        menuBox.getChildren().addAll(playersLabel, playersList, addPlayerField, addButton, removeButton, editButton, startButton);

        // Créer une étiquette pour les bots
        Label botsLabel = new Label("Bots:");
        // Ajouter la liste des bots et l'étiquette à la VBox
        menuBox.getChildren().addAll(botsLabel, botslist, addBotButton, removeBotButton);

        // Afficher la VBox dans la fenêtre pour afficher le menu
        Scene menuScene = new Scene(menuBox, Skyjo.XMAX, Skyjo.YMAX);
        stage.setScene(menuScene);
        stage.setTitle("Menu Skyjo");
    }

    public void show() {
        // Afficher la fenêtre pour afficher le menu
        stage.show();
    }

    public boolean isStart() {
        return start;
    }
}