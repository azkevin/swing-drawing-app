import javax.swing.JLabel;
import javax.swing.JToolBar;

public class CoordinateBar extends JToolBar 
{
    private JLabel coordinates;
    private JLabel frameSize;
    private Separator separator;

    public CoordinateBar()
    {
		coordinates = new JLabel();
		coordinates.setText("  0 x 0  ");
		this.add(coordinates);
		separator = new Separator();
		this.add(separator);
		frameSize = new JLabel();
		frameSize.setText("  0 x 0  ");
		this.add(frameSize);
    }

    public JLabel getCoordinates() 
    {
    	return coordinates;
    }

    public JLabel getFrameSize() 
    {
		return frameSize;
    }

    public JToolBar getStatusBar() 
    {
		return this;
    }
}
