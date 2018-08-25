package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Player extends BorderPane {

	
	Media media; // The Media class represents a media resource. It is instantiated from the string form of a source URI.
	MediaPlayer player; // The MediaPlayer class provides the controls for playing media.
	MediaView view; // A Node that provides a view of Media being played by a MediaPlayer. 
	Pane mpane;
	MediaBar mediaBar; // The media bar that controls the time and volume of the video.
	
	public Player(String file) {
		
		media = new Media(file);
		player = new MediaPlayer(media);
		view = new MediaView(player);
		mpane = new Pane();
		mediaBar = new MediaBar(player);
		
		mpane.getChildren().add(view);
		
		setCenter(mpane);
		setBottom(mediaBar);
		player.play();
	}
}
