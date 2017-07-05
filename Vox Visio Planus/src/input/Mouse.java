/**
 * 
 */
package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

/**
 * @author Docter60
 *
 */
public class Mouse implements MouseListener{

	private boolean leftClick;
	private boolean rightClick;
//	private boolean leftDown;
//	private boolean rightDown;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(leftClick) leftClick = false;
		if(rightClick) rightClick = false;
		
		if(SwingUtilities.isLeftMouseButton(e)) leftClick = true;
		if(SwingUtilities.isRightMouseButton(e)) rightClick = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
