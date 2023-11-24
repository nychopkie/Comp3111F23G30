package Interface;

import MazeMap.*;
import MazeGame.*;

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

    // handler for mouse button to test
    public void handleNavButton(int state){
        screen.setDisplayState(state);
        screen.display();
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
                handleNavButton(1);
            }
        });
        navigation.add(play);

        JButton test = new JButton("Shortest Path");
        test.setFont(new Font("Arial", Font.PLAIN, 50));
        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNavButton(5);
            }
        });
        navigation.add(test);

        JButton edit = new JButton("Edit Map");
        edit.setFont(new Font("Arial", Font.PLAIN, 50));
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNavButton(2);
            }
        });
        navigation.add(edit);
    }

    public JPanel initSideMenu(){
        JPanel sideMenu = new JPanel();
        sideMenu.setPreferredSize(new Dimension(380,30*(25+1)));
        sideMenu.setBackground(Color.GRAY);
        GridBagLayout sideMenuLayout = new GridBagLayout();
        sideMenu.setLayout(sideMenuLayout);
        return sideMenu;
    }

    public void setEditMap(){
        screen.mazeGame = new MazeMap();
        setBackground(Color.GRAY);
        setOpaque(true);
        GridBagConstraints d = new GridBagConstraints();
        d.insets = new Insets(-35,-20,0,0);  //top padding

        // col 0
        d.gridx = 0;
        // row 0
        d.gridy = 0;
        screen.mazeGame.changeState(true);
        screen.mazeGame.refreshColour();
        add(screen.mazeGame, d);

        // side menu
        JPanel sideMenu = initSideMenu();
        JLabel title = new JLabel();
        JLabel description = new JLabel();
        JPanel buttons = new JPanel();
        buttons.setOpaque(false);

        // set title
        title.setText("<html><b>Edit Map</b></html>");
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
        String descText = "Here is the edit map function.<hr>" +
                "gray cell = barrier <br>" +
                "white cell = path <br>" +
                "blue cell = entry <br>" +
                "red cell = exit<hr>" +
                "click the gray or white cells<br>" +
                "to draw the map";
        description.setText("<html><p>" + descText + "</p></html>");
        description.setHorizontalAlignment(JLabel.CENTER);
        description.setFont(new Font("Arial", Font.PLAIN, 20));
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
                handleNavButton(0);
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

    public boolean handleSPLoad(){
        JFileChooser chooser = new JFileChooser();
        int returnVal;

        boolean flag = true;
        while (flag){
            // for unit test
            if (Interface.testMode == 1){
                returnVal = JFileChooser.APPROVE_OPTION;
                File file = new File("Assets/Test_map/MazeMap_SAMPLE2.csv");
                chooser.setSelectedFile(file);
            }
            else if (Interface.testMode == 2){
                returnVal = JFileChooser.CANCEL_OPTION;
            }
            else if (Interface.testMode == 3){
                returnVal = JFileChooser.APPROVE_OPTION;
                File file = new File("Assets/Test_map/notCsv.txt");
                chooser.setSelectedFile(file);
            }
            else if (Interface.testMode == 4){
                returnVal = JFileChooser.APPROVE_OPTION;
                File file = new File("Assets/Test_map/MazeMap_InvalidExample.csv");
                chooser.setSelectedFile(file);
            }
            else{
                // human control
                returnVal = chooser.showOpenDialog(null);
            }

            if (returnVal == JFileChooser.APPROVE_OPTION){
                File selectedFile = chooser.getSelectedFile();
                if (!selectedFile.getPath().endsWith(".csv")) {
                    JOptionPane pane = new JOptionPane("Chosen file is not a .csv file, please load a valid map.",JOptionPane.WARNING_MESSAGE);
                    JDialog dialog = pane.createDialog(null, "warning");
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(5000);
                            } catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    dialog.setModal(false);
                    dialog.setFocusable(true);
                    dialog.setVisible(true);
                    //JOptionPane.showMessageDialog(screen.mazeGame, "Chosen file is not a .csv file, please load a valid map.");
                    if (Interface.testMode == 0){
                        continue;
                    }else{
                        return flag;
                    }
                }
                screen.mazeGame.load_MazeMap(selectedFile.getPath());
                if(Shortestpath.shortestPath(screen.mazeGame,screen.mazeGame.getEntry(),screen.mazeGame.getExit(),1)==null){
                    JOptionPane pane = new JOptionPane("Not a valid map, please choose another map",JOptionPane.WARNING_MESSAGE);
                    JDialog dialog = pane.createDialog(null, "warning");
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(5000);
                            } catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    dialog.setModal(false);
                    dialog.setFocusable(true);
                    dialog.setVisible(true);
                    //JOptionPane.showMessageDialog(screen.mazeGame, "Not a valid map, please choose another map");
                    if (Interface.testMode == 0){
                        continue;
                    }else{
                        return flag;
                    }
                }

                flag = false;
                screen.mazeGame.load_MazeMap(selectedFile.getPath());
                if(Shortestpath.shortestPath(screen.mazeGame,screen.mazeGame.getEntry(),screen.mazeGame.getExit(),1)==null) System.out.print("No Path");
            }
            else{
                flag = false;
            }
        }
        return flag;
    }

    /** function to test for B */
    public void setShortestPathExample(){
        setBackground(Color.GRAY);
        setOpaque(true);
        GridBagConstraints d = new GridBagConstraints();

        screen.mazeGame = new MazeMap();
        screen.mazeGame.load_MazeMap("Assets/map/MazeMap_shortestPathExample.csv");
        screen.mazeGame.changeState(false);
        Shortestpath.shortestPath(screen.mazeGame,screen.mazeGame.getEntry(),screen.mazeGame.getExit(),1);

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
        title.setText("<html><b>Shortest<br>Path</b></html>");
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
        String descText = "Here is the shortest path function.<hr>" +
                "Click load to load a map<hr>" ;
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
                handleSPLoad();
            }
        });

        /** exit button */
        JButton back = new JButton("EXIT");
        back.setFont(new Font("Arial", Font.PLAIN, 40));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNavButton(0);
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

    public boolean setGameScreen(){
        JFileChooser chooser = new JFileChooser();
        int returnVal;

        boolean flag = true;
        while (flag){
            // for unit test
            if (Interface.testMode == 1){
                returnVal = JFileChooser.APPROVE_OPTION;
                File file = new File("Assets/Test_map/MazeMap_SAMPLE2.csv");
                chooser.setSelectedFile(file);
            }
            else if (Interface.testMode == 2){
                returnVal = JFileChooser.CANCEL_OPTION;
            }
            else if (Interface.testMode == 3){
                returnVal = JFileChooser.APPROVE_OPTION;
                File file = new File("Assets/Test_map/notCsv.txt");
                chooser.setSelectedFile(file);
            }
            else if (Interface.testMode == 4){
                returnVal = JFileChooser.APPROVE_OPTION;
                File file = new File("Assets/Test_map/MazeMap_InvalidExample.csv");
                chooser.setSelectedFile(file);
            }
            else{
                // human control
                returnVal = chooser.showOpenDialog(null);
            }

            if (returnVal == JFileChooser.APPROVE_OPTION){
                File selectedFile = chooser.getSelectedFile();
                if (!selectedFile.getPath().endsWith(".csv")) {
                    //JOptionPane.showMessageDialog(this, "Chosen file is not a .csv file, please load a valid map.");
                    JOptionPane pane = new JOptionPane("Chosen file is not a .csv file, please load a valid map.",JOptionPane.WARNING_MESSAGE);
                    JDialog dialog = pane.createDialog(null, "warning");
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(5000);
                            } catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    dialog.setModal(false);
                    dialog.setFocusable(true);
                    dialog.setVisible(true);
                    if (Interface.testMode == 0){
                        continue;
                    }else{
                        return flag;
                    }
                };
                screen.mazeGame.load_MazeMap(selectedFile.getPath());
                if(Shortestpath.shortestPath(screen.mazeGame,screen.mazeGame.getEntry(),screen.mazeGame.getExit(),1)==null){
                    System.out.print("No Path");
                    //JOptionPane.showMessageDialog(this, "Not a valid map, please choose another map");
                    JOptionPane pane = new JOptionPane("Not a valid map, please choose another map",JOptionPane.WARNING_MESSAGE);
                    JDialog dialog = pane.createDialog(null, "warning");
                    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(5000);
                            } catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    dialog.setModal(false);
                    dialog.setFocusable(true);
                    dialog.setVisible(true);
                    if (Interface.testMode == 0){
                        continue;
                    }else{
                        return flag;
                    }
                }

                flag = false;

                setOpaque(true);
                setBackground(Color.GRAY);
                MazeGame MazeGame =  new MazeGame(selectedFile.getPath(),screen);

                setOpaque(true);
                setBackground(Color.GRAY);
                GridBagConstraints d = new GridBagConstraints();
                d.insets = new Insets(-35,-30,0,0);  //top padding

                // col 0
                d.gridx = 0;
                // row 0
                d.gridy = 0;
                add(MazeGame.getPanel(),d);

                // side menu
                JPanel sideMenu = initSideMenu();
                JLabel title = new JLabel();
                JLabel description = new JLabel();
                JPanel buttons = new JPanel();
                buttons.setOpaque(false);

                // set title
                title.setText("<html><b>Play Game</b></html>");
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
                String descText = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Instruction<hr>" +
                        "Press arrow key to control<br>" +
                        "Jerry (Yellow dot) to move to <br>" +
                        "the exit. Don't get caught by <br> " +
                        "Tom (Blue dot)";
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

                /** save button */
                JButton restart = new JButton("Restart");
                restart.setFont(new Font("Arial", Font.PLAIN, 40));
                restart.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MazeGame.stopTimer();
                        handleNavButton(1);
                    }
                });

                /** exit button */
                JButton back = new JButton("EXIT");
                back.setFont(new Font("Arial", Font.PLAIN, 40));
                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MazeGame.stopTimer();
                        handleNavButton(0);
                    }
                });

                buttons.add(restart);
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
                d.insets = new Insets(-40,0,0,-20);
                add(sideMenu,d);
            }
            else{
                screen.setDisplayState(0);
                screen.display();
                return flag;
            }
        }
    return flag;
    }

}

