/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.references.components;

import java.awt.Cursor;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * CSD201 - Java Swing Button
 *
 * @author PhucTDMCE190744
 */
public class ButtonSwing extends JButton {
    private int WIDTH;
    private int HEIGHT;
    private ImageIcon img;
    
    
    /**
     * The constructor.
     * 
     * @param imgURL The URL string of image
     * @param width The width of image
     * @param height The height of image
     */
    public ButtonSwing(String imgURL, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        
        // Loads Image
        ImageIcon imgOrigin = new ImageIcon(imgURL);
        
        Image imgResizable = imgOrigin.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        img = new ImageIcon(imgResizable);
        
        this.setIcon(img);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }
}
