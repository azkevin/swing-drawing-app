import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorDialog{}

class ColorDialogFrame extends JFrame implements ActionListener,ChangeListener
{
	// the following avoids a "warning" with Java 1.5.0 complier (?)
	static final long serialVersionUID = 42L;

	public static final int APPLY_OPTION = 0;
	public static final int CANCEL_OPTION = 1;
	int userResponse;
	
	JButton example;
	JButton ok;
	JButton cancel;
	JSlider red;
	JSlider blue;
	JSlider green;
	JLabel r;
	JLabel b;
	JLabel g;
	
	public ColorDialogFrame(Color c)
	{
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		example = new JButton();
		example.setHorizontalAlignment(SwingConstants.CENTER);
		example.setPreferredSize(new Dimension(250,300));
		example.setBackground(c);

		ok = new JButton("Apply");
		cancel = new JButton("Cancel");
		ok.setPreferredSize(cancel.getPreferredSize());

		red = new JSlider(0,255,c.getRed());
		blue = new JSlider(0,255,c.getBlue());		
		green = new JSlider(0,255,c.getGreen());

		r = new JLabel("R:");
		b = new JLabel("B:");
		g = new JLabel("G:");
		
		// -------------
		// add listeners
		// -------------


		ok.addActionListener(this);
		cancel.addActionListener(this);
		example.setEnabled(false);
		red.addChangeListener(this);
		green.addChangeListener(this);
		blue.addChangeListener(this);
		// -----------------
		// layout components
		// -----------------

		JPanel sliders = new JPanel();
		sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
		
		JPanel redSlider = new JPanel();
		redSlider.add(r);
		redSlider.add(red);
		JPanel greenSlider = new JPanel();
		greenSlider.add(g);
		greenSlider.add(green);
		JPanel blueSlider = new JPanel();
		blueSlider.add(b);
		blueSlider.add(blue);
		
		
		
		sliders.add(redSlider);
		
		sliders.add(greenSlider);
		sliders.add(blueSlider);



		JPanel p = new JPanel();
	
		p.add(example);
		p.add(sliders);

		p.add(ok);
		p.add(cancel);
		
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// tweek sizes of panels to make the dialog look nice
		
		this.setContentPane(p);
		this.pack();
	}

	// --------------------------------
	// implement ActionListener methods
	// --------------------------------

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();

	}

	// -------------
	// Other methods
	// -------------

	public Color getColor()
	{
		return new Color(red.getValue(),green.getValue(),blue.getValue());
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		example.setBackground(new Color(red.getValue(),green.getValue(),blue.getValue()));
	}

	// -------------
	// inner classes
	// -------------


	
}
