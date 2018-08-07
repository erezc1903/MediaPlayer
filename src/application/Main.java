package application;
	
import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

/*
 maybe add support to playing other type of media like mkv,avi...
 add subtitles property.
 */
public class Main extends Application {
	
	FileChooser mediaChooser;
	
	Player mediaPlayer;
	
	MenuBar menu;
	Menu fileMenu;
	MenuItem openItem;
	MenuItem exitItem;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			
			mediaChooser = new FileChooser();
			menu = new MenuBar();	
			fileMenu = new Menu("file");
			openItem = new MenuItem("open");
			exitItem = new MenuItem("exit");
			
			fileMenu.getItems().add(openItem);
			fileMenu.getItems().add(exitItem);
			menu.getMenus().add(fileMenu);
			
			openItem.setOnAction((e) -> {
				
				try {
					File mediaFile = mediaChooser.showOpenDialog(primaryStage);
					if(mediaPlayer != null) {
						mediaPlayer.player.dispose();
					}
					if(mediaFile != null) {
						mediaPlayer = new Player(mediaFile.toURI().toURL().toExternalForm());
						mediaPlayer.view.setFitWidth(scene.getWidth());
						root.setCenter(mediaPlayer);
					}

				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			exitItem.setOnAction((e) -> {
				Platform.exit();
			});
			
			root.setTop(menu);
			
			primaryStage.widthProperty().addListener((obs,oldval,newval) -> {
				if(mediaPlayer != null)
					mediaPlayer.view.setFitWidth(scene.getWidth());
			});
			
			primaryStage.setTitle("Media Player");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("media-player-icon.png")));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
