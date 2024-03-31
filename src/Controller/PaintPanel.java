package Controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Model.Circle;
import Model.Ellipse;
import Model.EraserTool;
import Model.ImageShape;
import Model.Line;
import Model.PencilTool;
import Model.Rectangle;
import Model.Shape;
import Model.Square;
import Model.Text;
import Model.Triangle;
import View.DrawFrame;

public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener {
	private final int PENCIL_TOOL = 0;
	private final int LINE_TOOL = 1;
	private final int RECTANGLE_TOOL = 2;
	private final int SQUARE_TOOL = 11;
	private final int CIRCLE_TOOL = 3;
	private final int TEXT_TOOL = 5;
	private final int IMAGE_TOOL = 12;
	private final int MOVE_TOOL = 10;
	private final int ERASER_TOOL = 6;
	private final int FILL_TOOL = 7;
	private final int ELLIPSE_TOOL = 8;
	private final int TRIANGLE_TOOL = 9;
	private final int DELETE_TOOL = 13;

	private TextDialog td;
	private ImageDialog imgd;
	private BasicStroke stroke = new BasicStroke((float) 2);
	BufferedImage canvas;
	Graphics2D graphics2D;
	private int activeTool = 0;
	private DrawFrame frame;

	private Stack<Shape> shapes;
	private Stack<Shape> removed;
	private Stack<Shape> preview;

	private Stack<OperationWrapper> operations;
	private Stack<OperationWrapper> undoneOperations;

	private Shape selectedShape;
	private int selectedShapeGroup;
	private Point initialMousePosition;
	private Point beforeMovePosition;

	private int grouped;

	int x1, y1, x2, y2;

	private boolean dragged = false;
	private Color currentColor;
	private Color fillColor;
	private boolean transparent;

	private int inkPanelWidth;
	private int inkPanelHeight;

	// Now for the constructors
	public PaintPanel(int f, DrawFrame frame, int width, int height) {
		// this.setPreferredSize(new Dimension(300,300));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		inkPanelWidth = dim.width - 150;
		inkPanelHeight = dim.height - 160;
		this.setSize(inkPanelWidth - 3, inkPanelHeight - 3); // the 3 accounts for the sp scroller
		this.setPreferredSize(new Dimension(inkPanelWidth - 3, inkPanelHeight - 3));
		this.setLayout(null);
		setDoubleBuffered(true);
		setLocation(10, 10);
		setBackground(Color.WHITE);
		currentColor = Color.BLACK;
		this.fillColor = Color.white;
		setFocusable(true);
		requestFocus();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.frame = frame;
		this.printPaintPanelSize(inkPanelWidth, inkPanelHeight);
		this.shapes = new Stack<Shape>();
		this.removed = new Stack<Shape>();
		this.operations = new Stack<OperationWrapper>();
		this.undoneOperations = new Stack<OperationWrapper>();
		this.grouped = 1;
		this.preview = new Stack<Shape>();
		this.transparent = true;
		td = new TextDialog(frame);
		imgd = new ImageDialog(frame);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (canvas == null) {
			canvas = new BufferedImage(inkPanelWidth, inkPanelHeight, BufferedImage.TYPE_INT_ARGB);
			graphics2D = canvas.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}
		g.drawImage(canvas, 0, 0, null);
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (Shape s : shapes) {
			s.draw(g2);
		}
		if (!preview.isEmpty()) {
			Shape s = preview.pop();
			s.draw(g2);
		}

	}

	public void setTool(int tool) {
		this.activeTool = tool;
	}

	public void setImage(BufferedImage image) {
		graphics2D.dispose();
		this.setInkPanel(image.getWidth(), image.getHeight());
		canvas = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		graphics2D = canvas.createGraphics();
		graphics2D.drawImage(image, 0, 0, null);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void clear() {
		graphics2D.setPaint(Color.white);
		graphics2D.fillRect(0, 0, getSize().width, getSize().height);
		shapes.removeAllElements();
		removed.removeAllElements();
		repaint();
		graphics2D.setColor(currentColor);
	}

	public void undo() {
		if (!operations.isEmpty()) {
			OperationWrapper lastOperation = operations.pop();
			if (lastOperation.getType() == OperationType.DRAW) {
				if (shapes.size() > 0 && shapes.peek().getGroup() == 0) {
					removed.push(shapes.pop());
					repaint();
				} else if (shapes.size() > 0 && shapes.peek().getGroup() != 0) {
					Shape lastRemoved = shapes.pop();
					removed.push(lastRemoved);

					while (!shapes.isEmpty() && shapes.peek().getGroup() == lastRemoved.getGroup()) {
						removed.push(shapes.pop());
						repaint();
					}
				}
			} else {
				Shape toMove = lastOperation.getShape();

				if (toMove.getGroup() == 0) {
					lastOperation.getShape().displace(-lastOperation.getDeltaX(), -lastOperation.getDeltaY());
				} else {
					Iterator<Shape> itr = shapes.iterator();

					while (itr.hasNext()) {
						Shape nextShape = itr.next();
						if (nextShape.getGroup() == toMove.getGroup()) {
							nextShape.displace(-lastOperation.getDeltaX(), -lastOperation.getDeltaY());
						}

					}
				}
			}
			undoneOperations.add(lastOperation);
			repaint();
		}
	}

	public void redo() {
		OperationWrapper lastOperation = undoneOperations.pop();
		if (lastOperation.getType() == OperationType.DRAW) {
			if (removed.size() > 0 && removed.peek().getGroup() == 0) {
				shapes.push(removed.pop());
				repaint();
			} else if (removed.size() > 0 && removed.peek().getGroup() != 0) {
				Shape lastRemoved = removed.pop();
				shapes.push(lastRemoved);

				while (removed.isEmpty() == false && removed.peek().getGroup() == lastRemoved.getGroup()) {
					shapes.push(removed.pop());
					repaint();
				}
			}
		} else {
			Shape toMove = lastOperation.getShape();

			if (toMove.getGroup() == 0) {
				lastOperation.getShape().displace(lastOperation.getDeltaX(), lastOperation.getDeltaY());
			} else {
				Iterator<Shape> itr = shapes.iterator();

				while (itr.hasNext()) {
					Shape nextShape = itr.next();
					if (nextShape.getGroup() == toMove.getGroup()) {
						nextShape.displace(lastOperation.getDeltaX(), lastOperation.getDeltaY());
					}
				}
			}
		}
		operations.add(lastOperation);
		repaint();
	}

	public void setColor(Color c) {
		currentColor = c;
		graphics2D.setColor(c);

	}

	public void setFillColor(Color c) {
		this.fillColor = c;
	}

	public void setThickness(float f) {
		stroke = new BasicStroke(f);
		graphics2D.setStroke(stroke);
	}

	public void setTransparency(Boolean b) {
		this.transparent = b;
	}

	public void floodFill(Point2D.Double point, Color fillColor) {
		Color targetColor = new Color(canvas.getRGB((int) point.getX(), (int) point.getY()));
		Queue<Point2D.Double> queue = new LinkedList<Point2D.Double>();
		queue.add(point);
		if (!targetColor.equals(fillColor))
			;
		while (!queue.isEmpty()) {
			Point2D.Double p = queue.remove();

			if ((int) p.getX() >= 0 && (int) p.getX() < canvas.getWidth() &&
					(int) p.getY() >= 0 && (int) p.getY() < canvas.getHeight())
				if (canvas.getRGB((int) p.getX(), (int) p.getY()) == targetColor.getRGB()) {
					canvas.setRGB((int) p.getX(), (int) p.getY(), fillColor.getRGB());
					queue.add(new Point2D.Double(p.getX() - 1, p.getY()));
					queue.add(new Point2D.Double(p.getX() + 1, p.getY()));
					queue.add(new Point2D.Double(p.getX(), p.getY() - 1));
					queue.add(new Point2D.Double(p.getX(), p.getY() + 1));
					// System.out.println("0");
				}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		Color primary = currentColor;
		Color secondary = fillColor;
		if (SwingUtilities.isRightMouseButton(e)) {
			primary = secondary;
			secondary = currentColor;
		}
		printCoords(e);
		x2 = e.getX();
		y2 = e.getY();
		dragged = true;
		if (activeTool == ERASER_TOOL) {
			shapes.push(new EraserTool(x1, y1, x2, y2, Color.white, stroke, grouped));
			operations.push(new OperationWrapper(OperationType.DRAW));
			repaint();
			x1 = x2;
			y1 = y2;
		}
		if (activeTool == PENCIL_TOOL) {
			shapes.push(new PencilTool(x1, y1, x2, y2, primary, stroke, grouped));
			operations.push(new OperationWrapper(OperationType.DRAW));
			repaint();
			x1 = x2;
			y1 = y2;
		} else if (activeTool == LINE_TOOL) {
			preview.push(new Line(x1, y1, x2, y2, primary, stroke));
			repaint();
		} else if (activeTool == RECTANGLE_TOOL) {
			if (x1 < x2 && y1 < y2) {
				preview.push(new Rectangle(x1, y1, x2, y2, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1));
			} else if (x2 < x1 && y1 < y2) {
				preview.push(new Rectangle(x2, y1, x1, y2, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1));
			} else if (x1 < x2 && y2 < y1) {
				preview.push(new Rectangle(x1, y2, x2, y1, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2));
			} else if (x2 < x1 && y2 < y1) {
				preview.push(new Rectangle(x2, y2, x1, y1, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2));
			}
			repaint();
		} else if (activeTool == SQUARE_TOOL) {
			int side = Math.min(
					Math.abs(x2 - x1),
					Math.abs(y2 - y1));
			if (x1 < x2 && y1 < y2) {
				preview.push(new Square(x1, y1, side, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1));
			} else if (x2 < x1 && y1 < y2) {
				preview.push(new Square(x2, y1, side, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1));
			} else if (x1 < x2 && y2 < y1) {
				preview.push(new Square(x1, y2, side, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2));
			} else if (x2 < x1 && y2 < y1) {
				preview.push(new Square(x2, y2, side, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2));
			}
			repaint();
		} else if (activeTool == TRIANGLE_TOOL) {
			if (x1 < x2 && y1 < y2) {
				preview.push(
						new Triangle(x1, y2, (int) (x1 + x2) / 2, y1, x2, y2, primary, stroke, secondary, transparent));
			} else if (x2 < x1 && y1 < y2) {
				preview.push(
						new Triangle(x1, y2, (int) (x1 + x2) / 2, y1, x2, y2, primary, stroke, secondary, transparent));
			} else if (x1 < x2 && y2 < y1) {
				preview.push(
						new Triangle(x1, y2, (int) (x1 + x2) / 2, y1, x2, y2, primary, stroke, secondary, transparent));
			} else if (x2 < x1 && y2 < y1) {
				preview.push(
						new Triangle(x1, y2, (int) (x1 + x2) / 2, y1, x2, y2, primary, stroke, secondary, transparent));
			}
			repaint();
		} else if (activeTool == CIRCLE_TOOL) {
			// int radius = (int) Math.sqrt(((x1 - x2) / 2) * ((x1 - x2) / 2) + ((y1 - y2) /
			// 2) * ((y1 - y2) / 2));
			int radius;
			if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
				radius = (int) (y2 - y1) / 2;
				radius = Math.abs(radius);
			} else {
				radius = (int) (x2 - x1) / 2;
				radius = Math.abs(radius);
			}
			if (x1 < x2 && y1 < y2) {
				preview.push(new Circle(x1, y1, radius, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1));
			} else if (x2 < x1 && y1 < y2) {
				preview.push(new Circle(x2, y1, radius, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x2, y1, x1 - x2, y2 - y1));
			} else if (x1 < x2 && y2 < y1) {
				preview.push(new Circle(x1, y2, radius, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x1, y2, x2 - x1, y1 - y2));
			} else if (x2 < x1 && y2 < y1) {
				preview.push(new Circle(x2, y2, radius, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x2, y2, x1 - x2, y1 - y2));
			}
			repaint();
		} else if (activeTool == ELLIPSE_TOOL) {
			if (x1 < x2 && y1 < y2) {
				preview.push(new Ellipse(x1, y1, x2, y2, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1));
			} else if (x2 < x1 && y1 < y2) {
				preview.push(new Ellipse(x2, y1, x1, y2, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x2, y1, x1 - x2, y2 - y1));
			} else if (x1 < x2 && y2 < y1) {
				preview.push(new Ellipse(x1, y2, x2, y1, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x1, y2, x2 - x1, y1 - y2));
			} else if (x2 < x1 && y2 < y1) {
				preview.push(new Ellipse(x2, y2, x1, y1, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x2, y2, x1 - x2, y1 - y2));
			}
			repaint();
		} else if (activeTool == MOVE_TOOL && selectedShape != null) {
			int deltaX = x2 - initialMousePosition.x;
			int deltaY = y2 - initialMousePosition.y;

			initialMousePosition.x = x2;
			initialMousePosition.y = y2;

			// Update the shape's position based on the mouse movement
			if (selectedShapeGroup != 0) {
				Iterator<Shape> iterator = shapes.iterator();
				while (iterator.hasNext()) {
					Shape shape = iterator.next();
					if (shape.getGroup() == selectedShapeGroup) {
						shape.displace(deltaX, deltaY);
					}
				}
			} else {
				selectedShape.displace(deltaX, deltaY);
			}

			repaint();
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		printCoords(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		printCoords(e);
		x1 = e.getX();
		y1 = e.getY();

		if (activeTool == MOVE_TOOL) {
			Stack<Shape> temp = new Stack<Shape>();

			while (!shapes.isEmpty()) {
				Shape shape = shapes.pop();
				if (shape.isPointInside(e.getX(), e.getY())) {
					selectedShape = shape;
					selectedShapeGroup = shape.getGroup();
					initialMousePosition = e.getPoint();
					beforeMovePosition = e.getPoint();
					break;
				} else {
					temp.push(shape);
				}
			}

			while (!temp.isEmpty()) {
				shapes.push(temp.pop());
			}
			if (selectedShape != null) {
				shapes.push(selectedShape);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		grouped++;
		Color primary = currentColor;
		Color secondary = fillColor;
		if (SwingUtilities.isRightMouseButton(e)) {
			primary = secondary;
			secondary = currentColor;
		}

		if (activeTool == LINE_TOOL && dragged) {
			shapes.push(new Line(x1, y1, x2, y2, primary, stroke));
			operations.push(new OperationWrapper(OperationType.DRAW));
			repaint();
			// graphics2D.drawLine(x1, y1, x2, y2);
		} else if (activeTool == RECTANGLE_TOOL && dragged) {

			if (x1 < x2 && y1 < y2) {
				shapes.push(new Rectangle(x1, y1, x2, y2, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1));
			} else if (x2 < x1 && y1 < y2) {
				shapes.push(new Rectangle(x2, y1, x1, y2, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1));
			} else if (x1 < x2 && y2 < y1) {
				shapes.push(new Rectangle(x1, y2, x2, y1, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2));
			} else if (x2 < x1 && y2 < y1) {
				shapes.push(new Rectangle(x2, y2, x1, y1, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2));
			}
			operations.push(new OperationWrapper(OperationType.DRAW));
			repaint();
		} else if (activeTool == SQUARE_TOOL && dragged) {
			int side = Math.min(
					Math.abs(x2 - x1),
					Math.abs(y2 - y1));
			if (x1 < x2 && y1 < y2) {
				shapes.push(new Square(x1, y1, side, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1));
			} else if (x2 < x1 && y1 < y2) {
				shapes.push(new Square(x2, y1, side, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1));
			} else if (x1 < x2 && y2 < y1) {
				shapes.push(new Square(x1, y2, side, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2));
			} else if (x2 < x1 && y2 < y1) {
				shapes.push(new Square(x2, y2, side, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2));
			}
			operations.push(new OperationWrapper(OperationType.DRAW));
			repaint();
		} else if (activeTool == TRIANGLE_TOOL) {
			if (x1 < x2 && y1 < y2) {
				shapes.push(
						new Triangle(x1, y2, (int) (x1 + x2) / 2, y1, x2, y2, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1));
			} else if (x2 < x1 && y1 < y2) {
				shapes.push(
						new Triangle(x1, y2, (int) (x1 + x2) / 2, y1, x2, y2, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1));
			} else if (x1 < x2 && y2 < y1) {
				shapes.push(
						new Triangle(x1, y2, (int) (x1 + x2) / 2, y1, x2, y2, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2));
			} else if (x2 < x1 && y2 < y1) {
				shapes.push(
						new Triangle(x1, y2, (int) (x1 + x2) / 2, y1, x2, y2, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2));
			}
			operations.push(new OperationWrapper(OperationType.DRAW));
			repaint();
		} else if (activeTool == CIRCLE_TOOL && dragged) {
			// int radius = (int) Math.sqrt(((x1 - x2) / 2) * ((x1 - x2) / 2) + ((y1 - y2) /
			// 2) * ((y1 - y2) / 2));
			int radius;
			if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
				radius = (int) (y2 - y1) / 2;
				radius = Math.abs(radius);
			} else {
				radius = (int) (x2 - x1) / 2;
				radius = Math.abs(radius);
			}
			if (x1 < x2 && y1 < y2) {
				shapes.push(new Circle(x1, y1, radius, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1));
			} else if (x2 < x1 && y1 < y2) {
				shapes.push(new Circle(x2, y1, radius, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x2, y1, x1 - x2, y2 - y1));
			} else if (x1 < x2 && y2 < y1) {
				shapes.push(new Circle(x1, y2, radius, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x1, y2, x2 - x1, y1 - y2));
			} else if (x2 < x1 && y2 < y1) {
				shapes.push(new Circle(x2, y2, radius, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x2, y2, x1 - x2, y1 - y2));
			}
			operations.push(new OperationWrapper(OperationType.DRAW));
			repaint();
		} else if (activeTool == ELLIPSE_TOOL && dragged) {
			if (x1 < x2 && y1 < y2) {
				shapes.push(new Ellipse(x1, y1, x2, y2, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1));
			} else if (x2 < x1 && y1 < y2) {
				shapes.push(new Ellipse(x2, y1, x1, y2, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x2, y1, x1 - x2, y2 - y1));
			} else if (x1 < x2 && y2 < y1) {
				shapes.push(new Ellipse(x1, y2, x2, y1, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x1, y2, x2 - x1, y1 - y2));
			} else if (x2 < x1 && y2 < y1) {
				shapes.push(new Ellipse(x2, y2, x1, y1, primary, stroke, secondary, transparent));
				// graphics2D.draw(new Ellipse2D.Double(x2, y2, x1 - x2, y1 - y2));
			}
			operations.push(new OperationWrapper(OperationType.DRAW));
			repaint();
		} else if (activeTool == DELETE_TOOL) {
			Stack<Shape> temp = new Stack<Shape>();

			while (!shapes.isEmpty()) {
				Shape shape = shapes.pop();
				if (shape.isPointInside(e.getX(), e.getY())) {
					if (shape.getGroup() != 0) {
						/////////////////////////////////////////////
						while (!temp.isEmpty()) {
							shapes.push(temp.pop());
						}

						while (!shapes.isEmpty()) {
							Shape shape2 = shapes.pop();
							if (shape2.getGroup() != shape.getGroup()) {
								temp.push(shape2);
							}
						}
						/////////////////////////////////////////////
					}
					break;
				} else {
					temp.push(shape);
				}
			}

			while (!temp.isEmpty()) {
				shapes.push(temp.pop());
			}
		} else if (activeTool == TEXT_TOOL) {
			int i = td.showCustomDialog(frame);
			if (i == TextDialog.APPLY_OPTION) {
				shapes.push(new Text(x1, y1, td.getInputSize(), td.getFont(), primary, stroke, td.getText()));
				operations.push(new OperationWrapper(OperationType.DRAW));
			}

		} else if (activeTool == IMAGE_TOOL) {
			int i = imgd.showCustomDialog(frame);
			if (i == ImageDialog.APPLY_OPTION) {
				int width = imgd.getWidthValue();
				int height = imgd.getHeightValue();
				File selectedFile = imgd.getSelectedFile();
				if (selectedFile != null) {
					shapes.push(new ImageShape(x1, y1, selectedFile, width, height));
					operations.push(new OperationWrapper(OperationType.DRAW));
				}
			}
		} else if (activeTool == MOVE_TOOL && selectedShape != null) {
			int deltaX = x2 - beforeMovePosition.x;
			int deltaY = y2 - beforeMovePosition.y;

			operations.push(new OperationWrapper(OperationType.MOVE, selectedShape, deltaX, deltaY));

			selectedShape = null;
		} else if (activeTool == FILL_TOOL) {
			floodFill(new Point2D.Double(x1, y1), fillColor);
		}

		dragged = false;
		removed.removeAllElements();
		repaint();
	}

	public void printCoords(MouseEvent e) {
		String posX = String.valueOf((int) e.getPoint().getX());
		String posY = String.valueOf((int) e.getPoint().getY());
		frame.getCoordinateBar().getCoordinates().setText(posX + ",  " + posY + " px");
	}

	// print drawer panel size at status tool bar
	public void printPaintPanelSize(int width, int height) {
		frame.getCoordinateBar().getFrameSize().setText(width + ",  " + height + " px");
	}

	public void setInkPanelWidth(int width) {
		this.inkPanelWidth = width;
	}

	public void setInkPanelHeight(int height) {
		this.inkPanelHeight = height;
	}

	public void setInkPanel(int width, int height) {
		canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		graphics2D = canvas.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.printPaintPanelSize(width, height);
		this.setSize(width - 3, height - 3);
		this.setPreferredSize(new Dimension(width - 3, height - 3));
		clear();

	}

	// Inner class to wrap both draw and move operations
	private static class OperationWrapper {
		private OperationType type;
		private Shape shape;
		private int deltaX;
		private int deltaY;

		public OperationWrapper(OperationType type) {
			this.type = type;
		}

		public OperationWrapper(OperationType type, Shape shape, int deltaX, int deltaY) {
			this.type = type;
			this.shape = shape;
			this.deltaX = deltaX;
			this.deltaY = deltaY;
		}

		public OperationType getType() {
			return type;
		}

		public Shape getShape() {
			return shape;
		}

		public int getDeltaX() {
			return deltaX;
		}

		public int getDeltaY() {
			return deltaY;
		}
	}

	// Enum to represent types of operations
	private enum OperationType {
		DRAW,
		MOVE
	}
}