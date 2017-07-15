/**
 * 
 */
package core;

import java.util.ArrayList;
import java.util.List;

import audio.VoxPlayer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import object.visualSpectrum.BarSpectrum;
import object.visualSpectrum.LinearSpectrum;
import object.visualSpectrum.VisualSpectrum;

/**
 * @author Docter60
 *
 */
public class VisualSpectrumManager {

	private List<VisualSpectrum> visualSpectrums;

	public VisualSpectrumManager(Stage primaryStage, VoxPlayer voxPlayer) {
		Scene scene = primaryStage.getScene();

		visualSpectrums = new ArrayList<VisualSpectrum>();

		BarSpectrum barSpectrum = new BarSpectrum(128, scene, voxPlayer.getSpectrumData());
		barSpectrum.getEffectsKit().setFillRainbow();
		barSpectrum.getEffectsKit().setGlow(1.0);

		LinearSpectrum linearSpectrum = new LinearSpectrum(128, scene, voxPlayer.getSpectrumData());
		linearSpectrum.getEffectsKit().setStrokeRainbow();
		linearSpectrum.getEffectsKit().setGlow(1.0);

		this.visualSpectrums.add(barSpectrum);
		this.visualSpectrums.add(linearSpectrum);
	}

	public void addVisualSpectrumsToScene(Group root) {
		for (VisualSpectrum vs : visualSpectrums)
			vs.addToScene(root);
	}

	public void updateMagnitudeData() {
		for (VisualSpectrum vs : visualSpectrums)
			vs.updateNodes();
	}

	public void setDataReference(float[] data) {
		for (VisualSpectrum vs : visualSpectrums)
			vs.setDataReference(data);
	}

	public void sceneResizeUpdate(double sceneWidth, double sceneHeight) {
		for (VisualSpectrum vs : visualSpectrums)
			vs.sceneResizeUpdate(sceneWidth, sceneHeight);
	}

}
