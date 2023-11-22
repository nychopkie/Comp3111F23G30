import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import MazeMap.Vertex;
import MazeMap.Shortestpath;
import MazeMap.MazeMap;

/**
 * =========== MazeGame Class ===========
 * Represents the main game window for the Tom and Jerry maze game.
 *
 * @attributes:
 * 1. mazeMap: MazeMap object representing the game's maze.
 * 2. size: Fixed size of the maze.
 * 3. tom: GameEntity representing Tom.
 * 4. jerry: GameEntity representing Jerry.
 * 5. panel: JPanel for rendering game components.
 * 6. timer: Timer for game loop and Jerry's movement.
 * 7. DELAY: Delay time for Jerry's movement.
 * 8. tomTimer: Timer for Tom's movement.
 * 9. TOM_DELAY: Delay time for Tom's movement.
 * 10. sizeOfSquare: Size of each square in the maze.
 * 11. jerryPosition: Current position of Jerry in the maze.
 * 12. entryPoint: Entry point vertex of the maze.
 * 13. exitPoint: Exit point vertex of the maze.
 *
 * @operations:
 * 1. MazeGame: Constructor to initialize the game.
 * 2. loadMaze: Loads the maze configuration from a file.
 * 3. getJerryPositionAsVertex: Returns Jerry's current position as a Vertex.
 * 4. gameLoop: Main game loop to handle game logic.
 * 5. moveJerry: Updates Jerry's position based on direction.
 * 6. isGameWon: Checks if the game is won.
 * 7. isGameLost: Checks if the game is lost.
 * 8. main: Entry point to start the game.
 */
public class MazeGame extends JFrame {
    private MazeMap mazeMap;
    private final int size = 30;
    private Tom tom;
    private Jerry jerry;
    private GamePanel panel;
    private Timer timer;
    private final int DELAY = 400; // Milliseconds, adjust for speed for Jerry
    private Timer tomTimer;
    private final int TOM_DELAY =300; // Shorter delay for Tom's movement
    private final int sizeOfSquare = 10;

    private Vertex jerryPosition;
    Vertex entryPoint;
    Vertex exitPoint;

