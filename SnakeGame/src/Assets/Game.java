package Assets;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Game extends Application {

	final int WIDTH = 600;
	final int HEIGHT = 540;

	double size = 30;
	double rectX = 0;
	double rectY = 0;  
	double previousX = 0;
	double previousY = 0;
	double previousPreviousX=0;
	double previousPreviousY=0;
	double randomX;
	double randomY;
	double xSpeed = size;


	//Creating assets (food, border, score, gameover)
	Rectangle food = new Rectangle(size, size);
	Rectangle border = new Rectangle(0, 0, WIDTH, HEIGHT);
	Random generator = new Random();
	ArrayList<Piece> snake = new ArrayList<Piece>(); //THE PIECES OF THE SNAKE
	Directions dir;
	Font font = new Font("Arial", 16);
	Text score = new Text(250,50,"Score: 000");
	Text gameover = new Text(250,25, "GAME OVER");
	Group root = new Group();

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {


		//Set up
		stage.setTitle("Snake");
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		scene.setFill(Color.WHITE);
		scene.setOnKeyPressed(this::movement);//ARROWKEY MOVEMENT HANDLER
		score.setFont(font);
		gameover.setFont(font);
		root.getChildren().add(score);
		border.setStroke(Color.CORAL);
		border.setStrokeWidth(5);
		border.setFill(Color.TRANSPARENT);
		root.getChildren().add(border);
		dir = Directions.DOWN;

		snake.add(new Piece());
		for(Piece piece : snake) {
			piece.setLayoutX(rectX);
			piece.setLayoutY(rectY);
			piece.setWidth(size);
			piece.setHeight(size);
			piece.setFill(Color.GHOSTWHITE);
			piece.setStroke(Color.SLATEGREY);
			root.getChildren().add(piece);
		}

		snake.get(0).setFill(Color.CORAL);//MAKING THE HEAD COLOR UNIQUE


		randomX = generator.nextInt(WIDTH/30)*30;
		randomY = generator.nextInt(HEIGHT/30)*30;
		food.setLayoutX(randomX);
		food.setLayoutY(randomY);
		food.setFill(Color.CORAL);
		root.getChildren().add(food);


		stage.setScene(scene);
		stage.show();


		AnimationTimer animator = new AnimationTimer(){

			long lastUpdate = System.nanoTime();
			@Override
			public void handle(long currentTime) {


				if(currentTime-lastUpdate >= 100000000) {
					// UPDATE


					if(checkLoss() == -1) {
						this.stop();
					}

					if(snake.get(0).getLayoutX() == food.getLayoutX() && snake.get(0).getLayoutY() == food.getLayoutY()) {
						moveFood();
						lengthen();
						addScore();
					}


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
	public void moveFood() {//GETS NEW COORDINATES FOR THE FOOD
		randomX = generator.nextInt(WIDTH/30)*30;
		randomY = generator.nextInt(HEIGHT/30)*30;

		for(Piece p : snake) {
			if(p.getLayoutX() == randomX && p.getLayoutY() == randomY) {
				moveFood();
			}
		}

		food.setLayoutX(randomX);
		food.setLayoutY(randomY);
	}
	public void lengthen() {
		snake.add(new Piece());
		snake.get(snake.size()-1).setLayoutX(rectX);
		snake.get(snake.size()-1).setLayoutY(rectY);
		snake.get(snake.size()-1).setWidth(size);
		snake.get(snake.size()-1).setHeight(size);
		snake.get(snake.size()-1).setFill(Color.GHOSTWHITE);
		snake.get(snake.size()-1).setStroke(Color.SLATEGREY);
		root.getChildren().add(snake.get(snake.size()-1));
	}
	public void addScore() {
		score.setText("Score: " + (snake.size()-1)*100);
	}
	public int checkLoss() {

		if(snake.get(0).getLayoutX() < 0 || snake.get(0).getLayoutX() >= WIDTH || snake.get(0).getLayoutY() < 0 || snake.get(0).getLayoutY() >= HEIGHT) {
			System.out.println("OUTOFBOUNDS");
			gameover.setFill(Color.RED);
			root.getChildren().add(gameover);
			return -1;
		}
		for(int i = 1; i < snake.size(); i++) {
			if(snake.get(0).getLayoutX() == snake.get(i).getLayoutX() && snake.get(0).getLayoutY() == snake.get(i).getLayoutY()) {
				System.out.println("GAME OVER");
				gameover.setFill(Color.RED);
				root.getChildren().add(gameover);
				root.getChildren().remove(score);
				root.getChildren().add(score);
				return -1;
			}

		}
		return(0);
	}


}
