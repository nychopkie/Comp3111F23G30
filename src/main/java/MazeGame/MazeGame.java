package MazeGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import MazeMap.Vertex;
import MazeMap.Shortestpath;
import MazeMap.MazeMap;
import Interface.*;

/**
 * Main class for the Tom and Jerry maze game.<br><hr>
 *
 * ATTRIBUTES<br>
 * - mazeMap: MazeMap object representing the game's maze layout.<br>
 * - size: Integer representing the fixed size of the maze.<br>
 * - tom: Tom instance representing the character Tom in the game.<br>
 * - jerry: Jerry instance representing the character Jerry in the game.<br>
 * - panel: JPanel for rendering game components.<br>
 * - timer: Timer for managing the game loop and Jerry's movement.<br>
 * - DELAY: Integer for delay time in milliseconds for Jerry's movement.<br>
 * - tomTimer: Timer for Tom's movement.<br>
 * - TOM_DELAY: Integer for delay time in milliseconds for Tom's movement.<br>
 * - sizeOfSquare: Integer for the size of each square in the maze.<br>
 * - jerryPosition: Vertex representing the current position of Jerry in the maze.<br>
 * - entryPoint: Vertex representing the entry point of the maze.<br>
 * - exitPoint: Vertex representing the exit point of the maze.<br><hr>
 *
 * OPERATIONS<br>
 * - MazeGame(): Constructor to initialize the game window, load the maze, set up game entities, and start the game timers.<br>
 * - loadMaze(String filePath): Loads the maze configuration from a specified file path.<br>
 * - getJerryPositionAsVertex(): Returns Jerry's current position as a Vertex object.<br>
 * - gameLoop(): Main game loop that handles game logic, updates positions, checks win/lose conditions, and refreshes the display.<br>
 * - isGameWon(): Checks if Jerry has reached the exit point, indicating a win.<br>
 * - isGameLost(): Checks if Tom has caught Jerry, indicating a loss.<br><hr>
 *
 * Additional methods for testing:<br>
 * - getMazeMap(): Returns the MazeMap object.<br>
 */

public class MazeGame {

    private static MazeMap mazeMap;
    private final int size = 30;
    public static Tom tom;
    public static Jerry jerry;
    private GamePanel panel;
    public static Timer timer;
    private final int DELAY = 400; // Milliseconds, adjust for speed for Jerry
    public static Timer tomTimer;
    private final int TOM_DELAY =300; // Shorter delay for Tom's movement
    private final int sizeOfSquare = 25;

    private Vertex jerryPosition;
    Vertex entryPoint;
    Vertex exitPoint;

    /**
     * MazeGame.MazeGame Constructor: Initializes the game window, loads the maze,
     * sets up game entities, and starts the game timers.
     */
    public MazeGame(String path, Interface screen) {
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
                    case KeyEvent.VK_UP:
                        Jerry.direction = Direction.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        Jerry.direction = Direction.DOWN;
                        break;
                    case KeyEvent.VK_LEFT:
                        Jerry.direction = Direction.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        Jerry.direction = Direction.RIGHT;
                        break;

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

    /**
     * returns the position of Jerry
     * @return Vertex jerryPosition
     */
    public Vertex getJerryPositionAsVertex() {
        return jerryPosition;
    }

    /**
     * returns the game panel
     * @return GamePanel panel
     */
    public GamePanel getPanel(){
        return panel;
    }

    /**
     * function to load maze to the game
     * @param filePath the file path to be passed into the game
     */
    public void loadMaze(String filePath) {
        mazeMap.load_MazeMap(filePath);

        entryPoint = mazeMap.getEntry();
        exitPoint = mazeMap.getExit();

        tom = new Tom(exitPoint.getx(), exitPoint.gety());
        jerry = new Jerry(entryPoint.getx(), entryPoint.gety());
        jerryPosition = new Vertex(sizeOfSquare, entryPoint.getx(), entryPoint.gety(), 0);

    }

    /**
     * Represents a basic game entity in the maze, such as Tom or Jerry.<br><hr>
     *
     * ATTRIBUTES<br>
     * - x, y: Integer coordinates of the entity in the maze.<br>
     * - color: Color used to represent the entity on the game panel.<br>
     * - direction: Direction of the entity's movement.<br><hr>
     *
     * OPERATIONS<br>
     * - GameEntity(int x, int y, Color color): Constructor to initialize the entity with coordinates and color.<br>
     * - draw(Graphics g): Method to draw the entity on the game panel.<br>
     * - getx(): Returns the x-coordinate of the entity.<br>
     * - gety(): Returns the y-coordinate of the entity.<br>
     * - getColor(): Returns the color of the entity.<br>
     */
    public static class GameEntity {
        int x, y;
        Color color;
        Direction direction;

        /**
         * constructor for GameEntity class
         * @param x the starting x-coordinate
         * @param y the starting y-coordinate
         * @param color the color of the Game Entity
         */
        public GameEntity(int x, int y, Color color) {
            this.x = x; //col
            this.y = y; //row
            this.color = color;
        }

        /**
         * function to draw the Game entity
         * @param g
         */
        public void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(x * 25, y * 25, 25, 25);
        }

        /**
         * returns the x-coordinate of Game Entity
         * @return int x
         */
        public int getx() {
            return this.x;
        }

        /**
         * Method to get the y-coordinate of Jerry
         * @return int y
         */
        public int gety() {
            return this.y;
        }

        /**
         * returns the colour of the Game Entity
         * @return Color color
         */
        public Color getColor() {
            return this.color;
        }
    }
    public boolean jerryHasMoved=false;

