/*
Tristan C, Van N
2024-05-31
Mr Guglielmi
Common utils for the game, stores fonts and other useful generators
*/
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Common utilities used around the game
 * @author Tristan C
 * @author Van N
 * @version 1.0
 */
public class Utils { // for fonts, custom colors, and other objects that are to be reused
    /**
     * Font used in messages
     */
    final static Font MESSAGE_FONT = new Font("Helvetica", Font.PLAIN, 20);
    /**
     * Font used for blocks
     */
    final static Font BLOCKTEXT_FONT = new Font("Helvetica", Font.PLAIN, 12);
    /**
     * Font used for the game headings
     */
    final static Font GAMEHEADING_FONT = new Font("Helvetica", Font.PLAIN, 16);
    /**
     * Climate messages
     */
    final static String[] climateTips =
    {"When a creature goes extinct, much like real life, it never comes back",
     "Carbon emissions and other greenhouse gases trap heat in the atmosphere, heating up the planet",
     "High temperatures will cause the death of animal species, and eventually, humans",
     "Try your hardest to reduce your carbon footprint!"};
    /**
     * Pixel font used in leaderboards and minigame end screens
     */
    static Font PIXEL;

    /**
     * Initialize fonts
     */
    public static void init(){
        try {
            PIXEL = Font.createFont(Font.TRUETYPE_FONT, new File("assets/pixel.ttf")).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(PIXEL);
        } catch (IOException | FontFormatException e) {

        }
    }

    /**
     * Create a new message panel
     * @param s The message to be displayed
     * @param x The x position of the message box
     * @param y The y position of the message box
     * @param w The width of the message box
     * @param h The height of the message box
     * @return The message box as a Swing component
     */
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

    /**
     * Create a new block text panel
     * @param s The message to be displayed
     * @param x The x position of the message box
     * @param y The y position of the message box
     * @param w The width of the message box
     * @param h The height of the message box
     * @return The block message as a Swing component
     */
    public static JTextArea blockTextPanel(String s, int x, int y, int w, int h) {
        JTextArea message = new JTextArea(s);
        message.setBorder(null);
        message.setBounds(x, y, w, h);
        message.setOpaque(false); // makes transparent
        message.setBackground(new Color(0,0,0,0)); // makes transparent
        message.setForeground(Color.white);
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setFont(BLOCKTEXT_FONT);
        return message;
    }
    /**
     * Create a new heading panel
     * @param s The message to be displayed
     * @param x The x position of the message box
     * @param y The y position of the message box
     * @param w The width of the message box
     * @param h The height of the message box
     * @return The heading panel as a Swing component
     */
    public static JTextArea gameHeadingPanel(String s, int x, int y, int w, int h) {
        JTextArea message = new JTextArea(s);
        message.setBorder(null);
        message.setBounds(x, y, w, h);
        message.setOpaque(false); // makes transparent
        message.setBackground(new Color(0,0,0,0)); // makes transparent
        message.setForeground(Color.white);
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setFont(GAMEHEADING_FONT);
        return message;
    }

    /**
     * Utility to select what color blocks are to draw
     * @param type The type the block is
     * @param name The name of the block
     * @return The color of the block
     */
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
            case "ice":
               return new Color(194, 217, 255);
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

    /**
     * Utility to draw a grid that makes a line every 50 pixels
     * @param g2d The graphics element to draw the grid on
     */
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
