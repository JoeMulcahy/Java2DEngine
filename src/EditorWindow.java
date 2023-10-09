import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class EditorWindow extends JPanel implements Runnable, MouseListener, MouseMotionListener {

    Thread thread;
    private int FPS = 60;
    private boolean isRunning;
    private int height = Helper.editorPanelHeight;
    private int width = Helper.editorPanelWidth;
    private int mouseX1;
    private int mouseY1;
    private int mouseX2;
    private int mouseY2;
    private boolean isMouseClick;

    private static java.util.List<GameObject> objects;

    public EditorWindow(){
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Helper.editorBackgroundColor);
        this.setFocusable(true);
        this.setVisible(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        objects = new ArrayList<>();
        Helper.currentShape = Helper.ShapeSelector.CIRCLE;

        isMouseClick = false;
        isRunning = true;
        thread = new Thread(this, "Editor");
        thread.start();
    }

    @Override
    public void run() {
    // This will be used to animate objects eventually
        while(isRunning){
            try{
                Thread.sleep(1000 / FPS);
            }
            catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        drawObjects(g2);
        Grid.draw(g2);
        Toolkit.getDefaultToolkit().sync();

        repaint();
    }

    public void drawObjects(Graphics2D g2){

        for(int i = 0; i < UndoRedoStack.getStackCounter(); i++){
            objects.get(i).draw(g2);
        }

        if(isMouseClick){
            drawSelectorBox(g2);
        }
        if(Helper.snapMode && Helper.showGrid){
            snapMode(g2);
        }
    }

    private Graphics2D drawSelectorBox(Graphics2D g2){

            int x1, y1, x2, y2;

            if(Helper.snapMode){
                int gridBoxSizeX = Helper.editorPanelWidth / Helper.gridRowsAndColumns;
                int gridBoxSizeY = Helper.editorPanelHeight / Helper.gridRowsAndColumns;
                int snapX1 = gridBoxSizeX * (mouseX1 / gridBoxSizeX);
                int snapY1 = gridBoxSizeY * (mouseY1 / gridBoxSizeY);
                int snapX2 = gridBoxSizeX * (mouseX2 / gridBoxSizeX) + gridBoxSizeX;
                int snapY2 = gridBoxSizeY * (mouseY2 / gridBoxSizeY) + gridBoxSizeY;

                x1 = snapX1;
                x2 = snapX2;
                y1 = snapY1;
                y2 = snapY2;
            }else{
                x1 = mouseX1;
                x2 = mouseX2;
                y1 = mouseY1;
                y2 = mouseY2;
            }

            g2.setColor(Color.LIGHT_GRAY);

            if(Helper.currentShape == Helper.ShapeSelector.RECT){
                if(x2 > x1  && y2 > y1) {
                    g2.drawRect(x1 , y1, x2 - x1 , y2 - y1);
                }else if(x2 < x1  &&  y2 > y1){
                    g2.drawRect(x2, y1, x1  - x2, y2 - y1);
                } else if (x2 > x1  && y2 < y1) {
                    g2.drawRect(x1 , y2, x2 - x1 , y1 - y2);
                }else if(x2 < x1  && y2 < y1){
                    g2.drawRect(x2, y2, x1  - x2, y1 - y2);
                }
            }

            if(Helper.currentShape == Helper.ShapeSelector.CIRCLE){
                if(x2 > x1  && y2 > y1) {
                    g2.drawOval(x1 , y1, x2 - x1 , y2 - y1);
                }else if(x2 < x1  &&  y2 > y1){
                    g2.drawOval(x2, y1, x1  - x2, y2 - y1);
                } else if (x2 > x1  && y2 < y1) {
                    g2.drawOval(x1 , y2, x2 - x1 , y1 - y2);
                }else if(x2 < x1  && y2 < y1){
                    g2.drawOval(x2, y2, x1  - x2, y1 - y2);
                }
            }

            if(Helper.currentShape == Helper.ShapeSelector.LINE || Helper.currentShape == Helper.ShapeSelector.POLY){
                g2.drawLine(mouseX1, mouseY1, mouseX2, mouseY2);
            }

        return g2;
    }

    private Graphics2D snapMode(Graphics2D g2){

        int gridBoxSizeX = Helper.editorPanelWidth / Helper.gridRowsAndColumns;
        int gridBoxSizeY = Helper.editorPanelHeight / Helper.gridRowsAndColumns;
        int snapX1 = gridBoxSizeX * (mouseX1 / gridBoxSizeX);
        int snapY1 = gridBoxSizeY * (mouseY1 / gridBoxSizeY);
        g2.setColor(Helper.currentColor);

        switch(Helper.currentShape){
            case RECT -> {
                if (Helper.fillShape) {
                    g2.fillRect(snapX1, snapY1, gridBoxSizeX, gridBoxSizeY);
                } else {
                    g2.drawRect(snapX1, snapY1, gridBoxSizeX, gridBoxSizeY);
                }
            }
            case CIRCLE -> {
                if (Helper.fillShape) {
                    g2.fillOval(snapX1, snapY1, gridBoxSizeX, gridBoxSizeY);
                } else {
                    g2.drawOval(snapX1, snapY1, gridBoxSizeX, gridBoxSizeY);
                }
            }
            case LINE -> {
                g2.drawLine(snapX1, snapY1, gridBoxSizeX, gridBoxSizeY);
            }
        }

        return g2;

    }

    public static void loadGameObjects(String objType, int x1, int y1, int x2, int y2,
                                       double rotationAngle, Color color, float lineThickness, boolean fillShape){

        switch(objType){
            case "rect" -> objects.add(new RectangleObject(x1, y1, x2, y2, 0,Helper.currentColor, Helper.lineThickness, Helper.fillShape));
            case "circle" -> objects.add(new CircleObject(x1, y1, x2, y2, 0, Helper.currentColor, Helper.lineThickness, Helper.fillShape));
            case "line" -> objects.add(new LineObject(x1, y1, x2, y2, 0, Helper.currentColor, Helper.lineThickness));
        }
        Helper.createdGameObjects = objects;
    }

    private void createGameObject(){
        Helper.numberOfObjectsDrawn++;

        //Yes! this is repetitive code
        //See snapMode()
        //Need to just get this working first
        int x1, y1, x2, y2;

        if(Helper.snapMode){
            int gridBoxSizeX = Helper.editorPanelWidth / Helper.gridRowsAndColumns;
            int gridBoxSizeY = Helper.editorPanelHeight / Helper.gridRowsAndColumns;
            int snapX1 = gridBoxSizeX * (mouseX1 / gridBoxSizeX);
            int snapY1 = gridBoxSizeY * (mouseY1 / gridBoxSizeY);
            int snapX2 = gridBoxSizeX * (mouseX2 / gridBoxSizeX) + gridBoxSizeX;
            int snapY2 = gridBoxSizeY * (mouseY2 / gridBoxSizeY) + gridBoxSizeY;

            x1 = snapX1;
            x2 = snapX2;
            y1 = snapY1;
            y2 = snapY2;
        }else{
            x1 = mouseX1;
            x2 = mouseX2;
            y1 = mouseY1;
            y2 = mouseY2;
        }

        if(Helper.instructionCounter != UndoRedoStack.getStackCounter()){
            updateGameObjectsArrayList();
        }

        if(Helper.currentShape == Helper.ShapeSelector.RECT){
            objects.add(new RectangleObject(x1, y1, x2, y2, 0,Helper.currentColor, Helper.lineThickness, Helper.fillShape));
        }
        if(Helper.currentShape == Helper.ShapeSelector.CIRCLE){
            objects.add(new CircleObject(x1, y1, x2, y2, 0, Helper.currentColor, Helper.lineThickness, Helper.fillShape));
        }
        if(Helper.currentShape == Helper.ShapeSelector.LINE){
            objects.add(new LineObject(x1, y1, x2, y2, 0, Helper.currentColor, Helper.lineThickness));
        }
//        if(Helper.currentShape == Helper.ShapeSelector.POLY){
//            objects.add(new PolygonObject(x1, y1, x2, y2, 0,100, 3, Helper.currentColor, Helper.lineThickness, Helper.fillShape));
//        }

        Helper.createdGameObjects = objects;
        UndoRedoStack.addToStack(objects.get(objects.size() - 1));
        Helper.instructionCounter++;
        GameObjectAttributesPanel.updateGameObjectJList();
        GameObjectAttributesPanel.jListOfGameObjectNames.setSelectedIndex(UndoRedoStack.getStackCounter() -1);
    }

    public void updateGameObjectsArrayList(){
        for(int i = objects.size() - 1; i >= UndoRedoStack.getStackCounter(); i--){
            objects.remove(i);
        }
        Helper.createdGameObjects = objects;
        GameObjectAttributesPanel.updateGameObjectJList();

    }

    public static void clearScreen() {
        objects.clear();
        Helper.numberOfObjectsDrawn = 0;

        // These are static methods in GameObjectsPanel class
        // Spaghetti like! I know
        GameObjectAttributesPanel.updateGameObjectJList();
        GameObjectAttributesPanel.updateAttributeValues(null);

        // initialised ids back to 1
        CircleObject.id = 1;
        RectangleObject.id = 1;
        LineObject.id = 1;
        PolygonObject.id = 1;

        Helper.instructionCounter = 0;
        UndoRedoStack.clearStack();

    }

    public EditorWindow getEditorWindow(){
        return this;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isMouseClick = false;
        mouseX2 = e.getX();
        mouseY2 = e.getY();
        isMouseClick = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseX2 = e.getX();
        mouseY2 = e.getY();
        createGameObject();
        isMouseClick = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX2 = e.getX();
        mouseY2 = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX1 = e.getX();
        mouseY1 = e.getY();

        // for StatsPanel class
        Helper.mouseX1 = mouseX1;
        Helper.mouseY1 = mouseY1;
    }
}
