
import javax.swing.JComponent;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent{

    private BufferedImage bufferedImage;

    public JImageDisplay (int x, int y){

        bufferedImage = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);

        // похоже на костыль
        Dimension dimension = new Dimension();
        dimension.setSize(x,y);

        setPreferredSize(dimension);
    }

}
