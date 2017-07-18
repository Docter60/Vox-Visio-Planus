/**
 * 
 */
package audio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Docter60
 *
 */
public class VoxPlaylist {
	
	public static final String wplFormat = "<?wpl version=\"1.0\"?>\n" + 
										   "<smil>\n" +
										   "  <head>\n" + 
										   "    <meta name=\"Generator\" content=\"Vox Visio Planus\"/>\n" +
										   "    <meta name=\"ItemCount\" content=\"0\"/>\n" + 
										   "    <title>Playlist</title>\n" + 
										   "  </head>\n" +
										   "  <body>\n" + 
										   "    <seq>\n" +
										   "    </seq>\n" +
										   "  </body>\n" +
										   "</smil>";
	
	public static String wplEntry = "<media src=\"\" albumTitle=\"\" albumArtist=\"\" trackTitle=\"\" trackArtist=\"\" />";
	
	private VoxPlayer voxPlayer;
	
	private File playlistFile;
	
	private String rawFile;
	
	private List<VoxMedia> media;
	
	public VoxPlaylist(String filePath, VoxPlayer voxPlayer) {
		this.playlistFile = new File(filePath);
		this.voxPlayer = voxPlayer;
		this.media = new ArrayList<VoxMedia>();
		this.rawFile = "";
		if(!this.playlistFile.exists())
			createPlaylistFile();
		else
			loadPlaylistFile();
	}
	
	public void addMedia(VoxMedia m) {
		this.media.add(m);
	}
	
	public void loadNextVoxMedia() {
		voxPlayer.load(media.get(0)); //temp
	}
	
	private void loadPlaylistFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(playlistFile));
			String line;
			while((line = reader.readLine()) != null) {
				this.rawFile = this.rawFile.concat(line + "\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createPlaylistFile() {
		try {
			this.playlistFile.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(playlistFile));
			writer.write(wplFormat);
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
