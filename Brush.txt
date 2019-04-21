
package SpriteEditor1;

import java.awt.Color;

public class Brush 
{
    protected enum BrushMode { NORMAL, ERASE, FILL, SELECT, PASTE, DRAW_LINE, DRAW_RECT, DRAW_OVAL }    // no copy or cut
    
    protected Color color;
    protected BrushMode brushMode;
    protected boolean [][] shape = { {true} };  // init the shape to 1x1, a single pixel filler
    protected Image copied;
    protected Image selected;
    protected int X;
    protected int Y;
    protected boolean hasP1;
    protected boolean hasP2;
    protected int selectedX1;
    protected int selectedX2;
    protected int selectedY1;
    protected int selectedY2;
    
    protected Brush()
    {
        color = Color.black;
        brushMode = BrushMode.NORMAL;
    }
}
