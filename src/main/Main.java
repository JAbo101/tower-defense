package main;

import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Main extends Application implements Runnable {

    private Canvas canvas;
    private GraphicsContext gc;

    private Game game;

    private boolean running = false;

    private final int TICKS_PER_SECOND = 100;
    private final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    private final int MAX_FRAMESKIP = 5;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("tower-defense");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        canvas = ((Controller) fxmlLoader.getController()).getCanvas();
        gc = canvas.getGraphicsContext2D();

        game = new Game();

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),
                (ActionEvent event) -> {
                    game.repaint(gc);
                }
        );

        gameLoop.getKeyFrames().add(kf);

        primaryStage.show();

        new Thread(this).start();
        gameLoop.play();
    }

    @Override
    public void run() {
        running = true;

        double next_game_tick = System.currentTimeMillis();
        int loops;

        while (running) {
            loops = 0;
            while (System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {
                game.update();
                next_game_tick += SKIP_TICKS;
                loops++;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
