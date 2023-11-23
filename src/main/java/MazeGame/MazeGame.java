package MazeGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import MazeMap.Vertex;
import MazeMap.Shortestpath;
import MazeMap.MazeMap;
import Interface.*;

/**
 * =========== MazeGame Class ===========
 * Main class for the Tom and Jerry maze game.
 * This class extends JFrame and represents the main window of the game.
 *
 * @attributes:
 * - mazeMap: MazeMap object representing the game's maze layout.
 * - size: Integer representing the fixed size of the maze.
 * - tom: Tom instance representing the character Tom in the game.
 * - jerry: Jerry instance representing the character Jerry in the game.
 * - panel: JPanel for rendering game components.
 * - timer: Timer for managing the game loop and Jerry's movement.
 * - DELAY: Integer for delay time in milliseconds for Jerry's movement.
 * - tomTimer: Timer for Tom's movement.
 * - TOM_DELAY: Integer for delay time in milliseconds for Tom's movement.
 * - sizeOfSquare: Integer for the size of each square in the maze.
 * - jerryPosition: Vertex representing the current position of Jerry in the maze.
 * - entryPoint: Vertex representing the entry point of the maze.
 * - exitPoint: Vertex representing the exit point of the maze.
 *
 * @operations:
 * - MazeGame(): Constructor to initialize the game window, load the maze, set up game entities, and start the game timers.
 * - loadMaze(String filePath): Loads the maze configuration from a specified file path.
 * - getJerryPositionAsVertex(): Returns Jerry's current position as a Vertex object.
 * - gameLoop(): Main game loop that handles game logic, updates positions, checks win/lose conditions, and refreshes the display.
 * - isGameWon(): Checks if Jerry has reached the exit point, indicating a win.
 * - isGameLost(): Checks if Tom has caught Jerry, indicating a loss.
 * - main(String[] args): Static entry point to start the game.
 *
 * Additional methods for testing:
 * - getMazeMap(): Returns the MazeMap object.
 * - getJerry(): Returns the Jerry instance.
 * - getTom(): Returns the Tom instance.
 */

public class MazeGame {

    private static MazeMap mazeMap;
    private final int size = 30;
    private static Tom tom;
    private static Jerry jerry;
    private GamePanel panel;
    private Timer timer;
    private final int DELAY = 400; // Milliseconds, adjust for speed for Jerry
    private Timer tomTimer;
    private final int TOM_DELAY =300; // Shorter delay for Tom's movement
    private final int sizeOfSquare = 25;

    private final Interface screen;

    private Vertex jerryPosition;
    Vertex entryPoint;
    Vertex exitPoint;

