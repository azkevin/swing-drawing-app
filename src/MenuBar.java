

import javax.swing.JMenuBar;


public class MenuBar {
    private JMenuBar menuBar;

    private MenuFile fileMenu;
    private MenuEdit editMenu;

    public MenuBar(DrawFrame frame, PaintPanel paint) 
    {
    	fileMenu = new MenuFile(frame, paint);
    	editMenu = new MenuEdit(frame, paint);

		menuBar = new JMenuBar();
		menuBar.add(fileMenu.makeMenu());
		menuBar.add(editMenu.makeMenu());
    }

    public JMenuBar getMenuBar() {
    	return this.menuBar;
    }
}
