/**
 * 
 */
package window;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import window.mod.ResizeMod;

/**
 * @author Docter60
 *
 */
public class InfoDialog extends WindowPane {
	public static final Image INFO_DIALOG_INFO = new Image(InfoDialog.class.getResource("info.png").toString());
	public static final Image INFO_DIALOG_WARNING = new Image(InfoDialog.class.getResource("warning.png").toString());
	public static final Image INFO_DIALOG_ERROR = new Image(InfoDialog.class.getResource("error.png").toString());
	public static final double WIDTH = 300;
	public static final double HEIGHT = 100;
	
	// set up moving to center of screen
	
	public static final int INFO = 0;
	public static final int ERROR = 1;
	public static final int WARNING = 2;
	public static final int EXIT = 3;
	
	public static final String EXIT_INFO = "Are you sure you want to quit?";
	
	public InfoDialog(int type, String info) {
		super(0, 0, WIDTH, HEIGHT);
		ResizeMod.makeUnresizeable(this.mainPane);
		final ImageView icon;
		
		switch (type) {
		case INFO:
			this.setTitle("Info");
			icon = new ImageView(INFO_DIALOG_INFO);
			icon.setTranslateX(30.0);
			icon.setTranslateY(20.0);
			break;
		case ERROR:
			this.setTitle("Info");
			icon = new ImageView(INFO_DIALOG_ERROR);
			icon.setScaleX(0.6);
			icon.setScaleY(0.6);
			icon.setTranslateX(20.0);
			icon.setTranslateY(10.0);
			break;
		case WARNING:
			this.setTitle("Info");
			icon = new ImageView(INFO_DIALOG_WARNING);
			icon.setScaleX(0.6);
			icon.setScaleY(0.6);
			icon.setTranslateX(20.0);
			icon.setTranslateY(10.0);
			break;
		default:
			icon = new ImageView(INFO_DIALOG_INFO);
		}
		
		Text text = new Text(info);
		text.setWrappingWidth(200);
		
		this.mainPane.setLeft(icon);
		this.mainPane.setCenter(text);
	}
	
	public InfoDialog(String info) {
		this(INFO, info);
	}
	
}
