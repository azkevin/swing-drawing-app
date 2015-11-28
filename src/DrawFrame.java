
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

class DrawFrame extends JFrame implements MouseMotionListener, MouseListener, ActionListener, ChangeListener
{
	// the following avoids a "warning" with Java 1.5.0 complier
	static final long serialVersionUID = 42L;

	//fields
	private JPanel contentPane;	
	private JPanel outerPanel;
	private PaintPanel inkPanel;
	final int MAX_SAMPLES = (30000); //Sufficient number of samples for points to draw.
	
	private JMenuItem openFile;
	private JMenuItem saveFile;
	private JMenuItem newFile;
	private JMenuItem undoMenu;
	private JMenuItem redoMenu;

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
	private JButton clear;
	
	private JMenuBar menuBar;
	private JMenu editMenu;
	private JMenu fileMenu;
	private JToolBar toolBar;
	
	private JFileChooser fc;
	private File f;
	private Image image  ;
	private JColorChooser cc;
	
	//ContentPane > Toolbar, Clearbutton, cc, (OuterPanel > inkPanel),
	public DrawFrame()
	{
		// construct the panels needed
		contentPane = new JPanel(new BorderLayout());
		outerPanel = new JPanel(new BorderLayout());
		inkPanel = new PaintPanel(0);
		
		// create a menu bar
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		// create the file menu and its components.
		fileMenu = new JMenu("File");
		openFile = new JMenuItem("Open");
		saveFile = new JMenuItem("Save",new ImageIcon(this.getClass().getResource("/icons/Save-24.png")));
		newFile = new JMenuItem("New");
		
		// create the edit menu
		editMenu = new JMenu("Edit");
		undoMenu = new JMenuItem("Undo");
		redoMenu = new JMenuItem("Redo");
		// newFile = new JMenuItem("");

		// create a tool bar		
		toolBar = new JToolBar(JToolBar.VERTICAL);
		toolBar.setFloatable(false);		
		this.initializeToolBar(toolBar);
		
		// create a file chooser for saving and opening
		fc = new JFileChooser(new File("."));
		fc.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));
		cc = new JColorChooser();
		cc.setPreviewPanel(new JPanel());
		cc.getSelectionModel().addChangeListener(this);
		
		// add the components together.
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		fileMenu.add(newFile);
		editMenu.add(undoMenu);
		editMenu.add(redoMenu);
		
		// add action listeners to menu options
		openFile.addActionListener(this);
		saveFile.addActionListener(this);
		newFile.addActionListener(this);
		
		// configure components and add them to the frame.
		//inkPanel.setSize(200, 200);
		outerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		outerPanel.add(inkPanel, "Center");
		contentPane.add(toolBar, "West");
		contentPane.add(outerPanel);
		contentPane.add(cc, "East");
		// contentPane.add(label);
		
		// add listeners to buttons
		this.addWindowListener(new WindowCloser());
		clear.addActionListener(this);
		rectangle.addActionListener(this);
		line.addActionListener(this);
		circle.addActionListener(this);
		erase.addActionListener(this);

		// set components into the contentPane
		this.setContentPane(contentPane);
		this.setPreferredSize(new Dimension(1064,1000));
	}

	private void initializeToolBar(JToolBar toolBar) {
		// ----------------
		// create buttons for the tool bar
		// ----------------
		
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
		clear = new JButton("Clear");
		
		// ----------------
		// add buttons to the tool bar
		// ----------------
		
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
		toolBar.addSeparator();
		toolBar.add(clear);
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
		} else if (source == clear){
			inkPanel.clear();
		} 
		else if (source == line) {
			inkPanel.setTool(1);
		}
		else if (source == rectangle) {
			inkPanel.setTool(2);
		}
		else if (source == circle) {
			inkPanel.setTool(3);
		}
		else if (source == erase){
			inkPanel.eraser();
		}
	}
	
	public void stateChanged(ChangeEvent e){
		inkPanel.setColor(cc.getColor());
	}
	private void openFile(File f) {

		// ----------------
		// update the contents of the jlabel to be the image from the selected file
		// ----------------

	//	Image image = Toolkit.getDefaultToolkit().getImage(f.getPath());
		try {
			inkPanel.setImage(ImageIO.read(f));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private void saveFile(File f) throws IOException {

		// ----------------
		// Take all the contents of the jpanel and save them to a png 
		// 		destination is the file they selected via the filechooser
		// ----------------
		Container c = inkPanel;
		BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
		c.paint(im.getGraphics());
		ImageIO.write(im, ".png", f );
	}



	@Override
	public void mouseMoved(MouseEvent me) {}

	@Override
	public void mouseClicked(MouseEvent me) {}
	
	@Override
	public void mouseEntered(MouseEvent me) {}
	
	@Override
	public void mouseExited(MouseEvent me) {}
	

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



	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}