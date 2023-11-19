package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {

    private JLabel title;
    private JPanel navigation;
    private Interface screen;

    public MainMenu(Interface screen){
        super();
        this.screen = screen;
        setSize(new Dimension(44*(26),30*(28)-12));
        setOpaque(false);
        //setBackground("Assets/Images/Start_BG");
        //TODO: THE FLOW OF THE INTERFACES AND THE MAIN MENU
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

        JButton option = new JButton("Option");
        option.setFont(new Font("Arial", Font.PLAIN, 50));
        option.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.setState(7);
                screen.display();
            }
        });
        navigation.add(option);
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

}

