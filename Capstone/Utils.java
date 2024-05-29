import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Utils { // for fonts, custom colors, and other objects that are to be reused
    final static Font MESSAGE_FONT = new Font("Helvetica", Font.PLAIN, 20);
    final static Font BLOCKTEXT_FONT = new Font("Helvetica", Font.PLAIN, 12);
    final static Font GAMEHEADING_FONT = new Font("Helvetica", Font.PLAIN, 16);
    final static String[] climateTips =
    {""};
    static Font PIXEL;
    public static void init(){
        try {
            PIXEL = Font.createFont(Font.TRUETYPE_FONT, new File("assets/pixel.ttf")).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(PIXEL);
        } catch (IOException | FontFormatException e) {

        }
    }
    public static JTextArea messagePanel(String s, int x, int y, int w, int h) {
        JTextArea message = new JTextArea(s);
        message.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        message.setHighlighter(null);
        message.setBounds(x, y, w, h);
        message.setBackground(Color.black);
        message.setForeground(Color.white);
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setFont(MESSAGE_FONT);
        return message;
    }
    
    public static JTextArea blockTextPanel(String s, int x, int y, int w, int h) {
        JTextArea message = new JTextArea(s);
        message.setBorder(null);
        message.setBounds(x, y, w, h);
        message.setOpaque(false); // makes transparent
        message.setBackground(new Color(0,0,0,0)); // makes transparent
        message.setForeground(Color.black);
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setFont(BLOCKTEXT_FONT);
        return message;
    }
    
    public static JTextArea gameHeadingPanel(String s, int x, int y, int w, int h) {
        JTextArea message = new JTextArea(s);
        message.setBorder(null);
        message.setBounds(x, y, w, h);
        message.setOpaque(false); // makes transparent
        message.setBackground(new Color(0,0,0,0)); // makes transparent
        message.setForeground(Color.black);
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setFont(GAMEHEADING_FONT);
        return message;
    }

    public static Color colorOfBlockType(String type, String name) {
        switch (type) {
            case "air":
                if (name.startsWith("Clean"))
                    return new Color(148, 189, 255);
                else
                    return new Color(176, 185, 235);
            case "water":
                if (name.startsWith("Clean"))
                    return new Color(78, 103, 204);
                else
                    return new Color(68, 146, 158);
            case "rock":
                return Color.GRAY;
            case "lava":
               return Color.ORANGE;
            case "soil":
               return new Color (92, 58, 21);
            default:
                return Color.BLACK;
        }
    }

    public static void drawGrid(Graphics g2d){
        g2d.setColor(Color.black);
        for (int i = 0; i<=800; i+=50){
            g2d.drawLine(i,0,i,600);
        }
        for (int i = 0; i<=600; i+=50){
            g2d.drawLine(0,i,800,i);
        }
    }
}
