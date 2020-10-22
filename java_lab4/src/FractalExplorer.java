import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.event.MouseAdapter;

//TODO NEED comments
public class FractalExplorer {

    private int length;

    private JImageDisplay jImageDisplay;

    private FractalGenerator fractalGenerator;

    private Rectangle2D.Double aDouble;

    public FractalExplorer (int l){
        length = l;

        aDouble = new Rectangle2D.Double();


        FractalGenerator.Mandelbrot.getInitialRange(aDouble);
    }

    public void createAndShowGUI(){
        JFrame frame = new JFrame("Фрактал Мандельброта");
        Container pane = frame.getContentPane();

        jImageDisplay = new JImageDisplay(length,length);
        Button button = new Button("Reset");

        ActionListener actionListener = e -> {
            FractalGenerator.Mandelbrot.getInitialRange(aDouble);
            drawFractal();
            jImageDisplay.repaint();
        };


        pane.add(button, BorderLayout.SOUTH);
        pane.add(jImageDisplay, BorderLayout.CENTER);
        frame.setContentPane(pane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button.addActionListener(actionListener);

        //not my code (THANK GOD)
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);


        MyMouseListener myMouseListener = new MyMouseListener();
        jImageDisplay.addMouseListener(myMouseListener.mouseListener);

    }

    private void drawFractal (){

        double xCoord;
        double yCoord;
        int numIters;

        for (int y = 1; y < length; y++){
            for (int x = 1; x < length; x++){

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

        fractalExplorer.drawFractal();



    }



        class MyMouseListener extends MouseAdapter {
        public  MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                double xCord;
                double yCord;

                xCord = FractalGenerator.getCoord(aDouble.x, aDouble.x + aDouble.width, length, e.getX());
                yCord = FractalGenerator.getCoord(aDouble.y, aDouble.y + aDouble.height, length, e.getY());

                FractalGenerator.recenterAndZoomRange(aDouble, xCord,yCord,0.5);

                drawFractal();

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }
}
