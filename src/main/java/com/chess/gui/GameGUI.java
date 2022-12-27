package com.chess.gui;

import com.chess.board.BoardController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class GameGUI extends Application {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 640;

    @Override
    public void start(Stage window) {
        StackPane root = new StackPane();
    
        VBox menu = createMenuScreen();
        root.getChildren().add(menu);       

        Scene menuScreen = new Scene(root, WIDTH, HEIGHT);
        window.setScene(menuScreen);
        window.setTitle("Chess");
        window.show();
    }

    private VBox createMenuScreen() {
        VBox menu = new VBox();

        Text menuTitle = new Text();      
        menuTitle.setText("Chess");
        menuTitle.setStyle("-fx-font-size:60;");
        menu.getChildren().add(menuTitle);

        Button play = createMenuButton();

        menu.setSpacing(10);
        menu.getChildren().addAll(play);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(0, 0, -HEIGHT/8, 0));
        return menu;
    }

    private Button createMenuButton() {
        Button button = new Button("PLAY");
        button.setPrefWidth(WIDTH/2);
        button.setPrefHeight(HEIGHT/10);

        if (Objects.equals("PLAY", "PLAY")) {
            Pane boardPane = createBoardScreen();
            button.setOnAction(e -> button.getScene().setRoot(boardPane));
        }

        return button;
    }

    private Pane createBoardScreen() {
        Pane root = new Pane();
        BoardController controller = new BoardController();
        root.getChildren().add(controller.getView());
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, controller);
        return root;        
    }
    
    public static void main(String[] args) {
		launch(args);
	}
}