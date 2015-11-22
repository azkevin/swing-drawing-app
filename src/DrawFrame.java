import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;



class DrawFrame extends JFrame implements MouseMotionListener, MouseListener, ActionListener
{
	// the following avoids a "warning" with Java 1.5.0 complier (?)
	static final long serialVersionUID = 42L;

	private int sampleCount;	//Count for the # of samples in the inkPanel.
	private JPanel contentPane;	
	private JPanel outerPanel;
	private JMenuBar menubar;
	private PaintPanel inkPanel;
	private JButton clearButton;
	private Point[] stroke;	
	final int MAX_SAMPLES = (30000); //Sufficient number of samples for points.
	
	private JMenuItem openFile;
	private JMenuItem saveFile;
	private JMenuItem newFile;
	
	private JButton select;
	private JButton open;
	private JButton line;
	private JButton rectangle;
	private JButton circle;
	private JButton oval;
	private JButton rotateLeft;
	private JButton rotateRight;
	private JButton flipHorizontal;
	private JButton flipVertical;
	private JButton text;
	private JButton erase;
	private JButton fill;
	private JButton undo;
	private JButton redo;
	
	
	JFileChooser fc;
	File f;
	Image image  ;
//	JLabel label = new JLabel("",new ImageIcon(image),JLabel.CENTER);
	
