import java.awt.*;
import javax.swing.*;

public class QTE {
   static int QTESize = 50;
   public QTE (String blockType) {
      Image planetImg = new ImageIcon("assets/"+blockType+".png").getImage().getScaledInstance(QTESize, QTESize, Image.SCALE_DEFAULT);
   }
}