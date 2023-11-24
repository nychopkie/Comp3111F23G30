package MazeMap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;


/** class that shows the map of the game<br><hr>
 * ATTRIBUTES<br>
 * 1. MazeMapData: the map data of all vertices<br>
 * 2. SIZE: the size of the map<br>
 * 3. PIXEL_SIZE: the pixel size of each vertex<br>
 * 4. GAP: the gap size between each vertex<br>
 * 5. entry: the entry's y-coordinate<br>
 * 6. exit: the exit's y-coordinate<br>
 * 7. savePath: the path where custom map would be saved<hr>
 * OPERATIONS<br>
 * 1. Default Constructor MazeMap()<br>
 * 2. getEntry()<br>
 * 3. getExit()<br>
 * 4. getSIZE()<br>
 * 5. setSavePath(String path)<br>
 * 6. refreshColour()<br>
 * 7. load_MazeMap(String filePath)<br>
 * 8. save_MazeMap(String filePath)<br>
 * 9. getMazeData()<br>
 **/
public class MazeMap extends JPanel{

    // variables for the class
    /** the maze map containing all Vertices */
    Vertex[][] MazeMapData;

    /** the value for the map sizing*/
    private static final int SIZE = 30, PIXEL_SIZE = 25, GAP = 1;

    /** the entry point of the maze*/
    private int entry;

    /** the exit point of the maze*/
    private int exit ;

    /** the path that the user customized map stores to*/
    private String savePath = "Assets/map/MazeMap_Custom";


    /**
     * MazeMap Constructor<br>
     * This constructor initializes an empty MazeMap
     */
    public MazeMap() {
        super();
        // the gap colour
        setBackground(Color.GRAY);
        setLayout(new GridLayout(SIZE, SIZE, GAP, GAP));

        // init the attribute in default mode
        this.MazeMapData = new Vertex[SIZE][SIZE];

        // helper var for choosing vertex type
        int v_type = 0;

        // determine an entry and exit pos
        this.entry = ThreadLocalRandom.current().nextInt(1, SIZE-1);
        this.exit = ThreadLocalRandom.current().nextInt(1, SIZE-1);

        // initializes each Vertex
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // determine the v_type first
                if (i == this.entry && j == 0) {
                    // entry
                    v_type = 2;
                }
                else if (i == this.exit && j == SIZE-1) {
                    // exit
                    v_type = 3;
                }
                else if (i == 0 || i == SIZE-1 || j == 0 || j == SIZE-1) {
                    // barrier
                    v_type = 1;
                }
                else {
                    // clear vertex path
                    v_type = 0;
                }

                // set each of the vertex according to the type
                this.MazeMapData[i][j] = new Vertex(PIXEL_SIZE, i, j, v_type);
                add(this.MazeMapData[i][j]);
            }
        }
        Vertex.canEdit = false;
        // set size of the map
        setPreferredSize(new Dimension(SIZE*(PIXEL_SIZE+GAP),SIZE*(PIXEL_SIZE+GAP)));
    }

    //accessor
    /**
     * Returns the entry Vertex of the map
     * @return Vertex() entry
     * */
    public Vertex getEntry(){
        return  MazeMapData[entry][0];
    }

    /**
     * Returns the exit Vertex of the map
     * @return Vertex() exit
     * */
    public Vertex getExit(){
        return MazeMapData[exit][SIZE-1];
    }

    /**
     * Returns the size of the map
     * @return int size of map
     * */
    public int getSIZE(){
        return SIZE;
    }

    /**
     * Modifier that changes the save path of the function, this is only invoked in unit test
     * @param  path the path of the saved file
     * */
    public void setSavePath(String path){
        savePath = path;
    }

    /** helper function to refresh map colour */
    public void refreshColour(){
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                MazeMapData[i][j].colourByType();
            }
        }
    }

    /** Load the map data csv into MazeMap.
     * <p>This function will only write back if the passed file is a .csv file, or the path could be found.</p>
     *
     * @param filePath the file path of the csv file
     * @return true if the file is found, false if the file is not found or wrong
     * */
    public boolean load_MazeMap(String filePath){
        if (!filePath.endsWith(".csv")) {
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
            dialog.setVisible(true);
            //JOptionPane.showMessageDialog(this, "Chosen file is not a .csv file, please load a valid map.");
            return false;
        };
        if (!(new File(filePath).exists())){
            JOptionPane pane = new JOptionPane("Cannot find chosen file, please try again.",JOptionPane.WARNING_MESSAGE);
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
            dialog.setVisible(true);
            return false;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int col = 0; col < SIZE; col++) {
                    // Trim whitespace and then parse the integer
                    MazeMapData[row][col].vertex_type = Integer.parseInt(values[col].trim());
                    refreshColour();
                }
                row++;
            }
            // change the entry and exit too
            for (int i = 0; i < SIZE; ++i){
                if (MazeMapData[i][0].vertex_type == 2){
                    entry = i;
                }
                if (MazeMapData[i][SIZE-1].vertex_type == 3){
                    exit = i;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  // It's better to print the stack trace for debugging
        }
        return true;
    }

    /** Save the MazeMap into a csv file.
     * <p>
     *     This function will save the file under Assets/map/MazeMap_Custom[i].csv where the i is the number of files that's custom
     * </p>
     * */
    public void save_MazeMap(){
        int n = 100;
        try {
            File file = new File(savePath + ".csv");
            for (int i = 0; i < n; i++) {
                if (file.exists()){
                    file = new File(savePath + i + ".csv");
                }
            }
            // write into it according to the format
            PrintWriter pw = new PrintWriter(file);
            pw.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < SIZE; i++){
                for (int j = 0; j < SIZE; j++){
                    if (j != 0){
                        writer.write(",");
                    }
                    writer.write(Integer.toString(MazeMapData[i][j].getVertex_type()));
                    if (j == 29){
                        writer.newLine();
                    }
                }
            }
            writer.flush();
            writer.close();

        } catch (IOException f) {
            f.printStackTrace();
        }
    }

    /**
     * Returns the map data as a 2D array of Vertex objects
     * @return Vertex[SIZE][SIZE] the map data and the individual vertices
     **/
    public Vertex[][] getMazedata(){
        return MazeMapData;
    }

}
