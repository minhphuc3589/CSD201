/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.gamecenter;

import DataAccessObject.File.UserDAO;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import lobby.gamecenter.games.Initialize;
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
    
    private TreeMap<String, ButtonSwing> gameIcons;
    private TreeMap<String, Initialize> games;
    
    private JPanel panelUIGamePlay;
    private JPanel panelHeader;
    private JPanel panelBody;
    private JPanel panelFooter;
    
    private ButtonSwing btnBACK;
    private ButtonSwing btnHIDE;
    private ButtonSwing btnQUIT;
    
    private JLabel labelTitleHeader;
    private JLabel labelFirstLicense;
    private JLabel labelSecondLicense;
    
    private UserAccount userAccount;
    
    // Sets current key of game to start the game when clicked game icon
    private String currentKeyGame;
    private Initialize currentValueGame;
    
    private int sizeOfGameIcon;

    /**
     * The constructor.
     */
    public GameCenter() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        gameIcons = new TreeMap<>();
        games = new TreeMap<>();
        
        panelUIGamePlay = new JPanel();
        panelHeader = new JPanel();
        panelBody = new JPanel();
        panelFooter = new JPanel();
        
        labelFirstLicense = new JLabel("License belongs to CSD201");
        labelSecondLicense = new JLabel("Lecturer: Le Thi Phuong Dung");
        
        btnBACK = new ButtonSwing("src/pictures/back-button.png", 25, 25);
        btnHIDE = new ButtonSwing("src/pictures/minimize-button.png", 25, 25);
        btnQUIT = new ButtonSwing("src/pictures/exit-button.png", 25, 25);
        
        WIDTH = (int) screenSize.getWidth();
        HEIGHT = (int) screenSize.getHeight();
        
        sizeOfGameIcon = WIDTH / 25 < 50 ? 50 : WIDTH / 25;
        
        currentValueGame = null;
        
        this.setResizable(false);
        this.setUndecorated(true);

        userAccount = UserDAO.userAccount;
        
        addGame("TicTacToe", new lobby.gamecenter.games.TicTacToe.Game(), "");
        
        btnBACK.setSize(25 ,25);
        btnHIDE.setSize(25, 25);
        btnQUIT.setSize(25, 25);
        
        btnHIDE.setLocation(WIDTH - 80, 25);
        btnQUIT.setLocation(WIDTH - 50, 25);
        
        // When clicked Back button, it will rollback the main Game Center
        btnBACK.addActionListener(e -> {
            panelUIGamePlay.remove(panelUIGamePlay.getComponent(3));
            
            // Sets focus on the main Game Center after rollbacked
            this.requestFocus();
            this.requestFocusInWindow();

            panelUIGamePlay.setVisible(false);
            panelHeaderHandler();
            panelBody.setVisible(true);
            panelFooter.setVisible(true);
        });
        
        btnHIDE.addActionListener(e -> {
            setIconifiedGameCenter();
        });

        btnQUIT.addActionListener(e -> {
            dispose();
        });
        
