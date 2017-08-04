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
 * @author Docter60
 * 
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

	private WindowListener windowListener;

	private double showX;
	private double showY;
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
		borderPane.layoutXProperty().removeListener(listenerHandles.get(wp).windowListener);
		borderPane.prefWidthProperty().removeListener(listenerHandles.get(wp).windowListener);
		borderPane.layoutYProperty().removeListener(listenerHandles.get(wp).windowListener);
		borderPane.prefHeightProperty().removeListener(listenerHandles.get(wp).windowListener);
		wp.getChildren().remove(listenerHandles.get(wp).lockButton);
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

		this.windowListener = new WindowListener();
		this.mainPane.prefWidthProperty().addListener(windowListener);
		this.mainPane.prefHeightProperty().addListener(windowListener);
		this.mainPane.layoutXProperty().addListener(windowListener);
		this.mainPane.layoutYProperty().addListener(windowListener);

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
			this.wp.setLayoutX(showX);
			this.wp.setLayoutY(showY);
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
		this.showX = this.wp.getLayoutX();
		this.showY = this.wp.getLayoutY();
		switch (this.state) {
		case N_SLIDE:
			this.hideX = this.showX;
			this.hideY = this.showY - this.wp.getMainPane().getPrefHeight();
			break;
		case S_SLIDE:
			this.hideX = this.showX;
			this.hideY = this.showY + this.wp.getMainPane().getPrefHeight();
			break;
		case E_SLIDE:
			this.hideX = this.showX + this.wp.getMainPane().getPrefWidth();
			this.hideY = this.showY;
			break;
		case W_SLIDE:
			this.hideX = this.showX - this.wp.getMainPane().getPrefWidth();
			this.hideY = this.showY;
			break;
		case DEFAULT:
			this.hideX = this.showX;
			this.hideY = this.showY;
			break;
		default:
			System.err.println("Slide value method error");
			break;
		}
	}

	private void slideWindowPane() {
		double currentX = this.wp.getLayoutX();
		double currentY = this.wp.getLayoutY();
		if (this.showPane) {
			this.wp.setLayoutX(interpolator.interpolate(currentX, this.showX, 0.07));
			this.wp.setLayoutY(interpolator.interpolate(currentY, this.showY, 0.07));
		} else {
			this.wp.setLayoutX(interpolator.interpolate(currentX, this.hideX, 0.07));
			this.wp.setLayoutY(interpolator.interpolate(currentY, this.hideY, 0.07));
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

	private class WindowListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			SlideMod.this.lockButton.setLayoutX(SlideMod.this.mainPane.getLayoutX()
					+ SlideMod.this.mainPane.getPrefWidth() - 2 * SlideMod.this.lockButton.getWidth() - 2);
			SlideMod.this.lockButton.setLayoutY(SlideMod.this.mainPane.getLayoutY() + 2);
		}
	}
}
