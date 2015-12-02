import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

class DrawFrame extends JFrame implements MouseMotionListener, MouseListener, ActionListener, ChangeListener
{
	// the following avoids a "warning" with Java 1.5.0 complier
	static final long serialVersionUID = 42L;

	//fields
	private JPanel contentPane;	
	private PaintPanel inkPanel;
	private JMenuBar menuBar;
	final int MAX_SAMPLES = (30000); //Sufficient number of samples for points to draw.

	private JButton select;
	private JButton open;
	private JButton pencil;
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
	
	private JComboBox comboBox;
	//private JButton thicker;
	//private JButton thinner;

	private JToolBar toolBar;
	
	private CoordinateBar coordinateBar;
	
	private JButton turquoise;
	private JButton emerald;
	private JButton peter_river;
	private JButton amethyst;
	private JButton wet_asphalt;
	private JButton green_sea;
	private JButton nephritis;
	private JButton belize_hole;
	private JButton wisteria;
	private JButton midnight_blue;
	private JButton sun_flower;
	private JButton carrot;
	private JButton alizarin;
	private JButton clouds;
	private JButton concrete;
	private JButton orange;
	private JButton pumpkin;
	private JButton pomegranate;
	private JButton silver;
	private JButton asbestos;
	
	private Color turquoiseC = new Color(26, 188, 156);
	private Color emeraldC = new Color(46, 204, 113);
	private Color peter_riverC = new Color(52, 152, 219);
	private Color amethystC = new Color(155, 89, 182);
	private Color wet_asphaltC = new Color(52, 73, 94);
	private Color green_seaC = new Color(22, 160, 133);
	private Color nephritisC = new Color(39, 174, 96);
	private Color belize_holeC = new Color(41, 128, 185);
	private Color wisteriaC = new Color(142, 68, 173);
	private Color midnight_blueC = new Color(44, 62, 80);
	private Color sun_flowerC = new Color(241, 196, 15);
	private Color carrotC = new Color(230, 126, 34);
	private Color alizarinC = new Color(231, 76, 60);
	private Color cloudsC = new Color(236, 240, 241);
	private Color concreteC = new Color(149, 165, 166);
	private Color orangeC = new Color(243, 156, 18);
	private Color pumpkinC = new Color(211, 84, 0);
	private Color pomegranateC = new Color(192, 57, 43);
	private Color silverC = new Color(189, 195, 199);
	private Color asbestosC = new Color(127, 140, 141);
	
	private JFileChooser fc;
	private File f;
	private Image image;
	//private ColorChooser cc;
	
	private FlowLayout layout;
	
	private final int CONTENT_PANE_WIDTH = 1400;
	private final int CONTENT_PANE_HEIGHT = 700;
	private final int CC_WIDTH = 400;
	private final int CC_HEIGHT = 300;
	private final Color background = Color.gray;
	
