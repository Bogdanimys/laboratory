
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

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage (bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
    }

    /**
     * just passes variable onto setRGB() of BuggeredImage
     */
    public void drawPixel (int x, int y, int rgbColor){
        bufferedImage.setRGB(x,y,rgbColor);
    }

    // BULLSHIT
    public void clearImage (){
        int[] aRGB = new int[101*101];

        bufferedImage.setRGB(1,1,100,100,aRGB,2,2);
    }

}
