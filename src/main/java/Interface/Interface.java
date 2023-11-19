package Interface;

import MazeMap.*;

import java.awt.Dimension;
import javax.swing.*;
import java.awt.*;


public class Interface extends JFrame {
    // make a container containing the map and the interface here
    // need to refractor in the end to make the code more readable

    // THE ATTRIBUTES
    /** The container for all the components */
    //private JFrame frame;
    /** The map part of the interface */
    private MazeMap mazeGame;
    /** the container for the game part */
    private GameScreen gameContainer;
    /** start menu */
    private MainMenu menuContainer;

    /** state of the interface to control what page now to show
     * 0: starting menu
     * 1: edit map
     * 2: play game
     * 3: test starting
     * 4: test A
     * 5: test B
     * 6: test C
     * 7: option */
    private int state;

    /** constuctor */
    public Interface(){
        // init the game frame
        setTitle("Maze game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        setPreferredSize(new Dimension(44*(26),30*(28)-12));
        pack();

        // init the default start screen as startmenu
        setState(0);
    }

    /** set the state of the interface */
    public void setState(int state){
        this.state = state;
    }

    void clearFrame(){
        getContentPane().removeAll();
        revalidate();
        repaint();
    }

    /** display the interface according to the state */
    void display(){
        clearFrame();

        if (state == 0){
            showMainMenu();
            //showGameWindow();
        }
        else if (state == 1){
            showGameWindow();
        }
        else if (state == 3){
            showTestingMenu();
        }
    }

    /** to show the menu screen */
    void showMainMenu(){
        menuContainer = new MainMenu(this);
        ImageIcon img = new ImageIcon("Assets/Images/Start_BG.jpg");
        JLabel background = new JLabel();
        Image image = img.getImage(); // transform it
        Image bg = image.getScaledInstance(44*(26), 30*(28)-12,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(bg);  // transform it back
        background.setIcon(img);
        setContentPane(background);

        menuContainer.setStartMenu();
        add(menuContainer);
        setVisible(true);
    }

    void showTestingMenu(){
        menuContainer = new MainMenu(this);
        ImageIcon img = new ImageIcon("Assets/Images/Start_BG.jpg");
        JLabel background = new JLabel();
        Image image = img.getImage(); // transform it
        Image bg = image.getScaledInstance(44*(26), 30*(28)-12,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(bg);  // transform it back
        background.setIcon(img);
        setContentPane(background);

        menuContainer.setTestMenu();
        add(menuContainer);
        setVisible(true);
    }

    /** to show the game screen */
    void showGameWindow(){
        mazeGame = new MazeMap();
        gameContainer = new GameScreen(this,mazeGame);

        add(gameContainer);
        setVisible(true);
    }
}
