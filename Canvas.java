
//  You ain't nobody til you got somebody, you ain't nobody til you got somebody 
//  Oh tell you me you love me, I need someone, on days like this I do, on days like this I do
//  You ain't nobody til you got somebody, you ain't nobody til you got somebody

package SpriteEditor1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Canvas extends JPanel
{
    protected Image spriteLayer;
    protected int layerIndex;
    protected final int pixelDistance = 10;                                     // this can change
    protected final Color defaultColor = Color.LIGHT_GRAY.brighter();
    
    protected Canvas()
    {
        layerIndex = 0;
        spriteLayer = SpriteEditor1.sprite.layers.get(layerIndex);
        
        addMouseListener(new MouseClickListener());
        addMouseMotionListener(new MouseMovementListener());
        
    }
    
    public void paintComponent(Graphics g)  // repaint is called whenever the mouse is entered, exited, moved, clicked, or dragged
            //  repaint draws the sprite layer, black pixel outline grid, the brush, and the selected rectangle 
    { 
        for (int y = 0; y < spriteLayer.height; ++y)
            for (int x = 0; x < spriteLayer.width; ++x)
            {
                if (spriteLayer.colors[y][x] == null) g.setColor(defaultColor);
                else g.setColor(spriteLayer.colors[y][x]);
                g.fillRect(x*pixelDistance, y*pixelDistance, pixelDistance, pixelDistance);
            }
        if (pixelDistance < 5);
        else
        {
            g.setColor(Color.black);
            for (int y = 0; y <= spriteLayer.height; ++y)       // drawing the black pixel outlines after the fact is actually convenient in a couple ways
                g.drawLine(0, y*pixelDistance, spriteLayer.width * pixelDistance, y*pixelDistance);
            for (int x = 0; x <= spriteLayer.width; ++x)
                g.drawLine(x*pixelDistance, 0, x*pixelDistance, spriteLayer.height * pixelDistance);
        }
        
        switch (SpriteEditor1.brush.brushMode)
        {
            case NORMAL :
            {
                for (int y = 0; y < SpriteEditor1.brush.shape.length; ++y)
                    for (int x = 0; x < SpriteEditor1.brush.shape[y].length; ++x)
                        if (SpriteEditor1.brush.shape[y][x])
                        {
                            int X = SpriteEditor1.brush.X - SpriteEditor1.brush.shape[y].length/2 + x;    // declaring ints locally for the sake of code readability only
                            int Y = SpriteEditor1.brush.Y - SpriteEditor1.brush.shape.length/2 + y;
                            g.setColor(SpriteEditor1.brush.color);
                     //       System.out.println("Setting color to " + SpriteEditor.brush.color.toString());
                            g.fillRect(X*pixelDistance, Y*pixelDistance, pixelDistance, pixelDistance);
                      //      System.out.println("Filling rect at " + X*pixelDistance + " , " + Y*pixelDistance);
                        }
                break;
            }
            case ERASE :
            {
                for (int y = 0; y < SpriteEditor1.brush.shape.length; ++y)
                    for (int x = 0; x < SpriteEditor1.brush.shape[y].length; ++x)
                        if (SpriteEditor1.brush.shape[y][x])
                        {
                            int X = SpriteEditor1.brush.X - SpriteEditor1.brush.shape[y].length/2 + x;
                            int Y = SpriteEditor1.brush.Y - SpriteEditor1.brush.shape.length/2 + y;
                            g.setColor(defaultColor);                                                       // the only difference from the above logic
                            g.fillRect(X*pixelDistance, Y*pixelDistance, pixelDistance, pixelDistance);
                        }
                break;
            }
            case SELECT :
            {
                if (SpriteEditor1.brush.hasP1)
                {
                    g.setColor(Color.GRAY);
                    if (SpriteEditor1.brush.hasP2)
                    {
                        //  draw the rectangle from p1 to p2
                        
                        g.drawRect(SpriteEditor1.brush.selectedX1 / 10, SpriteEditor1.brush.selectedY1 / 10, SpriteEditor1.brush.selectedX2 / 10, SpriteEditor1.brush.selectedY2 / 10);
                    }
                    else 
                    {
                        // draw the rectangle from p1 to current brush x and y
                        g.drawRect(SpriteEditor1.brush.selectedX1 / 10, SpriteEditor1.brush.selectedY1 / 10, SpriteEditor1.brush.X, SpriteEditor1.brush.Y);
                    }
                }
                break;
            }
            default : break;        // here is a situation in which this line is super handy
        }

    }
    
    class MouseClickListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
    //        System.out.println("MOUSE CLICKED at " + SpriteEditor.brush.X + " , " + SpriteEditor.brush.Y + "\n\n");
            
            PaintLogic(SpriteEditor1.brush.X, SpriteEditor1.brush.Y);
            
        }
        public void mousePressed(MouseEvent e)
        {}
        public void mouseReleased(MouseEvent e)
        {}
        public void mouseEntered(MouseEvent e)
        {
            SpriteEditor1.brush.X = e.getX()/pixelDistance;
            SpriteEditor1.brush.Y = e.getY()/pixelDistance;
      //      System.out.println("MOUSE ENTERED at " + SpriteEditor.brush.X + " , " + SpriteEditor.brush.Y + "\n\n");
            repaint();
        }
        public void mouseExited(MouseEvent e)
        {}
        public void mouseDragged(MouseEvent e)
        {}
        public void mouseMoved(MouseEvent e)
        {}   
    }
    class MouseMovementListener implements MouseMotionListener
    {
        public void mouseMoved(MouseEvent e)
        {
            SpriteEditor1.brush.X = e.getX()/pixelDistance;
            SpriteEditor1.brush.Y = e.getY()/pixelDistance;
       //     System.out.println("MOUSE MOVED to " + SpriteEditor.brush.X + " , " + SpriteEditor.brush.Y + "\n\n");
            repaint();
        }
        public void mouseDragged(MouseEvent e)
        {
            SpriteEditor1.brush.X = e.getX()/pixelDistance;
            SpriteEditor1.brush.Y = e.getY()/pixelDistance;
     //       System.out.println("MOUSE DRAGGED at " + SpriteEditor.brush.X + " , " + SpriteEditor.brush.Y + "\n\n");
            PaintLogic(SpriteEditor1.brush.X, SpriteEditor1.brush.Y);
        }
    }
    
    protected void PaintLogic(int X, int Y)         // invoked whenever the mouse is clicked or dragged on the canvas
                                                    // changes the colors of the virtual sprite in memory
    {
        if (X < 0 || X >= spriteLayer.width || Y < 0 || Y >= spriteLayer.height );
        else switch (SpriteEditor1.brush.brushMode)
        {
            case NORMAL :
            {
                for (int y = 0; y < SpriteEditor1.brush.shape.length; ++y)
                    for (int x = 0; x < SpriteEditor1.brush.shape[y].length; ++x)
                    {
                        if (SpriteEditor1.brush.shape[y][x])
                            spriteLayer.colors[Y - SpriteEditor1.brush.shape.length/2 + y][X - SpriteEditor1.brush.shape.length/2 + x] = SpriteEditor1.brush.color;
                    }
                break;
                          
            }
            case ERASE :
            {
                for (int y = 0; y < SpriteEditor1.brush.shape.length; ++y)
                    for (int x = 0; x < SpriteEditor1.brush.shape[y].length; ++x)
                    {
                        if (SpriteEditor1.brush.shape[y][x])
                            spriteLayer.colors[Y - SpriteEditor1.brush.shape.length/2 + y][X - SpriteEditor1.brush.shape.length/2 + x] = null;
                    }  
                break;
            }
            case FILL :
            {
                fillLogic(X, Y, spriteLayer.colors[Y][X]);
                break;
            }
            case SELECT :           // doesn't draw the selected rectangle, that happens in repaint; just sets the coordinates
            {
                if (!SpriteEditor1.brush.hasP1) 
                {
                    SpriteEditor1.brush.hasP1 = true;
                    SpriteEditor1.brush.selectedX1 = X;
                    SpriteEditor1.brush.selectedY1 = Y;
                }
                else                                         
                {
                    SpriteEditor1.brush.hasP2 = true;
                    SpriteEditor1.brush.selectedX2 = X;
                    SpriteEditor1.brush.selectedY2 = Y;
                }
                break;
                
                // so if you click another part of the canvas, or as you drag the mouse in select mode, a new P2 is selected. P1 and P2 are both wiped in other situations
            }
            case PASTE :
            {
                if (SpriteEditor1.brush.copied == null) break;                       // clicking on the canvas in paste mode with nothing copied does nothing
                for (int y = 0; y < SpriteEditor1.brush.copied.height; ++y)
                    for (int x = 0; x < SpriteEditor1.brush.copied.width; ++x)
                        spriteLayer.colors[SpriteEditor1.brush.Y + y][SpriteEditor1.brush.X + x] = SpriteEditor1.brush.copied.colors[y][x];
                break;
            }
        }
        repaint();
    }
    
    protected void fillLogic(int x, int y, Color c)
    {
        if (x < 0 | x >= spriteLayer.width | y < 0 | y >= spriteLayer.height) return;
        if (spriteLayer.colors[y][x] == c) spriteLayer.colors[y][x] = SpriteEditor1.brush.color;     // it is important to reset the color first
        else return;  
        
        fillLogic(x, y + 1, c);             // it goes up, left, right, down
        fillLogic(x + 1, y, c);
        fillLogic(x - 1, y, c);
        fillLogic(x, y - 1, c);
          
    }
}
