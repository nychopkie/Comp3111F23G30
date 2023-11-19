package Interface;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    private JLabel title;
    private JPanel navigation;

    public MainMenu(){
        super();
        setSize(new Dimension(44*(26),30*(28)-12));
        setOpaque(false);
        //setBackground("Assets/Images/Start_BG");
        //TODO: THE FLOW OF THE INTERFACES AND THE MAIN MENU
        GridBagLayout startMenuLayout = new GridBagLayout();
        setLayout(startMenuLayout);

        setStartMenu();
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
        navigation.add(play);

        JButton test = new JButton("Test game");
        test.setFont(new Font("Arial", Font.PLAIN, 50));
        navigation.add(test);

        JButton option = new JButton("Option");
        option.setFont(new Font("Arial", Font.PLAIN, 50));
        navigation.add(option);
    }

}

