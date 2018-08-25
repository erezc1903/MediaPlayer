package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class MediaBar extends HBox {

// Two sliders that go at the bottom of the screen. One for adjusting the volume and for adjusting the time of the video.
	
	Slider time;
	Slider vol;

// A play/pause button + play and pause icons.
	Button play;
	Image playIcon;
	ImageView PLIcon;
	Image pauseIcon;
	ImageView PAIcon;
	
// A mute button + mute and speaker icons.
	Button mute;
	Image muteIcon;
	ImageView MIcon;
	Image speakerIcon;
	ImageView SIcon;
	
	Label volumeLBL;
	
	MediaPlayer player; // The actual object that plays the video. Received as input for the constructor.
	

	
	public MediaBar(MediaPlayer mPlayer) {
		player = mPlayer;	
		vol = new Slider();
		time = new Slider();
		play = new Button();
		mute = new Button();
		volumeLBL = new Label("Volume :");
		
		setAlignment(Pos.CENTER); // Keep the media bar at the center of the window at all time.
		setStyle("-fx-background-color:white"); // Set the background color of the media bar to be white.
		setPadding(new Insets(10,10,10,10));
		
// Every object that is added and should be displayed on the screen needs to be added as a child node of HBox object.
		getChildren().add(play);
		getChildren().add(time);
		getChildren().add(volumeLBL);
		getChildren().add(vol);
		getChildren().add(mute);

		initVolumeSlider(); // Initialize the volume related buttons. 
		initTimeSlider(); // Initialize the time related buttons. 

// Whenever the play/pause button is clicked, the following lambda expression will be activated to play/pause the video and adjust the icon accordingly.
		play.setOnAction((e) -> { 
			Status status = player.getStatus();
			if(status == Status.PLAYING) {
				player.pause();
				play.setGraphic(PAIcon);
			} else if(status == Status.PAUSED) {
				player.play();
				play.setGraphic(PLIcon);
			}
		});
		
// Whenever the mute button is clicked, the following lambda expression will be activated to enable/disable the sound and adjust the icon accordingly.
		mute.setOnAction((e) -> {
			if(!player.isMute()) {
				player.setVolume(0);
				mute.setGraphic(MIcon);
				player.setMute(true);
			}
			else {
				player.setVolume(vol.getValue()/100);
				mute.setGraphic(SIcon);
				player.setMute(false);
			}
			
		});

// Synchronize the current time of the video and the time slider.
		player.currentTimeProperty().addListener((o) -> {
			time.setValue(player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis() * 100);
		});
		
// Adjust the current time of the video after the time slider is clicked. 
		time.valueProperty().addListener((o) -> {
			if(time.isPressed())
				player.seek(player.getMedia().getDuration().multiply(time.getValue()/100));
		});
	
// Adjust the volume of the video after the volume slider is clicked. 
		vol.valueProperty().addListener((o) -> {
			player.setVolume(vol.getValue()/100);
			if(player.isMute()) {
				mute.setGraphic(SIcon);
				player.setMute(false);
			}
		});
		
	}
	
	public void initVolumeSlider() {
		vol.prefWidth(70);
		vol.setMinWidth(30);
		
		vol.setValue(100);
		
		muteIcon = new Image(getClass().getResourceAsStream("mute-icon.png"));
		MIcon = new ImageView(muteIcon);
		
		MIcon.setFitWidth(18);
		MIcon.setFitHeight(18);
		
		speakerIcon = new Image(getClass().getResourceAsStream("speaker-icon.png"));
		SIcon = new ImageView(speakerIcon);

		SIcon.setFitWidth(18);
		SIcon.setFitHeight(18);
		mute.setGraphic(MIcon);
	}
	
	public void initTimeSlider() {
		
		playIcon = new Image(getClass().getResourceAsStream("play-icon.png"));
		PLIcon = new ImageView(playIcon);
		
		PLIcon.setFitWidth(18);
		PLIcon.setFitHeight(18);
		
		pauseIcon = new Image(getClass().getResourceAsStream("pause-icon.png"));
		PAIcon = new ImageView(pauseIcon);

		PAIcon.setFitWidth(18);
		PAIcon.setFitHeight(18);
		
		play.setGraphic(PAIcon);
	}
}
