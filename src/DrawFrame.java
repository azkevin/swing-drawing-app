import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class DrawFrame extends JFrame
{
	// the following avoids a "warning" with Java 1.5.0 complier
	static final long serialVersionUID = 42L;

	//fields
	private JPanel contentPane;	
	private static PaintPanel inkPanel;
	private JMenuBar menuBar;
	private JToolBar toolBar;
	private JPanel cc1;
	private JColorChooser cc2;
	private CoordinateBar coordinateBar;

	private final int CONTENT_PANE_WIDTH = 1300;
	private final int CONTENT_PANE_HEIGHT = 700;
	private final Color background = Color.GRAY;
	
	//ContentPane > Toolbar,  cc, InkPanel,
	public DrawFrame()
	{
		// construct our layout manager.
		contentPane = new JPanel();
		contentPane.setLayout(null);
		
		// create a menu bar
		menuBar = (new MenuBar(this, this.inkPanel)).getMenuBar();
		

		// create a tool bar		
		toolBar = (new ToolBar(this)).getToolBar();

		// create coordinate bar at the bottom
		coordinateBar = new CoordinateBar();
		
		// create color chooser 1 (minas)
		cc1 = (new ColorChooser1()).getPanel();

		// create default color chooser
		cc2 = (new ColorChooser2(this)).getColorChooser();
		
		// construct the panels needed. (INKPANEL COMES LAST)
		inkPanel = new PaintPanel(0, this);
		
		
		// configure components and add them to the frame.
		contentPane.add(inkPanel);
		//contentPane.add(cc1);
		//contentPane.add(cc2);
		contentPane.setBackground(background);
		
		// add listeners to buttons
		this.addWindowListener(new WindowCloser());

		// set components into the contentPane
		this.setJMenuBar(menuBar);
		this.add(coordinateBar, BorderLayout.PAGE_END);
		this.add(toolBar, BorderLayout.WEST);
		this.add(contentPane);
		this.add(cc1, BorderLayout.EAST);
		
		this.setSize(CONTENT_PANE_WIDTH, CONTENT_PANE_HEIGHT);
		this.setPreferredSize(new Dimension(CONTENT_PANE_WIDTH,CONTENT_PANE_HEIGHT));
	}
	
	
	private class WindowCloser extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent event)
		{
			System.exit(0);
		}
	}
	
    public CoordinateBar getCoordinateBar() 
    {
    	return this.coordinateBar;
    }
    
    public static PaintPanel getInkPanel()
    {
    	return inkPanel;
    }

}
