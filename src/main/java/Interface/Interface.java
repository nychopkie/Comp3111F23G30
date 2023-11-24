package Interface;

import MazeGame.MazeGame;
import MazeMap.*;

import java.awt.Dimension;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/** class that displays the game<br><hr>
 *ATTRIBUTES<br>
 * 1. MazeGame: the maze map<br>
 * 2. state: the state that the screen should be displaying<br>
 * 3. GAP: the gap size between each vertex<br>
 * 4. static testMode: for turning on test mode for unit test<hr>
 * OPERATIONS<br>
 * 1. Interface()<br>
 * 2. setDisplayState(int state)<br>
 * 3. display()<br>
 * 4. showMainMenu()<br>
 * 5. showGameWindow()<br>
 * 6. showMapEdit()<br>
 * 7. showTestB()<br>
 * 8. handleNavButton(int state)<br>
 * 9. handleSPLoad()
 **/
public class Interface extends JFrame {

    // THE ATTRIBUTES
    /** The map part of the interface */
    MazeMap mazeGame;

    /** state of the interface to control what page now to show<br>
     * 0: starting menu<br>
     * 1: edit map<br>
     * 2: play game<br>
     * 5: test shortestPath<br>
     **/
    private int state;

    /**
     * for testing:<br>
     * 1 = have choice<br>
     * 2 = no choice<br>
     * 3 = invalid inputs (not csv)<br>
     * 4 = invalid input (unplayable)<br>
     * 0 = not test
     */
    public static int testMode = 0;

    /** constructor of Interface */
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

    /**
     * set the state of the interface
     * @param state the state of the Display
     * @return true if valid state, else false
     */
    public boolean setDisplayState(int state){
        if (state < 0 || state > 6){
            return false;
        }
        this.state = state;
        return true;
    }

