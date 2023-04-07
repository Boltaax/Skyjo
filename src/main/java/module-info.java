module lp2a.game.skyjogame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires com.almasb.fxgl.all;

    opens lp2a.game.skyjogame to javafx.fxml;
    exports lp2a.game.skyjogame;
}