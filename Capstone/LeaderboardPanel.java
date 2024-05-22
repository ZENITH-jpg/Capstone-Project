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
        this.isFocusable();
        this.setBackground(Color.black);
        this.addKeyListener(this);
        this.setBounds(0,0,800,600);
        this.setLayout(null);
        players = new ArrayList<>();
        fillPlayers();
        //JTextArea message = Utils.messagePanel("guide",50,490,700,70);
        //this.add(message);
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
        Utils.drawGrid(g2d);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
