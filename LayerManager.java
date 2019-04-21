
package SpriteEditor1;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;

public class LayerManager extends JFrame 
{
    protected JButton newLayer = new JButton("New Layer");
    protected JButton previous = new JButton("<");
    protected JButton next = new JButton(">");
    protected JButton delete = new JButton("Delete Layer");
    
    protected LayerManager()
    {
        if (SpriteEditor1.sprite.layers.size() == 1)
        {
            setSize(125, 75); 
            newLayer.addActionListener(new ButtonListener());
            add(newLayer);
        }
        else
        {
            setSize(300, 75);
            /// int X = 
     //       add(new PreviewPanel());
            add(new ButtonPanel());     // ;) So that the size of the PreviewPanel gets respected. Button size doesn't matter
                                        // BorderLayout just works like this. Put the buttons at the bottom and the previews center
        }
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    class ButtonPanel extends JPanel
    {
        protected ButtonPanel()
        {
            setLayout(new GridLayout(1,4));
            newLayer.addActionListener(new ButtonListener());
            previous.addActionListener(new ButtonListener());
            next.addActionListener(new ButtonListener());
            delete.addActionListener(new ButtonListener());
            add(newLayer); 
            add(previous);
            add(next);
            add(delete);
        }
    }
    class PreviewPanel extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            for (int i = 0; i < SpriteEditor1.sprite.layers.size(); ++i)
            {
                // yeah, this is gonna be tough ...
            }
        }
    }
    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String label = ( (JButton)e.getSource() ).getText();
            if (label.equals("New Layer"))
            {
                SpriteEditor1.sprite.layers.add(new Image(SpriteEditor1.sprite.width, SpriteEditor1.sprite.height));
                SpriteEditor1.canvas.layerIndex ++;
                SpriteEditor1.canvas.spriteLayer = SpriteEditor1.sprite.layers.get(SpriteEditor1.canvas.layerIndex);
                SpriteEditor1.canvas.repaint();
                
                LayerManager temp = SpriteEditor1.layerManager;
                SpriteEditor1.layerManager = new LayerManager();
                temp.dispose();
                
            }
            if (label.equals("Delete Layer"))
            {
                SpriteEditor1.sprite.layers.remove(SpriteEditor1.canvas.spriteLayer);
                SpriteEditor1.canvas.layerIndex --;
                if (SpriteEditor1.canvas.layerIndex < 0) SpriteEditor1.canvas.layerIndex = 0;
                SpriteEditor1.canvas.spriteLayer = SpriteEditor1.sprite.layers.get(SpriteEditor1.canvas.layerIndex);
                SpriteEditor1.canvas.repaint();
                
                LayerManager temp = SpriteEditor1.layerManager;
                SpriteEditor1.layerManager = new LayerManager();
                temp.dispose();
            }
            if (label.equals("<"))
            {
                SpriteEditor1.canvas.layerIndex --;
                if (SpriteEditor1.canvas.layerIndex < 0) SpriteEditor1.canvas.layerIndex++;
                else 
                {
                    SpriteEditor1.canvas.spriteLayer = SpriteEditor1.sprite.layers.get(SpriteEditor1.canvas.layerIndex);
                    SpriteEditor1.canvas.repaint();
                }
            }
            if (label.equals(">"))
            {
                SpriteEditor1.canvas.layerIndex ++;
                if (SpriteEditor1.canvas.layerIndex >= SpriteEditor1.sprite.layers.size()) SpriteEditor1.canvas.layerIndex--;
                else 
                {
                    SpriteEditor1.canvas.spriteLayer = SpriteEditor1.sprite.layers.get(SpriteEditor1.canvas.layerIndex);
                    SpriteEditor1.canvas.repaint();
                }
            }
        }
    }
}