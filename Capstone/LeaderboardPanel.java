/*
Tristan C
2024-05-24
Mr Guglielmi
Loads scores from leaderboard.txt and creates a leaderboard panel
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controls the data and the graphics of the leaderboard page
 * @author Tristan C
 * @version 1.0
 */
public class LeaderboardPanel extends JPanel implements KeyListener {
    /**
     * Subclass that creates a player that has a score and a name
     */
    private static class Player{ // class player that keeps info about score and name of player
        /**
         * The name of the player
         */
        private String name; //fields
        /**
         * The score that the player got
         */
        private int score;

        /**
         * Constructor of player
         * @param name the name of the player
         * @param score the score the player got in the game
         */
        public Player(String name, int score) { // constructor
            this.name = name;
            this.score = score;
        }
        // getters

        /**
         * Get the player's name
         * @return the player's name
         */
        public String getName() {
            return name;
        }
        /**
         * Get the player's score
         * @return the player's score
         */
        public int getScore() {
            return score;
        }
    }

    /**
     * Window leaderboard is being displayed on
     */
    Window window; //fields
    /**
     * The leaderboard logo
     */
    Image logo;
    /**
     * The background of the leaderboard
     */
    Image background; // images
    /**
     * list of players in the scores file
     */
    ArrayList<Player> players; // player list

    /**
     * Constructor for the leaderboard, sets up assets and scores list
     * @param w the window to display the leaderboard on
     */
    public LeaderboardPanel(Window w){ //constructor
        window = w; // set up
        logo = new ImageIcon("assets/leaderboard.png").getImage();
        background = new ImageIcon("assets/lBackground.png").getImage();
        this.setFocusable(true); // default panel init
        this.setBackground(Color.black);
        this.addKeyListener(this);
        this.setBounds(0,0,800,600);
        this.setLayout(null);
        players = new ArrayList<>(); // set up players list
        fillPlayers(); // fill and sort
        JTextArea message = Utils.messagePanel("Press 'B' to return to menu",50,490,700,70); // and context box
        this.add(message);
    }

    public void addPlayer(String name, int score) {
       Player p = new Player(name, score);
       players.add(p);
       sort(players);
       try {
         PrintWriter printer = new PrintWriter (new FileWriter("leaderboard.txt ", true ));
         printer.println(name+" "+score);
         printer.close();
       } catch (Exception e) {
         System.out.println(e.getMessage());
       }
    }
    
    /**
     * Fills the players array with players from the leaderboard file
     */
    private void fillPlayers(){
        try {
            Scanner f = new Scanner(new File("leaderboard.txt")); // get players from file
            while (f.hasNext()){
                String s = f.nextLine();
                String[] a = s.split(" ");
                String name = a[0];
                int score = Integer.parseInt(a[1]);
                players.add(new Player(name,score)); // add to list
            }
        } catch (FileNotFoundException e){
            System.out.print(e);
        }
        sort(players);
        window.repaint();
    }

    /**
     * Sorts the players in the leaderboard in descending order. Follows a merge sort.
     * @param arr the player array to be sorted
     */
    private void sort(ArrayList<Player> arr){ // merge sort
        int x = arr.size()/2;
        if(x == 0){
            return;
        }
        ArrayList<Player> left = new ArrayList<>();
        ArrayList<Player> right = new ArrayList<>();
        for (int i = 0; i < x; i++) { // split list into 2 smaller lists
            left.add(arr.get(i));
        }
        for (int i = x; i < arr.size(); i++) {
            right.add(arr.get(i));
        }
        sort(left); // sort lists
        sort(right);
        merge(right,left,arr); // merge lists
    }

    /**
     * Merge tool for the sort function
     * @param r the right list
     * @param l the left list
     * @param a the list to put sorted elements in
     */
    private void merge(ArrayList<Player> r, ArrayList<Player> l, ArrayList<Player> a){
        int i = 0;
        int j = 0;
        int k = 0;
        while(i<r.size() && j<l.size()){ // merge lists in descending order
            if(r.get(i).getScore() < l.get(j).getScore()){
                a.set(k,l.get(j));
                j++;
            }else{
                a.set(k,r.get(i));
                i++;
            }
            k++;
        }
        while(i<r.size()){ // add remaining players to lists
            a.set(k,r.get(i));
            i++;
            k++;
        }
        while(j<l.size()){
            a.set(k,l.get(j));
            j++;
            k++;
        }
    }

    /**
     * The graphics of the leaderboard
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g){ //screen
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(background,0,0,null); //bg
        g2d.drawImage(logo,132,40,null); //top text
        g2d.setColor(Color.black);
        g2d.fillRect(90,140,620,315); //leaderboard border
        g2d.setColor(Color.white);
        g2d.fillRect(100,150,600,295); //box
        g2d.setColor(Color.black);
        for (int i = 0; i < 5; i++) {
            g2d.fillRect(100,195+50*i,600,10); //grid lines
        }
        g2d.fillRect(295,150,10,295); // more grid lines
        g2d.fillRect(495,150,10,295);
        g2d.setFont(Utils.PIXEL);
        g2d.drawString("PLACE",110,185); // headings
        g2d.drawString("NAME",315,185);
        g2d.drawString("SCORE",515,185);
        for (int i = 0; i < 5; i++) {
            g2d.drawString((i+1)+"",110,237+50*i); // place
            if(i<players.size()){
                g2d.drawString(players.get(i).getName(),315,237+50*i); //name
                g2d.drawString(players.get(i).getScore()+"",515,237+50*i); //score
            } else {
                g2d.drawString("NA",315,237+50*i); // in case leaderboard isn't filled
                g2d.drawString("NA",515,237+50*i);
            }
        }
        //Utils.drawGrid(g2d);
    }

    /**
     * Key listener when the key is typed
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Key listener when the key is pressed
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) { //go back to main menu on b pressed
        if(e.getKeyCode() == KeyEvent.VK_B){
            window.returnMenu();
        }
    }

    /**
     * Key listener when the key is released
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
