/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.gamecenter.games.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import lobby.gamecenter.games.Initialize;

public class MainMenu extends JFrame implements Initialize {
    
    private boolean playing;

    private static final String LEADERBOARD_FILE = "leaderboard.txt";
    private static final Color[] NUMBER_COLORS = {
        Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA, Color.ORANGE, Color.CYAN, Color.PINK, Color.BLACK
    };

    // Main menu constructor
    public MainMenu() {
        playing = true;
    }

    // Displays the leaderboard window
    private void showLeaderboard() {
        java.util.List<String> leaderboardList = new ArrayList<>(); // Create a list to store leaderboard entries

        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Ignore empty lines
                    leaderboardList.add(line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No scores available."); // Show message if no scores exist
            return;
        }

        // Sort the leaderboard list based on time (Format: "Name - 10s")
        leaderboardList.sort((a, b) -> {
            try {
                String timeAString = a.split("-")[1].trim().replace("s", ""); // Extract and clean time from entry A
                String timeBString = b.split("-")[1].trim().replace("s", ""); // Extract and clean time from entry B

                int timeA = Integer.parseInt(timeAString); // Convert to integer
                int timeB = Integer.parseInt(timeBString);

                return Integer.compare(timeA, timeB); // Sort in ascending order
            } catch (Exception e) {
                return 0; // If there's a formatting error, keep original order
            }
        });

        // Limit leaderboard to top 10 fastest players
        int maxPlayers = Math.min(10, leaderboardList.size());
        StringBuilder leaderboard = new StringBuilder("🏆 Top 10 Leaderboard 🏆\n");

        for (int i = 0; i < maxPlayers; i++) {
            leaderboard.append((i + 1)).append(". ").append(leaderboardList.get(i)).append("\n");
        }

        // Display the leaderboard in a message dialog
        JOptionPane.showMessageDialog(this, leaderboard.toString(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }

    // Displays the tutorial window with game instructions
    private void showTutorial() {
        String tutorialMessage = "Minesweeper Instructions:\n"
                + "1. Left-click to reveal a cell.\n"
                + "2. Right-click to flag a mine.\n"
                + "3. Reveal all non-mine cells to win!";
        JOptionPane.showMessageDialog(this, tutorialMessage, "Tutorial", JOptionPane.INFORMATION_MESSAGE);
    }

    // Displays the "About Us" window with team member names
    private void showAboutUs() {
        String aboutMessage = "About Us:\n"
                + "FPT University - Spring2025 - CSD201 - SE1905\n"
                + "Implementation team:\n"
                + "Nguyen Kim Bao Nguyen - CE191239 (Leader)\n"
                + "Truong Khai Toan - CE190173\n"
                + "Do Duc Hai - CE192014\n"
                + "Truong Doan Minh Phuc - CE190744\n"
                + "Duong Nguyen Kim Chi - CE191215\n";
        JOptionPane.showMessageDialog(this, aboutMessage, "About Us", JOptionPane.INFORMATION_MESSAGE);
    }

    // Displays the end-game options when the player wins or loses
    public static void showEndGameOptions(JFrame parentFrame, boolean isWin, int rows, int cols, int mines) {
        // Set message based on game result
        String message = isWin ? "Congratulations! You won! Do you want to play again?"
                : "You lost! Do you want to try again?";

        // Create a panel to hold buttons
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Create buttons for different options
        JButton replayButton = new JButton("Play Again");
        JButton menuButton = new JButton("Back to Menu");
        JButton exitButton = new JButton("Exit");

        // Action listener for replay button - starts a new game
        replayButton.addActionListener(e -> {
            new Minesweeper(rows, cols, mines); // Start a new Minesweeper game
            parentFrame.dispose(); // Close the current window
        });

        // Action listener for menu button - returns to main menu
        menuButton.addActionListener(e -> {
            new MainMenu(); // Open the main menu
            parentFrame.dispose(); // Close the current window
        });

        // Action listener for exit button - closes the application
        exitButton.addActionListener(e -> MainMenu.showExitFrame(parentFrame));

        // Add buttons to panel
        panel.add(replayButton);
        panel.add(menuButton);
        panel.add(exitButton);

        // Show dialog with game result message and options
        JOptionPane.showOptionDialog(
                parentFrame,
                message,
                isWin ? "Victory!" : "Game Over", // Dialog title based on game result
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{panel}, // Add panel with buttons
                null
        );
    }

    private static void showExitFrame(JFrame menuFrame) {
        
        if (menuFrame != null) {
            menuFrame.dispose(); // Close the menu before showing exit frame
        }

        // Create the farewell frame
        JFrame exitFrame = new JFrame("Goodbye!");
        exitFrame.setSize(300, 150);
        exitFrame.setLayout(new BorderLayout());

        // Display farewell message
        JLabel message = new JLabel("Thank you for playing Minesweeper!", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 16));

        exitFrame.add(message, BorderLayout.CENTER);
        exitFrame.setLocationRelativeTo(null); // Center the frame on the screen
        exitFrame.setVisible(true);

        // Create a timer to close the farewell frame and exit the game after 2 seconds
        Timer timer = new Timer(2000, e -> {
            exitFrame.dispose(); // Close the farewell frame
        });
        timer.setRepeats(false); // Run only once
        timer.start();
    }

    @Override
    public void start(int width, int height) {
        
        setTitle("Minesweeper Menu"); // Set window title
        setSize(400, 300); // Set window size
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 1)); // Arrange components in a 5-row grid layout

        // Create buttons for menu options
        JButton playButton = new JButton("Play");
        JButton leaderboardButton = new JButton("Leaderboard");
        JButton tutorialButton = new JButton("Tutorial");
        JButton aboutButton = new JButton("About Us");
        JButton exitButton = new JButton("Exit");

        // Add event listener to 'Play' button
        playButton.addActionListener(e -> {
            new StartMenu(); // Open the start menu
            dispose(); // Close the current menu window
        });

        // Add event listener to 'Leaderboard' button
        leaderboardButton.addActionListener(e -> showLeaderboard()); // Show leaderboard window

        // Add event listener to 'Tutorial' button
        tutorialButton.addActionListener(e -> showTutorial()); // Show tutorial window

        // Add event listener to 'About Us' button
        aboutButton.addActionListener(e -> showAboutUs()); // Show 'About Us' window

        // Add event listener to 'Exit' button
        exitButton.addActionListener(e -> showExitFrame(this)); // Exit the application

        // Add buttons to the main menu window
        add(playButton);
        add(leaderboardButton);
        add(tutorialButton);
        add(aboutButton);
        add(exitButton);

        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true); // Display the menu window
    }
    
    @Override
    public boolean getStatus() {
        return playing;
    }
    
    @Override
    public void dispose() {
        playing = false;
        
        super.dispose();
    }

    @Override
    public JPanel getJPanel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JFrame getJFrame() {
        return this;
    }

    @Override
    public int getTypeGame() {
        return 0;
    }
}
