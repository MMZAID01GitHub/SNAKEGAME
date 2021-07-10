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

	double size = 50;
	double rectX = 100;
	double rectY = 200;  
	double previousX = 0;
	double previousY = 0;
	double previousPreviousX=0;
	double previousPreviousY=0;
	double xSpeed = size;
	

	ArrayList<Rectangle> snake = new ArrayList<Rectangle>();
	ArrayList<Location> locations = new ArrayList<Location>();
	

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
		root.getChildren().add(rect);

		Rectangle rect2 = new Rectangle();
		rect2.setLayoutX(rectX);
		rect2.setLayoutY(rectY);
		rect2.setWidth(size);
		rect2.setHeight(size);
		rect2.setFill(Color.PURPLE);
		rect2.setStroke(Color.ORANGE);
		root.getChildren().add(rect2);
		
		Rectangle rect3 = new Rectangle();
		rect3.setLayoutX(rectX);
		rect3.setLayoutY(rectY);
		rect3.setWidth(size);
		rect3.setHeight(size);
		rect3.setFill(Color.BLUE);
		rect3.setStroke(Color.ORANGE);
		root.getChildren().add(rect3);
		
		



		

		stage.setScene(scene);
		stage.show();

		
		AnimationTimer animator = new AnimationTimer(){

			long lastUpdate = System.nanoTime();
			@Override
			public void handle(long currentTime) {
				
				if(currentTime-lastUpdate >= 250000000) {
					// UPDATE
					
					previousX = rectX;
					previousY = rectY;
					previousPreviousX = rect2.getLayoutX();
					previousPreviousY = rect2.getLayoutY();
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
					rect2.setLayoutX(previousX);
					rect2.setLayoutY(previousY);
					rect3.setLayoutX(previousPreviousX);
					rect3.setLayoutY(previousPreviousY);
//					if(dir == Directions.DOWN) {
	//
//						rect2.setLayoutY(rect.getLayoutY()-size);
//						rect2.setLayoutX(rectX);
//						
//						
	//
//					}
//					if(dir == Directions.UP) {
//						rect2.setLayoutY(rect.getLayoutY()+size);
//						rect2.setLayoutX(rectX);
//						
//					}
//					if(dir == Directions.LEFT) {
//						rect2.setLayoutX(rect.getLayoutX()+size);
//						rect2.setLayoutY(rectY);
//						
//					}
//					if(dir == Directions.RIGHT) {
//						rect2.setLayoutX(rect.getLayoutX()-size);
//						rect2.setLayoutY(rectY);
//					
//					}
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