//        this.requestFocus();
        this.requestFocusInWindow();
        
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
                
                System.out.println("abc");
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        this.getContentPane().add(panelUIGamePlay);
        this.getContentPane().add(panelHeader);
        this.getContentPane().add(panelBody);
        this.getContentPane().add(panelFooter);        
    }
    
    /**
     * Adds a new game into games list.
     */
    private void addGame(String nameOfGame, Initialize game, String imgURL) {
        if (nameOfGame.isEmpty() || nameOfGame == null) {
            System.out.println("Name of game cannot be null or empty");
            return;
        }
        
        imgURL = imgURL.isEmpty() ? "src/pictures/unknown-game.jpg" : imgURL;
        
        gameIcons.put(nameOfGame, new ButtonSwing(imgURL, sizeOfGameIcon, sizeOfGameIcon));
        games.put(nameOfGame, game);
    }
    
    /**
     * Configures each game in games list.
     */
    private void configureGames() {
        int x_coordinate = 0;
        int y_coordinate = 0;
        
        for (Map.Entry<String, ButtonSwing> entry : gameIcons.entrySet()) {
            if (gameIcons.isEmpty()) {
                System.out.println("Don't have any game in games list");
                
                return;
            }
            
            currentKeyGame = entry.getKey();
            currentValueGame = games.get(currentKeyGame);
            
            ButtonSwing game = gameIcons.get(currentKeyGame);

            if (100 + sizeOfGameIcon * x_coordinate > WIDTH - 100) {
                y_coordinate++;
            }

            game.setBounds(100 + sizeOfGameIcon * x_coordinate, 100 + sizeOfGameIcon * y_coordinate, sizeOfGameIcon, sizeOfGameIcon);

            game.addActionListener(e -> {
                currentValueGame.start((WIDTH * 6) / 8, HEIGHT);
                
                currentValueGame.getFrame().setMaximumSize(new Dimension((WIDTH * 6) / 8, HEIGHT));
                currentValueGame.getFrame().setLocation(currentValueGame.getFrame().getWidth(), 0);
                
                panelUIGamePlay.add(btnHIDE);
                panelUIGamePlay.add(btnQUIT);
                panelUIGamePlay.add(currentValueGame.getFrame());
                panelUIGamePlay.setVisible(true);
                
                panelHeader.setVisible(false);
                panelBody.setVisible(false);
                panelFooter.setVisible(false);
                
                currentValueGame.getFrame().repaint();
                
            });

            x_coordinate++;
            
            panelBody.add(game);
        }
            
    }
    
    /**
     * Initializes all of components in UI Game Play.
     */
    private void panelUIGamePlayHandler() {
        panelUIGamePlay.setSize(WIDTH, HEIGHT);
        panelUIGamePlay.setVisible(false);
        panelUIGamePlay.setLayout(null);
        
        btnBACK.setLocation(25, 25);
        
        panelUIGamePlay.add(btnBACK);
        
        panelUIGamePlay.repaint();
    }
    
    /**
     * Initializes all of components in Header.
     */
    private void panelHeaderHandler() {
        panelHeader.setSize(WIDTH, HEIGHT / 8);
        panelHeader.setLocation(0, 0);
        panelHeader.setVisible(true);
        panelHeader.setLayout(null);
        
        panelHeader.add(btnHIDE);
        panelHeader.add(btnQUIT);
        
        panelHeader.repaint();
    }
    
    /**
     * Initializes all of components in Body.
     */
    public void panelBodyHandler() {
        panelBody.setSize(WIDTH, (HEIGHT * 6) / 8);
        panelBody.setLocation(0, HEIGHT / 8);
        panelBody.setVisible(true);
        panelBody.setLayout(null);
        
        configureGames();
        
        panelBody.repaint();
    }
    
    /**
     * Initializes all of components in Footer.
     */
    public void panelFooterHandler() {
        panelFooter.setSize(WIDTH, HEIGHT / 5);
        panelFooter.setLocation(0, (HEIGHT * 4) / 5);
        panelFooter.setVisible(true);
        panelFooter.setLayout(null);
        
        Font labelLicenseFont = new Font("Times New Roman", Font.BOLD, 20);
        
        labelFirstLicense.setSize(1000, 100);
        labelFirstLicense.setLocation(100, 100);
        labelFirstLicense.setFont(labelLicenseFont);
        
        labelSecondLicense.setSize(1000, 100);
        labelSecondLicense.setLocation(100, 150);
        labelSecondLicense.setFont(labelLicenseFont);
        
        panelFooter.add(labelFirstLicense);
        panelFooter.add(labelSecondLicense);
        
        panelFooter.repaint();
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
        
        // Initializes all of components in UI Game Play.
        panelUIGamePlayHandler();
        
        // Initializes all of components in Header.
        panelHeaderHandler();
        
        // Initializes all of components in Body.
        panelBodyHandler();
        
        // Initializes all of components in Footer.
        panelFooterHandler();
        
    }
    
    /**
     * Sets state of Game Center to be icon state.
     */
    public void setIconifiedGameCenter() {

        // Removes the focus after being clicked HIDE button to avoid re-set state of Game Center.
        this.getContentPane().requestFocusInWindow();

        // Sets state of Game Center to be icon state.
        this.setState(JFrame.ICONIFIED);
    }

    /**
     * Closes the Game Center.
     */
    @Override
    public void dispose() {
        JFrame newFrame = new JFrame();
        JOptionPane alert = new JOptionPane();

        // Prevents alert initialized more 1 times when clicked or pressed Exit button
        this.requestFocusInWindow();

        int choice = alert.showConfirmDialog(newFrame, "Do you want to quit?", "   GROUP 3 - CSD201", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            super.dispose();
            newFrame.dispose();
        } else {
            newFrame.dispose();
            return;
        }
    }
    
    /**
     * Starts the Game Center.
     */
    public void start() {
        init();
    }
}
