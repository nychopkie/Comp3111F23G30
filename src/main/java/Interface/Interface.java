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
     * 0: starting menu
     * 1: edit map
     * 2: play game
     * 3: test starting
     * 4: test A
     * 5: test B
     * 6: test C */
    private int state;

    /** constructor */
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
        setState(5);

        mazeGame = new MazeMap();
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
        else if (state == 2){
            showMapEdit();
        }
        else if (state == 3){
            showTestingMenu();
        }
        else if (state == 4){
            showTestA();
        }
        else if (state == 5){
            showTestB();
        }
        else if (state == 6){
            showTestC();
        }
    }

    /** 0: to show the menu screen */
    void showMainMenu(){
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
    void showGameWindow(){
        container = new Container(this);
        container.setEditMap();

        add(container);
        setVisible(true);
    }

    /** 2: to show edit screen */
    void showMapEdit(){
        container = new Container(this);
        container.setEditMap();

        add(container);
        setVisible(true);
    }

    /** 3: show choosing testing window */
    void showTestingMenu(){
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

    /** 4: show test A */
    void showTestA(){
        container = new Container(this);
        container.setEditMap();

        add(container);
        setVisible(true);
    }

    /** 5: show test B */
    void showTestB(){
        container = new Container(this);
        container.setShortestPathExample();

        add(container);
        setVisible(true);
    }

    /** 6: show test C */
    void showTestC(){
        container = new Container(this);
        container.setEditMap();

        add(container);
        setVisible(true);
    }
}
