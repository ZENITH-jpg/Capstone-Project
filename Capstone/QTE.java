import java.awt.*;
import javax.swing.*;

public class QTE {
   static int QTESize = 50;
   private Image QTEImg;
   public QTE (String blockType) {
      QTEImg = new ImageIcon("assets/"+blockType+".png").getImage().getScaledInstance(QTESize, QTESize, Image.SCALE_DEFAULT);
   }
   public Image getImage() {
      return QTEImg;
   }
}