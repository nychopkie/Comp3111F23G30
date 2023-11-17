package Interface;

import MazeMap.MazeMap;

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

    public SideMenu(MazeMap map){
        super();
        this.map = map;
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
                try {
                    File file = new File("Assets/map/MazeMap_Custom.csv");
                    // write into it according to the format
                    PrintWriter pw = new PrintWriter(file);
                    pw.close();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    for (int i = 0; i < 30; i++){
                        for (int j = 0; j < 30; j++){
                            if (j != 0){
                                writer.write(",");
                            }
                            writer.write(Integer.toString(map.getMazedata()[i][j].getVertex_type()));
                            if (j == 29){
                                writer.newLine();
                            }
                        }
                    }
                    writer.flush();
                    writer.close();

                } catch (IOException f) {
                    System.out.println("An error occurred.");
                    f.printStackTrace();
                }
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