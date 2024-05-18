import javax.swing.*;
import java.awt.*;

public class Utils { //for fonts, custom colors, and other objects that are to be reused
    final static Font MESSAGE_FONT = new Font("Helvetica", Font.PLAIN, 20);
    public static JTextArea messagePanel(String s, int x, int y, int w, int h){
        JTextArea message = new JTextArea(s);
        message.setBorder(BorderFactory.createLineBorder(Color.white,2));
        message.setHighlighter(null);
        message.setBounds(x,y,w,h);
        message.setBackground(Color.black);
        message.setForeground(Color.white);
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setFont(MESSAGE_FONT);
        return message;
    }
}
