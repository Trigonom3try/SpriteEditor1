
package SpriteEditor1;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.util.Scanner;

public class ColorCreator extends JFrame
{
    protected JSlider R = new JSlider(SwingConstants.HORIZONTAL, 0, 255, 0);
    protected JSlider G = new JSlider(SwingConstants.HORIZONTAL, 0, 255, 0);
    protected JSlider B = new JSlider(SwingConstants.HORIZONTAL, 0, 255, 0);
    protected JLabel Rlabel = new JLabel("Red : " + R.getValue());
    protected JLabel Glabel = new JLabel("Green : " + G.getValue());
    protected JLabel Blabel = new JLabel("Blue : " + B.getValue());
    protected DemoPanel demoPanel = new DemoPanel();                    // the demo panel must be accessible by other classes/method within this module
                                                                        // the slider and button panels do not
    
    protected ColorCreator()
    {
        setSize(350, 300);
        setResizable(false);
        setTitle("Color Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // the color selector's an essential part of the app, might as well have this
        setLayout(new GridLayout(2, 1));
        add(new SliderPanel());
        add(demoPanel);
        setVisible(true);
    }
    class SliderPanel extends JPanel
    {
        protected SliderPanel()
        {
            setLayout(new GridLayout(6,2));
            R.addChangeListener(new ColorChanger());
            G.addChangeListener(new ColorChanger());
            B.addChangeListener(new ColorChanger());
            add(R);                         add(Rlabel);
            add(new ColorButton("<R"));     add(new ColorButton("R>"));
            add(G);                         add(Glabel);
            add(new ColorButton("<G"));     add(new ColorButton("G>"));
            add(B);                         add(Blabel);
            add(new ColorButton("<B"));     add(new ColorButton("B>"));
        }
    }
    class DemoPanel extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            g.setColor(SpriteEditor1.brush.color);
            g.fillRect(0,0,350,150);;
        }
    }
    
    
    class ColorChanger implements ChangeListener
    {
        public void stateChanged(ChangeEvent c)
        {
            SpriteEditor1.brush.color = new Color(R.getValue(), G.getValue(), B.getValue());
            if ( (JSlider)c.getSource() == R ) Rlabel.setText("Red : " + R.getValue());
            if ( (JSlider)c.getSource() == G ) Glabel.setText("Green : " + G.getValue());
            if ( (JSlider)c.getSource() == B ) Blabel.setText("Blue : " + B.getValue());
            demoPanel.repaint();
        }
    }
    
    class ColorButton extends JButton
    {
        protected ColorButton(String label)
        {
            super(label);
            addActionListener(new ColorButtonListener());
        }
    }
    
    class ColorButtonListener implements ActionListener     // pressing a color button also deselects any selected custom or default color from a palette
    {
        public void actionPerformed(ActionEvent e)    
        {
            String label = ((ColorButton)e.getSource()).getText();
            System.out.println(label + " button pressed");
            if (label.equals("<R"))
            {
                System.out.println("Current value of R : " + R.getValue());
                if (R.getValue() == 0) return;
                R.setValue(R.getValue() - 1);
                System.out.println("R set to " + R.getValue());
                Rlabel.setText("Red : " + R.getValue());
                demoPanel.repaint();
            }
            if (label.equals("R>"))
            {
                System.out.println("Current value of R : " + R.getValue());
                if (R.getValue() == 255) return;
                R.setValue(R.getValue() + 1);
                System.out.println("R set to " + R.getValue());
                Rlabel.setText("Red : " + R.getValue());
                demoPanel.repaint();
            }
            if (label.equals("<G"))
            {
                System.out.println("Current value of G : " + G.getValue());
                if (G.getValue() == 0) return;
                G.setValue(G.getValue() - 1);
                System.out.println("G set to " + G.getValue());
                Glabel.setText("Green : " + G.getValue());
                demoPanel.repaint();
            }
            if (label.equals("G>"))
            {
                System.out.println("Current value of G : " + G.getValue());
                if (G.getValue() == 255) return;
                G.setValue(G.getValue() + 1);
                System.out.println("G set to " + G.getValue());
                Glabel.setText("Green : " + G.getValue());
                demoPanel.repaint();
            }
            if (label.equals("<B"))
            {
                System.out.println("Current value of B : " + B.getValue());
                if (B.getValue() == 0) return;
                B.setValue(B.getValue() - 1);
                System.out.println("B set to " + B.getValue());
                Blabel.setText("Blue : " + B.getValue());
                demoPanel.repaint();
            }
            if (label.equals("B>"))
            {
                System.out.println("Current value of B : " + B.getValue());
                if (B.getValue() == 255) return;
                B.setValue(B.getValue() + 1);
                System.out.println("B set to " + B.getValue());
                Blabel.setText("Blue : " + B.getValue());
                demoPanel.repaint();
            }
        }
    }
}
