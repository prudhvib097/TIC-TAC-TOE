import java.awt.*;
import java.awt.event.*;

public class App extends Frame implements ActionListener {
    // 3x3 grid for Tic-Tac-Toe
    private Button[][] buttons = new Button[3][3];
    private String currentPlayer = "X";
    private boolean gameOver = false;

    public App() {
        // Set up the frame
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setLayout(new GridLayout(3, 3));

        // Initialize buttons and add action listeners
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
        }

        // Close the frame when the window is closed
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        Button clickedButton = (Button) e.getSource();

        // Check if the button is already clicked
        if (!clickedButton.getLabel().equals("")) return;

        // Set the text for the clicked button (current player)
        clickedButton.setLabel(currentPlayer);

        // Check for win or draw
        int[][] winningCombination = checkWin();
        if (winningCombination != null) {
            gameOver = true;
            highlightWinningCombination(winningCombination);
            showMessage(currentPlayer + " wins!");
        } else if (isBoardFull()) {
            gameOver = true;
            showMessage("It's a draw!");
        } else {
            // Switch player
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        }
    }

    private int[][] checkWin() {
        // Check rows and columns for a winner
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (buttons[i][0].getLabel().equals(currentPlayer) &&
                buttons[i][1].getLabel().equals(currentPlayer) &&
                buttons[i][2].getLabel().equals(currentPlayer)) {
                return new int[][]{{i, 0}, {i, 1}, {i, 2}};
            }
            // Check columns
            if (buttons[0][i].getLabel().equals(currentPlayer) &&
                buttons[1][i].getLabel().equals(currentPlayer) &&
                buttons[2][i].getLabel().equals(currentPlayer)) {
                return new int[][]{{0, i}, {1, i}, {2, i}};
            }
        }

        // Check diagonals
        if (buttons[0][0].getLabel().equals(currentPlayer) &&
            buttons[1][1].getLabel().equals(currentPlayer) &&
            buttons[2][2].getLabel().equals(currentPlayer)) {
            return new int[][]{{0, 0}, {1, 1}, {2, 2}};
        }
        if (buttons[0][2].getLabel().equals(currentPlayer) &&
            buttons[1][1].getLabel().equals(currentPlayer) &&
            buttons[2][0].getLabel().equals(currentPlayer)) {
            return new int[][]{{0, 2}, {1, 1}, {2, 0}};
        }

        // No win
        return null;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getLabel().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void highlightWinningCombination(int[][] winningCombination) {
        for (int[] cell : winningCombination) {
            buttons[cell[0]][cell[1]].setBackground(Color.GREEN);
        }
    }

    private void showMessage(String message) {
        // Create a dialog box to display the result
        Dialog dialog = new Dialog(this, "Game Over", true);
        dialog.setLayout(new FlowLayout());
        dialog.setSize(200, 100);
        Label label = new Label(message);
        Button button = new Button("OK");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                resetGame();
            }
        });

        dialog.add(label);
        dialog.add(button);
        dialog.setVisible(true);
    }

    private void resetGame() {
        // Reset the board and game state
        gameOver = false;
        currentPlayer = "X";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setLabel("");
                buttons[i][j].setBackground(Color.WHITE);
            }
        }
    }

    public static void main(String[] args) {
        new App();
    }
}
