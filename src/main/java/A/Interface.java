package A;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;


public class Interface{
    // make a container containing the map and the interface here
    // need to refractor in the end to make the code more readable

    // THE ATTRIBUTES
    /** The container for all the components */
    private JFrame frame;
    /** The map part of the interface */
    private MazeMap mazeGame;
    /** The side control navigation menu */
    private JPanel sideMenu;
    /** the container for the game part */
    private JPanel gameContainer;
    /** start menu */
    private JPanel menuContainer;

    /** constuctor */
    public Interface(){
        frame = new JFrame();
        frame.setTitle("Maze game");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.pack();
        frame.setSize(screenSize.width,screenSize.height);
        //frame.setLayout(new GridLayout(1, 2));
    }

    /** to show the menu screen */
    void showMainMenu(){
        menuContainer = new JPanel();
        frame.add(menuContainer);
    }

    /** to show the game screen */
    void showGameWindow(){
        mazeGame = new MazeMap();

        sideMenu = new JPanel();
        sideMenu.setSize(new Dimension(10*(25+1),30*(25+1)));
        sideMenu.setMaximumSize(new Dimension(10*(25+1),30*(25+1)));
        sideMenu.setBackground(Color.PINK);

        gameContainer = new JPanel();
        gameContainer.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameContainer.add(mazeGame);
        gameContainer.add(sideMenu);
        frame.add(gameContainer);
        frame.setVisible(true);
    }

    // testing for now
    public static void main(String[] args0) {
        Interface screen = new Interface();
        screen.showGameWindow();
    }
}
