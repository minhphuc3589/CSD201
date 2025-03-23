/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.gamecenter;

import DataAccessObject.File.UserDAO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import lobby.references.components.ButtonSwing;
import lobby.references.data.UserAccount;

/**
 * CSD201 - Game Center
 *
 * @author PhucTDMCE190744
 */
public class GameCenter extends JFrame {
    private int WIDTH;
    private int HEIGHT;
    
    private JPanel panelHeader;
    private JPanel panelBody;
    private JPanel panelFooter;
    
    private UserAccount userAccount;

    /**
     * The constructor.
     */
    public GameCenter() {
        panelHeader = new JPanel();
        panelBody = new JPanel();
        panelFooter = new JPanel();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int) screenSize.getWidth();
        HEIGHT = (int) screenSize.getHeight();
        
        this.setResizable(false);
        this.setUndecorated(true);

        userAccount = UserDAO.userAccount;
        
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
//        this.addWindowStateListener(e -> {
//            if (e.getNewState() == JFrame.ICONIFIED) {
//                System.out.println("Frame Minimized");
//            } else if (e.getNewState() == JFrame.NORMAL) {
//                System.out.println("Frame Restored");
//            }
//        });
        
        this.getContentPane().add(panelHeader);
    }

    /**
     * Closes the Game Center.
     */
    @Override
    public void dispose() {
        JFrame newFrame = new JFrame();
        JOptionPane alert = new JOptionPane();
        
        int choice = alert.showConfirmDialog(newFrame, "Do you want to quit?", "GROUP 3 - CSD201", JOptionPane.YES_NO_OPTION);
        
        if (choice == alert.YES_OPTION) {
            super.dispose();
            newFrame.dispose();
        }
    }
    
    /**
     * Initializes all of components in Header.
     */
    private void panelHeaderHandler() {
        panelHeader.setSize(WIDTH, HEIGHT / 5);
        panelHeader.setVisible(true);
        panelHeader.setLayout(null);
        
        ButtonSwing btnHIDE = new ButtonSwing("src/pictures/minimize-button.png", 25, 25);
        ButtonSwing btnQUIT = new ButtonSwing("src/pictures/exit-button.png", 25, 25);

        btnHIDE.setBounds(panelHeader.getWidth() - 80, 25, 25, 25);
        btnQUIT.setBounds(panelHeader.getWidth() - 50, 25, 25, 25);
        
        btnHIDE.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setIconifiedGameCenter();   
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        
        btnQUIT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                dispose();
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        panelHeader.add(btnHIDE);
        panelHeader.add(btnQUIT);
        
//        panelHeader.revalidate();
        panelHeader.repaint();

    }
    
    public void panelBodyHandler() {
        panelBody.setSize(WIDTH, (HEIGHT * 3) / 5);
        panelBody.setLocation(0, HEIGHT / 5);
    }
    
    public void panelFooterHandler() {
        panelFooter.setSize(WIDTH, HEIGHT / 5);
        panelFooter.setLocation(0, (HEIGHT * 4) / 5);
    }
    
    /**
     * Initializes the Game Center.
     */
    public void init() {
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        
        // Initializes all of components in Header
        panelHeaderHandler();
        
        panelBodyHandler();
        
        panelFooterHandler();
        
    }
    
    /**
     * Sets state of Game Center to be icon state.
     */
    public void setIconifiedGameCenter() {

        // Removes the focus after being clicked HIDE button to avoid re-set state of Game Center.
        this.getContentPane().requestFocusInWindow();

        // Sets state of Game Center to be icon state
        this.setState(JFrame.ICONIFIED);
    }

    /**
     * Starts the Game Center.
     */
    public void start() {
        init();
    }
}
