
package SpriteEditor1;

import java.util.Scanner;
import java.io.*;
import java.awt.Color;
import java.util.ArrayList;

public class Sprite 
{
    protected String name = "Untitled";
    protected int width;
    protected int height;
    protected ArrayList<Image> layers = new ArrayList<Image>();      // these will be 2D animations 
    
    public Sprite(int w, int h)
    {
        width = w;  height = h;
        layers.add(new Image(w, h));
    }
    
    public Sprite(File f)    throws FileNotFoundException       // changed from protected to public for testing reasons
    {
        Scanner reader = new Scanner(f);
        width = reader.nextInt();
        height = reader.nextInt();
        boolean hasNextLayer;
        do {                            // this logic, declaring a boolean and running a do-while loop, saves a text boolean in the file
            Image i = new Image(width,height);
            
            for (int h = 0; h < height; ++h)
                for (int w = 0; w < width; ++w)
                {
                    boolean b = reader.nextBoolean();
                    if (!b) 
                    {
                        i.colors[h][w] = null;  continue;       // continue from inner loop, outer loop, or while loop?
                    }
                    int red = reader.nextInt();
                    int green = reader.nextInt();
                    int blue = reader.nextInt();
                    i.colors[h][w] = new Color(red, green, blue);
                }
            layers.add(i);
            hasNextLayer = reader.nextBoolean();
        }   while (hasNextLayer);
    }
}
