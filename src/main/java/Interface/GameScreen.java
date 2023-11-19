package Interface;

import MazeMap.MazeMap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class GameScreen extends JPanel {

    private MazeMap mazemap;
    private Interface screen;

    public GameScreen(Interface screen,MazeMap mazeMap){
        this.mazemap = mazeMap;
        GridBagLayout gameLayout = new GridBagLayout();
        setLayout(gameLayout);
    }

    JPanel initSideMenu(){
        JPanel sideMenu = new JPanel();
        sideMenu.setPreferredSize(new Dimension(350,30*(25+1)));
        sideMenu.setBackground(Color.PINK);
        GridBagLayout sideMenuLayout = new GridBagLayout();
        sideMenu.setLayout(sideMenuLayout);
        return sideMenu;
    }

    public void setEditMap(){
        GridBagConstraints d = new GridBagConstraints();

        // col 0
        d.gridx = 0;
        // row 0
        d.gridy = 0;
        add(mazemap, d);

        // side menu
        // set title
        JPanel sideMenu = initSideMenu();
        JLabel title = new JLabel();
        JLabel description = new JLabel();
        JPanel buttons = new JPanel();
        buttons.setOpaque(false);

        title.setText("Edit Map");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 65));
        GridBagConstraints c = new GridBagConstraints();
        // col 0
        c.gridx = 0;
        // row 0
        c.gridy = 0;
        c.weighty = 0.5;
        add(title,c);

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
        add(description,c);

        // set button
        buttons.setLayout(new GridLayout(3,1,0,15));

        /** save button */
        JButton save = new JButton("SAVE");
        save.setFont(new Font("Arial", Font.PLAIN, 40));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazemap.save_MazeMap();
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
                    mazemap.load_MazeMap(selectedFile.getPath());
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
        add(buttons,c);

        // col 1
        c.gridx = 1;
        // row 0
        c.gridy = 0;
        add(sideMenu,d);
    }
}
