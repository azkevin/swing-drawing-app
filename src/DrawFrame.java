import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Arc2D;
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
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.javafx.geom.Rectangle;



class DrawFrame extends JFrame implements MouseMotionListener, MouseListener, ActionListener, ChangeListener
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
	
	private JMenuItem undoMenu;
	private JMenuItem redoMenu;

	private Color background = new Color(39,174,96);
	
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
	JColorChooser cc;
//	JLabel label = new JLabel("",new ImageIcon(image),JLabel.CENTER);
	
	// constructor
	public DrawFrame()
	{
		//this.setBackground(background);
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
		
		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		fileMenu.add(newFile);
		
		// ----------------
		// create the edit menu
		// ----------------

		JMenu editMenu = new JMenu("Edit");

		undoMenu = new JMenuItem("Undo");
		redoMenu = new JMenuItem("Redo");
		// newFile = new JMenuItem("");

		editMenu.add(undoMenu);
		editMenu.add(redoMenu);
		
		menuBar.add(editMenu);

		// ----------------
		// create a tool bar
		// ----------------
		
		JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
		toolBar.setFloatable(false);
		
		initializeToolBar(toolBar);
		
		erase.addActionListener(this);
		
		// ----------------
		// create a file chooser for saving and opening
		// ----------------

		fc = new JFileChooser(new File("."));
		final String[] EXTENSIONS = { ".jpg", ".jpeg" };
		fc.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));



		cc = new JColorChooser();
		
		cc.setPreviewPanel(new JPanel());
		
		cc.getSelectionModel().addChangeListener(this);
		
		// ----------------
		// add action listeners to menu options
		// ----------------
		
		openFile.addActionListener(this);
		saveFile.addActionListener(this);
		newFile.addActionListener(this);

	//	JPanel panel = new JPanel();
				
		inkPanel = new PaintPanel(0);
		clearButton = new JButton("Clear");
		stroke = new Point[MAX_SAMPLES];
		
		// configure components
		//inkPanel.setSize(200, 200);
		
		outerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		outerPanel.add(inkPanel, "Center");
	
		contentPane.add(toolBar);
		contentPane.add(outerPanel);
		contentPane.add(clearButton);
		contentPane.add(cc);
		// contentPane.add(label);
		
		// add listeners
		this.addWindowListener(new WindowCloser());
		clearButton.addActionListener(this);
		rectangle.addActionListener(this);
		line.addActionListener(this);
		circle.addActionListener(this);

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

	class PaintPanel extends JPanel
	{
		private final int PENCIL_TOOL = 0;
		private final int LINE_TOOL = 1;
		private final int RECTANGLE_TOOL = 2;
		private final int CIRCLE_TOOL = 3;
		
		BufferedImage canvas;
		Graphics2D graphics2D;
		private int activeTool = 0;
		private JLabel label;
		
		int x1, y1, x2, y2;
		

		private Color currentColor;

		public PaintPanel()
		{
			// construct components
			// contentPane > outerPanel > inkPanel > (Components: clearButton, stroke)
			// configure components
			// add listeners
			// set components into the contentPane
			this.setBackground(Color.white);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			this.setPreferredSize(new Dimension(250, 250));
		}

		//Now for the constructors
		public PaintPanel(int f){
			this.setPreferredSize(new Dimension(800,600));
			currentColor = Color.BLACK;
			addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent e){
					x1 = e.getX();
					y1 = e.getY();
				}
				
				public void mouseReleased(MouseEvent e) {
					if (activeTool == LINE_TOOL) {
						graphics2D.drawLine(x1, y1, x2, y2);
						System.out.println("0");
					}
					else if (activeTool == RECTANGLE_TOOL) {
						if (x1 < x2 && y1 < y2) {
							graphics2D.draw(new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1));
						}
						else if (x2 < x1 && y1 < y2) {
							graphics2D.draw(new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1));
						}
						else if (x1 < x2 && y2 < y1) {
							graphics2D.draw(new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2));
						}
						else if (x2 < x1 && y2 < y1) {
							graphics2D.draw(new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2));
						}
						
					}
					else if (activeTool == CIRCLE_TOOL) {
						if (x1 < x2 && y1 < y2) {
							graphics2D.draw(new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1));
						}
						else if (x2 < x1 && y1 < y2) {
							graphics2D.draw(new Ellipse2D.Double(x2, y1, x1 - x2, y2 - y1));
						}
						else if (x1 < x2 && y2 < y1) {
							graphics2D.draw(new Ellipse2D.Double(x1, y2, x2 - x1, y1 - y2));
						}
						else if (x2 < x1 && y2 < y1) {
							graphics2D.draw(new Ellipse2D.Double(x2, y2, x1 - x2, y1 - y2));
						}
					}
					repaint();
				}
			});
			//if the mouse is pressed it sets the oldX & oldY
			//coordinates as the mouses x & y coordinates
			addMouseMotionListener(new MouseMotionAdapter()
			{
				public void mouseDragged(MouseEvent e){
					x2 = e.getX();
					y2 = e.getY();
					if (activeTool == PENCIL_TOOL) {
						graphics2D.drawLine(x1, y1, x2, y2);
						repaint();
						x1 = x2;
						y1 = y2;
					}
				}
				public void mouseMoved(MouseEvent e) {
					
				}
				
			});
			//while the mouse is dragged it sets currentX & currentY as the mouses x and y
			//then it draws a line at the coordinates
			//it repaints it and sets oldX and oldY as currentX and currentY
		}
		

		public void paintComponent(Graphics g){
			if(canvas == null){
				canvas = new BufferedImage(800, 600,BufferedImage.TYPE_INT_ARGB);
				graphics2D = canvas.createGraphics();
				graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				clear();
			}
			g.drawImage(canvas, 0, 0, null);
		}
		
		public void setTool(int tool) {
			this.activeTool = tool;
		}
		
		public void setImage(BufferedImage image) {
			graphics2D.dispose();
			canvas = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			graphics2D = canvas.createGraphics();
			graphics2D.drawImage(image, 0, 0, null);
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		public void clear(){
			graphics2D.setPaint(Color.white);
			graphics2D.fillRect(0, 0, getSize().width, getSize().height);
			repaint();
			graphics2D.setColor(currentColor);
		}
		
		public void eraser(){ // find a transparent color later
			graphics2D.setColor(Color.WHITE);
		}
		public void setColor(Color c){
			currentColor = c;
			graphics2D.setColor(c);
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