    /**
     * MazeGame Constructor: Initializes the game window, loads the maze,
     * sets up game entities, and starts the game timers.
     */
    public MazeGame() {

        mazeMap = new MazeMap();
        loadMaze("/Users/meng/IdeaProjects/Tom and Jerry/src/main/java/MazaMap_TnJ.csv"); // change this

        jerryPosition = new Vertex(sizeOfSquare, entryPoint.getx(), entryPoint.gety(), 0);


        add(mazeMap);

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
                panel.repaint();
            }
        });
        setFocusable(true);
        setVisible(true);

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
     * 1. x, y: Coordinates of the entity in the maze.
     * 2. color: The color used to represent the entity on the game panel.
     *
     * @operations:
     * 1. GameEntity: Constructor to initialize the entity.
     * 2. draw: Draws the entity on the game panel.
     */
    class GameEntity {
        int x, y;
        Color color;

        public GameEntity(int x, int y, Color color) {
            this.x = x; //col
            this.y = y; //row
            this.color = color;
        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(x * 10, y * 10, 10, 10);
        }
    }
    public boolean jerryHasMoved=false;
    /**
     * =========== Tom Class ===========
     * Represents the character Tom in the game, extending the GameEntity class.
     *
     * @attributes:
     * Inherits x, y, color from GameEntity.
     * Additional attributes:
     * 1. hasReachedPath: Indicates if Tom has reached his path.
     * 2. pathToJerry: Array storing the calculated path to Jerry.
     * 3. pathIndex: Current index in the pathToJerry array.
     *
     * @operations:
     * 1. Tom: Constructor to initialize Tom's position and color.
     * 2. getCurrentPosition: Returns Tom's current position as a Vertex.
     * 3. move: Moves Tom towards Jerry based on the calculated path.
     * 4. calculatePathToJerry: Calculates the shortest path to Jerry.
     * 5. followPathStepByStep: Follows the calculated path one step at a time.
     * 6. setPositionForTesting: Sets Tom's position for testing purposes.
     * 7. getPathToJerry: Returns the current path to Jerry.
     */
    class Tom extends GameEntity {
        private boolean hasReachedPath = false;
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
                return; // Don't move Tom until Jerry has moved
            }

            // Recalculate the path to Jerry in every move call
            calculatePathToJerry(mazeMap, jerryPosition);
            followPathStepByStep();
        }


        public void calculatePathToJerry(MazeMap mazeMap, Vertex jerryPosition) {
            pathToJerry = Shortestpath.shortestPath(mazeMap, this.getCurrentPosition(), jerryPosition, 0);
            pathIndex = 1; // Reset path index to start following the new path
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
        //*** for test case
        public void setPositionForTesting(Vertex newPosition) {
            this.x = newPosition.getx();
            this.y = newPosition.gety();
        }
    }

    /**
     * =========== Jerry Class ===========
     * Represents the character Jerry in the game, extending the GameEntity class.
     *
     * @attributes:
     * Inherits x, y, color from GameEntity.
     * Additional attributes:
     * 1. direction: The current movement direction of Jerry.
     *
     * @operations:
     * 1. Jerry: Constructor to initialize Jerry's position, color, and initial direction.
     * 2. setDirection: Sets the movement direction of Jerry.
     * 3. move: Moves Jerry based on the current direction.
     * 4. isValidMove: Checks if a move is valid within the maze.
     * 5. setPositionForTesting: Sets Jerry's position for testing purposes.
     * 6. getDirection: Returns Jerry's current movement direction.
     */
    class Jerry extends GameEntity {
        private Direction direction;

        public Jerry(int x, int y) {
            super(y, x, Color.ORANGE);// swaped
            this.direction = Direction.LEFT; // Initial direction
//            System.out.println("Jerry initialized at: (" + x + ", " + y + ")");
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
        private boolean isValidMove(int newX, int newY, Vertex[][] mazeData) {
            return newX >= 0 && newX < size && newY >= 0 && newY < size && mazeData[newY][newX].getVertex_type() != 1;
        }

        //*** for test case
        public void setPositionForTesting(Vertex newPosition) {
            this.x = newPosition.getx();
            this.y = newPosition.gety();
        }
        // Method to get the x-coordinate of Jerry
        public int getx() {
            return this.x;
        }
        // Method to get the y-coordinate of Jerry
        public int gety() {
            return this.y;
        }

    }
    /**
     * =========== GamePanel Class ===========
     * Represents the main panel where the game is drawn.
     *
     * @attributes:
     * No additional attributes.
     *
     * @operations:
     * 1. paintComponent: Custom painting method for the game panel.
     * 2. getPreferredSize: Returns the preferred size of the game panel.
     */
    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Vertex[][] mazeData = mazeMap.getMazedata();
            for (int row = 0; row < mazeData.length; row++) {
                for (int col = 0; col < mazeData[row].length; col++) {
                    if (mazeData[row][col].getVertex_type() == 1) {
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(col * 10, row * 10, 10, 10);
                    }
                }
            }
            tom.draw(g);
            jerry.draw(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(300, 300);
        }
    }
    /**
     * =========== Direction Enum ===========
     * Represents the possible movement directions for Jerry in the game.
     *
     * @attributes:
     * Enum values: UP, DOWN, LEFT, RIGHT.
     *
     * @operations:
     * No operations, as this is an enum.
     */
    enum Direction {
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
            JOptionPane.showMessageDialog(this, "Tom caught Jerry! You lose.");

        } else if (jerry.x == exitX&& jerry.y == exitY) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Jerry reached the Exit! You win!");

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
    public void moveJerry(Direction direction) {
        // Logic to move Jerry based on the direction
        // This might involve setting Jerry's direction and then calling a method to update his position
        this.jerry.setDirection(direction);
        this.jerry.move();
        jerryHasMoved = true;
    }

    public boolean isGameWon() {
        Vertex jerryPos = new Vertex(0,getJerry().getx(),getJerry().gety(),0);
        Vertex exitPoint = mazeMap.getExit();
        return (jerryPos.getx()==exitPoint.getx()) && (jerryPos.gety()==exitPoint.gety());
    }
    public boolean isGameLost() {
        Vertex jerryPos = new Vertex(0,getJerry().getx(),getJerry().gety(),0);
        Vertex tomPos = getTom().getCurrentPosition();
        return (jerryPos.getx()==tomPos.getx()) && (jerryPos.gety()==tomPos.gety());
    }

    public static void main(String[] args) {
        new MazeGame();
    }
}
