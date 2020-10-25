import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {

    private int length;

    private JImageDisplay jImageDisplay;

    private FractalGenerator fractalGenerator;

    private Rectangle2D.Double aDouble;

    public FractalExplorer (int l){
        length = l;

        aDouble = new Rectangle2D.Double();


        new Mandelbrot().getInitialRange(aDouble);

        fractalGenerator = new Mandelbrot();
    }

    /**
     * Creates: JFrame with JImageDispaly in which fractal is drawn,
     * button for resetting zoom range.
     */
    public void createAndShowGUI(){
        JFrame frame = new JFrame("Фрактал Мандельброта");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jImageDisplay = new JImageDisplay(length,length);
        jImageDisplay.addMouseListener(new MyMouseListener().mouseListener);

        JPanel jPanelForComboBox = new JPanel();

        JComboBox<FractalGenerator> jComboBox = new JComboBox<>();
        jComboBox.addItem(new Mandelbrot());
        jComboBox.addItem(new Tricorn());
        jComboBox.addItem(new BurningShip());

        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fractalGenerator =(FractalGenerator) jComboBox.getSelectedItem();
                drawFractal();
                jImageDisplay.repaint();
            }
        });

        jPanelForComboBox.add(new JLabel("Fractal"));
        jPanelForComboBox.add(jComboBox);

        Button button = new Button("Reset");
        ActionListener actionListener = e -> {
            fractalGenerator.getInitialRange(aDouble);
            drawFractal();
            jImageDisplay.repaint();
        };
        button.addActionListener(actionListener);

        frame.add(jImageDisplay, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        frame.add(jPanelForComboBox, BorderLayout.NORTH);

        //not my code
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);


    }

    private void drawFractal (){

        double xCoord;
        double yCoord;

        int numIters;

        for (int y = 1; y < length; y++){
            for (int x = 1; x < length; x++){

                xCoord = FractalGenerator.getCoord(aDouble.x, aDouble.x + aDouble.width, length, x);
                yCoord = FractalGenerator.getCoord(aDouble.y, aDouble.y + aDouble.height, length, y);

                numIters = fractalGenerator.numIterations(xCoord,yCoord);

                if (numIters != -1){
                    float hue = 0.7f + (float) numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 0.74f, 0.74f);
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
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        };
    }
}
