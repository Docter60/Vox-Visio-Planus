/**
 * 
 */
package audio;

import javafx.scene.image.Image;

/**
 * @author Docter60
 *
 */
public interface MediaChangedListener {
	public void changed(String[] songInfo, Image albumCover);
}
