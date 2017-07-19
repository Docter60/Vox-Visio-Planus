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
	
	public static String wplEntryStart = "     <media src=\"";
	public static String wplEntryEnd = "\" albumTitle=\"\" albumArtist=\"\" trackTitle=\"\" trackArtist=\"\" />\n";
	
	private VoxPlayer voxPlayer;
	private File playlistFile;
	private String rawFile;
	private List<VoxMedia> media;
	private int currentIndex;
	
	public VoxPlaylist(String filePath, VoxPlayer voxPlayer) {
		this.playlistFile = new File(filePath);
		this.voxPlayer = voxPlayer;
		this.media = new ArrayList<VoxMedia>();
		this.rawFile = "";
		this.currentIndex = 0;
		if(!this.playlistFile.exists())
			createPlaylistFile();
		else
			loadPlaylistFile();
	}
	
	public void addMedia(VoxMedia m) {
		this.media.add(m);
		String newEntry = wplEntryStart + m.getPath() + wplEntryEnd;
		int newIndex = this.rawFile.indexOf("  </head>");
		this.rawFile = this.rawFile.substring(0, newIndex) + newEntry + this.rawFile.substring(newIndex, this.rawFile.length());
		saveRawFile();
	}
	
	public void removeMedia(int index) {
		this.media.remove(index);
	}
	
	public void loadNextVoxMedia() { // boolean for shuffle?
		if(currentIndex + 1 < media.size())
			voxPlayer.load(media.get(++currentIndex));
		else
			voxPlayer.load(media.get(currentIndex));
	}
	
	public void loadPreviousVoxMedia() {
		if(currentIndex > 0)
			voxPlayer.load(media.get(--currentIndex));
		else
			voxPlayer.load(media.get(currentIndex));
	}
	
	// Need to add editing, swap, and shuffle methods
	
	public void closePlaylist() {
		this.saveRawFile();
	}
	
	private void loadMedia() {
		int lastIndex = 0;
		while(lastIndex != -1) {
			lastIndex = this.rawFile.indexOf("src=\"", lastIndex);
			
			if(lastIndex != -1) {
				lastIndex += 5;
				int endIndex = this.rawFile.indexOf("\"", lastIndex);
				String path = this.rawFile.substring(lastIndex, endIndex);
				this.media.add(new VoxMedia(path));
				lastIndex++;
			}
		}
	}
	
	private void saveRawFile() {
		try {
			this.playlistFile.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(playlistFile));
			writer.write(rawFile);
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadPlaylistFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(playlistFile));
			String line;
			while((line = reader.readLine()) != null) {
//				if(line.contains("<media")) {
//					int beginIndex = line.indexOf("src=\"");
//					int endIndex = line.indexOf("\" album");
//					System.out.println(beginIndex + ", " + endIndex);
//				}
				this.rawFile = this.rawFile.concat(line + "\n");
			}
			reader.close();
			loadMedia();
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
			this.rawFile = wplFormat;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