	//ContentPane > Toolbar,  cc, InkPanel,
	public DrawFrame()
	{
		// construct our layout manager.
		layout = new FlowLayout(FlowLayout.RIGHT);
		
		// construct the panels needed
		contentPane = new JPanel(layout);
		inkPanel = new PaintPanel(0);
		
		// create a menu bar
		menuBar = (new MenuBar(this, inkPanel)).getMenuBar();
		this.setJMenuBar(menuBar);
		

		// create a tool bar		
		toolBar = new JToolBar(JToolBar.VERTICAL);
		toolBar.setFloatable(false);		
		this.initializeToolBar(toolBar);
		
		// create coordinate bar at the bottom
		coordinateBar = new CoordinateBar();
		
		// create a file chooser for saving and opening
		fc = new JFileChooser(new File("."));
		fc.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));
		
		// create a color chooser for the drawing components.
		//cc = new ColorChooser();
		//cc.setPreviewPanel(new JPanel());
		//cc.setPreferredSize(new Dimension(CC_WIDTH,CC_HEIGHT));
		//cc.getSelectionModel().addChangeListener(this);

		//List<AbstractColorChooserPanel> choosers = new ArrayList<>(Arrays.asList(cc.getChooserPanels()));
		//cc.remove(0);
		//JPanel swatch = new JPanel();
		//cc.add(swatch, 0);
		//cc.setChooserPanels(choosers.toArray(new AbstractColorChooserPanel[0]));
				
		//thicker = new JButton("Thicker");
		//thinner = new JButton("Thinner");
	
		//width.add(thicker);
		//width.add(thinner);
		
		//thicker.addActionListener(this);
		//thinner.addActionListener(this);
		
		JPanel cc = new JPanel();
		cc.setLayout(new GridLayout(4,5));
		this.initializeColorChooser(cc);

		turquoise.addActionListener(this);
		emerald.addActionListener(this);
		peter_river.addActionListener(this);
		amethyst.addActionListener(this);
		wet_asphalt.addActionListener(this);
		green_sea.addActionListener(this);
		nephritis.addActionListener(this);
		belize_hole.addActionListener(this);
		wisteria.addActionListener(this);
		midnight_blue.addActionListener(this);
		sun_flower.addActionListener(this);
		carrot.addActionListener(this);
		alizarin.addActionListener(this);
		clouds.addActionListener(this);
		concrete.addActionListener(this);
		orange.addActionListener(this);
		pumpkin.addActionListener(this);
		pomegranate.addActionListener(this);
		silver.addActionListener(this);
		asbestos.addActionListener(this);
		
		
		// configure components and add them to the frame.

		contentPane.add(inkPanel);
		contentPane.add(toolBar);
		//contentPane.add(thicker);
		//contentPane.add(thinner);
		contentPane.add(cc);
		contentPane.setBackground(background);
        contentPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    	contentPane.add(coordinateBar, BorderLayout.PAGE_END);
		
		
		// add listeners to buttons
		this.addWindowListener(new WindowCloser());
		clear.addActionListener(this);
		rectangle.addActionListener(this);
		line.addActionListener(this);
		circle.addActionListener(this);
		erase.addActionListener(this);
		pencil.addActionListener(this);
		comboBox.addActionListener(this);
		
		// set components into the contentPane
		this.setContentPane(contentPane);
		this.setPreferredSize(new Dimension(CONTENT_PANE_WIDTH,CONTENT_PANE_HEIGHT));
	}

	private void initializeToolBar(JToolBar toolBar) {
		toolBar.setBackground(Color.gray);
		// ----------------
		// create buttons for the tool bar
		// ----------------
		
		select = new JButton("Select");
		//open = new JButton("Open");
		pencil = new JButton("Pencil");
		line = new JButton("Line",new ImageIcon(this.getClass().getResource("/icons/Line-24.png")));
		rectangle = new JButton("Rectangle",new ImageIcon(this.getClass().getResource("/icons/Rectangle-24.png")));
		circle = new JButton("Circle",new ImageIcon(this.getClass().getResource("/icons/Circled.png")));
		//oval = new JButton("Oval");
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
		
		select.setBackground(background);
		//open.setBackground(background);
		pencil.setBackground(background);
		
		String[] items = {"2","3","4","5","6","7","8","9","10"};
		comboBox = new JComboBox(items);
		
		// ----------------
		// add buttons to the tool bar
		// ----------------
		
		toolBar.add(select);
		//toolBar.add(open);
		toolBar.addSeparator();
		toolBar.add(pencil);
		toolBar.add(line);
		toolBar.add(rectangle);
		toolBar.add(circle);
		//toolBar.add(oval);
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
		toolBar.add(comboBox);
	}
	private void initializeColorChooser(JPanel panel) {
		turquoise = new JButton("");
		turquoise.setPreferredSize(new Dimension(50,50));
		turquoise.setBackground(turquoiseC);
	
		
		emerald = new JButton("");
		emerald.setPreferredSize(new Dimension(50,50));
		emerald.setBackground(emeraldC);
		
		peter_river = new JButton("");
		peter_river.setPreferredSize(new Dimension(50,50));
		peter_river.setBackground(peter_riverC);
		amethyst = new JButton("");
		amethyst.setPreferredSize(new Dimension(50,50));
		amethyst.setBackground(amethystC);
		wet_asphalt = new JButton("");
		wet_asphalt.setPreferredSize(new Dimension(50,50));
		wet_asphalt.setBackground(wet_asphaltC);
		green_sea = new JButton("");
		green_sea.setPreferredSize(new Dimension(50,50));
		green_sea.setBackground(green_seaC);
		nephritis = new JButton("");
		nephritis.setPreferredSize(new Dimension(50,50));
		nephritis.setBackground(nephritisC);
		belize_hole = new JButton("");
		belize_hole.setPreferredSize(new Dimension(50,50));
		belize_hole.setBackground(belize_holeC);
		wisteria = new JButton("");
		wisteria.setPreferredSize(new Dimension(50,50));
		wisteria.setBackground(wisteriaC);
		midnight_blue = new JButton("");
		midnight_blue.setPreferredSize(new Dimension(50,50));
		midnight_blue.setBackground(midnight_blueC);
		sun_flower = new JButton("");
		sun_flower.setPreferredSize(new Dimension(50,50));
		sun_flower.setBackground(sun_flowerC);
		carrot = new JButton("");
		carrot.setPreferredSize(new Dimension(50,50));
		carrot.setBackground(carrotC);
		alizarin = new JButton("");
		alizarin.setPreferredSize(new Dimension(50,50));
		alizarin.setBackground(alizarinC);
		clouds = new JButton("");
		clouds.setPreferredSize(new Dimension(50,50));
		clouds.setBackground(cloudsC);
		concrete = new JButton("");
		concrete.setPreferredSize(new Dimension(50,50));
		concrete.setBackground(concreteC);
		orange = new JButton("");
		orange.setPreferredSize(new Dimension(50,50));
		orange.setBackground(orangeC);
		pumpkin = new JButton("");
		pumpkin.setPreferredSize(new Dimension(50,50));
		pumpkin.setBackground(pumpkinC);
		pomegranate = new JButton("");
		pomegranate.setPreferredSize(new Dimension(50,50));
		pomegranate.setBackground(pomegranateC);
		silver = new JButton("");
		silver.setPreferredSize(new Dimension(50,50));
		silver.setBackground(silverC);
		asbestos = new JButton("");
		asbestos.setPreferredSize(new Dimension(50,50));
		asbestos.setBackground(asbestosC);
		
		
		
		panel.add(turquoise);
		panel.add(emerald);
		panel.add(peter_river);
		panel.add(amethyst);
		panel.add(wet_asphalt);
		panel.add(green_sea);
		panel.add(nephritis);
		panel.add(belize_hole);
		panel.add(wisteria);
		panel.add(midnight_blue);
		panel.add(sun_flower);
		panel.add(carrot);
		panel.add(alizarin);
		panel.add(clouds);
		panel.add(concrete);
		panel.add(orange);
		panel.add(pumpkin);
		panel.add(pomegranate);
		panel.add(silver);
		panel.add(asbestos);
		
	}
	
	
	// implement ActionListener method (initially run)
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();

		if (source == clear){
			inkPanel.clear();
		}
		else if (source == pencil){
			inkPanel.setTool(0);
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
		else if (source == select){
			inkPanel.setTool(4);
		}
		else if (source == text){
			inkPanel.setTool(5);
		}
		else if (source == erase){
			inkPanel.eraser();
		}
//		else if (source == thicker){
//		
//			inkPanel.thicker();
//		}
//		else if (source == thinner){
//	
//			inkPanel.thinner();
//		}
		else if (source == comboBox){
			System.out.println(1);
			JComboBox combo = (JComboBox)ae.getSource();
			String current = (String) combo.getSelectedItem();
			inkPanel.setThickness(Float.valueOf(current) );
		}
		else{
			JButton b = (JButton) source;
			inkPanel.setColor(b.getBackground());
		}
	}
	
	public void stateChanged(ChangeEvent e){
		//inkPanel.setColor(cc.getColor());
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
