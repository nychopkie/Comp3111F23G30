package Interface;

import MazeMap.MazeMap;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {

    private MazeMap mazemap;
    private SideMenu sideMenu;
    private Interface screen;

    public GameScreen(Interface screen,MazeMap mazeMap){
        this.mazemap = mazeMap;
        sideMenu = new SideMenu(mazeMap,screen);
        GridBagLayout gameLayout = new GridBagLayout();
        setLayout(gameLayout);
        GridBagConstraints c = new GridBagConstraints();

        // col 0
        c.gridx = 0;
        // row 0
        c.gridy = 0;
        add(mazeMap, c);

        // col 1
        c.gridx = 1;
        // row 0
        c.gridy = 0;
        add(sideMenu,c);
    }
}
