import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuFile implements ActionListener
{
	private JMenuItem openFile;
	private JMenuItem saveFile;
	private JMenuItem newFile;
	private JMenu fileMenu; 
	private JFileChooser fc;
	private File f;
	private Image image;
	
	private DrawFrame frame;
	private PaintPanel paint;
	private Dimension newDimensions = new Dimension(700,500);
	
    public MenuFile(DrawFrame frame, PaintPanel paint) 
	{
    	this.frame = frame;
    	this.paint = paint;
		fileMenu = this.makeMenu();
			
		fc = new JFileChooser(new File("."));
		fc.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));
	}
    
    public JMenu makeMenu() 
    {
		fileMenu = new JMenu("File");
	
		fileMenu.add(fileNew());
		fileMenu.add(fileOpen());
		fileMenu.add(fileSave());
	
		return fileMenu;
    }
    
    private JMenuItem fileNew() 
    {
    	newFile = new JMenuItem("New");
		newFile.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		newFile.addActionListener(this);
		return newFile;
    }

    private JMenuItem fileOpen() 
    {
    	openFile = new JMenuItem("Open");
		openFile = new JMenuItem("Open");
		openFile.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		openFile.addActionListener(this);
		return openFile;
    }

    private JMenuItem fileSave() 
    {
		saveFile = new JMenuItem("Save",new ImageIcon(this.getClass().getResource("/icons/Save-24.png")));
		saveFile.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		saveFile.addActionListener(this);
		return saveFile;
    }
    
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();

		if (source == newFile)
		{
			newFile();
			
		}
		
		if (source == openFile) {
			if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				f = fc.getSelectedFile();
				openFile(f);
			}
		} else if (source == saveFile) {
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
			// open new tab with new picture
			// blank canvas
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
}
