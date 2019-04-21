
package SpriteEditor1;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.io.*;

public class InitialSelectionWindow extends JFrame
{
    protected JButton newSprite = new JButton("New");
    protected JButton loadSprite = new JButton("Load");
    
    protected InitialSelectionWindow()
    {
        setSize(200,200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SpriteEditor2D v0.1 by T3y_Soft");
        setLayout(new GridLayout(3, 1));
        add(new JLabel("Please make a selection : "));
        newSprite.addActionListener(new ButtonListener());
        loadSprite.addActionListener(new ButtonListener());
        add(newSprite);
        add(loadSprite);
        setVisible(true);
        
    }
    
    class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e)
        {
            String s = ((JButton)e.getSource()).getText();
            if (s.equals("New"))
            {
                new SizeSelector();
            }
            else
            {
                JOptionPane loadPane = new JOptionPane();
                // code here for what happens when you press either the OK or Cancel buttons on a JOptionPane
                String fileName = loadPane.showInputDialog("Load file : ");
                File f = new File(fileName);
                
                try{ SpriteEditor1.sprite = new Sprite(f); } catch (FileNotFoundException fnf)   {System.out.println("FnF exception caught"); }
                
                SpriteEditor1.canvas = new Canvas();
                SpriteEditor1.brush = new Brush();
                SpriteEditor1.editorWindow = new EditorWindow();
                SpriteEditor1.brushCustomizer = new BrushCustomizer();
                SpriteEditor1.colorCreator = new ColorCreator();
                SpriteEditor1.layerManager = new LayerManager();
            }
            dispose();
        }
    }
}