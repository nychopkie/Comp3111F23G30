package placeholder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MazeGame extends JFrame {
    private int[][] maze;
    private final int size = 30;
    private Tom tom;
    private Jerry jerry;
    private GamePanel panel;
    private Timer timer;
    private final int DELAY = 130; // Milliseconds, adjust for speed for Jerry
    private Timer tomTimer;
    private final int TOM_DELAY = 100; // Shorter delay for Tom's movement


    public MazeGame() {
        loadMaze("/Users/meng/IdeaProjects/Tom and Jerry/src/main/java/MazaMap_TnJ.csv");
        tom = new Tom(29, 1); // Assuming the exit point is at (29, 2)
        jerry = new Jerry(0, 12); // Assuming the entry point is at (0, 13)

        panel = new GamePanel();
        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:    jerry.setDirection(Direction.UP); break;
                    case KeyEvent.VK_DOWN:  jerry.setDirection(Direction.DOWN); break;
                    case KeyEvent.VK_LEFT:  jerry.setDirection(Direction.LEFT); break;
                    case KeyEvent.VK_RIGHT: jerry.setDirection(Direction.RIGHT); break;
                }
            }
        });
        setFocusable(true);
        setVisible(true);

        timer = new Timer(DELAY, e -> gameLoop());
        timer.start();

        tomTimer = new Timer(TOM_DELAY, e -> {
            tom.move(maze);
            panel.repaint();
        });
        tomTimer.start();
    }


//    ******* This is where you load the maze!!!!!
    public void loadMaze(String filePath) {
        maze = new int[size][size];
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int col = 0; col < size; col++) {
                    // Trim whitespace and then parse the integer
                    maze[row][col] = Integer.parseInt(values[col].trim());
                    //System.out.println("Maze[" + row + "][" + col + "] = " + maze[row][col]); // Debugging statement
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();  // It's better to print the stack trace for debugging
        }
    }

    class GameEntity {
        int x, y;
        Color color;

        public GameEntity(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(x * 10, y * 10, 10, 10);
        }
    }

    class Tom extends GameEntity {
        public Tom(int x, int y) {
            super(x, y, Color.RED);
        }

        public void move(int[][] maze) {
            int newX = x;
            int newY = y + 1;

            // Check if moving down is possible, otherwise move left
            if (newY >= size || maze[newY][x] == 1) {
                newX = x - 1;
                newY = y;
                if (newX < 0 || maze[y][newX] == 1) {
                    // Unable to move left, stay in current position
                    return;
                }
            }

            x = newX;
            y = newY;
        }
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    class Jerry extends GameEntity {
        private Direction direction;

        public Jerry(int x, int y) {
            super(x, y, Color.ORANGE);
            this.direction = Direction.RIGHT; // Initial direction
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public void move(int[][] maze) {
            int newX = x, newY = y;
            switch (direction) {
                case UP:    newY--; break;
                case DOWN:  newY++; break;
                case LEFT:  newX--; break;
                case RIGHT: newX++; break;
            }
            if (isValidMove(newX, newY, maze)) {
                x = newX;
                y = newY;
            }
        }

        private boolean isValidMove(int newX, int newY, int[][] maze) {
            return newX >= 0 && newX < size && newY >= 0 && newY < size && maze[newY][newX] == 0;
        }
    }
    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            // paint the gery barrier
            super.paintComponent(g);
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (maze[row][col] == 1) {
                        g.setColor(Color.DARK_GRAY); // Set color for barriers
                        g.fillRect(col * 10, row * 10, 10, 10);
                    }
                }
            }
            // draw tom and jerry
            tom.draw(g);
            jerry.draw(g);
        }
            @Override
            public Dimension getPreferredSize () {
                return new Dimension(300, 300);
            }
        }
    private void gameLoop() {
        jerry.move(maze);
        tom.move(maze); // Tom moves every loop iteration
        int exitX = 29;
        int exitY = 1;
        // Check for win/lose conditions
        if (jerry.x == tom.x && jerry.y == tom.y) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Tom caught Jerry! You lose.");
        } else if (jerry.x == exitX && jerry.y == exitY) { // assuming exitX and exitY are defined
            timer.stop();
            JOptionPane.showMessageDialog(this, "Jerry reached the Exit! You win!");
        }

        panel.repaint();
    }

    // **** test cases
    public int[][] getMaze() {
        return maze;
    }
    public int[] getJerryPosition() {
        return new int[] { jerry.x, jerry.y };
    }
    public void moveJerry(Direction direction) {
        jerry.setDirection(direction);
        jerry.move(maze);
    }


    public static void main(String[] args) {
        new MazeGame();
    }
}
