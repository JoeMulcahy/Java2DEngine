import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class EditorWindow extends JPanel implements Runnable, MouseListener, MouseMotionListener {

    //
    // Must add currently selected object here instead of referencing it from Helper
    //////////////////////////

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
    public static EditorWindow Instance;
    private java.util.List<GameObject> objects;
    private boolean isDrawn = false;

    public EditorWindow(){

        Instance = this;

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
                repaint();
            }
            catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        drawObjects(g2);
        Grid.draw(g2);
        Toolkit.getDefaultToolkit().sync();

    }

    public void drawObjects(Graphics2D g2){

        if(Helper.toggleGraphicsOn){
            for(int i = 0; i < UndoRedoStack.Instance.getStackCounter(); i++){
                objects.get(i).draw(g2);
            }

            if(isMouseClick){
                drawSelectorBox(g2);
            }

            if(Helper.currentlySelectedGameObject != null && objects.size() > 0 && Helper.highlighterOn){
                Helper.currentlySelectedGameObject.drawHighlighterBox(true, g2);
            }

            if(Helper.drawShapeAtCursor && Helper.showGrid){
                drawShapeAtCursor(g2);
            }
        }
    }

    private Graphics2D drawSelectorBox(Graphics2D g2){

            int x1, y1, x2, y2, width = 0, height = 0;
            int snapOffsetX = 0;
            int snapOffSetY = 0;

            int gridBoxSizeX = Helper.editorPanelWidth / Helper.gridRowsAndColumns;
            int gridBoxSizeY = Helper.editorPanelHeight / Helper.gridRowsAndColumns;

            if(Helper.snapMode){
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

            width = Math.abs(x2 - x1);
            height = Math.abs(y2 - y1);

            switch(Helper.currentShape){
                case RECT, POLY -> {
                    g2.drawRect((x2 > x1 ? x1 : x1 - width), (y2 > y1 ? y1 : y1 - height), width, height);
                }
                case CIRCLE -> {
                    g2.drawOval((x2 > x1 ? x1 : x1 - width), (y2 > y1 ? y1 : y1 - height), width, height);
                }
                case LINE -> g2.drawLine(x1, y1, x2, y2);
            }

        return g2;
    }

    private Graphics2D drawShapeAtCursor(Graphics2D g2){
        // Draw the currently selected shape at the current mouse position
        // Can be toggle on/off

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
        }

        return g2;

    }

    public void loadGameObjects(String objType, int x1, int y1, int x2, int y2,
                                       double rotationAngle, Color color, float lineThickness, boolean fillShape, boolean drawBorder){

        switch(objType){
            case "rect" -> objects.add(new RectangleObject(x1, y1, x2, y2, rotationAngle, color, lineThickness, fillShape, drawBorder));
            case "circle" -> objects.add(new CircleObject(x1, y1, x2, y2, rotationAngle, color, lineThickness, fillShape, drawBorder));
            case "line" -> objects.add(new LineObject(x1, y1, x2, y2, rotationAngle, color, lineThickness, false));
        }
        Helper.createdGameObjects = objects;
        UndoRedoStack.Instance.addToStack(objects.get(objects.size() - 1));
        Helper.instructionCounter++;
        GameObjectAttributesPanel.Instance.updateGameObjectJList();
        GameObjectAttributesPanel.jListOfGameObjectNames.setSelectedIndex(UndoRedoStack.Instance.getStackCounter() -1);
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

        if(Helper.instructionCounter != UndoRedoStack.Instance.getStackCounter()){
            updateGameObjectsArrayList();
        }

        if(Helper.currentShape == Helper.ShapeSelector.RECT){
            objects.add(new RectangleObject(x1, y1, x2, y2, 0,Helper.currentColor, Helper.lineThickness, Helper.fillShape, Helper.toggleObjectBorder));
        }
        if(Helper.currentShape == Helper.ShapeSelector.CIRCLE){
            objects.add(new CircleObject(x1, y1, x2, y2, 0, Helper.currentColor, Helper.lineThickness, Helper.fillShape, Helper.toggleObjectBorder));
        }
        if(Helper.currentShape == Helper.ShapeSelector.LINE){
            objects.add(new LineObject(x1, y1, x2, y2, 0, Helper.currentColor, Helper.lineThickness, false));
        }
        if(Helper.currentShape == Helper.ShapeSelector.POLY){
            objects.add(new PolygonObject(x1, y1, x2, y2, null, 0, Helper.currentColor, Helper.lineThickness, Helper.fillShape, Helper.toggleObjectBorder));
        }

        Helper.createdGameObjects = objects;
        UndoRedoStack.Instance.addToStack(objects.get(objects.size() - 1));
        Helper.instructionCounter++;
        GameObjectAttributesPanel.Instance.updateGameObjectJList();
        GameObjectAttributesPanel.jListOfGameObjectNames.setSelectedIndex(UndoRedoStack.Instance.getStackCounter() -1);
    }

    public void reorderObjects(GameObject o, int currentIndex, int newValue){
        // This used to set the z-value or depth of the object in the Editor window
        System.out.println(o.toString());
        System.out.println("index: " + currentIndex);
        System.out.println("new: " + newValue);

        if(newValue >= 0 && newValue < objects.size()){
            objects.remove(o);
            objects.add(newValue, o);
        }
        else{
            System.out.println("invalid operation");
        }
    }

    public void deleteObject(int index){
        //GameObject o = null;
        objects.set(index, null);
       // objects.remove(index);
    }

    public void updateGameObjectsArrayList(){
        for(int i = objects.size() - 1; i >= UndoRedoStack.Instance.getStackCounter(); i--){
            objects.remove(i);
        }
        Helper.createdGameObjects = objects;
        GameObjectAttributesPanel.Instance.updateGameObjectJList();

    }

    public void clearScreen() {
        objects.clear();
        Helper.numberOfObjectsDrawn = 0;

        GameObjectAttributesPanel.Instance.updateGameObjectJList();
        GameObjectAttributesPanel.Instance.updateAttributeValues(null);

        // initialised ids back to 1
        CircleObject.id = 1;
        RectangleObject.id = 1;
        LineObject.id = 1;
        PolygonObject.id = 1;

        Helper.instructionCounter = 0;
        UndoRedoStack.Instance.clearStack();

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
        mouseX1= e.getX();
        mouseY1 = e.getY();
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

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
    }

}
