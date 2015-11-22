import java.awt.BorderLayout;
import java.awt.Container;
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
		saveFile = new JMenuItem("Save");
		newFile = new JMenuItem("New");

		menuBar.add(fileMenu);

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
			sampleCount = 0;
		}

	}
	private void openFile(File f) {

		// ----------------
		// update the contents of the jlabel to be the image from the selected file
		// ----------------

		Image image = Toolkit.getDefaultToolkit().getImage(f.getPath());
//		label.setIcon(new ImageIcon(image));
//		label.repaint();
		
		inkPanel.setImage(image);
		inkPanel.drawImage();
		inkPanel.repaint();
	}

	private void saveFile(File f) throws IOException {

		// ----------------
		// Take all the contents of the jpanel and save them to a png 
		// 		destination is the file they selected via the filechooser
		// ----------------

		Container c = this.getContentPane();
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