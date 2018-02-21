/**
 * 
 */
package audio;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *  
 * 
 * @author Docter60
 */
public class Playlist {
	
	private File file;
	private String title;
	
	DocumentBuilderFactory factory; 
	DocumentBuilder builder;
	Document doc;
	Element songParent;
	NodeList songs;
	
	public Playlist(String path) {
		file = new File(path);
		title = path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf("."));
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			if(file.createNewFile())
				createFile();
			else
				openFile();
			songs = doc.getElementsByTagName("media");
		} catch (IOException | ParserConfigurationException e1) {
			e1.printStackTrace();
		}
	}
	
	public void addSong(String path) {
		if(new File(path).isFile()) {
			Element media = doc.createElement("media");
			Attr source = doc.createAttribute("src");
			source.setValue(path);
			media.setAttributeNode(source);
			songParent.appendChild(media);
		}
	}
	
	public void addSongAt(String path, int index) {
		if(new File(path).isFile()) {
			Element media = doc.createElement("media");
			Attr source = doc.createAttribute("src");
			source.setValue(path);
			media.setAttributeNode(source);
			songs.item(index).getParentNode().insertBefore(media, songs.item(index));
		}
	}
	
	public void removeLastSong() {
		songParent.removeChild(songs.item(songs.getLength() - 1));
	}
	
	public void removeSongAt(int index) {
		songParent.removeChild(songs.item(index));
		songs.item(index).setTextContent("");
	}
	
	public void save() {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try{
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "YES");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);
		transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void openFile() {
		try {
			doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			songs = doc.getElementsByTagName("media");
			NodeList list = doc.getElementsByTagName("seq");
			songParent = (Element) list.item(0);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createFile() {
		doc = builder.newDocument();
		
		Element rootElement = doc.createElement("smil");
		doc.appendChild(rootElement);
		
		Element head = doc.createElement("head");
		rootElement.appendChild(head);
		
		Element generator = doc.createElement("meta");
		Attr attrName = doc.createAttribute("name");
		attrName.setValue("Generator");
		Attr attrContent = doc.createAttribute("content");
		attrContent.setValue("Vox Visio Planus");
		generator.setAttributeNode(attrName);
		generator.setAttributeNode(attrContent);
		head.appendChild(generator);
		
		Element itemCount = doc.createElement("meta");
		Attr attrName2 = doc.createAttribute("name");
		attrName2.setValue("ItemCount");
		Attr attrContent2 = doc.createAttribute("content");
		attrContent2.setValue("0");
		itemCount.setAttributeNode(attrName2);
		itemCount.setAttributeNode(attrContent2);
		head.appendChild(itemCount);
		
		Element title = doc.createElement("title");
		title.appendChild(doc.createTextNode(this.title));
		head.appendChild(title);
		
		Element body = doc.createElement("body");
		rootElement.appendChild(body);
		
		Element seq = doc.createElement("seq");
		body.appendChild(seq);
		songParent = seq;
		
		save();
	}
	
}
