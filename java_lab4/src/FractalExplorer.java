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

        aDouble = new Rectangle2D.Double(0,0,0,0);
        // TODO
        // doesnt seem right

        FractalGenerator.Mandelbrot.getInitialRange(aDouble);
    }

    public void createAndShowGUI(){
        JFrame frame = new JFrame("Java La Java");

        // TODO
        // Have no idea what Im doing
        frame.getContentPane().add(new JImageDisplay(length,length));

        frame.getContentPane().add(new Button("..."), BorderLayout.SOUTH);

        //not my code (THANK GOD)
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.createAndShowGUI();
    }
}
