package Model;

import java.awt.Color;

public interface ClosedShape extends Shape {
    Color getFillColor();

    void fill(Color fillColor);
}
