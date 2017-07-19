/**
 * 
 */
package core;

import java.util.ArrayList;
import java.util.List;

import audio.VoxPlayer;
import javafx.scene.Group;
import javafx.scene.Scene;
import object.visualSpectrum.BarSpectrum;
import object.visualSpectrum.LinearSpectrum;
import object.visualSpectrum.VisualSpectrum;

/**
 * @author Docter60
 *
 */
public class VisualSpectrumManager {

	private List<VisualSpectrum> visualSpectrums;

	public VisualSpectrumManager(VoxVisioPlanus voxVisioPlanus) {
		Scene scene = voxVisioPlanus.getPrimaryStage().getScene();

		VoxPlayer voxPlayer = voxVisioPlanus.getVoxPlayer();
		visualSpectrums = new ArrayList<VisualSpectrum>();

		BarSpectrum barSpectrum = new BarSpectrum(128, scene, voxVisioPlanus.getVoxPlayer().getSpectrumData());
		barSpectrum.getEffectsKit().setFillRainbow();
		barSpectrum.getEffectsKit().setGlow(1.0);

		LinearSpectrum linearSpectrum = new LinearSpectrum(128, scene, voxVisioPlanus.getVoxPlayer().getSpectrumData());
		linearSpectrum.getEffectsKit().setStrokeRainbow();
		linearSpectrum.getEffectsKit().setGlow(1.0);

		this.visualSpectrums.add(barSpectrum);
		this.visualSpectrums.add(linearSpectrum);
		
		voxPlayer.addMediaSpectrumListener(barSpectrum);
		voxPlayer.addMediaSpectrumListener(linearSpectrum);
		
		voxVisioPlanus.addResizeListener(barSpectrum);
		voxVisioPlanus.addResizeListener(linearSpectrum);
	}

	public void addVisualSpectrumsToScene(Group root) {
		for (VisualSpectrum vs : visualSpectrums)
			vs.addToScene(root);
	}

	public void updateMagnitudeData() {
		for (VisualSpectrum vs : visualSpectrums)
			vs.updateNodes();
	}
}
