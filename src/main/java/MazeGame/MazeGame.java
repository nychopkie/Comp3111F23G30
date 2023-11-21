package MazeGame;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import MazeMap.Vertex;
import MazeMap.Shortestpath;
import MazeMap.MazeMap;

public class MazeGame extends JFrame {
    private MazeMap mazeMap;
    private final int size = 30;
    private Tom tom;
    private Jerry jerry;
    private GamePanel panel;
    private Timer timer;
    private final int DELAY = 500; // Milliseconds, adjust for speed for Jerry
    private Timer tomTimer;
    private final int TOM_DELAY =400; // Shorter delay for Tom's movement
    private final int sizeOfSquare = 10;

    private Vertex jerryPosition;

    public MazeGame() {

        mazeMap = new MazeMap();
        loadMaze("Assets/map/MazeMap_SAMPLE.csv"); // change this
        Vertex entryPoint = mazeMap.getEntry();
        jerryPosition = new Vertex(sizeOfSquare, entryPoint.getx(), entryPoint.gety(), 0); // Assuming 0 is the correct vertex type

        add(mazeMap);

        panel = new GamePanel();
        add(panel);

//        tom = new Tom(29, 1); // Assuming the exit point is at (29, 2) //x,y -> col,row
//        jerry = new Jerry(0, 12); // Assuming the entry point is at (0, 13) //x,y ->col,row

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!jerry.isOnPath) {
                    jerry.moveRightUntilPath(mazeMap);
                } else {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:    jerry.setDirection(Direction.UP); break;
                        case KeyEvent.VK_DOWN:  jerry.setDirection(Direction.DOWN); break;
                        case KeyEvent.VK_LEFT:  jerry.setDirection(Direction.LEFT); break;
                        case KeyEvent.VK_RIGHT: jerry.setDirection(Direction.RIGHT); break;
                    }
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

        Vertex entryPoint = mazeMap.getEntry();

        Vertex exitPoint = mazeMap.getExit();


        tom = new Tom(exitPoint.getx(), exitPoint.gety());
        jerry = new Jerry(entryPoint.getx(), entryPoint.gety());
        jerryPosition = new Vertex(sizeOfSquare, entryPoint.getx(), entryPoint.gety(), 0);

        // Additional setup if necessary
    }


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
    private boolean jerryHasMoved=false;
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
            return mazeMap.getMazedata()[this.y][this.x]; // Replace '0' with the correct vertex type for Tom
        }
        public void move(MazeMap mazeMap, Vertex jerryPosition) {

            if (!jerryHasMoved) {
                return; // Don't move Tom until Jerry has moved
            }

            if (!hasReachedPath) {
                moveLeftUntilPath(mazeMap);
            }

            // Recalculate the path to Jerry in every move call
            calculatePathToJerry(mazeMap, jerryPosition);
            followPathStepByStep();
        }

        private void moveLeftUntilPath(MazeMap mazeMap) {
            // Check if the left position is a path or Tom is still in the barrier
            if (this.x > 0 && (mazeMap.getMazedata()[this.y][this.x - 1].getVertex_type() == 0 || mazeMap.getMazedata()[this.y][this.x].getVertex_type() != 0)) {
                this.x--; // Move left

                if (mazeMap.getMazedata()[this.y][this.x].getVertex_type() == 0) {
                    hasReachedPath = true; // Tom has reached the path
                }
            }
        }

        private void calculatePathToJerry(MazeMap mazeMap, Vertex jerryPosition) {
            pathToJerry = Shortestpath.shortestPath(mazeMap, this.getCurrentPosition(), jerryPosition, 0);
            pathIndex = 1; // Reset path index to start following the new path
        }

        private void followPathStepByStep() {
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


                //pathIndex++; // Increment the path index to move to the next step in the next call
            }
        }


    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    class Jerry extends GameEntity {
        private Direction direction;
        private boolean isOnPath = false;

        public Jerry(int x, int y) {
            super(y, x, Color.ORANGE);// swaped
            this.direction = Direction.LEFT; // Initial direction
//            System.out.println("Jerry initialized at: (" + x + ", " + y + ")");
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public void move() {
            if (!isOnPath) {
                moveRightUntilPath(mazeMap);
                return;
            }
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
        private void moveRightUntilPath(MazeMap mazeMap) {
            if (this.x < mazeMap.getCOLS() - 1 && (mazeMap.getMazedata()[this.y][this.x + 1].getVertex_type() == 0 || mazeMap.getMazedata()[this.y][this.x].getVertex_type() != 0)) {
                this.x++; // Move right

                if (mazeMap.getMazedata()[this.y][this.x].getVertex_type() == 0) {
                    isOnPath = true; // Jerry has reached the path
                }
            }
        }


        private boolean isValidMove(int newX, int newY, Vertex[][] mazeData) {
            return newX >= 0 && newX < size && newY >= 0 && newY < size && mazeData[newY][newX].getVertex_type() == 0;
        }

    }
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
    public int findClearVertexRowInLastColumn() {
        Vertex[][] mazeData = mazeMap.getMazedata();
        int lastColIndex = mazeData[0].length - 1; // Assuming all rows have the same number of columns

        for (int row = 0; row < mazeData.length; row++) {
            if (mazeData[row][lastColIndex].getVertex_type() == 0) {
                return row; // Return the row index if the vertex is clear
            }
        }

        return -1; // Return -1 if no clear vertex is found in the last column
    }
    private void gameLoop() {
        jerry.move(); // Assuming move() uses mazeMap internally

        Vertex jerryPosition = getJerryPositionAsVertex();
        tom.move(mazeMap, jerryPosition);

        Vertex exitPoint = mazeMap.getExit();
//        int exitX = exitPoint.gety();
//        int exitY = exitPoint.getx();
//
//        System.out.println("x"+(mazeMap.getMazedata()[0].length - 1));
//        System.out.println("y"+findClearVertexRowInLastColumn());


        if (jerry.x == tom.x && jerry.y == tom.y) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Tom caught Jerry! You lose.");
        } else if (jerry.x == (mazeMap.getMazedata()[0].length - 1) && jerry.y == findClearVertexRowInLastColumn()) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Jerry reached the Exit! You win!");
        }

        panel.repaint();
    }


    // **** test cases
//    public int[][] getMaze() {
//        return maze;
//    }
//    public int[] getJerryPosition() {
//        return new int[] { jerry.x, jerry.y };
//    }
//    public void moveJerry(Direction direction) {
//        jerry.setDirection(direction);
//        jerry.move(maze);
//    }


    public static void main(String[] args) {
        new MazeGame();
    }
}