package lobby.gamecenter.games.TicTacToe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import lobby.gamecenter.games.Initialize;

/**
 * CSD201 - TicTacToe [Java Swing]
 * 
 * @author PhucTDMCE190744
 */
public class Game implements Initialize {

    private JPanel frame;
    
    private int turn = 0;
    private boolean gameOver = false;
    private String currentPlayer = "X";
    private String playerX = "X";
    private String playerO = "O";
    private JLabel textLabel = new JLabel();
    private JButton rePlay = new JButton();
    private Board board;
    private Timer turnTimer;
    private int timeLeft; // Số giây còn lại

    public Game() {
    }

    private void startTurnTimer() {
        if (turnTimer != null) {
            turnTimer.stop(); // Dừng timer cũ nếu có
        }

        timeLeft = 15; // Mỗi lượt có 15 giây
        textLabel.setText(currentPlayer + "'s Turn ( " + timeLeft + "s )"); // Hiển thị thời gian ban đầu

        turnTimer = new Timer(1000, e -> { // Chạy mỗi 1 giây
            timeLeft--;
            textLabel.setText(currentPlayer + "'s Turn ( " + timeLeft + "s )");
            textLabel.setForeground(Color.white);
            if (timeLeft <= 0) { // Nếu hết giờ
                turnTimer.stop();
                switchTurn();
            }
        });

        turnTimer.start(); // Bắt đầu đếm
    }

    public void handleTileClick(int r, int c) {
        if (gameOver) {
            return;
        }

        JButton tile = board.getTile(r, c);

        if (tile.getText().isEmpty()) {
            tile.setText(currentPlayer);
            tile.setForeground(currentPlayer.equals(playerX) ? Color.red : Color.blue);
            turn++;
            checkWinner();
            if (!gameOver) {
                switchTurn();
            }
        }
    }

    private void switchTurn() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X"; // Đổi lượt
        textLabel.setForeground(Color.white);
        startTurnTimer(); // Bắt đầu đếm 15 giây cho lượt mới
    }

    void checkWinner() {
        if (board.checkVictory(currentPlayer)) {
            gameOver = true;
            turnTimer.stop();
            textLabel.setText(currentPlayer + " is the winner!");
            textLabel.setForeground(java.awt.Color.yellow);
            board.highlightWinner(currentPlayer);
            return;
        }

        // Check tie
        if (turn == 9) {
            gameOver = true;
            turnTimer.stop();
            textLabel.setText("Tie!!!!");
            textLabel.setForeground(Color.yellow); // Màu vàng đậm
            board.highlightTie();
        }
    }

    public void resetGame() {
        if (turnTimer != null) {
            turnTimer.stop();
        }
        gameOver = false;
        turn = 0;
        currentPlayer = playerX;
        textLabel.setText("Start");
        textLabel.setForeground(Color.white); // Đặt lại màu chữ
        board.resetBoard();
        startTurnTimer(); // Khởi động lại bộ đếm cho lượt mới
    }
    
    /**
     * Starts the game.
     * 
     * @param width The width of the game
     * @param height The height of the game
     */
    @Override
    public void start(int width, int height) {
        frame = new JPanel();
        frame.setSize(600, height);
        frame.setLayout(new java.awt.BorderLayout());

        // Panel chứa tiêu đề và trạng thái game
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(2, 1));
        titlePanel.setBackground(Color.darkGray);

// Label cố định "Tic-Tac-Toe"
        JLabel titleLabel = new JLabel("Tic-Tac-Toe", SwingConstants.CENTER);
        titleLabel.setForeground(new Color(255, 255, 102)); // vàng sáng
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.darkGray);

// Label hiển thị trạng thái game ("Start" hoặc lượt chơi)
        textLabel.setForeground(new Color(51, 153, 255)); // xanh sáng
        textLabel.setFont(new Font("Arial", Font.BOLD, 45));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setText("Start");
        textLabel.setOpaque(true);
        textLabel.setBackground(Color.darkGray);

// Thêm hai label vào panel
        titlePanel.add(titleLabel);
        titlePanel.add(textLabel);

// Thêm panel vào frame
        frame.add(titlePanel, BorderLayout.NORTH);

        // Setup board
        board = new Board(this);
        frame.add(board.getBoardPanel(), BorderLayout.CENTER);

        // Setup Replay button
        rePlay.setText("Replay");
        rePlay.setFont(new Font("sans serif", Font.CENTER_BASELINE, 30));
        rePlay.setForeground(Color.white);
        rePlay.setBackground(Color.red);
        rePlay.setFocusPainted(false);
        rePlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        frame.add(rePlay, BorderLayout.SOUTH);
        rePlay.setPreferredSize(new Dimension(600, 50));
        frame.setVisible(true);
    }
    
    /**
     * Gets the main frame.
     * 
     * @return The main frame of game
     */
    @Override
    public JPanel getJPanel() {
        return frame;
    }

    @Override
    public JFrame getJFrame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getTypeGame() {
        return 1;
    }

    @Override
    public boolean getStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
