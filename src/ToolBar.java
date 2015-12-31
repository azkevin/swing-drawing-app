import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ToolBar implements ActionListener {
	private JToolBar toolBar;
	private JButton pencil;
	private JButton line;
	private JButton rectangle;
	private JButton circle;
	private JButton text;
	private JButton erase;
	private JButton fill;
	private JButton undo;
	private JButton redo;
	private JButton clear;
	private Dimension newDimensions = new Dimension(700,500);
	private JButton save;
	private JButton open;
	private JButton newFile;
	private JFileChooser fc;
	private JComboBox comboBox;
	private File f;
	private DrawFrame frame;

	TextDialog td;
	
	public ToolBar(DrawFrame frame) {
		this.frame = frame;
		fc = new JFileChooser(new File("."));
		fc.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));
		this.initializeToolBar();
		td = new TextDialog(frame);
		clear.addActionListener(this);
		rectangle.addActionListener(this);
		line.addActionListener(this);
		circle.addActionListener(this);
		erase.addActionListener(this);
		pencil.addActionListener(this);
		comboBox.addActionListener(this);
		undo.addActionListener(this);
		redo.addActionListener(this);
		text.addActionListener(this);
		save.addActionListener(this);
		open.addActionListener(this);
		newFile.addActionListener(this);
	}

	private void initializeToolBar() {
		// ----------------
		// create buttons for the tool bar
		// ----------------
		toolBar = new JToolBar(JToolBar.VERTICAL);
		toolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
		toolBar.setFloatable(false);
		toolBar.setLayout(new GridLayout(18,0));

		//toolBar.setBackground( new Color(0, 153, 204));
		
		save = new JButton("Save",new ImageIcon(this.getClass().getResource("/icons/Save-24.png")));
		open = new JButton("Open",new ImageIcon(this.getClass().getResource("/icons/Add Folder-24.png")));
		newFile = new JButton("New",new ImageIcon(this.getClass().getResource("/icons/Create New-24.png")));
		pencil = new JButton("Pencil",new ImageIcon(this.getClass().getResource("/icons/Pencil-24.png")));
		line = new JButton("Line", new ImageIcon(this.getClass().getResource("/icons/Line-24.png")));
		rectangle = new JButton("Rectangle", new ImageIcon(this.getClass().getResource("/icons/Rectangle-24.png")));
		circle = new JButton("Circle", new ImageIcon(this.getClass().getResource("/icons/Circled.png")));
		text = new JButton("Text",new ImageIcon(this.getClass().getResource("/icons/Type-24.png")));
		erase = new JButton("Erase", new ImageIcon(this.getClass().getResource("/icons/Eraser-24.png")));
		undo = new JButton("Undo", new ImageIcon(this.getClass().getResource("/icons/Undo-24.png")));
		redo = new JButton("Redo", new ImageIcon(this.getClass().getResource("/icons/Redo-24.png")));
		clear = new JButton("Clear",new ImageIcon(this.getClass().getResource("/icons/Trash-24.png")));
		
		String[] items = { "Line Width","1", "2", "3", "4", "5", "6", "7", "8" };
		
		comboBox = new JComboBox(items);
		comboBox.setMaximumSize(new Dimension(100,25));

		// ----------------
		// add buttons to the tool bar
		// ----------------
		toolBar.add(newFile);
		toolBar.add(open);
		toolBar.add(save);
		toolBar.addSeparator();
		toolBar.add(pencil);
		toolBar.add(line);
		toolBar.add(rectangle);
		toolBar.add(circle);
		toolBar.addSeparator();
		toolBar.add(text);
		toolBar.add(erase);
		toolBar.add(clear);
		toolBar.addSeparator();
		toolBar.add(undo);
		toolBar.add(redo);
		toolBar.addSeparator();
		toolBar.add(comboBox);
	}

	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();

		if (source == clear) {
			frame.getInkPanel().clear();
		} else if (source == pencil) {
			frame.getInkPanel().setTool(0);
		} else if (source == line) {
			frame.getInkPanel().setTool(1);
		} else if (source == rectangle) {
			frame.getInkPanel().setTool(2);
		} else if (source == circle) {
			frame.getInkPanel().setTool(3);
		} else if (source == text) {

			frame.getInkPanel().setTool(5);			

		} else if (source == erase) {
			frame.getInkPanel().setTool(6);
		} else if (source == fill) {
			frame.getInkPanel().setTool(7);
		} else if (source == undo) {
			frame.getInkPanel().undo();
		} else if (source == redo) {
			frame.getInkPanel().redo();
		}
		else if (source == comboBox) {
			try {
			JComboBox combo = (JComboBox) ae.getSource();
			String current = (String) combo.getSelectedItem();
			frame.getInkPanel().setThickness(Float.valueOf(current));
			}catch(NumberFormatException e){
				
			}
		} else if (source == open) {
			if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				f = fc.getSelectedFile();
				openFile(f);
			}
		} else if (source == save) {
			// open file saver
			if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
				f = new File(fc.getSelectedFile() + ".png");									
				try {
					saveFile(f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (source == newFile) {
			
			newFile();
		}else {
			JButton b = (JButton) source;
			frame.getInkPanel().setColor(b.getBackground());
		}
	}

	public JToolBar getToolBar() {
		return this.toolBar;
	}
	
	private void setDimensions(int width, int height)
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		if(height > dim.height - 160 && width > dim.width - 150)
		{
			frame.getSP().setSize(dim.width - 150, dim.height - 160);
		}	
		else if(width > dim.width - 150)
		{
			frame.getSP().setSize(dim.width - 150, height);
		}
		else if(height > dim.height - 160)
		{
			frame.getSP().setSize(width, dim.height - 160);
		}
		else
		{
			frame.getSP().setSize(width, height);
		}
	}
	private void newFile()
	{
		JFrame newFileFrame = new JFrame();
		newFileFrame.setTitle("New");
		newFileFrame.setBackground(Color.GRAY);
		newFileFrame.setSize(400, 200);
		newFileFrame.setPreferredSize(new Dimension(400,200));
		newFileFrame.setLayout(null);
		newFileFrame.setResizable(false);
		newFileFrame.pack();

		// put the frame in the middle of the display
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		newFileFrame.setLocation(dim.width / 2 - newFileFrame.getSize().width / 2, dim.height / 2 - newFileFrame.getSize().height / 2);

		newFileFrame.setVisible(true);

		JTextField width = new JTextField();
		width.setSize(100, 25);
		width.setLocation(100, 25);
		
		JLabel widthLabel = new JLabel("Width (px):");
		widthLabel.setSize(75, 25);
		widthLabel.setLocation(25, 25);
		
		JLabel heightLabel = new JLabel("Height (px):");
		heightLabel.setSize(75, 25);
		heightLabel.setLocation(25, 75);
		
		JTextField height = new JTextField();
		height.setLocation(100, 75);
		height.setSize(100, 25);

		JButton okay = new JButton("OK");
		okay.setLocation(250, 25);
		okay.setSize(75, 25);
		okay.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							newDimensions = new Dimension(Integer.parseInt(width.getText()), 
									Integer.parseInt(height.getText()));
							System.out.println(newDimensions);
							frame.getInkPanel().setInkPanel(newDimensions.width, newDimensions.height);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							setDimensions(newDimensions.width, newDimensions.height);
							newFileFrame.dispose();
						}
						catch (NumberFormatException nfe)
						{
							JOptionPane.showMessageDialog(null, 
									"Invalid numeric entry. A proper integer is required.", 
									"New", 
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
		);

		JButton cancel = new JButton("Cancel");
		cancel.setSize(75, 25);
		cancel.setLocation(250, 75);
		cancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					newFileFrame.dispose();
				}
			}
		);
		
		newFileFrame.add(heightLabel);
		newFileFrame.add(widthLabel);
		newFileFrame.add(width);
		newFileFrame.add(height);
		newFileFrame.add(okay);
		newFileFrame.add(cancel);
	}
	private void openFile(File f) {

		// ----------------
		// update the contents of the jlabel to be the image from the selected file
		// ----------------

	//	Image image = Toolkit.getDefaultToolkit().getImage(f.getPath());
		try {
			frame.getInkPanel().setImage(ImageIO.read(f));
			newDimensions = new Dimension(ImageIO.read(f).getWidth(), ImageIO.read(f).getHeight());
			setDimensions(newDimensions.width, newDimensions.height);
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
		BufferedImage im = makePanel(frame.getInkPanel());
		ImageIO.write(im, "png", f);
	}
	private BufferedImage makePanel(JPanel panel)
	{
	    int w = panel.getWidth();
	    int h = panel.getHeight();
	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = bi.createGraphics();
	    panel.print(g);
	    return bi;
	}
}