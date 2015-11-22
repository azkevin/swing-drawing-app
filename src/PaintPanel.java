import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class PaintPanel extends JPanel
	{
		// the following avoids a "warning" with Java 1.5.0 complier (?)
		static final long serialVersionUID = 42L;

		private final Color INK_COLOR_BLACK = new Color(0, 0, 0);
		private final Stroke INK_STROKE_3 = new BasicStroke(3.0f);

		private Vector<Line2D.Double> v;

		private Image image;
		private JLabel label;
		
		public PaintPanel()
		{
			// construct components
			// contentPane > outerPanel > inkPanel > (Components: clearButton, stroke)
			
			// configure components
			
			// add listeners
			
			// set components into the contentPane
			v = new Vector<Line2D.Double>();
			this.setBackground(Color.white);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			this.setPreferredSize(new Dimension(250, 250));
			
			//label = new JLabel("",new ImageIcon(image),JLabel.CENTER);
			//this.add(label);
			//Image image = Toolkit.getDefaultToolkit().getImage(f.getPath());
		}
		public PaintPanel(Image image){
			
			v = new Vector<Line2D.Double>();
			this.setBackground(Color.white);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			this.setPreferredSize(new Dimension(250, 250));
			
			label = new JLabel("",new ImageIcon(image),JLabel.CENTER);
			this.image = image;
			this.add(label);
			
		}
		public void setImage(Image image){
			this.image = image;
		}
		
		public void drawImage(){
			
			label = new JLabel("",new ImageIcon(image),JLabel.CENTER);
			
			
			label.setIcon(new ImageIcon(image));
			label.repaint();
					
			
		}
		
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			paintInkStrokes(g);
		}

		/**
		 * Paint all the line segments stored in the vector
		 */
		private void paintInkStrokes(Graphics g)
		{
			Graphics2D g2 = (Graphics2D) g;

			// set the inking color
			g2.setColor(INK_COLOR_BLACK);

			// set the stroke thickness, and cap and join attributes ('round')
			Stroke s = g2.getStroke(); // save current stroke
			g2.setStroke(INK_STROKE_3); // set desired stroke

			// retrive each line segment and draw it
			for (int i = 0; i < v.size(); ++i)
				g2.draw((Line2D.Double) v.elementAt(i));

			g2.setStroke(s); // restore stroke
		}

		/**
		 * Draw one line segment, then add it to the vector.
		 * <p>
		 */
		public void drawInk(int x1, int y1, int x2, int y2)
		{
			// get graphics context
			Graphics2D g2 = (Graphics2D) this.getGraphics();

			// create the line
			Line2D.Double inkSegment = new Line2D.Double(x1, y1, x2, y2);

			g2.setColor(INK_COLOR_BLACK); // set the inking color
			//Stroke s = g2.getStroke(); // save current stroke
			g2.setStroke(INK_STROKE_3); // set desired stroke
			g2.draw(inkSegment); // draw it!
			//g2.setStroke(s); // restore stroke
			v.add(inkSegment); // add to vector
		}

		public void clear()
		{
			v.clear();
			this.repaint();
		}
	}