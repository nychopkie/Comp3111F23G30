package Interface;

import MazeMap.*;

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
    private SideMenu sideMenu;
    /** the container for the game part */
    private JPanel gameContainer;
    /** start menu */
    private MainMenu menuContainer;

    /** constuctor */
    public Interface(){
        // init the game frame
        frame = new JFrame();
        frame.setTitle("Maze game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.pack();
        frame.setSize(new Dimension(46*(26)+12,30*(28)-16));
    }

    /** to show the menu screen */
    void showMainMenu(){
        menuContainer = new MainMenu();
        frame.add(menuContainer);
        frame.setVisible(true);
    }

    /** to show the game screen */
    void showGameWindow(){
        mazeGame = new MazeMap();
        sideMenu = new SideMenu();

        gameContainer = new JPanel();
        gameContainer.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // col 0
        c.gridx = 0;
        // row 0
        c.gridy = 0;
        gameContainer.add(mazeGame, c);

        // col 1
        c.gridx = 1;
        // row 0
        c.gridy = 0;
        // increases components width by 10 pixels
        c.ipadx = 10*40;
        // increases components height by 50 pixels
        // make the relative
        c.ipady = 30*(26)-11;
        gameContainer.add(sideMenu,c);

        frame.add(gameContainer);
        frame.setVisible(true);
    }

    // testing for now
    public static void main(String[] args0) {
        Interface screen = new Interface();
        screen.showGameWindow();
        //screen.showMainMenu();
    }
}
