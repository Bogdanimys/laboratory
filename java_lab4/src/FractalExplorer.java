import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {

    private int length;

    private JImageDisplay jImageDisplay;

    private FractalGenerator fractalGenerator;

    private Rectangle2D.Double aDouble;

    public FractalExplorer (int l){
        length = l;

        aDouble = new Rectangle2D.Double();
        // TODO
        // doesnt seem right

        FractalGenerator.Mandelbrot.getInitialRange(aDouble);
    }

    public void createAndShowGUI(){
        JFrame frame = new JFrame("Java La Java");
        Container pane = frame.getContentPane();

        jImageDisplay = new JImageDisplay(length,length);
        Button button = new Button("AAAAA");

        // TODO
        // Have no idea what Im doing
        pane.add(button, BorderLayout.SOUTH);
        pane.add(jImageDisplay, BorderLayout.CENTER);
        frame.setContentPane(pane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //not my code (THANK GOD)
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        drawFractal();

    }

    private void drawFractal (){

        double xCoord;
        double yCoord;
        int numIters;

        for (int y = 1; y < length; y++){
            for (int x = 1; x < length; x++){
                // TODO
                //
                xCoord = FractalGenerator.getCoord(aDouble.x, aDouble.x + aDouble.width, length, x);
                yCoord = FractalGenerator.getCoord(aDouble.y, aDouble.y + aDouble.height, length, y);
                numIters = FractalGenerator.Mandelbrot.numIterations(xCoord,yCoord);

                if (numIters != -1){
                    float hue = 0.7f + (float) numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    jImageDisplay.drawPixel(x,y,rgbColor);
                }
                else jImageDisplay.drawPixel(x,y,0);
            }
        }
        jImageDisplay.repaint();
    }

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.createAndShowGUI();
    }
}
