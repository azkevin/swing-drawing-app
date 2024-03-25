package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Text implements Shape {

    private final String text;
    private final int x;
    private final int y;
    private final Font font;
    private final Color color;
    private final int group = 0;

    public Text(int x, int y, int fontSize, Font font, Color color, BasicStroke stroke, String text) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = font;
        this.color = color;
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
        // Not applicable for text, we don't need width/height information
        return 0;
    }

    @Override
    public int getY2() {
        // Not applicable for text, we don't need width/height information
        return 0;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public BasicStroke getStroke() {
        // Not applicable for text, there's no stroke for text
        return null;
    }

    @Override
    public int getGroup() {
        return group;
    }

    @Override
    public boolean isPointInside(int xD, int yD) {
        // TODO
        return false;
    }

    @Override
    public void draw(Graphics2D g) {
        // g.setFont(font);
        // g.setColor(color);
        // FontMetrics metrics = g.getFontMetrics(font);
        // int ascent = metrics.getAscent(); // Get ascent for baseline positioning
        // g.drawString(text, x, y + ascent); // Draw text with baseline adjustment

        g.setFont(font);
        g.drawString(text, x, y);
    }
}
