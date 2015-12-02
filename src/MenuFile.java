import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
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

		if (source == openFile) {
			if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				f = fc.getSelectedFile();
				openFile(f);
			}
		} else if (source == saveFile) {
			// open file saver
			if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
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
		}
	}
	
    
	private void openFile(File f) {

		// ----------------
		// update the contents of the jlabel to be the image from the selected file
		// ----------------

	//	Image image = Toolkit.getDefaultToolkit().getImage(f.getPath());
		try {
			paint.setImage(ImageIO.read(f));
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
		Container c = paint;
		BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
		c.paint(im.getGraphics());
		ImageIO.write(im, ".png", f );
	}
	
}
