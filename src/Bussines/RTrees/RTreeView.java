package Bussines.RTrees;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.awt.*;

public class RTreeView extends JPanel {
    private RTree rTree;

    /**
     * Constructor de la classe RTreeView
     * @param rTree
     */
    public RTreeView(RTree rTree) {
        this.rTree = rTree;
        SwingUtilities.invokeLater(this::initialize);
    }

    /**
     * Inicialitza la vista
     */
    private void initialize() {
        JFrame frame = new JFrame("RTree VIEW");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int frameWidth = screenWidth / 2;
        int frameHeight = screenHeight / 2;
        frame.setPreferredSize(new Dimension(frameWidth, frameHeight));

        JPanel treePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTree(g, rTree.getRoot(), -100, -100, -100, -100);
            }
        };

        frame.add(treePanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Dibuixa el R-Tree
     * @param g Graphics
     * @param root Node arrel
     * @param minX Coordenada X mínima
     * @param minY Coordenada Y mínima
     * @param maxX Coordenada X màxima
     * @param maxY Coordenada Y màxima
     */
    private void drawTree(Graphics g, Rectangle root, int minX, int minY, int maxX, int maxY) {
        if (root == null) return;
        if (root.isLeaf()) {
            drawBardissa(g, root, minX, minY, maxX, maxY); // Dibuja la hoja
        } else {
            drawRectangle(g, root); // Dibuja el nodo
        }
    }

    /**
     * Dibuixa una bardissa.
     * @param g Graphics
     * @param rectBardissa Bardissa a dibuixar
     * @param minX Coordenada X mínima
     * @param minY Coordenada Y mínima
     * @param maxX Coordenada X màxima
     * @param maxY Coordenada Y màxima
     */
    private void drawBardissa(Graphics g, Rectangle rectBardissa, int minX, int minY, int maxX, int maxY) {
        for (Bardissa bardissa : rectBardissa.getBardissas()) {
            g.setColor(bardissa.getColor());

            int bardissaX = scaleValue(bardissa.getLongitud(), rectBardissa.getWest(), rectBardissa.getEst(), minX, (minX + maxX - 10));
            int bardissaY = scaleValue(bardissa.getLatitud(), rectBardissa.getSouth(), rectBardissa.getNorth(), minY, (minY + maxY - 10));
            g.fillOval(bardissaX, bardissaY, 10, 10);
        }
    }

    /**
     * Dibuixa un rectangle
     * @param g Graphics
     * @param rectangle Rectangle a dibuixar
     */
    private void drawRectangle(Graphics g, Rectangle rectangle) {
        //int i = 0;    // Para que los rectángulos no se superpongan
        for (Rectangle child : rectangle.getRectangles()) {
            g.setColor(Color.BLACK);

            int x = scaleValue(child.getCenterLongitud(), rectangle.getWest(), rectangle.getEst(), 30, 330);
            int y = scaleValue(child.getCenterLatitud(), rectangle.getSouth(), rectangle.getNorth(), 30, 330);
            int width = scaleValue(child.getEst(), rectangle.getWest(), rectangle.getEst(), 30, 330) - scaleValue(child.getWest(), rectangle.getWest(), rectangle.getEst(), 30, 330);
            int height = scaleValue(child.getNorth(), rectangle.getSouth(), rectangle.getNorth(), 30, 330) - scaleValue(child.getSouth(), rectangle.getSouth(), rectangle.getNorth(), 30, 330);

            //y = i + y;    // Para que los rectángulos no se superpongan
            g.drawRect(x, y, width, height);
            //i = i + 60;   // Para que los rectángulos no se superpongan
            drawTree(g, child, x, y, width, height);
        }
    }

    /**
     * Estableix la operació de sortida de la vista
     * @param operation
     */
    public void setDefaultCloseOperation(int operation) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.setDefaultCloseOperation(operation);
    }

    /**
     * Estableix la visibilitat de la vista
     */
    public void pack() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.pack();
    }

    /**
     * Estableix la posició de la vista
     * @param c Component
     */
    public void setLocationRelativeTo(Component c) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.setLocationRelativeTo(c);
    }

    /**
     * Escala los valores de las coordenadas de un rectángulo para que se ajusten a la ventana del Swing del R-Tree
     * @param value Valor a escalar
     * @param minValue Valor mínimo
     * @param maxValue Valor máximo
     * @param minScale Escala mínima
     * @param maxScale Escala máxima
     */
    private int scaleValue(double value, double minValue, double maxValue, int minScale, int maxScale) {
        int scaledValue = (int) (((value - minValue) / (maxValue - minValue)) * (maxScale - minScale) + minScale);
        return scaledValue;
    }
}
