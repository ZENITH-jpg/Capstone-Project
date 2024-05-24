import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LeaderboardPanel extends JPanel implements KeyListener {
    private static class Player{ // class player that keeps info about score and name of player
        private String name; //fields
        private int score;

        public Player(String name, int score) { // constructor
            this.name = name;
            this.score = score;
        }
        // getters
        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
    Window window; //fields
    Image logo;
    Image background; // images
    ArrayList<Player> players; // player list
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
        sort(players);
        JTextArea message = Utils.messagePanel("Press 'B' to return to menu",50,490,700,70); // and context box
        this.add(message);
    }
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
    }
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
                g2d.drawString("NA",315,237+50*i); // incase leaderboard isn't filled
                g2d.drawString("NA",515,237+50*i);
            }
        }
        //Utils.drawGrid(g2d);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) { //go back to main menu on b pressed
        if(e.getKeyCode() == KeyEvent.VK_B){
            window.returnMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}