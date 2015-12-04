
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

public class ToolBar implements ActionListener {
	private JToolBar toolBar;

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

	private DrawFrame frame;

	TextDialog td;
	
	public ToolBar(DrawFrame frame) {
		this.frame = frame;

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
		fill.addActionListener(this);
		text.addActionListener(this);
	}

	private void initializeToolBar() {
		// ----------------
		// create buttons for the tool bar
		// ----------------
		toolBar = new JToolBar(JToolBar.VERTICAL);
		toolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
		toolBar.setFloatable(false);

		select = new JButton("Select");
		// open = new JButton("Open");
		pencil = new JButton("Pencil");
		line = new JButton("Line", new ImageIcon(this.getClass().getResource("/icons/Line-24.png")));
		rectangle = new JButton("Rectangle", new ImageIcon(this.getClass().getResource("/icons/Rectangle-24.png")));
		circle = new JButton("Circle", new ImageIcon(this.getClass().getResource("/icons/Circled.png")));
		// oval = new JButton("Oval");
		rotateLeft = new JButton("Rotate left",
				new ImageIcon(this.getClass().getResource("/icons/Rotate Left-24.png")));
		rotateRight = new JButton("Rotate right",
				new ImageIcon(this.getClass().getResource("/icons/Rotate Right-24.png")));
		flipHorizontal = new JButton("Flip horizontal",
				new ImageIcon(this.getClass().getResource("/icons/Flip Horizontal-24.png")));
		flipVertical = new JButton("Flip vertical",
				new ImageIcon(this.getClass().getResource("/icons/Flip Vertical-24.png")));
		text = new JButton("Text");
		erase = new JButton("Erase", new ImageIcon(this.getClass().getResource("/icons/Eraser-24.png")));
		fill = new JButton("Fill", new ImageIcon(this.getClass().getResource("/icons/Fill Color-24.png")));
		undo = new JButton("Undo", new ImageIcon(this.getClass().getResource("/icons/Undo-24.png")));
		redo = new JButton("Redo", new ImageIcon(this.getClass().getResource("/icons/Redo-24.png")));
		clear = new JButton("Clear");

		// select.setBackground(background);
		// open.setBackground(background);
		// pencil.setBackground(background);

		String[] items = { "1", "2", "3", "4", "5", "6", "7", "8" };
		comboBox = new JComboBox(items);
		comboBox.setMaximumSize(new Dimension(20, 50));

		// ----------------
		// add buttons to the tool bar
		// ----------------

		toolBar.add(select);
		toolBar.addSeparator();
		toolBar.add(pencil);
		toolBar.add(line);
		toolBar.add(rectangle);
		toolBar.add(circle);
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
		} else if (source == select) {
			frame.getInkPanel().setTool(4);
		} else if (source == text) {
			
			
				int i = td.showCustomDialog(frame);
		
			if (i == TextDialog.APPLY_OPTION) {
		
					
		
				}

			
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
			System.out.println(1);
			JComboBox combo = (JComboBox) ae.getSource();
			String current = (String) combo.getSelectedItem();
			frame.getInkPanel().setThickness(Float.valueOf(current));
		} else {
			JButton b = (JButton) source;
			frame.getInkPanel().setColor(b.getBackground());
		}
	}

	public JToolBar getToolBar() {
		return this.toolBar;
	}

}