    /**
     * MazeGame.MazeGame Constructor: Initializes the game window, loads the maze,
     * sets up game entities, and starts the game timers.
     */
    public MazeGame(String path, Interface screen) {
        this.screen = screen;
        mazeMap = new MazeMap();
        loadMaze(path); // change this

        jerryPosition = new Vertex(sizeOfSquare, entryPoint.getx(), entryPoint.gety(), 0);


        //add(mazeMap);

        panel = new GamePanel();
        //add(panel);

        //pack();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:    jerry.setDirection(Direction.UP); break;
                    case KeyEvent.VK_DOWN:  jerry.setDirection(Direction.DOWN); break;
                    case KeyEvent.VK_LEFT:  jerry.setDirection(Direction.LEFT); break;
                    case KeyEvent.VK_RIGHT: jerry.setDirection(Direction.RIGHT); break;

                }
                panel.repaint();
            }
        });
        //setFocusable(true);
        //setVisible(true);

        tomTimer = new Timer(TOM_DELAY, e -> {
            tom.move(mazeMap, getJerryPositionAsVertex());
            panel.repaint();
        });

        tomTimer.start();

        timer = new Timer(DELAY, e -> gameLoop());
        timer.start();


    }

    public Vertex getJerryPositionAsVertex() {
        return jerryPosition;
    }

    public GamePanel getPanel(){
        return panel;
    }

    public void stopTimer(){
        timer.stop();
    }

    //    ******* This is where you load the maze!!!!!
    public void loadMaze(String filePath) {
        mazeMap.load_MazeMap(filePath);

        entryPoint = mazeMap.getEntry();
        exitPoint = mazeMap.getExit();

        tom = new Tom(exitPoint.getx(), exitPoint.gety());
        jerry = new Jerry(entryPoint.getx(), entryPoint.gety());
        jerryPosition = new Vertex(sizeOfSquare, entryPoint.getx(), entryPoint.gety(), 0);

    }

    /**
     * =========== GameEntity Class ===========
     * Represents a basic game entity in the maze, such as Tom or Jerry.
     *
     * @attributes:
     * - x, y: Integer coordinates of the entity in the maze.
     * - color: Color used to represent the entity on the game panel.
     * - direction: Direction of the entity's movement.
     *
     * @operations:
     * - GameEntity(int x, int y, Color color): Constructor to initialize the entity with coordinates and color.
     * - draw(Graphics g): Method to draw the entity on the game panel.
     * - getx(): Returns the x-coordinate of the entity.
     * - gety(): Returns the y-coordinate of the entity.
     * - getColor(): Returns the color of the entity.
     */

    public static class GameEntity {
        int x, y;
        Color color;
        Direction direction;

        public GameEntity(int x, int y, Color color) {
            this.x = x; //col
            this.y = y; //row
            this.color = color;
        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(x * 25, y * 25, 25, 25);
        }
        public int getx() {
            return this.x;
        }
        // Method to get the y-coordinate of Jerry
        public int gety() {
            return this.y;
        }

        public Color getColor() {
            return this.color;
        }
    }
    public boolean jerryHasMoved=false;

    /**
     * =========== Tom Class ===========
     * Represents the character Tom in the game, extending the GameEntity class.
     *
     * @attributes:
     * - Inherits x, y, color from GameEntity.
     * - pathToJerry: Array of Vertex objects storing the calculated path to Jerry.
     * - pathIndex: Integer index for the current position in the pathToJerry array.
     * - flag: Integer flag used in movement logic.
     *
     * @operations:
     * - Tom(int x, int y): Constructor to initialize Tom's position and color.
     * - getCurrentPosition(): Returns Tom's current position as a Vertex.
     * - move(MazeMap mazeMap, Vertex jerryPosition): Moves Tom towards Jerry based on the calculated path.
     * - calculatePathToJerry(MazeMap mazeMap, Vertex jerryPosition): Calculates the shortest path to Jerry.
     * - followPathStepByStep(): Method for Tom to follow the calculated path step by step.
     * - setPathToJerry(Vertex[] mockPath): Sets a mock path for Tom, used for testing.
     */
    public class Tom extends GameEntity {
        private Vertex[] pathToJerry;  // Store the calculated path to Jerry
        private int pathIndex = 0;
        private int flag=0;
        public Tom(int x, int y) {
            super(y, x, Color.BLUE);
        }
        public Vertex getCurrentPosition() {
            // Assuming Vertex has a constructor that takes x, y, and vertex type
            // Provide the size of the square along with Tom's position and vertex type
            // The vertex type for Tom's position can be a path (0) or another appropriate value
            return mazeMap.getMazedata()[this.y][this.x];
        }
        public void move(MazeMap mazeMap, Vertex jerryPosition) {
            if (!jerryHasMoved) {
                return;
            }
            // Merging calculatePathToJerry logic
            pathToJerry = Shortestpath.shortestPath(mazeMap, this.getCurrentPosition(), jerryPosition, 0);
            pathIndex = 1; // Reset path index to start following the new path

            followPathStepByStep();
        }
        public void setPathToJerry(Vertex[] mockPath) {
            this.pathToJerry = mockPath;
        }

        public void followPathStepByStep() {
            if (pathToJerry != null && pathIndex < pathToJerry.length) {

                if(flag==0){
                    flag++;
                    return ;
                } else {
                    Vertex nextStep = pathToJerry[pathIndex];
                    this.x = nextStep.gety();
                    this.y = nextStep.getx();
                    flag--;

                }

            }
        }
    }

    /**
     * =========== Jerry Class ===========
     * Represents the character Jerry in the game, extending the GameEntity class.
     *
     * @attributes:
     * - Inherits x, y, color from GameEntity.
     * - direction: Direction representing the current movement direction of Jerry.
     *
     * @operations:
     * - Jerry(int x, int y): Constructor to initialize Jerry's position, color, and initial direction.
     * - setDirection(Direction direction): Sets the movement direction of Jerry.
     * - move(): Moves Jerry based on the current direction and validates the move.
     * - isValidMove(int newX, int newY, Vertex[][] mazeData): Checks if a proposed move is valid within the maze.
     * - getDirection(): Returns Jerry's current movement direction.
     * - getColor(): Returns Jerry's current color.
     */

    public class Jerry extends GameEntity {
        private Direction direction;

        public Jerry(int x, int y) {
            super(y, x, Color.ORANGE);// swaped
            this.direction = Direction.LEFT; // Initial direction
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public void move() {
            int newX = x, newY = y;
            Vertex[][] mazeData = mazeMap.getMazedata(); // Get maze data from MazeMap

            switch (direction) {
                case UP:    newY--; break;
                case DOWN:  newY++; break;
                case LEFT:  newX--; break;
                case RIGHT: newX++; break;
            }

            if (isValidMove(newX, newY, mazeData)) {
                x = newX;
                y = newY;
                // Update Jerry's position as a Vertex
                jerryPosition = mazeMap.getMazedata()[y][x];
                jerryHasMoved = true;// Update with new coordinates
            }
        }
        public boolean isValidMove(int newX, int newY, Vertex[][] mazeData) {
            return newX >= 0 && newX < size && newY >= 0 && newY < size && mazeData[newY][newX].getVertex_type() != 1;
        }
        public Direction getDirection() {
            return this.direction;
        }
    }

    /**
     * =========== GamePanel Class ===========
     * Represents the main panel where the game is drawn.
     *
     * @operations:
     * - paintComponent(Graphics g): Custom painting method for the game panel.
     * - getPreferredSize(): Returns the preferred size of the game panel.
     */
    public static class GamePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Vertex[][] mazeData = mazeMap.getMazedata();
            for (int row = 0; row < mazeData.length; row++) {
                for (int col = 0; col < mazeData[row].length; col++) {
                    if (mazeData[row][col].getVertex_type() == 1) {
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(col * 25, row * 25, 25, 25);
                    }
                }
            }
            tom.draw(g);
            jerry.draw(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(30 * 25, 30 * 25);
        }
    }

    /**
     * =========== Direction Enum ===========
     * Enum representing possible movement directions for Jerry in the game.
     *
     * @attributes:
     * - Enum values: UP, DOWN, LEFT, RIGHT.
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // main game logic loop
    public void gameLoop() {
        jerry.move(); // Assuming move() uses mazeMap internally

        Vertex jerryPosition = getJerryPositionAsVertex();
        tom.move(mazeMap, jerryPosition);

        Vertex exitPoint = mazeMap.getExit();
        int exitX = exitPoint.gety();
        int exitY = exitPoint.getx();

        if (jerry.x == tom.x && jerry.y == tom.y) {
            timer.stop();
            JOptionPane.showMessageDialog(panel, "Tom caught Jerry! You lose.");

        } else if (jerry.x == exitX&& jerry.y == exitY) {
            timer.stop();
            JOptionPane.showMessageDialog(panel, "Jerry reached the Exit! You win!");

        }

        panel.repaint();
    }

    // **** test cases
    public MazeMap getMazeMap() {
        return this.mazeMap;
    }
    public Jerry getJerry() {
        return this.jerry;
    }

    public Tom getTom() {
        return this.tom;
    }

//    public static void main(String[] args) {
//        new MazeGame();
//    }
}
