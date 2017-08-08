/**
 * 
 */
package window.mod;

import java.util.HashMap;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import window.WindowPane;
import window.mod.SnapMod.Snap;

/**
 * TODO Fix hotSpot relocation
 * 
 * @author Docter60
 */
public class SlideMod {
	public static final Image LOCK_IMAGE = new Image(SlideMod.class.getResource("lock.png").toString());
	public static final Image UNLOCK_IMAGE = new Image(SlideMod.class.getResource("unlock.png").toString());
	public static final Interpolator interpolator = Interpolator.LINEAR;

	private static HashMap<WindowPane, SlideMod> listenerHandles = new HashMap<WindowPane, SlideMod>();
	private static Timeline slider;

	private static enum Slide {
		DEFAULT, W_SLIDE, N_SLIDE, E_SLIDE, S_SLIDE
	};

	private WindowPane wp;
	private Pane mainPane;
	private Button lockButton;
	private Slide state;
	private HotSpot hotSpot;

	private WindowPositionListener windowPositionListener;
	private WindowSizeListener windowSizeListener;
	private SceneChangeListener sceneChangeListener;
	private SceneResizeListener sceneResizeListener;

	private double hideX;
	private double hideY;

	private boolean isLocked;
	private boolean showPane;

	public static void setSlideable(WindowPane wp) {
		SlideMod.initializeSlider();
		listenerHandles.put(wp, new SlideMod(wp));
	}

	public static void setUnslideable(WindowPane wp) {
		Pane borderPane = wp.getMainPane();
		borderPane.layoutXProperty().removeListener(listenerHandles.get(wp).windowPositionListener);
		borderPane.prefWidthProperty().removeListener(listenerHandles.get(wp).windowSizeListener);
		borderPane.layoutYProperty().removeListener(listenerHandles.get(wp).windowPositionListener);
		borderPane.prefHeightProperty().removeListener(listenerHandles.get(wp).windowSizeListener);
		wp.getMainPane().sceneProperty().removeListener(listenerHandles.get(wp).sceneChangeListener);
		wp.getChildren().remove(listenerHandles.get(wp).lockButton);

		if (wp.getScene() != null) {
			wp.getScene().widthProperty().removeListener(listenerHandles.get(wp).sceneResizeListener);
			wp.getScene().heightProperty().removeListener(listenerHandles.get(wp).sceneResizeListener);
		}

		listenerHandles.remove(wp);
	}

