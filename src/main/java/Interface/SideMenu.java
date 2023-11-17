package Interface;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class SideMenu extends JPanel{

    private JPanel title;
    private JPanel description;
    private JPanel buttons;

    public SideMenu(){
        super();
        //sideMenuContainer = new JPanel();
        setSize(new Dimension(10*(25+1),30*(25+1)));
        setBackground(Color.PINK);
    }

}