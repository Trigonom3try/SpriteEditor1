package SpriteEditor1;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class SpriteEditor1 
{
    protected static Brush brush;          
    protected static Canvas canvas;
    protected static Sprite sprite;
    protected static EditorWindow editorWindow;
    protected static ColorCreator colorCreator;
    protected static LayerManager layerManager;
    protected static BrushCustomizer brushCustomizer;
    
    public static void main (String [] args)
    {
        new InitialSelectionWindow();
    }
}