import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JToolBar;

public class CoordinateBar extends JToolBar 
{
	private static final long serialVersionUID = -3379603174188645836L;
	private JLabel coordinates;
    private JLabel frameSize;
    private Separator separator;
    
    public CoordinateBar()
    {
    	JLabel coordinatePic = new JLabel(new ImageIcon(getClass().getResource("/icons/coordinates.png")));
    	this.add(coordinatePic);
		coordinates = new JLabel();
		coordinates.setText("  0 x 0  ");
		this.add(coordinates);
		
		separator = new Separator();
		this.add(separator);
		
    	JLabel sizePic = new JLabel(new ImageIcon(getClass().getResource("/icons/size.png")));
    	this.add(sizePic);
		frameSize = new JLabel();
		frameSize.setText("  0 x 0  ");
		this.add(frameSize);
		this.setFloatable(false);
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
    }
    
    
    public JLabel getCoordinates() 
    {
    	return coordinates;
    }

    public JLabel getFrameSize() 
    {
		return frameSize;
    }

    public JToolBar getCoordinateBar() 
    {
		return this;
    }
}