    /**
     * Represents the character Tom in the game, extending the GameEntity class.<br><hr>
     *
     * ATTRIBUTES<br>
     * - Inherits x, y, color from GameEntity.<br>
     * - pathToJerry: Array of Vertex objects storing the calculated path to Jerry.<br>
     * - pathIndex: Integer index for the current position in the pathToJerry array.<br>
     * - flag: Integer flag used in movement logic.<br><hr>
     *
     * OPERATIONS<br>
     * - Tom(int x, int y): Constructor to initialize Tom's position and color.<br>
     * - getCurrentPosition(): Returns Tom's current position as a Vertex.<br>
     * - move(MazeMap mazeMap, Vertex jerryPosition): Moves Tom towards Jerry based on the calculated path.<br>
     * - calculatePathToJerry(MazeMap mazeMap, Vertex jerryPosition): Calculates the shortest path to Jerry.<br>
     * - followPathStepByStep(): Method for Tom to follow the calculated path step by step.<br>
     * - setPathToJerry(Vertex[] mockPath): Sets a mock path for Tom, used for testing.<br>
     */
    public class Tom extends GameEntity {
        private Vertex[] pathToJerry;  // Store the calculated path to Jerry
        private int pathIndex = 0;
        private int flag=0;

        /**
         * Constructor to create Tom
         * @param x the x-coordinate of Tom
         * @param y the y-coordinate of Tom
         */
        public Tom(int x, int y) {
            super(y, x, Color.BLUE);
        }

        /**
         * returns the current position of Tom
         * @return Vertex current position
         */
        public Vertex getCurrentPosition() {
            // Assuming Vertex has a constructor that takes x, y, and vertex type
            // Provide the size of the square along with Tom's position and vertex type
            // The vertex type for Tom's position can be a path (0) or another appropriate value
            return mazeMap.getMazedata()[this.y][this.x];
        }

        /**
         * function to control movement since Tom always goes the shortest distance to Jerry
         * @param mazeMap the map
         * @param jerryPosition Jerry's position
         */
        public void move(MazeMap mazeMap, Vertex jerryPosition) {
            if (!jerryHasMoved) {
                return;
            }
            // Merging calculatePathToJerry logic
            pathToJerry = Shortestpath.shortestPath(mazeMap, this.getCurrentPosition(), jerryPosition, 0);
            pathIndex = 1; // Reset path index to start following the new path

            followPathStepByStep();
        }

        /**
         * sets the path to Jerry
         * @param mockPath the shortest path to Jerry
         */
        public void setPathToJerry(Vertex[] mockPath) {
            this.pathToJerry = mockPath;
        }

        /**
         * function to control movement of Tom
         */
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
     * Represents the character Jerry in the game, extending the GameEntity class.<br><hr>
     *
     * ATTRIBUTES<br>
     * - Inherits x, y, color from GameEntity.<br>
     * - direction: Direction representing the current movement direction of Jerry.<br><hr>
     *
     * OPERATIONS<br>
     * - Jerry(int x, int y): Constructor to initialize Jerry's position, color, and initial direction.<br>
     * - setDirection(Direction direction): Sets the movement direction of Jerry.<br>
     * - move(): Moves Jerry based on the current direction and validates the move.<br>
     * - getDirection(): Returns Jerry's current movement direction.<br>
     * - getColor(): Returns Jerry's current color.<br>
     */
    public class Jerry extends GameEntity {
        public static Direction direction;

        /**
         * Constructor for Jerry
         * @param x the x-coordinate
         * @param y the y-coordinate
         */
        public Jerry(int x, int y) {
            super(y, x, Color.ORANGE);// swapped
            Jerry.direction = Direction.LEFT; // Initial direction
        }

        /**
         * function to control the movement of Jerry
         */
        public void move() {
            int newX = x, newY = y;
            Vertex[][] mazeData = mazeMap.getMazedata(); // Get maze data from MazeMap

            switch (direction) {
                case UP:    newY--; break;
                case DOWN:  newY++; break;
                case LEFT:  newX--; break;
                case RIGHT: newX++; break;
            }

            if (newX >= 0 && newX < size && newY >= 0 && newY < size && mazeData[newY][newX].getVertex_type() != 1) {
                x = newX;
                y = newY;
                // Update Jerry's position as a Vertex
                jerryPosition = mazeMap.getMazedata()[y][x];
                jerryHasMoved = true;// Update with new coordinates
            }
        }
    }

    /**
     * Represents the main panel where the game is drawn.<br><hr>
     *
     * OPERATIONS<br>
     * - paintComponent(Graphics g): Custom painting method for the game panel.<br>
     * - getPreferredSize(): Returns the preferred size of the game panel.<br>
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
     * Enum representing possible movement directions for Jerry in the game.<br><hr>
     *
     * ATTRIBUTES<br>
     * - Enum values: UP, DOWN, LEFT, RIGHT.
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * The main game loop
     */
    public void gameLoop() {
        jerry.move(); // Assuming move() uses mazeMap internally

        Vertex jerryPosition = getJerryPositionAsVertex();
        tom.move(mazeMap, jerryPosition);

        Vertex exitPoint = mazeMap.getExit();
        int exitX = exitPoint.gety();
        int exitY = exitPoint.getx();

        if (jerry.x == tom.x && jerry.y == tom.y) {
            timer.stop();
            tomTimer.stop();
            JOptionPane.showMessageDialog(panel, "Tom caught Jerry! You lose.");

        } else if (jerry.x == exitX&& jerry.y == exitY) {
            timer.stop();
            tomTimer.stop();
            JOptionPane.showMessageDialog(panel, "Jerry reached the Exit! You win!");

        }

        panel.repaint();
    }

    /**
     * returns the mazemap
     * @return MazeMap mazeMap
     */
    public MazeMap getMazeMap() {
        return this.mazeMap;
    }
}
