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
    MazeMap mazeGame;
    /** start menu */
    private Container container;

    /** state of the interface to control what page now to show
     * 0: starting menu<br>
     * 1: edit map<br>
     * 2: play game<br>
     * 3: test starting<br>
     * 4: test A<br>
     * 5: test B<br>
     * 6: test C */
    private int state;

    // for testing: 1 = have choice, 2 = no choice, 3 = invalid inputs
    public static int testMode = 0;

    /** constructor */
    public Interface(){
        // init the game frame
        setTitle("Maze game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        //setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);

        setPreferredSize(new Dimension(44*(26),30*(28)-12));
        pack();

        // init the default start screen as startmenu
        setDisplayState(0);

        mazeGame = new MazeMap();
    }

    /** set the state of the interface */
    public boolean setDisplayState(int state){
        if (state < 0 || state > 6){
            return false;
        }
        this.state = state;
        return true;
    }

    /** display the interface according to the state */
    public void display(){
        getContentPane().removeAll();
        revalidate();
        repaint();

        if (state == 0){
            showMainMenu();
        }
        else if (state == 1){
            showGameWindow();
        }
        else if (state == 2){
            showMapEdit();
        }
        else if (state == 3){
            showTestingMenu();
        }
        else if (state == 4){
            showMapEdit();
        }
        else if (state == 5){
            showTestB();
        }
        else if (state == 6){
            showGameWindow();
        }
    }

    /** 0: to show the menu screen */
    public void showMainMenu(){
        container = new Container(this);
        ImageIcon img = new ImageIcon("Assets/Images/Start_BG.jpg");
        JLabel background = new JLabel();
        Image image = img.getImage(); // transform it
        Image bg = image.getScaledInstance(44*(26), 30*(28)-12,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(bg);  // transform it back
        background.setIcon(img);
        setContentPane(background);

        container.setStartMenu();
        add(container);
        setVisible(true);
    }

    /** 1: to show the game screen */
    public void showGameWindow(){
        ImageIcon img = new ImageIcon("Assets/Images/transparent.png");
        JLabel background = new JLabel();
        Image image = img.getImage(); // transform it
        Image bg = image.getScaledInstance(44*(26), 30*(28)-12,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(bg);  // transform it back
        background.setIcon(img);
        setContentPane(background);

        container = new Container(this);
        container.setGameScreen();

        add(container);
        setVisible(true);
    }

    /** 2: to show edit screen */
    public void showMapEdit(){
        ImageIcon img = new ImageIcon("Assets/Images/transparent.png");
        JLabel background = new JLabel();
        Image image = img.getImage(); // transform it
        Image bg = image.getScaledInstance(44*(26), 30*(28)-12,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(bg);  // transform it back
        background.setIcon(img);
        setContentPane(background);

        container = new Container(this);
        container.setEditMap();

        add(container);
        setVisible(true);
    }

    /** 3: show choosing testing window */
    public void showTestingMenu(){
        container = new Container(this);
        ImageIcon img = new ImageIcon("Assets/Images/Start_BG.jpg");
        JLabel background = new JLabel();
        Image image = img.getImage(); // transform it
        Image bg = image.getScaledInstance(44*(26), 30*(28)-12,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(bg);  // transform it back
        background.setIcon(img);
        setContentPane(background);

        container.setTestMenu();
        add(container);
        setVisible(true);
    }

    /** 5: show test B */
    public void showTestB(){
        ImageIcon img = new ImageIcon("Assets/Images/transparent.png");
        JLabel background = new JLabel();
        Image image = img.getImage(); // transform it
        Image bg = image.getScaledInstance(44*(26), 30*(28)-12,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(bg);  // transform it back
        background.setIcon(img);
        setContentPane(background);

        container = new Container(this);
        container.setShortestPathExample();

        add(container);
        setVisible(true);
    }
}
