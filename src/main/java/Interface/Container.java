package Interface;

import MazeMap.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Container extends JPanel {

    private JLabel title;
    private JPanel navigation;
    private Interface screen;

    public Container(Interface screen){
        super();
        this.screen = screen;
        setSize(new Dimension(44*(26),30*(28)-12));
        setOpaque(false);
        GridBagLayout startMenuLayout = new GridBagLayout();
        setLayout(startMenuLayout);
    }

    public void setStartMenu(){
        GridBagConstraints c = new GridBagConstraints();

        title = new JLabel("<html>" + "Tom and Jerry" + "<br>" + "&nbsp;&nbsp;Maze Game" + "</html>");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(-350,0,0,40*14);  //top padding
        //c.fill = GridBagConstraints.HORIZONTAL;
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Verdana", Font.PLAIN, 65));
        //c.weighty = 0.5;
        add(title, c);

        navigation = new JPanel();
        c.gridx = 0;
        c.gridy = 1;
        //c.ipady = 40;
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(-50,0,0,40*14);  //top padding
        add(navigation, c);
        navigation.setLayout(new GridLayout(3,1,0,30));
        navigation.setOpaque(false);

        JButton play = new JButton("Play game");
        play.setFont(new Font("Arial", Font.PLAIN, 50));
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.setState(1);
                screen.display();
            }
        });
        navigation.add(play);

        JButton test = new JButton("Test game");
        test.setFont(new Font("Arial", Font.PLAIN, 50));
        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.setState(3);
                screen.display();
            }
        });
        navigation.add(test);

        JButton edit = new JButton("Edit Map");
        edit.setFont(new Font("Arial", Font.PLAIN, 50));
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.setState(2);
                screen.display();
            }
        });
        navigation.add(edit);
    }

    public void setTestMenu(){
        GridBagConstraints c = new GridBagConstraints();

        title = new JLabel("<html>" + "Game Testing" + "</html>");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(-350,0,0,40*14);  //top padding
        //c.fill = GridBagConstraints.HORIZONTAL;
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Verdana", Font.PLAIN, 65));
        //c.weighty = 0.5;
        add(title, c);

        navigation = new JPanel();
        c.gridx = 0;
        c.gridy = 1;
        //c.ipady = 40;
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(-80,0,0,40*14);  //top padding
        add(navigation, c);
        navigation.setLayout(new GridLayout(4,1,0,15));
        navigation.setOpaque(false);

        JButton testA = new JButton("Test A");
        testA.setFont(new Font("Arial", Font.PLAIN, 50));
        testA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.setState(4);
                screen.display();
            }
        });
        navigation.add(testA);

        JButton testB = new JButton("Test B");
        testB.setFont(new Font("Arial", Font.PLAIN, 50));
        testB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.setState(5);
                screen.display();
            }
        });
        navigation.add(testB);

        JButton testC = new JButton("Test C");
        testC.setFont(new Font("Arial", Font.PLAIN, 50));
        testC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.setState(6);
                screen.display();
            }
        });
        navigation.add(testC);

        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 50));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.setState(0);
                screen.display();
            }
        });
        navigation.add(back);
    }

    JPanel initSideMenu(){
        JPanel sideMenu = new JPanel();
        sideMenu.setPreferredSize(new Dimension(380,30*(25+1)));
        sideMenu.setBackground(Color.GRAY);
        GridBagLayout sideMenuLayout = new GridBagLayout();
        sideMenu.setLayout(sideMenuLayout);
        return sideMenu;
    }

    public void setEditMap(){
        setBackground(Color.GRAY);
        setOpaque(true);
        GridBagConstraints d = new GridBagConstraints();
        d.insets = new Insets(-35,-20,0,0);  //top padding

        // col 0
        d.gridx = 0;
        // row 0
        d.gridy = 0;
        screen.mazeGame.changeState(true);
        add(screen.mazeGame, d);

        // side menu
        JPanel sideMenu = initSideMenu();
        JLabel title = new JLabel();
        JLabel description = new JLabel();
        JPanel buttons = new JPanel();
        buttons.setOpaque(false);

        // set title
        title.setText("Edit Map");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 65));
        GridBagConstraints c = new GridBagConstraints();
        // col 0
        c.gridx = 0;
        // row 0
        c.gridy = 0;
        c.weighty = 0.5;
        sideMenu.add(title,c);

        // set description
        String descText = "here is the edit <br>" +
                "map function. idk <br>" +
                "would new line works <br>" +
                "here but oh well worth <br>" +
                "to try";
        description.setText("<html><p>" + descText + "</p></html>");
        description.setHorizontalAlignment(JLabel.CENTER);
        description.setFont(new Font("Arial", Font.PLAIN, 23));
        // col 0
        c.gridx = 0;
        // row 1
        c.gridy = 1;
        sideMenu.add(description,c);

        // set button
        buttons.setLayout(new GridLayout(3,1,0,15));

        /** save button */
        JButton save = new JButton("SAVE");
        save.setFont(new Font("Arial", Font.PLAIN, 40));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.mazeGame.save_MazeMap();
            }
        });

        /** load button */
        JButton load = new JButton("LOAD");
        load.setFont(new Font("Arial", Font.PLAIN, 40));
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    File selectedFile = chooser.getSelectedFile();
                    screen.mazeGame.load_MazeMap(selectedFile.getPath());
                }
            }
        });

        /** exit button */
        JButton back = new JButton("EXIT");
        back.setFont(new Font("Arial", Font.PLAIN, 40));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.setState(0);
                screen.display();
            }
        });

        buttons.add(save);
        buttons.add(load);
        buttons.add(back);
        // col 0
        c.gridx = 0;
        // row 1
        c.gridy = 2;
        c.ipadx = 150;
        c.ipady = 50;
        c.weighty = 1.5;
        sideMenu.add(buttons,c);

        // col 1
        d.gridx = 1;
        // row 0
        d.gridy = 0;
        d.insets = new Insets(-40,0,0,0);  //top padding
        add(sideMenu,d);
    }

    /** function to test for B */
    public void setShortestPathExample(){
        setBackground(Color.GRAY);
        setOpaque(true);
        GridBagConstraints d = new GridBagConstraints();

        int[][] b = {
                {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1},
                {1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1},
                {1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1},
                {1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1},
                {1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
                {1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
                {2, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0},
                {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1},
                {0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1},
                {1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 3},
                {1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
                {1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1},
                {1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1},
                {1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0},
                {1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1},
                {1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0 }};
        screen.mazeGame = new MazeMap(b);
        screen.mazeGame.changeState(false);
        if(Shortestpath.shortestPath(screen.mazeGame,screen.mazeGame.getEntry(),screen.mazeGame.getExit(),1)==null) System.out.print("No Path");

        d.insets = new Insets(-35,-20,0,0);  //top padding
        // col 0
        d.gridx = 0;
        // row 0
        d.gridy = 0;
        add(screen.mazeGame, d);

        // side menu
        JPanel sideMenu = initSideMenu();
        JLabel title = new JLabel();
        JLabel description = new JLabel();
        JPanel buttons = new JPanel();
        buttons.setOpaque(false);

        // set title
        title.setText("<html>Shortest<br>Path</html>");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 65));
        GridBagConstraints c = new GridBagConstraints();
        // col 0
        c.gridx = 0;
        // row 0
        c.gridy = 0;
        c.weighty = 0.5;
        sideMenu.add(title,c);

        // set description
        String descText = "here is the shortest<br>" +
                "path function. idk <br>" +
                "would new line works <br>" +
                "here but oh well worth <br>" +
                "to try";
        description.setText("<html><p>" + descText + "</p></html>");
        description.setHorizontalAlignment(JLabel.CENTER);
        description.setFont(new Font("Arial", Font.PLAIN, 23));
        // col 0
        c.gridx = 0;
        // row 1
        c.gridy = 1;
        sideMenu.add(description,c);

        // set button
        buttons.setLayout(new GridLayout(2,1,0,15));

        /** load button */
        JButton load = new JButton("LOAD");
        load.setFont(new Font("Arial", Font.PLAIN, 40));
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    File selectedFile = chooser.getSelectedFile();
                    screen.mazeGame.load_MazeMap(selectedFile.getPath());
                }
            }
        });

        /** exit button */
        JButton back = new JButton("EXIT");
        back.setFont(new Font("Arial", Font.PLAIN, 40));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.setState(0);
                screen.display();
            }
        });

        buttons.add(load);
        buttons.add(back);
        // col 0
        c.gridx = 0;
        // row 1
        c.gridy = 2;
        c.ipadx = 150;
        c.ipady = 50;
        c.weighty = 1.5;
        sideMenu.add(buttons,c);

        // col 1
        d.gridx = 1;
        // row 0
        d.gridy = 0;
        d.insets = new Insets(-40,0,0,0);  //top padding
        add(sideMenu,d);
    }

}

