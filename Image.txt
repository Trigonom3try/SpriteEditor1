
package SpriteEditor1;

import java.awt.Color;

public class Image 
{
    protected int width;
    protected int height;
    
    protected Color [][] colors;
    
    protected Image(int w, int h)
    {
        width = w;
        height = h;
        colors = new Color[height][width];
        
        for (int y = 0; y < height; ++y)
            for (int x = 0; x < width; ++x)
                colors[y][x] = null;
    }
    protected Image(Image i)
    {
        width = i.width;    height = i.height;
        colors = new Color[height][width];
        for (int y = 0; y < height; ++y)
            for (int x = 0; x < width; ++x)
                colors[y][x] = i.colors[y][x];
    }
}