
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuEdit implements ActionListener
{
	private JMenu editMenu;
	
	private JMenuItem undoMenu;
	private JMenuItem redoMenu;
	
	private DrawFrame frame;
	private PaintPanel paint;
	
    public MenuEdit(DrawFrame frame, PaintPanel paint) 
	{
    	this.frame = frame;
    	this.paint = paint;
    	editMenu = this.makeMenu();
	}
    
    public JMenu makeMenu() 
    {
		editMenu = new JMenu("Edit");
	
		editMenu.add(editUndo());
		editMenu.add(editRedo());
	
		return editMenu;
	}
    
    public JMenuItem editUndo() 
    {
		undoMenu = new JMenuItem("Undo");
		undoMenu.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		undoMenu.addActionListener(this);
		return undoMenu;
    }

    public JMenuItem editRedo() 
    {
		redoMenu = new JMenuItem("Redo");
		redoMenu.setAccelerator(KeyStroke.getKeyStroke("ctrl Y"));
		redoMenu.addActionListener(this);
		return redoMenu;
    }
    
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();

		if (source == undoMenu) {

		} 
		if (source == redoMenu) {
			// open file save
		}
	}
	

}
