/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.gamecenter.games.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Minesweeper extends JFrame {

    private Board board; // The game board
    private JButton[][] buttons; // 2D array of buttons representing the board
    private int rows, cols, mines; // Number of rows, columns, and mines
    private boolean gameOver = false; // Flag to check if the game is over
    private boolean firstClick = true; // Flag to track if it's the first click
    private long startTime; // Stores the start time of the game
    private static final String LEADERBOARD_FILE = "leaderboard.txt"; // File to store leaderboard data
    private static final Color[] NUMBER_COLORS = {
        Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA, Color.ORANGE, Color.CYAN, Color.PINK, Color.BLACK
    };

    // Constructor to initialize the Minesweeper game
    public Minesweeper(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        board = new Board(rows, cols, mines);
        buttons = new JButton[rows][cols];

        setLayout(new GridLayout(rows, cols)); // Setting grid layout
        initializeButtons(); // Initializing game buttons

        setTitle("Minesweeper");

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        setupKeyBindings(); // Enables ESC key for pause menu
    }

    // Initializes the buttons on the game board
    private void initializeButtons() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 16));
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
                button.setFocusPainted(false);
                buttons[i][j] = button;
                add(button);

                int row = i, col = j;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (gameOver) {
                            return;
                        }
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            handleClick(row, col);
                        }
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            handleRightClick(row, col);
                        }
                    }
                });
            }
        }
    }

    // Sets up key bindings for the game (ESC key for pause menu)
    private void setupKeyBindings() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        // Bind the ESC key to trigger the pause menu
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "pauseGame");
        actionMap.put("pauseGame", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPauseMenu(); // Show the pause menu when ESC is pressed
            }
        });
    }

    // Reveals empty cells if they have no nearby mines
    private void revealEmptyCells(int row, int col) {
        // Iterate over the surrounding 8 cells (including the current one)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int r = row + i; // Calculate the row index of the adjacent cell
                int c = col + j; // Calculate the column index of the adjacent cell

                // Check if the adjacent cell is within bounds
                if (r >= 0 && r < rows && c >= 0 && c < cols) {
                    Cell cell = board.getCell(r, c); // Get the cell object

                    // If the cell is not revealed and is not a mine
                    if (!cell.isRevealed() && !cell.isMine()) {
                        cell.reveal(); // Mark the cell as revealed
                        buttons[r][c].setBackground(Color.LIGHT_GRAY); // Change button color to indicate revealed state
                        buttons[r][c].setText(String.valueOf(cell.getNearbyMines())); // Display the number of nearby mines

                        // If the cell has no nearby mines, recursively reveal adjacent cells
                        if (cell.getNearbyMines() == 0) {
                            revealEmptyCells(r, c);
                        }
                    }
                }
            }
        }
    }

    // Moves mines if the first clicked cell contains a mine
    private void moveMines(int firstRow, int firstCol) {
        int newRow, newCol;
        do {
            // Generate random row and column indices for new mine position
            newRow = (int) (Math.random() * rows);
            newCol = (int) (Math.random() * cols);
        } while ((newRow == firstRow && newCol == firstCol) || board.getCell(newRow, newCol).isMine()); // Ensure new position is not the same as the first click and not already a mine

        // Remove mine from the originally clicked cell
        board.getCell(firstRow, firstCol).setMine(false);
        // Place mine at the new random position
        board.getCell(newRow, newCol).setMine(true);

        // Recalculate nearby mine counts after moving the mine
        board.calculateNearbyMines();
    }

    // Handles the event when the player left-clicks on a cell
    private void handleClick(int row, int col) {
        // Check if this is the player's first click
        if (firstClick) {
            startTime = System.currentTimeMillis(); // Start tracking playtime

            // If the first clicked cell is a mine, relocate mines to ensure a fair start
            if (board.getCell(row, col).isMine()) {
                moveMines(row, col);
            }
            firstClick = false; // Mark that the first click has occurred
        }

        // Get the clicked cell
        Cell cell = board.getCell(row, col);

        // If the cell is already revealed, do nothing
        if (cell.isRevealed()) {
            return;
        }

        // Reveal the cell
        cell.reveal();
        buttons[row][col].setBackground(Color.LIGHT_GRAY); // Change background to indicate it's revealed

        // If the cell is a mine, handle game over scenario
        if (cell.isMine()) {
            buttons[row][col].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12)); // Set font to support emoji
            buttons[row][col].setText("\uD83D\uDCA3"); // 💣 Display bomb emoji
            buttons[row][col].setHorizontalTextPosition(SwingConstants.CENTER);
            buttons[row][col].setVerticalTextPosition(SwingConstants.CENTER);
            buttons[row][col].setBackground(Color.RED); // Change background to red indicating explosion

            showLoseDialog(); // Display game over message
        } else {
            // Get the number of mines surrounding the cell
            int minesAround = cell.getNearbyMines();
            if (minesAround > 0) {
                // Display the number of nearby mines in the cell
                buttons[row][col].setText(String.valueOf(minesAround));
                buttons[row][col].setForeground(NUMBER_COLORS[minesAround - 1]); // Set color based on the number
            } else {
                // If no mines are around, reveal surrounding empty cells
                revealEmptyCells(row, col);
            }

            // If the game is not over and all non-mine cells are revealed, the player wins
            if (!gameOver && checkWinCondition()) {
                showWinDialog(); // Display win message
            }
        }
    }

    // Handles right-click event to flag or unflag a cell
    private void handleRightClick(int row, int col) {
        Cell cell = board.getCell(row, col); // Get the selected cell

        // If the cell is already revealed, do nothing (cannot flag a revealed cell)
        if (cell.isRevealed()) {
            return;
        }

        // If the cell is already flagged, remove the flag
        if (cell.isFlagged()) {
            cell.setFlagged(false); // Unflag the cell
            buttons[row][col].setBackground(Color.white); // Reset background color
            buttons[row][col].setText(""); // Remove flag icon
        } else {
            // If the cell is not flagged, flag it
            cell.setFlagged(true); // Mark the cell as flagged
            buttons[row][col].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12)); // Set font to support emoji
            buttons[row][col].setText("\uD83D\uDEA9"); // 🚩 Flag emoji
            buttons[row][col].setHorizontalTextPosition(SwingConstants.CENTER);
            buttons[row][col].setVerticalTextPosition(SwingConstants.CENTER);
            buttons[row][col].setBackground(Color.ORANGE); // Change background to orange indicating a flag
        }
    }

    // Checks if the player has won the game
    private boolean checkWinCondition() {
        for (int i = 0; i < rows; i++) { // Iterate through all rows
            for (int j = 0; j < cols; j++) { // Iterate through all columns
                Cell cell = board.getCell(i, j); // Retrieve the cell object
                if (!cell.isMine() && !cell.isRevealed()) { // If a non-mine cell is not revealed
                    return false; // The game is not yet won
                }
            }
        }
        return true; // All non-mine cells are revealed, player wins
    }

    // Displays the win dialog
    private void showWinDialog() {
        gameOver = true; // Set the game over state to true
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // Calculate elapsed time in seconds
        String playerName = JOptionPane.showInputDialog(this, "You won! Enter your name:"); // Prompt for player's name
        if (playerName != null) { // If a name is entered
            saveToLeaderboard(playerName, elapsedTime); // Save the result to the leaderboard
        }
        MainMenu.showEndGameOptions(this, true, rows, cols, mines); // Show the end-game options
    }

    // Displays the lose dialog
    private void showLoseDialog() {
        gameOver = true; // Set the game over state to true
        JOptionPane.showMessageDialog(this, "Game Over!"); // Display game over message
        MainMenu.showEndGameOptions(this, false, rows, cols, mines); // Show the end-game options
    }

    private void showPauseMenu() {
        // Create a dialog window for the pause menu
        JDialog pauseMenu = new JDialog(this, "Pause", true);
        pauseMenu.setSize(300, 200); // Set the size of the pause menu
        pauseMenu.setLayout(new GridLayout(3, 1)); // Use a grid layout with 3 rows and 1 column

        // Create buttons for resume, restart, and exit options
        JButton resumeButton = new JButton("Resume Game");
        JButton restartButton = new JButton("Restart Game");
        JButton exitButton = new JButton("Back to Menu");

        // Add action listener to resume button: closes the pause menu when clicked
        resumeButton.addActionListener(e -> pauseMenu.dispose());

        // Add action listener to restart button: closes the pause menu and restarts the game
        restartButton.addActionListener(e -> {
            pauseMenu.dispose();
            restartGame();
        });

        // Add action listener to exit button: closes the pause menu, exits the game, and returns to the main menu
        exitButton.addActionListener(e -> {
            pauseMenu.dispose();
            dispose(); // Close the current Minesweeper window
            new MainMenu(); // Open the main menu
        });

        // Add buttons to the pause menu dialog
        pauseMenu.add(resumeButton);
        pauseMenu.add(restartButton);
        pauseMenu.add(exitButton);

        // Center the pause menu relative to the main game window
        pauseMenu.setLocationRelativeTo(this);
        pauseMenu.setVisible(true); // Display the pause menu
    }

    // Method to restart the game
    private void restartGame() {
        dispose(); // Close the current game window
        new Minesweeper(rows, cols, mines); // Create a new instance of the game
    }

    // Saves player's score to the leaderboard file
    private void saveToLeaderboard(String name, long time) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE, true))) { // Open file in append mode
            writer.write(name + " - " + time + "s\n"); // Write player's name and time to file
        } catch (IOException e) {
            e.printStackTrace(); // Print error details if file writing fails
        }
    }
}
