
package SpriteEditor1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EditorWindow extends JFrame
{
    private final int lowerMargin = 60;     // we need a few below the canvas to contain the SavePanel
    private final int rightMargin = 20;
    protected int windowWidth = (SpriteEditor1.sprite.width * SpriteEditor1.canvas.pixelDistance) + rightMargin;
    protected int windowHeight = (SpriteEditor1.sprite.height * SpriteEditor1.canvas.pixelDistance) + lowerMargin;
    
    
    protected EditorWindow()
    {
        setSize( windowWidth, windowHeight );
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(SpriteEditor1.sprite.name + ".s1          Layer: " + SpriteEditor1.canvas.layerIndex + "/" + SpriteEditor1.sprite.layers.size());
        add(SpriteEditor1.canvas, BorderLayout.CENTER);
        add(new SavePanel(), BorderLayout.SOUTH);       // the SavePanel object doesn't need to have a global reference in code
        setVisible(true);
    }
    
    class SavePanel extends JPanel
    {
        private JButton save = new JButton("Save");
        private JButton load = new JButton("Load");
        private JButton newSprite = new JButton("New");
    
        protected SavePanel()
        {
            setLayout(new GridLayout(1,3));
            save.addActionListener(new SaveButtonListener());
            load.addActionListener(new SaveButtonListener());
            newSprite.addActionListener(new SaveButtonListener());
            add(save);      add(load);      add(newSprite);
        }
    
    
        class SaveButtonListener implements ActionListener 
        {
            public void actionPerformed(ActionEvent e)  
            {
                String label = ((JButton)e.getSource()).getText();
        
                if (label.equals("Save")) 
                    try { saveSprite(); }
                    catch (FileNotFoundException fnf) {}
                if (label.equals("Load")) 
                    try { loadSprite(); }
                    catch (FileNotFoundException fnf) {}
                if (label.equals("New"))  newSprite();    
            }  
        }
    
    
        protected void saveSprite()  throws FileNotFoundException
        {
            JOptionPane savePane = new JOptionPane();
            // code for cancel button
            String fileName = savePane.showInputDialog("Save as : ");
            fileName += ".s1";
            PrintWriter pw = new PrintWriter(fileName);         // PrintWriter automatically creates a file of the specified name if it doesn't exist *
                                                                // and truncates it to 0 size if it exists. This version of SE assumes that the user is sure of overwrite **
            pw.append(SpriteEditor1.sprite.width + " ");
            pw.append(SpriteEditor1.sprite.height + " ");
        
            for (int i = 0; i < SpriteEditor1.sprite.layers.size(); ++i)
            {
                Image m = SpriteEditor1.sprite.layers.get(i);
                for (int y = 0; y < m.height; ++y)
                    for (int x = 0; x < m.width; ++x)
                    {
                        if (m.colors[y][x] == null) 
                        {
                            pw.append("false "); continue;
                        }
                        Color c = m.colors[y][x];
                        pw.append("true ");                // yep, we need to let it know if there's a color there
                        pw.append(c.getRed() + " ");
                        pw.append(c.getGreen() + " ");
                        pw.append(c.getBlue() + " ");
                    }
                if (i+1 == SpriteEditor1.sprite.layers.size()) pw.append("false ");
                else pw.append("true ");
            }
            pw.close();         //  Don't forget this line
        }
    
    
        protected void loadSprite()  throws FileNotFoundException
        {
            JOptionPane loadPane = new JOptionPane();
            String fileName = loadPane.showInputDialog("Load file : ");
            File f = new File(fileName);
            JOptionPane surePane = new JOptionPane();
            if (surePane.showConfirmDialog(null, "Are you sure? Any unsaved progress will be lost") == 0)         // yes = 0, no = 1, cancel = 2
            {
                EditorWindow tempEW = SpriteEditor1.editorWindow;    // need these lines instead of invoking dispose
                LayerManager tempLM = SpriteEditor1.layerManager;
                    // don't need to create new brush, color selector, or color creator. They will all just carry over to the new sprite
                SpriteEditor1.sprite = new Sprite(f);
                SpriteEditor1.canvas = new Canvas();
                SpriteEditor1.editorWindow = new EditorWindow();
                SpriteEditor1.layerManager = new LayerManager();
                
                tempEW.dispose();   tempLM.dispose();   
            }
        }
        protected void newSprite()
        {
            JOptionPane surePane = new JOptionPane();
            if (surePane.showConfirmDialog(null, "Are you sure? Any unsaved progress will be lost") == 0)         // yes = 0, no = 1, cancel = 2
            {
                SpriteEditor1.editorWindow.dispose();
                SpriteEditor1.brushCustomizer.dispose();
                SpriteEditor1.colorCreator.dispose();
                SpriteEditor1.layerManager.dispose();
                new SizeSelector();
            }
        }
    }
}
