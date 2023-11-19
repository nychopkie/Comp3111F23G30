package Interface;

import MazeMap.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class SideMenu extends JPanel {

    private JLabel title;
    private JLabel description;
    private JPanel buttons;

    private MazeMap map;
    private Interface screen;

    public SideMenu(MazeMap map, Interface screen){
        super();
        this.map = map;
        this.screen = screen;
        setPreferredSize(new Dimension(350,30*(25+1)));
        setBackground(Color.PINK);
        GridBagLayout sideMenuLayout = new GridBagLayout();
        setLayout(sideMenuLayout);

        title = new JLabel();
        description = new JLabel();
        buttons = new JPanel();
        buttons.setOpaque(false);

        setEditSideMenu();
    }

    void setEditSideMenu(){
        // set title
        title.setText("Edit Map");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Verdana", Font.PLAIN, 65));
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
                map.save_MazeMap();
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
                    map.load_MazeMap(selectedFile.getPath());
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
    }

}