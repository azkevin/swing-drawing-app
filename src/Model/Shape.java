package Model;

import java.awt.BasicStroke;
import java.awt.Color;

public interface Shape extends MoveableElement {
    BasicStroke getStroke();

    Color getColor();
}
