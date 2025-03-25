/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.gamecenter;

import DataAccessObject.File.UserDAO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import lobby.gamecenter.games.Initialize;
import lobby.references.components.ButtonSwing;
import lobby.references.data.UserAccount;

/**
 * CSD201 - Game Center
 *
 * @author PhucTDMCE190744
 */
public class GameCenter extends JFrame {
    private Dimension screenSize;
    
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
    
    private ActionListener rollback;
    
    private UserAccount userAccount;
    
    // Sets current key of game to start the game when clicked game icon
    private String currentKeyGame;
    private JFrame currentGamePlay;

    private int x_coordinateGame;
    private int y_coordnateGame;
    
    private int sizeOfGameIcon;
    
    private int indexOfFrame;

    /**
     * The constructor.
     */
    public GameCenter() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        WIDTH = (int) screenSize.getWidth();
        HEIGHT = (int) screenSize.getHeight();
        
        indexOfFrame = -1;
        
        gameIcons = new TreeMap<>();
        games = new TreeMap<>();
        
        panelUIGamePlay = new JPanel();
        panelHeader = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {           
                super.paintComponent(g); // Call parent class's method to ensure proper rendering
                Graphics2D g2 = (Graphics2D) g;  // Use Graphics2D for better control

                g2.setStroke(new BasicStroke(2)); // Set line thickness to 5 pixels
                g2.drawLine(0, (int) screenSize.getHeight() / 8, (int) screenSize.getWidth(), (int) screenSize.getHeight() / 8);  // Draw a bigger line
            }
        };
        panelBody = new JPanel();
        panelFooter = new JPanel() {
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Call parent class's method to ensure proper rendering
                Graphics2D g2 = (Graphics2D) g;  // Use Graphics2D for better control

                g2.setStroke(new BasicStroke(2)); // Set line thickness to 5 pixels
                g2.drawLine(0, 0, (int) screenSize.getWidth(), 0);  // Draw a bigger line
            }
            
        };
        
        rollback = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (indexOfFrame != -1) {
                    panelUIGamePlay.remove(panelUIGamePlay.getComponent(indexOfFrame));
                }
                

                // Sets focus on the main Game Center after rollbacked
                setFocusInWindow();

                panelUIGamePlay.setVisible(false);
                panelHeaderHandler();
                panelBody.setVisible(true);
                panelFooter.setVisible(true);
            }
        };
        
        btnBACK = new ButtonSwing("src/pictures/back-button.png", 25, 25);
        btnHIDE = new ButtonSwing("src/pictures/minimize-button.png", 25, 25);
        btnQUIT = new ButtonSwing("src/pictures/exit-button.png", 25, 25);
        
        sizeOfGameIcon = WIDTH / 25 < 50 ? 50 : WIDTH / 25;
        
        this.setResizable(false);
        this.setUndecorated(true);

        userAccount = UserDAO.userAccount;
        
        // Path: src/pictures/<image_name>
        addGame("TicTacToe", new lobby.gamecenter.games.TicTacToe.Game(), "src/pictures/tictactoe-icon.png");
        addGame("Minesweeper", new lobby.gamecenter.games.Minesweeper.MainMenu(), "src/pictures/minesweeper-icon.png");
        addGame("Chess", new lobby.gamecenter.games.Chess.chess.ChessGame(), "src/pictures/chess-icon.png");
        
        btnBACK.setSize(25 ,25);
        btnHIDE.setSize(25, 25);
        btnQUIT.setSize(25, 25);
        
        btnHIDE.setLocation(WIDTH - 80, 25);
        btnQUIT.setLocation(WIDTH - 50, 25);
        
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
    
    public void setFocusInWindow() {
        this.requestFocus();
        this.requestFocusInWindow();
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
            Initialize gamePlay = games.get(currentKeyGame);
            
            ButtonSwing gameIcon = gameIcons.get(currentKeyGame);

            if (100 + sizeOfGameIcon * x_coordinate > WIDTH - 100) {
                y_coordinate++;
            }
            
            gameIcon.setSize(sizeOfGameIcon, sizeOfGameIcon);
            gameIcon.setLocation(100 + (sizeOfGameIcon + 100)* x_coordinate, 100 + (sizeOfGameIcon + 100)* y_coordinate);
            
            this.x_coordinateGame = x_coordinate;
            this.y_coordnateGame = y_coordinate;

            gameIcon.addActionListener(e -> {
                
                // Types:
                // 0: JFrame
                // 1: JPanel
                switch (gamePlay.getTypeGame()) {
                    case 0:
                        Timer timer = new Timer(100, event -> {
                            gamePlay.getJFrame().setVisible(true);
                            gamePlay.start((WIDTH * 6) / 8, HEIGHT);
                        });
                        
                        timer.setRepeats(false);
                        timer.start();
                        
                        Timer count = new Timer(100, event -> {
                            if (!gamePlay.getStatus()) {
                                timer.stop();
                                ((Timer) event.getSource()).stop();
                                btnBACK.addActionListener(rollback);
                                btnBACK.doClick();
                            }
                        });
                        
                        count.start();
                        
                        indexOfFrame = -1;
                        currentGamePlay = gamePlay.getJFrame();

                        btnBACK.removeActionListener(rollback);
                        
                        panelUIGamePlay.add(btnHIDE);
                        panelUIGamePlay.add(btnQUIT);
                        panelUIGamePlay.setVisible(true);

                        panelHeader.setVisible(false);
                        panelBody.setVisible(false);
                        panelFooter.setVisible(false);

                        gamePlay.getJFrame().repaint();
                        
                        break;
                        
                    case 1:
                        gamePlay.start((WIDTH * 6) / 8, HEIGHT);

                        gamePlay.getJPanel().setMaximumSize(new Dimension((WIDTH * 6) / 8, HEIGHT));
                        gamePlay.getJPanel().setLocation(gamePlay.getJPanel().getWidth(), 0);

                        indexOfFrame = 3;
                        
                        panelUIGamePlay.add(btnHIDE);
                        panelUIGamePlay.add(btnQUIT);
                        panelUIGamePlay.add(gamePlay.getJPanel());
                        panelUIGamePlay.setVisible(true);

                        panelHeader.setVisible(false);
                        panelBody.setVisible(false);
                        panelFooter.setVisible(false);
                        
                        gamePlay.getJPanel().repaint();
                        
                        break;
                }
                
                
            });

            x_coordinate++;
            
            panelBody.add(gameIcon);
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
        
        // When clicked Back button, it will rollback the main Game Center
        btnBACK.addActionListener(rollback);
        
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
        
        JLabel labelTitle;
        
        Font fontTitle = new Font("Times New Roman", Font.BOLD, 40);
        
        labelTitle = new JLabel("GROUP 3 - CSD201");
        
        labelTitle.setSize(labelTitle.getText().length() * fontTitle.getSize(), 50);
        labelTitle.setLocation((int) (WIDTH / 2 - labelTitle.getSize().getWidth() / 3), (int) (panelHeader.getSize().getHeight() - fontTitle.getSize()) / 2);
        labelTitle.setFont(fontTitle);
        
        panelHeader.add(labelTitle);
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
        panelBody.setBackground(Color.lightGray);
        panelBody.setVisible(true);
        panelBody.setLayout(null);
        
        configureGames();
        
        panelBody.repaint();
    }
    
    /**
     * Initializes all of components in Footer.
     */
    public void panelFooterHandler() {
        panelFooter.setSize(WIDTH, HEIGHT / 8);
        panelFooter.setLocation(0, (HEIGHT * 7) / 8);
        panelFooter.setVisible(true);
        panelFooter.setLayout(null);
        
        JLabel labelFirstLicense;
        JLabel labelSecondLicense;
        
        Font fontLicense = new Font("Times New Roman", Font.BOLD, 32);
        
        labelFirstLicense = new JLabel("LICENSE BELONGS TO CSD201");
        labelSecondLicense = new JLabel("LECTURER: LE THI PHUONG DUNG");
        
        labelFirstLicense.setSize(labelFirstLicense.getText().length() * fontLicense.getSize(), 50);
        labelFirstLicense.setLocation((int)(WIDTH / 2 - labelFirstLicense.getSize().getWidth() / 3), (int) ((panelFooter.getSize().getHeight() - fontLicense.getSize()) / 4));
        labelFirstLicense.setFont(fontLicense);
        
        labelSecondLicense.setSize(labelSecondLicense.getText().length() * fontLicense.getSize(), 50);
        labelSecondLicense.setLocation((int)(WIDTH / 2 - labelSecondLicense .getSize().getWidth() / 3), labelFirstLicense.getY() + fontLicense.getSize() + 10);
        labelSecondLicense.setFont(fontLicense);
        
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
            if (currentGamePlay != null) currentGamePlay.dispose();
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
