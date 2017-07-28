/**
 * 
 */
package audio;

import javafx.scene.image.Image;

/**
 * @author Docter60
 *
 */
public interface VoxMediaInfoListener {

	void onMediaInfoUpdate(String[] info, Image albumCover);
	
}
