package captainsly.randomclasses.javafx;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

/**
 * You can extend this class and use it to draw simple animated drawings
 * By following Lodev's RayCasting tutorial you can use this to get a simple, albeit laggy, raycaster
 * see the JavaFX-Raycast Project I have on github for an example.
 * https://github.com/CaptainSly/JavaFX-Raycast
 * @author Zachary
 *
 */
public abstract class CanvasApplication extends Application {

	private BorderPane root;
	private GridPane grid;
	private Canvas canvas;

	private Scene canvasScene;

	private GraphicsContext gc;

	// Input

	private ArrayList<String> input;

	// Frame Times

	private double time, oldTime, frameTime;

	public CanvasApplication(double canvasWidth, double canvasHeight) {
		root = new BorderPane();
		grid = new GridPane();	
		canvas = new Canvas(canvasWidth, canvasHeight);
		canvasScene = new Scene(root);

		input = new ArrayList<String>();

		root.setTop(grid);
		root.setCenter(canvas);

		canvasScene.setOnKeyPressed(e -> {
			if (!input.contains(e.getCode().toString()))
				input.add(e.getCode().toString());
		});

		canvasScene.setOnKeyReleased(e -> {
			input.remove(e.getCode().toString());
		});

		gc = canvas.getGraphicsContext2D();
		time = System.currentTimeMillis();
	}

	public void gameLoop() {
		new AnimationTimer() {

			@Override
			public void handle(long currentNanoTime) {

				oldTime = time;
				time = System.currentTimeMillis();
				redraw();
				render();
				frameTime = time - oldTime;

			}

		}.start();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(canvasScene);
		gameLoop();
	}

	public abstract void render();

	public void redraw() {
		gc.setStroke(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	public void drawLine(int x, int y, int x2, int y2, Color color) {
		gc.setStroke(color);
		gc.strokeLine(x, y, x2, y2);
	}

	public void drawLine(int x, int y, int x2, int y2, CycleMethod method, Color... colors) {

		ArrayList<Stop> stops = new ArrayList<Stop>();
		int i = 0;
		for (Color c : colors) {
			stops.add(new Stop(i, c));
			i++;
		}

		LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, method, stops);

		gc.setStroke(linearGradient);
		gc.strokeLine(x, y, x2, y2);

	}

	public void drawText(String text, int x, int y, Color color) {
		gc.setStroke(color);
		gc.strokeText(text, x, y);
	}

	public void drawRect(int x, int y, int w, int h, Color color) {
		gc.setStroke(color);
		gc.strokeRect(x, y, w, h);
	}

	public void drawPixel(int x, int y, Color color) {
		gc.setStroke(color);
		gc.strokeLine(x, y, x, y);
	}

	public void drawPixel(int x, int y, CycleMethod method, Color... colors) {
		ArrayList<Stop> stops = new ArrayList<Stop>();
		int i = 0;
		for (Color c : colors) {
			stops.add(new Stop(i, c));
			i++;
		}

		LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, method, stops);

		gc.setStroke(linearGradient);
		gc.strokeLine(x, y, x, y);
	}

	public void drawImage(Image img, int x, int y) {
		gc.drawImage(img, x, y);
	}

	public void drawImage(Image img, int x, int y, int w, int h) {
		gc.drawImage(img, x, y, w, h);
	}

	public BorderPane getRoot() {
		return root;
	}

	public GridPane getGrid() {
		return grid;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public double getCanvasWidth() {
		return canvas.getWidth();
	}

	public double getCanvasHeight() {
		return canvas.getHeight();
	}

	public ArrayList<String> getInput() {
		return input;
	}

	public double getDelta() {
		return frameTime;
	}

	public Scene getCanvasScene() {
		return canvasScene;
	}

	public GraphicsContext getGraphicsContext() {
		return gc;
	}
}