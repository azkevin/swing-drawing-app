package Controller;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JToolBar;

public class CoordinateBar extends JToolBar {
	private JLabel coordinates;
	private JLabel frameSize;
	private Separator separator;

	public CoordinateBar() {
		JLabel coordinatePic = new JLabel(resizeImageIcon("/icons/coordinates.png", 22, 22));
		this.add(coordinatePic);
		coordinates = new JLabel();
		coordinates.setText("  0 x 0  ");
		this.add(coordinates);

		separator = new Separator();
		this.add(separator);

		JLabel sizePic = new JLabel(resizeImageIcon("/icons/size.png", 24, 24));
		this.add(sizePic);
		frameSize = new JLabel();
		frameSize.setText("  0 x 0  ");
		this.add(frameSize);
		this.setFloatable(false);
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
	}

	private ImageIcon resizeImageIcon(String imagePath, int width, int height) {
		ImageIcon icon = new ImageIcon(this.getClass().getResource(imagePath));
		Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}

	public JLabel getCoordinates() {
		return coordinates;
	}

	public JLabel getFrameSize() {
		return frameSize;
	}

	public JToolBar getCoordinateBar() {
		return this;
	}
}
