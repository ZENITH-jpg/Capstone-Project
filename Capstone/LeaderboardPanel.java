import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LeaderboardPanel extends JPanel implements KeyListener {
    private static class Player{
        private String name;
        private int score;

        public Player(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
    Window window;
    Image logo;
    Image background;
    ArrayList<Player> players;
    public LeaderboardPanel(Window w){
        window = w;
        logo = new ImageIcon("assets/leaderboard.png").getImage();
        background = new ImageIcon("assets/lBackground.png").getImage();
        this.setFocusable(true);
        this.setBackground(Color.black);
        this.addKeyListener(this);
        this.setBounds(0,0,800,600);
        this.setLayout(null);
        players = new ArrayList<>();
        fillPlayers();
        sort(players);
        JTextArea message = Utils.messagePanel("Press 'B' to return to menu",50,490,700,70);
        this.add(message);
    }
    private void fillPlayers(){
        try {
            Scanner f = new Scanner(new File("leaderboard.txt"));
            while (f.hasNext()){
                String s = f.nextLine();
                String[] a = s.split(" ");
                String name = a[0];
                int score = Integer.parseInt(a[1]);
                players.add(new Player(name,score));
            }
        } catch (FileNotFoundException e){
            System.out.print(e);
        }
    }
    private void sort(ArrayList<Player> arr){
        int x = arr.size()/2;
        if(x == 0){
            return;
        }
        ArrayList<Player> left = new ArrayList<>();
        ArrayList<Player> right = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            left.add(arr.get(i));
        }
        for (int i = x; i < arr.size(); i++) {
            right.add(arr.get(i));
        }
        sort(left);
        sort(right);
        merge(right,left,arr);
    }
    private void merge(ArrayList<Player> r, ArrayList<Player> l, ArrayList<Player> a){
        int i = 0;
        int j = 0;
        int k = 0;
        while(i<r.size() && j<l.size()){
            if(r.get(i).getScore() < l.get(j).getScore()){
                a.set(k,l.get(j));
                j++;
            }else{
                a.set(k,r.get(i));
                i++;
            }
            k++;
        }
        while(i<r.size()){
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
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(background,0,0,null);
        g2d.drawImage(logo,132,40,null);
        g2d.setColor(Color.black);
        g2d.fillRect(90,140,620,315);
        g2d.setColor(Color.white);
        g2d.fillRect(100,150,600,295);
        g2d.setColor(Color.black);
        for (int i = 0; i < 5; i++) {
            g2d.fillRect(100,195+50*i,600,10);
        }
        g2d.fillRect(295,150,10,295);
        g2d.fillRect(495,150,10,295);
        g2d.setFont(Utils.PIXEL);
        g2d.drawString("PLACE",110,185);
        g2d.drawString("NAME",315,185);
        g2d.drawString("SCORE",515,185);
        for (int i = 0; i < 5; i++) {
            g2d.drawString((i+1)+"",110,237+50*i);
            if(i<players.size()){
                g2d.drawString(players.get(i).getName(),315,237+50*i);
                g2d.drawString(players.get(i).getScore()+"",515,237+50*i);
            } else {
                g2d.drawString("NA",315,237+50*i);
                g2d.drawString("NA",515,237+50*i);
            }
        }
        //Utils.drawGrid(g2d);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_B){
            window.returnMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
