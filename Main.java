package application;

import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {

	// Variables
	public static final int move = 200;
	public static final int size = move;
	public static final int gameSize = 4; 
	public static final double textsize = 0.9; 
	public static int xMax = size * gameSize;
	public static int yMax = size * gameSize;
	public static int inPlaceCount = 0;

	public static ArrayList<StackPane> stackList = new ArrayList<StackPane>();
	public static ArrayList<Integer> numbers = new ArrayList<Integer>();

	@Override
	public void start(Stage primaryStage) {

		for (int i = 0; i < gameSize; i++)
			for (int j = 0; j < gameSize; j++) {
				if (i * gameSize + j != gameSize*gameSize-1) {
					Rectangle rect = new Rectangle();
					StackPane stack = new StackPane();
					Text text = new Text();
					rect.setHeight(size);
					rect.setWidth(size);
					rect.setFill(Color.color(Math.random(), Math.random(), Math.random()));
					rect.setStrokeWidth(50);

					int randomint = new Random().nextInt(gameSize*gameSize-1) + 1;
					while (numbers.contains(randomint)) {
						randomint = new Random().nextInt(gameSize*gameSize-1) + 1;
					}
					numbers.add(randomint);

					text.setText(randomint + "");
					text.setFont(new Font(size*textsize));

					stack.getChildren().addAll(rect, text);
					stack.setLayoutX(j * size);
					stack.setLayoutY(i * size);

					stack.setOnMousePressed(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent me) {
							// MOVE
							if (stack.getLayoutX() != xMax - size && canMove(stack, 'r')) { // RIGHT
								stack.setLayoutX(stack.getLayoutX() + move);
							} else if (stack.getLayoutX() != 0 && canMove(stack, 'l')) { // LEFT
								stack.setLayoutX(stack.getLayoutX() - move);
							} else if (stack.getLayoutY() != yMax - size && canMove(stack, 'u')) { // UP
								stack.setLayoutY(stack.getLayoutY() + move);
							} else if (stack.getLayoutY() != 0 && canMove(stack, 'd')) { // DOWN
								stack.setLayoutY(stack.getLayoutY() - move);
							}
						}
					});

					stackList.add(stack);
				} else {
					break;
				}
			}

		Group root = new Group();

		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				// CheckIfWon
			inPlaceCount = 0;
				

				for (StackPane stack : stackList) {
					 try { 
						 Text c = (Text) stack.getChildren().get(1);
						 int num = Integer.parseInt(c.getText());
						 int x = 0;
						 int y = 0;
						 int sum;
						 
						 sum = (num-1) * move;
						 
						 for(int i = 0; i<sum; i = i + move) {
							 x = x + size;
							 if(x == xMax) {
								 x = 0;
								 y = y + size;
							 }
						 }

						 if(stack.getLayoutX() == x && stack.getLayoutY() == y) {
							 inPlaceCount++;
						 }
						 
					 	}catch(Exception e) {
					  System.out.println("NOT A TEXT TYPE");
					  }
				}
				if(inPlaceCount == gameSize*gameSize-1) {
					Text text = new Text();
					text.setText("YOU WON!!!");
					text.setFont(new Font(size*0.5));
					text.setFill(Color.WHITE);
					text.setX(xMax/2 - text.getLayoutBounds().getWidth()/2);
					text.setY(yMax/2);
					root.getChildren().add(text);
					System.out.println("Player have won");
				}
			}
		});

		for (StackPane stack : stackList) {
			root.getChildren().add(stack);
		}
		Scene scene = new Scene(root, xMax, yMax);
		primaryStage.setTitle("SLIDING PUZZLE GAME");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static boolean canMove(StackPane stack, char g) {
		boolean canMove = true;
		switch (g) {
		case 'r':
			for (StackPane tempstack : stackList) {
				if (stack.getLayoutX() + size == tempstack.getLayoutX()
						&& stack.getLayoutY() == tempstack.getLayoutY()) {
					canMove = false;
				}
			}
			break;
		case 'l':
			for (StackPane tempstack : stackList) {
				if (stack.getLayoutX() - size == tempstack.getLayoutX()
						&& stack.getLayoutY() == tempstack.getLayoutY()) {
					canMove = false;
				}
			}
			break;
		case 'u':
			for (StackPane tempstack : stackList) {
				if (stack.getLayoutY() + size == tempstack.getLayoutY()
						&& stack.getLayoutX() == tempstack.getLayoutX()) {
					canMove = false;
				}
			}
			break;
		case 'd':
			for (StackPane tempstack : stackList) {
				if (stack.getLayoutY() - size == tempstack.getLayoutY()
						&& stack.getLayoutX() == tempstack.getLayoutX()) {
					canMove = false;
				}
			}
			break;
		}

		return canMove;
	}
}