	// constructor
	public DrawFrame()
	{
		// construct components
		// contentPane > outerPanel > inkPanel > (Components: clearButton, stroke)
		
		contentPane = new JPanel();
		outerPanel = new JPanel(new BorderLayout());
		
		// ----------------
		// create a menu bar
		// ----------------

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		// ----------------
		// create the file menu
		// ----------------

		JMenu fileMenu = new JMenu("File");

		openFile = new JMenuItem("Open");
		saveFile = new JMenuItem("Save",new ImageIcon(this.getClass().getResource("/icons/Save-24.png")));
		
		newFile = new JMenuItem("New");

		menuBar.add(fileMenu);

		// ----------------
		// create a tool bar
		// ----------------
		
		JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
		
		
		
		select = new JButton("Select");
		open = new JButton("Open");
		line = new JButton("Line",new ImageIcon(this.getClass().getResource("/icons/Line-24.png")));
		rectangle = new JButton("Rectangle",new ImageIcon(this.getClass().getResource("/icons/Rectangle-24.png")));
		circle = new JButton("Circle",new ImageIcon(this.getClass().getResource("/icons/Circled.png")));
		oval = new JButton("Oval");
		rotateLeft = new JButton("Rotate left",new ImageIcon(this.getClass().getResource("/icons/Rotate Left-24.png")));
		rotateRight  = new JButton("Rotate right",new ImageIcon(this.getClass().getResource("/icons/Rotate Right-24.png")));
		flipHorizontal = new JButton("Flip horizontal",new ImageIcon(this.getClass().getResource("/icons/Flip Horizontal-24.png")));
		flipVertical  = new JButton("Flip vertical",new ImageIcon(this.getClass().getResource("/icons/Flip Vertical-24.png")));
		text = new JButton("Text");
		erase = new JButton("Erase",new ImageIcon(this.getClass().getResource("/icons/Eraser-24.png")));
		fill = new JButton("Fill",new ImageIcon(this.getClass().getResource("/icons/Fill Color-24.png")));
		undo = new JButton("Undo",new ImageIcon(this.getClass().getResource("/icons/Undo-24.png")));
		redo = new JButton("Redo",new ImageIcon(this.getClass().getResource("/icons/Redo-24.png")));
		
		
		
		
		
		
		
		
		
		
		
		
		
		toolBar.add(select);
		toolBar.add(open);
		toolBar.addSeparator();
		toolBar.add(line);
		toolBar.add(rectangle);
		toolBar.add(circle);
		toolBar.add(oval);
		toolBar.addSeparator();
		toolBar.add(rotateLeft);
		toolBar.add(rotateRight);
		toolBar.add(flipHorizontal);
		toolBar.add(flipVertical);
		toolBar.addSeparator();
		toolBar.add(text);
		toolBar.add(erase);
		toolBar.add(fill);
		toolBar.addSeparator();
		toolBar.add(undo);
		toolBar.add(redo);
		
		
		
		
		
		
		
		
		
		
		
		
		
		// ----------------
		// create a file chooser for saving and opening
		// ----------------

		fc = new JFileChooser(new File("."));
		final String[] EXTENSIONS = { ".jpg", ".jpeg" };
		fc.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));

		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		fileMenu.add(newFile);

		// ----------------
		// add action listeners to menu options
		// ----------------

		openFile.addActionListener(this);
		saveFile.addActionListener(this);
		newFile.addActionListener(this);

	//	JPanel panel = new JPanel();
				
		inkPanel = new PaintPanel();
		clearButton = new JButton("Clear");
		stroke = new Point[MAX_SAMPLES];
		
		// configure components
		inkPanel.setSize(200, 200);
		
		outerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		outerPanel.add(inkPanel, "Center");
	
		contentPane.add(toolBar);
		contentPane.add(outerPanel);
		contentPane.add(clearButton);
	
		// contentPane.add(label);
		
		// add listeners
		this.addWindowListener(new WindowCloser());
		inkPanel.addMouseMotionListener(this);
		inkPanel.addMouseListener(this);
		clearButton.addActionListener(this);

		// set components into the contentPane
		this.setContentPane(contentPane);
		this.setPreferredSize(new Dimension(1064,768));
	}

	// implement ActionListener method (initially run)
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();

		if (source == openFile) {
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				f = fc.getSelectedFile();
				openFile(f);
			}
		} else if (source == saveFile) {
			// open file saver
			if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				f = fc.getSelectedFile();

				try {
					saveFile(f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (source == newFile) {
			// open new tab with new picture
			// blank canvas





		} else if (source == clearButton){
			inkPanel.clear();
			
		}

	}
	private void openFile(File f) {

		// ----------------
		// update the contents of the jlabel to be the image from the selected file
		// ----------------

		Image image = Toolkit.getDefaultToolkit().getImage(f.getPath());
		
		inkPanel.drawImage(image);

	}

	private void saveFile(File f) throws IOException {

		// ----------------
		// Take all the contents of the jpanel and save them to a png 
		// 		destination is the file they selected via the filechooser
		// ----------------

		Container c = inkPanel;
		BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
		c.paint(im.getGraphics());
		ImageIO.write(im, "PNG", f);


	}

	// implement MouseMotionListener methods (7)
	@Override
	public void mouseDragged(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();

		if (SwingUtilities.isLeftMouseButton(e))
		{
			stroke[sampleCount] = new Point(x, y);
			int x1 = (int) stroke[sampleCount - 1].getX();
			int y1 = (int) stroke[sampleCount - 1].getY();
			int x2 = (int) stroke[sampleCount].getX();
			int y2 = (int) stroke[sampleCount].getY();
			if (sampleCount < MAX_SAMPLES - 1)
				++sampleCount;

			// draw ink trail from previous point to current point
			inkPanel.drawInk(x1, y1, x2, y2);
		}
	}

	@Override
	public void mouseMoved(MouseEvent me) {}

	@Override
	public void mouseClicked(MouseEvent me) {}
	
	@Override
	public void mouseEntered(MouseEvent me) {}
	
	@Override
	public void mouseExited(MouseEvent me) {}
	
	// a mouse button was pressed
	@Override
	public void mousePressed(MouseEvent me)
	{
		sampleCount = 0;
		int x = me.getX();
		int y = me.getY();
		stroke[sampleCount] = new Point(x, y);
		if (sampleCount < MAX_SAMPLES - 1)
			++sampleCount;
	}

	// a mouse button was released
	@Override
	public void mouseReleased(MouseEvent me) {}


	
	private class WindowCloser extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent event)
		{
			System.exit(0);
		}
	}
}