	private static void initializeSlider() {
		if (SlideMod.slider == null) {
			slider = new Timeline(new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					for (SlideMod sm : listenerHandles.values())
						sm.refresh();
				}
			}));
			slider.setCycleCount(Timeline.INDEFINITE);
			slider.play();
		}
	}

	private SlideMod(WindowPane wp) {
		this.wp = wp;
		this.mainPane = wp.getMainPane();
		this.lockButton = createLockButton(mainPane);
		this.lockButton.setOnAction(new LockListener());
		this.state = getState();

		this.windowPositionListener = new WindowPositionListener();
		this.windowSizeListener = new WindowSizeListener();
		this.sceneChangeListener = new SceneChangeListener();
		this.sceneResizeListener = new SceneResizeListener();
		this.mainPane.prefWidthProperty().addListener(windowSizeListener);
		this.mainPane.prefHeightProperty().addListener(windowSizeListener);
		this.mainPane.layoutXProperty().addListener(windowPositionListener);
		this.mainPane.layoutYProperty().addListener(windowPositionListener);

		wp.getMainPane().sceneProperty().addListener(this.sceneChangeListener);

		this.wp.getChildren().add(lockButton);
	}

	public void refresh() {
		this.state = getState();
		this.handleState();
	}

	private Slide getState() {
		Slide slideState = Slide.DEFAULT;
		Snap snapState = SnapMod.getSnapState(wp);
		if (snapState == Snap.NW_SNAP || snapState == Snap.N_SNAP || snapState == Snap.NE_SNAP)
			slideState = Slide.N_SLIDE;
		else if (snapState == Snap.SW_SNAP || snapState == Snap.S_SNAP || snapState == Snap.SE_SNAP)
			slideState = Slide.S_SLIDE;
		else if (snapState == Snap.W_SNAP)
			slideState = Slide.W_SLIDE;
		else if (snapState == Snap.E_SNAP)
			slideState = Slide.E_SLIDE;
		return slideState;
	}

	private void handleState() {
		if (this.hotSpot != null && (this.state == Slide.DEFAULT || !this.isLocked)) {
			this.removeHotSpotProperties(wp);
			this.wp.setTranslateX(0);
			this.wp.setTranslateY(0);
			((Group) this.wp.getScene().getRoot()).getChildren().remove(hotSpot);
			this.hotSpot = null;
		} else if (this.isLocked && this.state != Slide.DEFAULT) {
			if (this.hotSpot == null) {
				this.configureSlideValues();
				double xPos = this.mainPane.getLayoutX();
				double yPos = this.mainPane.getLayoutY();
				double width = this.mainPane.getPrefWidth();
				double height = this.mainPane.getPrefHeight();
				this.hotSpot = new HotSpot(xPos, yPos, width, height);
				this.setHotSpotProperties(hotSpot);
				this.setHotSpotProperties(wp);
				int index = this.wp.getScene().getRoot().getChildrenUnmodifiable().indexOf(this.wp);
				((Group) this.wp.getScene().getRoot()).getChildren().add(index, hotSpot);
			} else {
				this.slideWindowPane();
			}
		}
	}

	private void configureSlideValues() {
		switch (this.state) {
		case N_SLIDE:
			this.hideX = 0;
			this.hideY = -this.wp.getMainPane().getPrefHeight();
			break;
		case S_SLIDE:
			this.hideX = 0;
			this.hideY = this.wp.getMainPane().getPrefHeight();
			break;
		case E_SLIDE:
			this.hideX = this.wp.getMainPane().getPrefWidth();
			this.hideY = 0;
			break;
		case W_SLIDE:
			this.hideX = -this.wp.getMainPane().getPrefWidth();
			this.hideY = 0;
			break;
		case DEFAULT:
			this.hideX = 0;
			this.hideY = 0;
			break;
		default:
			System.err.println("Slide value method error");
			break;
		}
	}

	private void slideWindowPane() {
		double currentX = this.wp.getTranslateX();
		double currentY = this.wp.getTranslateY();
		if (this.showPane) {
			this.wp.setTranslateX(interpolator.interpolate(currentX, 0, 0.07));
			this.wp.setTranslateY(interpolator.interpolate(currentY, 0, 0.07));
		} else {
			this.wp.setTranslateX(interpolator.interpolate(currentX, this.hideX, 0.07));
			this.wp.setTranslateY(interpolator.interpolate(currentY, this.hideY, 0.07));
		}
	}

	private static Button createLockButton(Pane mainPane) {
		Button lockButton = new Button();
		ImageView lockView = new ImageView(UNLOCK_IMAGE);
		lockView.setFitWidth(15);
		lockView.setFitHeight(15);
		lockView.fitWidthProperty().bind(lockButton.prefWidthProperty());
		lockView.fitHeightProperty().bind(lockButton.prefHeightProperty());
		lockButton.setPrefWidth(16);
		lockButton.setPrefHeight(16);
		lockButton.setPadding(Insets.EMPTY);
		lockButton.setGraphic(lockView);
		lockButton.setId("lockButton");
		lockButton.setLayoutX(mainPane.getLayoutX() + mainPane.getPrefWidth() - 2 * lockView.getFitWidth() - 2);
		lockButton.setLayoutY(mainPane.getLayoutY() + 2);
		return lockButton;
	}

	private class HotSpot extends Rectangle {
		public HotSpot(double x, double y, double width, double height) {
			super(x, y, width, height);
			this.setFill(Color.TRANSPARENT);
			this.setStroke(Color.TRANSPARENT);
		}
	}

	private void setHotSpotProperties(Node node) {
		node.setOnMouseEntered(new WindowEnterListener());
		node.setOnMouseExited(new WindowExitListener());
	}

	private void removeHotSpotProperties(Node node) {
		node.setOnMouseEntered(null);
		node.setOnMouseExited(null);
	}

	private void refreshLockButton() {
		double width = SlideMod.this.mainPane.getPrefWidth();
		this.lockButton.setLayoutX(this.mainPane.getLayoutX() + width - 2 * this.lockButton.getWidth() - 2);
		this.lockButton.setLayoutY(this.mainPane.getLayoutY() + 2);
	}

	public static HashMap<WindowPane, SlideMod> getListenerHandles() {
		return listenerHandles;
	}

	public double getHideX() {
		return hideX;
	}

	public double getHideY() {
		return hideY;
	}

	private class LockListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			SlideMod.this.isLocked = !SlideMod.this.isLocked;
			ImageView lockView;
			if (isLocked)
				lockView = new ImageView(LOCK_IMAGE);
			else
				lockView = new ImageView(UNLOCK_IMAGE);
			lockView.setFitWidth(15);
			lockView.setFitHeight(15);
			lockView.fitWidthProperty().bind(lockButton.prefWidthProperty());
			lockView.fitHeightProperty().bind(lockButton.prefHeightProperty());
			SlideMod.this.lockButton.setGraphic(lockView);
		}

	}

	private void screenResizeUpdate() {
		this.wp.setTranslateX(0);
		this.wp.setTranslateY(0);

		double newSceneHeight = this.wp.getScene().getHeight();
		double newSceneWidth = this.wp.getScene().getWidth();

		double width = this.mainPane.getPrefWidth();
		double height = this.mainPane.getPrefHeight();

		double oldLayoutX = this.wp.getMainPane().getLayoutX();
		double oldLayoutY = this.wp.getMainPane().getLayoutY();

		double newLayoutX;
		double newLayoutY;

		switch (this.state) {
		case N_SLIDE:
			newLayoutX = oldLayoutX;
			newLayoutY = oldLayoutY;
			break;
		case S_SLIDE:
			newLayoutX = oldLayoutX;
			newLayoutY = newSceneHeight - height;
			break;
		case E_SLIDE:
			newLayoutX = oldLayoutX;
			newLayoutY = oldLayoutY;
			break;
		case W_SLIDE:
			newLayoutX = newSceneWidth - width;
			newLayoutY = oldLayoutY;
			break;
		default:
			System.err.println("Slide on scene resize error");
		case DEFAULT:
			newLayoutX = 0;
			newLayoutY = 0;
			break;
		}
		
		this.mainPane.setLayoutX(newLayoutX);
		this.mainPane.setLayoutY(newLayoutY);
		
		configureSlideValues();
		
		if(this.hotSpot != null) {
			double xPos = this.mainPane.getLayoutX();
			double yPos = this.mainPane.getLayoutY();
			this.hotSpot.setX(xPos);
			this.hotSpot.setY(yPos);
			this.wp.setTranslateX(hideX);
			this.wp.setTranslateY(hideY);
		}
		this.refreshLockButton();
	}

	private class WindowEnterListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			SlideMod.this.showPane = true;
		}
	}

	private class WindowExitListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			SlideMod.this.showPane = false;
		}
	}

	private class WindowPositionListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			SlideMod.this.refreshLockButton();
			HotSpot hotSpot = SlideMod.this.hotSpot;
			if(hotSpot != null) {
				double xPos = SlideMod.this.mainPane.getLayoutX();
				double yPos = SlideMod.this.mainPane.getLayoutY();
				hotSpot.setX(xPos);
				hotSpot.setY(yPos);
			}
		}
	}

	private class SceneResizeListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			SlideMod.this.screenResizeUpdate();
		}
	}

	private class WindowSizeListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			SlideMod.this.refreshLockButton();

			double newWidth = SlideMod.this.mainPane.getPrefWidth();
			double newHeight = SlideMod.this.mainPane.getPrefHeight();

			if (SlideMod.this.isLocked) {
				SlideMod.this.lockButton.fire();
			}

			if (SlideMod.this.hotSpot != null) {
				SlideMod.this.wp.setTranslateX(0);
				SlideMod.this.wp.setTranslateY(0);
				SlideMod.this.configureSlideValues();
				HotSpot hotSpot = SlideMod.this.hotSpot;
				hotSpot.setWidth(newWidth);
				hotSpot.setHeight(newHeight);
				System.out.println(SlideMod.this.mainPane.getLayoutX());
				hotSpot.setLayoutX(SlideMod.this.mainPane.getLayoutX());
				hotSpot.setLayoutY(SlideMod.this.mainPane.getLayoutY());
			}
		}
	}

	private class SceneChangeListener implements ChangeListener<Scene> {
		@Override
		public void changed(ObservableValue<? extends Scene> obs, Scene oldVal, Scene newVal) {
			if (SlideMod.this.getState() != Slide.DEFAULT) {
				SlideMod.this.lockButton.fire();
			}
			SlideMod.this.mainPane.getScene().widthProperty().addListener(SlideMod.this.sceneResizeListener);
			SlideMod.this.mainPane.getScene().heightProperty().addListener(SlideMod.this.sceneResizeListener);
		}
	}
}
