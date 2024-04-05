package Model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Text implements MoveableElement {

    private final String text;
    private int x;
    private int y;
    private int height;
    private int width;
    private final Font font;
    private final int group = 0;

    public Text(int x, int y, int fontSize, Font font, Color color, BasicStroke stroke, String text) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = font;
    }

    @Override
    public int getX1() {
        return x; // Top-left corner for positioning
    }

    @Override
    public int getY1() {
        return y; // Baseline for text positioning
    }

    @Override
    public int getX2() {
        return x + width;
    }

    @Override
    public int getY2() {
        return y + height;
    }

    @Override
    public int getGroup() {
        return group;
    }

    @Override
    public boolean isPointInside(int xD, int yD) {
        return (xD >= this.x && xD <= (this.x + width) && yD <= this.y && yD >= (this.y - height));
    }

    @Override
    public void displace(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setFont(font);
        g.drawString(text, x, y);

        // height and width calc
        FontMetrics fm = g.getFontMetrics(this.font);
        Rectangle2D bounds = fm.getStringBounds(text, g);
        this.width = (int) bounds.getWidth();
        this.height = (int) bounds.getHeight();
    }
}
