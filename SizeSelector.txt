
package SpriteEditor1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SizeSelector extends JFrame
{
    protected int width;        // these are the width and height of the sprite to be drawn
    protected int height;
    protected CheckBoxPanel widthBoxPanel = new CheckBoxPanel(true);
    protected CheckBoxPanel heightBoxPanel = new CheckBoxPanel(false);
    protected JButton accept = new JButton("Accept");
    
    protected SizeSelector()
    {
        setSize(250,500);
        setResizable(false);
        setLayout(new GridLayout(1, 3));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(widthBoxPanel);
        add(heightBoxPanel);
        accept.addActionListener(new ButtonListener());
        add(accept);
        setVisible(true);
    }
    
    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (width == 0 | height == 0)       // "short-circuiting" OR
            {
                // don't actually need anything to happen when you hit the button in this case
            }
            else
            {
                SpriteEditor1.sprite = new Sprite(width, height);
                SpriteEditor1.brush = new Brush();   
                SpriteEditor1.canvas = new Canvas();     // dependent on sprite AND brush
                SpriteEditor1.editorWindow = new EditorWindow();     // dependent on canvas
                SpriteEditor1.brushCustomizer = new BrushCustomizer();
                SpriteEditor1.colorCreator = new ColorCreator();
                SpriteEditor1.layerManager = new LayerManager();     // dependent on sprite
                dispose();
            }
        }
    }
        
    class CheckBoxPanel extends JPanel
    {
        protected boolean widthHeight;
        protected CheckBox [] checkBoxes;
        protected CheckBoxPanel(boolean wh)
        {
            if (wh)
            {
                checkBoxes = new CheckBox[10];
                setLayout(new GridLayout(11,1));
                add(new JLabel("Width : "));
            }
            else
            {
                checkBoxes = new CheckBox[6];
                setLayout(new GridLayout(7,1));
                add(new JLabel("Height : "));
            }
            
            for (int i = 0; i < checkBoxes.length; ++i)
            {
                checkBoxes[i] = new CheckBox("  " + (i+1) + "0", wh);
                add(checkBoxes[i]);
            }
        }
    }
    class CheckBox extends JCheckBox
    {
        boolean widthHeight;
        protected CheckBox(String label, boolean wh)
        {
            widthHeight = wh;
            setText(label);
            addActionListener(new CheckBoxListener());
        }
    }
    class CheckBoxListener extends AbstractAction
    {
        public void actionPerformed(ActionEvent e)
        {
            CheckBox cb = (CheckBox)e.getSource();
            if (cb.widthHeight)
            {
                for (int i = 0; i < widthBoxPanel.checkBoxes.length; ++i)
                {
                    if (widthBoxPanel.checkBoxes[i] != cb)
                    {
                        widthBoxPanel.checkBoxes[i].setSelected(false);
                    }
                    else if (width == (i+1)*10)     // this occurs when the checkbox has already been checked, and you click it again to deselect it
                    {
                        width = 0;  cb.setSelected(false);  
                    }      // 
                    else width = (i+1)*10;
                }
            }
            else
            {
                for (int i = 0; i < heightBoxPanel.checkBoxes.length; ++i)
                {
                    if (heightBoxPanel.checkBoxes[i] != cb)
                    {
                        heightBoxPanel.checkBoxes[i].setSelected(false);
                    }
                    else if (height == (i+1)*10)      
                    {
                        height = 0;  cb.setSelected(false);  
                    }      
                    else height = (i+1)*10;
                }
            }
        }
    }
}