    /** refresh and display the interface according to the state */
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
        else if (state == 5){
            showTestB();
        }
    }

    /**
     * handler for mouse button to test
     * @param state
     */
    public void handleNavButton(int state){
        setDisplayState(state);
        display();
    }

    /** state 0: to show the menu screen */
    public void showMainMenu(){
        JPanel container = new JPanel();
        container.setSize(new Dimension(44*(26),30*(28)-12));
        container.setOpaque(false);
        GridBagLayout startMenuLayout = new GridBagLayout();
        container.setLayout(startMenuLayout);

        ImageIcon img = new ImageIcon("Assets/Images/Start_BG.jpg");
        JLabel background = new JLabel();
        Image image = img.getImage(); // transform it
        Image bg = image.getScaledInstance(44*(26), 30*(28)-12,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(bg);  // transform it back
        background.setIcon(img);
        setContentPane(background);

        // container code
        GridBagConstraints c = new GridBagConstraints();

        JLabel title = new JLabel("<html>" + "Tom and Jerry" + "<br>" + "&nbsp;&nbsp;Maze Game" + "</html>");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(-350,0,0,40*14);  //top padding
        //c.fill = GridBagConstraints.HORIZONTAL;
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Verdana", Font.PLAIN, 65));
        //c.weighty = 0.5;
        container.add(title, c);

        JPanel navigation = new JPanel();
        c.gridx = 0;
        c.gridy = 1;
        //c.ipady = 40;
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(-50,0,0,40*14);  //top padding
        container.add(navigation, c);
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

        // add container to the thing
        add(container);
        setVisible(true);
    }

    /** state 1: to show the game screen */
    public boolean showGameWindow(){
        ImageIcon img = new ImageIcon("Assets/Images/transparent.png");
        JLabel background = new JLabel();
        Image image = img.getImage(); // transform it
        Image bg = image.getScaledInstance(44*(26), 30*(28)-12,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(bg);  // transform it back
        background.setIcon(img);
        setContentPane(background);

        // container code
        JPanel container = new JPanel();
        container.setSize(new Dimension(44*(26),30*(28)-12));
        container.setOpaque(false);
        GridBagLayout startMenuLayout = new GridBagLayout();
        container.setLayout(startMenuLayout);

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
                mazeGame.load_MazeMap(selectedFile.getPath());
                if(Shortestpath.shortestPath(mazeGame,mazeGame.getEntry(),mazeGame.getExit(),1)==null){
                    System.out.print("No Path");
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

                MazeGame MazeGame =  new MazeGame(selectedFile.getPath(),this);

                container.setOpaque(true);
                container.setBackground(Color.GRAY);
                GridBagConstraints d = new GridBagConstraints();
                d.insets = new Insets(-35,-30,0,0);  //top padding

                // col 0
                d.gridx = 0;
                // row 0
                d.gridy = 0;
                container.add(MazeGame.getPanel(),d);

                // side menu
                JPanel sideMenu = new JPanel();
                sideMenu.setPreferredSize(new Dimension(380,30*(25+1)));
                sideMenu.setBackground(Color.GRAY);
                GridBagLayout sideMenuLayout = new GridBagLayout();
                sideMenu.setLayout(sideMenuLayout);

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
                container.add(sideMenu,d);
            }
            else{
                setDisplayState(0);
                display();
                return flag;
            }
        }

        // add container to interface
        add(container);
        setVisible(true);
        return flag;
    }

    /** state 2: to show edit screen */
    public void showMapEdit(){
        ImageIcon img = new ImageIcon("Assets/Images/transparent.png");
        JLabel background = new JLabel();
        Image image = img.getImage(); // transform it
        Image bg = image.getScaledInstance(44*(26), 30*(28)-12,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(bg);  // transform it back
        background.setIcon(img);
        setContentPane(background);

        // container
        JPanel container = new JPanel();
        container.setSize(new Dimension(44*(26),30*(28)-12));
        container.setOpaque(false);
        GridBagLayout startMenuLayout = new GridBagLayout();
        container.setLayout(startMenuLayout);

        mazeGame = new MazeMap();
        container.setBackground(Color.GRAY);
        container.setOpaque(true);
        GridBagConstraints d = new GridBagConstraints();
        d.insets = new Insets(-35,-20,0,0);  //top padding

        // col 0
        d.gridx = 0;
        // row 0
        d.gridy = 0;
        Vertex.canEdit = true;
        mazeGame.refreshColour();
        container.add(mazeGame, d);

        // side menu
        JPanel sideMenu = new JPanel();
        sideMenu.setPreferredSize(new Dimension(380,30*(25+1)));
        sideMenu.setBackground(Color.GRAY);
        GridBagLayout sideMenuLayout = new GridBagLayout();
        sideMenu.setLayout(sideMenuLayout);

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
                mazeGame.save_MazeMap();
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
                    mazeGame.load_MazeMap(selectedFile.getPath());
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
        container.add(sideMenu,d);

        // add container to map
        add(container);
        setVisible(true);
    }

    /**
     * help test the button click of the shortest path function
     * @return true if cannot load, false if can load or cancel operation
     */
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
                mazeGame.load_MazeMap(selectedFile.getPath());
                if(Shortestpath.shortestPath(mazeGame,mazeGame.getEntry(),mazeGame.getExit(),1)==null){
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
                mazeGame.load_MazeMap(selectedFile.getPath());
                Shortestpath.shortestPath(mazeGame,mazeGame.getEntry(),mazeGame.getExit(),1);
            }
            else{
                flag = false;
            }
        }
        return flag;
    }

    /** state 5: show the shortest path */
    public void showTestB(){
        ImageIcon img = new ImageIcon("Assets/Images/transparent.png");
        JLabel background = new JLabel();
        Image image = img.getImage(); // transform it
        Image bg = image.getScaledInstance(44*(26), 30*(28)-12,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        img = new ImageIcon(bg);  // transform it back
        background.setIcon(img);
        setContentPane(background);

        // container
        JPanel container = new JPanel();
        container.setSize(new Dimension(44*(26),30*(28)-12));
        container.setOpaque(false);
        GridBagLayout startMenuLayout = new GridBagLayout();
        container.setLayout(startMenuLayout);

        container.setBackground(Color.GRAY);
        container.setOpaque(true);
        GridBagConstraints d = new GridBagConstraints();

        mazeGame = new MazeMap();
        mazeGame.load_MazeMap("Assets/map/MazeMap_shortestPathExample.csv");
        Vertex.canEdit = false;
        Shortestpath.shortestPath(mazeGame,mazeGame.getEntry(),mazeGame.getExit(),1);

        d.insets = new Insets(-35,-20,0,0);  //top padding
        // col 0
        d.gridx = 0;
        // row 0
        d.gridy = 0;
        container.add(mazeGame, d);

        // side menu
        JPanel sideMenu = new JPanel();
        sideMenu.setPreferredSize(new Dimension(380,30*(25+1)));
        sideMenu.setBackground(Color.GRAY);
        GridBagLayout sideMenuLayout = new GridBagLayout();
        sideMenu.setLayout(sideMenuLayout);

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
        container.add(sideMenu,d);

        // add container to display
        add(container);
        setVisible(true);
    }
}
