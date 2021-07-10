package Assets;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Game extends Application {

	final int WIDTH = 600;
	final int HEIGHT = 400;

	double size = 30;
	double rectX = 100;
	double rectY = 200;  
	double previousX = 0;
	double previousY = 0;
	double previousPreviousX=0;
	double previousPreviousY=0;
	double xSpeed = size;
	

	ArrayList<Piece> snake = new ArrayList<Piece>(); //THE PIECES OF THE SNAKE
	

	Directions dir;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {



		stage.setTitle("Snake");

		Group root = new Group();
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		scene.setFill(Color.BLACK);
		scene.setOnKeyPressed(this::movement);//ARROWKEY MOVEMENT HANDLER

		dir = Directions.DOWN;
//		Piece rect = new Piece();
//		rect.setLayoutX(rectX);
//		rect.setLayoutY(rectY);
//		rect.setWidth(size);
//		rect.setHeight(size);
//		rect.setFill(Color.AZURE);
//		rect.setStroke(Color.ORANGE);
//		root.getChildren().add(rect);
		for(int i = 0; i < 10; i ++) {//INSTANTIATING SOME PIECES
			snake.add(new Piece());
		}
		for(Piece piece : snake) {
			piece.setLayoutX(rectX);
			piece.setLayoutY(rectY);
			piece.setWidth(size);
			piece.setHeight(size);
			piece.setFill(Color.AQUAMARINE);
			piece.setStroke(Color.ORANGE);
			root.getChildren().add(piece);
		}
		
		snake.get(0).setFill(Color.CORAL);//MAKING THE HEAD COLOR UNIQUE

		

		stage.setScene(scene);
		stage.show();

		
		AnimationTimer animator = new AnimationTimer(){

			long lastUpdate = System.nanoTime();
			@Override
			public void handle(long currentTime) {
				
				if(currentTime-lastUpdate >= 250000000) {
					// UPDATE

					//RECORDING COORDINATES BEFORE REPLACEMENT
					for(Piece p : snake) {
						p.previousX = p.getLayoutX();
						p.previousY = p.getLayoutY();
					}
					
					//MOVING A SPACE IN DIRECTION OF DIR
					switch(dir) {
					case RIGHT:
						rectX+=xSpeed;
						break;
					case LEFT:
						rectX-=xSpeed;
						break;
					case UP:
						rectY-=xSpeed;
						break;
					case DOWN:
						rectY+=xSpeed;
						break;
					}
					
					
					//DRAWING THE HEAD
					snake.get(0).setLayoutX(rectX);
					snake.get(0).setLayoutY(rectY);
					
					//DRAWING REST OF PIECES IN THE PREVIOUS LOCATION OF THE PIECE PRECEDING IT
					for(int i = 1; i < snake.size(); i++) {
						snake.get(i).setLayoutX(snake.get(i-1).previousX);
						snake.get(i).setLayoutY(snake.get(i-1).previousY);

					}

					
					
					lastUpdate = currentTime;
				}
				

			}      
		};

		animator.start();     
	}

	public enum Directions{
		LEFT,
		RIGHT,
		UP,
		DOWN
	}

	public void movement(KeyEvent event) {//CHANGING DIRECTION WITH KEY PRESS
		if(event.getCode() == KeyCode.RIGHT) {
			dir=Directions.RIGHT;
		}
		if(event.getCode() == KeyCode.LEFT) {
			dir = Directions.LEFT;
		}
		if(event.getCode() == KeyCode.DOWN) {
			dir = Directions.DOWN;
		}
		if(event.getCode() == KeyCode.UP) {
			dir = Directions.UP;
		}
	}
}
