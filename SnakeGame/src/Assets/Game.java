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

	double size = 20;
	double rectX = 100;
	double rectY = 200;  
	double previousX = 100;
	double previousY = 200;
	double xSpeed = 0.5;

	ArrayList<Rectangle> snake = new ArrayList<Rectangle>();
	

	Directions dir;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {



		stage.setTitle("Basic JavaFX demo");

		Group root = new Group();
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		scene.setFill(Color.BLACK);
		scene.setOnKeyPressed(this::movement);

		dir = Directions.DOWN;
		Rectangle rect = new Rectangle();
		rect.setLayoutX(rectX);
		rect.setLayoutY(rectY);
		rect.setWidth(size);
		rect.setHeight(size);
		rect.setFill(Color.AZURE);
		rect.setStroke(Color.ORANGE);
		// root.getChildren().add(rect);

		Rectangle rect2 = new Rectangle();
		rect2.setLayoutX(rectX);
		rect2.setLayoutY(rectY);
		rect2.setWidth(size);
		rect2.setHeight(size);
		rect2.setFill(Color.PURPLE);
		rect2.setStroke(Color.ORANGE);
		// root.getChildren().add(rect2);
		snake.add(rect);
		snake.add(rect2);
		
		for(int i = 0; i < 3; i ++) {
			snake.add(new Rectangle());
		}
		
		
		snake.add(new Rectangle());
		snake.add(new Rectangle());
		snake.add(new Rectangle());
		
		for(Rectangle r : snake) {
			//  	r.setFill(Color.AZURE);
			r.setStroke(Color.ORANGE);
			r.setWidth(size);
			r.setHeight(size);
			root.getChildren().add(r);
		}
		

		

		stage.setScene(scene);
		stage.show();


		AnimationTimer animator = new AnimationTimer(){

			@Override
			public void handle(long arg0) {

				// UPDATE
				
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

				rect.setLayoutX(rectX);
				rect.setLayoutY(rectY);

				if(dir == Directions.DOWN) {
					//snake.get(1).setLayoutX(rectX);
					//snake.get(2).setLayoutY(snake.get(1).getLayoutY()-size); 
					//snake.get(2).setLayoutX(rectX);
					for(int i = 1; i < snake.size();i++) {
						snake.get(i).setLayoutY(snake.get(i-1).getLayoutY()-size);
						snake.get(i).setLayoutX(rectX);
					}
					

				}
				if(dir == Directions.UP) {
					//rect2.setLayoutY(rect.getLayoutY()+size);
					//rect2.setLayoutX(rectX);
					for(int i = 1; i < snake.size();i++) {
						snake.get(i).setLayoutY(snake.get(i-1).getLayoutY()+size);
						snake.get(i).setLayoutX(rectX);
					}
				}
				if(dir == Directions.LEFT) {
					//rect2.setLayoutX(rect.getLayoutX()+size);
					//rect2.setLayoutY(rectY);
					for(int i = 1; i < snake.size();i++) {
						snake.get(i).setLayoutX(snake.get(i-1).getLayoutX()+size);
						snake.get(i).setLayoutY(rectY);
					}
				}
				if(dir == Directions.RIGHT) {
					//rect2.setLayoutX(rect.getLayoutX()-size);
					//rect2.setLayoutY(rectY);
					for(int i = 1; i < snake.size();i++) {
						snake.get(i).setLayoutX(snake.get(i-1).getLayoutX()-size);
						snake.get(i).setLayoutY(rectY);
					}
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

	public void movement(KeyEvent event) {
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