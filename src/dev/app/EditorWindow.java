package dev.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EditorWindow extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {

    //
    // Must add currently selected object here instead of referencing it from Helper
    //////////////////////////

    Thread thread;
    private int FPS = 60;
    private boolean isRunning;
    private int height = Settings.editorPanelHeight;
    private int width = Settings.editorPanelWidth;
    private int mouseX1;
    private int mouseY1;
    private int mouseX2;
    private int mouseY2;
    private boolean isMouseClick;
    public static EditorWindow Instance;
    private java.util.List<GameObject> objects;
    private java.util.List<TextObject> textObjects;
    protected static java.util.List<Collision> objectsWithCollisionDetection;
    private static Grid editorGrid;
    private ObjectHighLighter highlighter;
    int counter = 0;
    double rotationDirection = 1;

    public EditorWindow() {

        Instance = this;

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Settings.editorBackgroundColor);
        this.setFocusable(true);
        this.setVisible(true);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);

        this.addKeyListener(this);


        objects = new ArrayList<>();
        objectsWithCollisionDetection = new ArrayList<>();
        textObjects = new ArrayList<>();
        GameManager.currentSelectedTool = GameManager.Tool.CIRCLE;

        editorGrid = new Grid();
        highlighter = new ObjectHighLighter();

        isMouseClick = false;
        isRunning = true;
        thread = new Thread(this, "Editor");
        thread.start();
    }

    public static Grid getEditorGrid() {
        return editorGrid;
    }

    public java.util.List<GameObject> getGameObjects() {
        return this.objects;
    }

    public Player player = new Player(new RectangleObject());

    @Override
    public void run() {
        // This will be used to animate objects eventually
        while (isRunning) {
            try {
                Thread.sleep(1000 / FPS);
                counter++;

                if(GameManager.gotNuts){
                    animationTest(objects);
                }

                updatesObjectPhysics();

                repaint();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void updatesObjectPhysics(){

        player.updatePosition();

        if(!objectsWithCollisionDetection.isEmpty()){
            objectsWithCollisionDetection.forEach(s -> s.updateCollisionBoxLocation());
        }

        java.util.List<Collision[]> collisions = Collision.collisionChecker(objectsWithCollisionDetection);

        for(int i = 0; i < collisions.size(); i++){
            GameObject o1 = collisions.get(i)[0].getAssociatedGameObject();
            GameObject o2 = collisions.get(i)[1].getAssociatedGameObject();

            o1.setDx(o1.getDx() * -1);
            o1.setDy(o1.getDy() * -1);
            o2.setDx(o2.getDx() * -1);
            o2.setDy(o2.getDy() * -1);
        }

    }

    public void animationTest(java.util.List<GameObject> objects){

        int rx = (int)Math.random() * 3;
        int ry = (int)Math.random() * 3;

        if(!objects.isEmpty()){

            for (int i = 0; i < objects.size(); i ++) {
                GameObject o = objects.get(i);

                o.setRotationAngle((o.getRotationAngle() + (double) counter / 3000) * rotationDirection % 360);

                if(o.x1 <= 0 || o.x1 + o.getWidth() >= width){
                    o.dx = o.dx * -1;
                }

                if(o.y1 <= 0 || o.y1 + o.getHeight() >= height){
                    o.dy = o.dy * -1;
                }

                o.setX1(o.x1 + o.dx);
                o.setX2(o.x2 + o.dx);
                o.setY1(o.y1 + o.dy);
                o.setY2(o.y2 + o.dy);

            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (Settings.toggleCoordinatesAtCursor) {
            drawCoordinatesAtCursor(g2);
        }

        drawObjects(g2);
        editorGrid.drawGrid(g);

        if (isMouseClick) {
            drawSelectorBox(g2);
        }

        if(Settings.toggleCollisionBoxes){
            objectsWithCollisionDetection.forEach(s -> s.drawCollisionBox(g2));
        }

        if (GameManager.drawShapeAtCursor && Grid.Instance.getIsVisible()) {
            drawShapeAtCursor(g2);
        }

        if (GameManager.currentlySelectedGameObject != null && objects.size() > 0 && GameManager.highlighterOn) {
            highlighter.drawHighlighterBox(g2, GameManager.currentlySelectedGameObject);
        }

        Toolkit.getDefaultToolkit().sync();

    }

    public void drawObjectNames(Graphics2D g, GameObject o){
        g.drawString(o.getName(), o.getX1(), o.getY1());
    }

    public void drawObjects(Graphics2D g2) {

        //player.draw(g2);

        if (Settings.toggleGraphicsOn) {
            for (int i = 0; i < UndoRedoStack.Instance.getStackCounter(); i++) {
                objects.get(i).draw(g2);

                if(Settings.toggleObjectNamesOnEditor){
                    drawObjectNames(g2, objects.get(i));
                }

            }
        }
    }

    private Graphics2D drawSelectorBox(Graphics2D g2) {

        int x1, y1, x2, y2, width = 0, height = 0;

        int gridBoxSizeX = editorGrid.getCellWidth();
        int gridBoxSizeY = editorGrid.getCellHeight();

        if (GameManager.snapMode) {
            int snapX1 = gridBoxSizeX * (mouseX1 / gridBoxSizeX);
            int snapY1 = gridBoxSizeY * (mouseY1 / gridBoxSizeY);
            int snapX2 = gridBoxSizeX * (mouseX2 / gridBoxSizeX) + gridBoxSizeX;
            int snapY2 = gridBoxSizeY * (mouseY2 / gridBoxSizeY) + gridBoxSizeY;

            x1 = snapX1;
            x2 = snapX2;
            y1 = snapY1;
            y2 = snapY2;
        } else {
            x1 = mouseX1;
            x2 = mouseX2;
            y1 = mouseY1;
            y2 = mouseY2;
        }

        g2.setColor(Color.LIGHT_GRAY);

        width = Math.abs(x2 - x1);
        height = Math.abs(y2 - y1);

        switch (GameManager.currentSelectedTool) {
            case RECT, POLY, TEXT -> {
                g2.drawRect((x2 > x1 ? x1 : x1 - width), (y2 > y1 ? y1 : y1 - height), width, height);
            }
            case CIRCLE -> {
                g2.drawOval((x2 > x1 ? x1 : x1 - width), (y2 > y1 ? y1 : y1 - height), width, height);
            }
            case LINE -> g2.drawLine(x1, y1, x2, y2);
        }

        return g2;
    }

    private Graphics2D drawCoordinatesAtCursor(Graphics2D g2) {

        int x, y;

        if(GameManager.snapMode){
            int s[] = getSnapPoints();
            x = s[0];
            y = s[1];
        }else{
            x = mouseX1;
            y = mouseY1;
        }

        if(mouseY1 < editorGrid.getCellHeight()){

            x += EditorWindow.getEditorGrid().getCellWidth() + 3;
            y += EditorWindow.getEditorGrid().getCellHeight();
        }

        g2.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        g2.drawString("" + mouseX1 + ", " + mouseY1, x, y - 10);

        return g2;
    }

    private Graphics2D drawShapeAtCursor(Graphics2D g2) {
        int x, y;
        Font font = new Font(Font.SERIF, Font.PLAIN,  10);

        int gridBoxSizeX = editorGrid.getCellWidth();
        int gridBoxSizeY = editorGrid.getCellHeight();

        if(GameManager.snapMode){
            int s[] = getSnapPoints();
            x = s[0];
            y = s[1];
        }else{
            x = mouseX1;
            y = mouseY1;
        }

        g2.setColor(GameManager.currentColor);

        if (GameManager.fillShape) {
            g2.setStroke(new BasicStroke(GameManager.currentSelectedTool == GameManager.Tool.POLY ? 2 : 4));
        } else {
            g2.setStroke(new BasicStroke(1));
        }

        int pointSize = Settings.toolPanelHeight / 100;

        switch (GameManager.currentSelectedTool) {
            case RECT -> {
                g2.drawRect(x, y, gridBoxSizeX, gridBoxSizeY);
            }
            case CIRCLE -> {
                g2.drawOval(x, y, gridBoxSizeX, gridBoxSizeY);
            }
            case POLY -> {
                int[] polyX = {x, x + gridBoxSizeX, x};
                int[] polyY = {y, y + gridBoxSizeY, y + gridBoxSizeY};
                g2.drawPolygon(polyX, polyY, 3);
            }
            case LINE -> {
                g2.drawLine(x, y, x, y + gridBoxSizeY);
            }
            case SELECT -> {
                g2.drawLine(x, y + gridBoxSizeY / 2, x + gridBoxSizeX, y + gridBoxSizeY / 2);
                g2.drawLine(x + gridBoxSizeX / 2, y, x + gridBoxSizeX / 2, y + gridBoxSizeY);
            }
            case TEXT -> {
                g2.setFont(font);
                g2.drawString("T", x, y);
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
        GameManager.createdGameObjects = objects;
        UndoRedoStack.Instance.addToStack(objects.get(objects.size() - 1));
        GameManager.instructionCounter++;
        InspectorPanel.Instance.updateGameObjectJList();
        InspectorPanel.jListOfGameObjectNames.setSelectedIndex(UndoRedoStack.Instance.getStackCounter() -1);
    }

    private void createGameObject(){
        GameManager.numberOfObjectsDrawn++;
        int x1, y1, x2, y2;

        if(GameManager.snapMode){
            int s[] = getSnapPoints();
            x1 = s[0];
            x2 = s[2];
            y1 = s[1];
            y2 = s[3];
        }else{
            x1 = mouseX1;
            x2 = mouseX2;
            y1 = mouseY1;
            y2 = mouseY2;
        }

        if(GameManager.instructionCounter != UndoRedoStack.Instance.getStackCounter()){
            updateGameObjectsArrayList();
        }

        if(GameManager.currentSelectedTool == GameManager.Tool.RECT){

            objects.add(new RectangleObject(x1, y1, x2, y2, 0, GameManager.currentColor, GameManager.lineThickness,
                    GameManager.fillShape, GameManager.toggleObjectBorder));

        }
        else if(GameManager.currentSelectedTool == GameManager.Tool.CIRCLE){

            objects.add(new CircleObject(x1, y1, x2, y2, 0, GameManager.currentColor, GameManager.lineThickness,
                    GameManager.fillShape, GameManager.toggleObjectBorder));

        }
        else if(GameManager.currentSelectedTool == GameManager.Tool.LINE){

            objects.add(new LineObject(x1, y1, x2, y2, 0, GameManager.currentColor, GameManager.lineThickness, false));

        }
        else if(GameManager.currentSelectedTool == GameManager.Tool.POLY){

            objects.add(new PolygonObject(x1, y1, x2, y2, null, 0, GameManager.currentColor,
                    GameManager.lineThickness, GameManager.fillShape, GameManager.toggleObjectBorder, false));

        }else if(GameManager.currentSelectedTool == GameManager.Tool.TEXT){

            String text = ToolPanel.Instance.getText();
            objects.add(new TextObject(x1, y1, x2, y2, 0, GameManager.currentColor, GameManager.lineThickness,
                    GameManager.fillShape, GameManager.toggleObjectBorder, false, text));

        }

        //objectsWithCollisionDetection.add(new Collision(objects.get(objects.size() - 1)));

        GameManager.currentlySelectedGameObject = objects.get(objects.size() - 1);      //Make newly created GameObject the currently selected object
        InspectorPanel.Instance.updateObjectsPropertiesPanel(objects.size() - 1); //Updates Inspector attributes with new object

        GameManager.createdGameObjects = objects;
        UndoRedoStack.Instance.addToStack(objects.get(objects.size() - 1));
        GameManager.instructionCounter++;
        InspectorPanel.Instance.updateGameObjectJList();
        InspectorPanel.jListOfGameObjectNames.setSelectedIndex(UndoRedoStack.Instance.getStackCounter() -1);
    }

    public void reorderObjects(GameObject o, int currentIndex, int newValue){
        // This used to set the z-value or depth of the object in the Editor window
        if(newValue >= 0 && newValue < objects.size()){
            objects.remove(o);
            objects.add(newValue, o);
        }
        else{

        }
    }

    public void deleteObject(int index){
        //dev.joe.GameObject o = null;
        objects.set(index, null);
       // objects.remove(index);
    }

    public void updateGameObjectsArrayList(){

        for(int i = objects.size() - 1; i >= UndoRedoStack.Instance.getStackCounter(); i--){
            objects.remove(i);
        }
        GameManager.createdGameObjects = objects;
        InspectorPanel.Instance.updateGameObjectJList();

    }

    public void clearScreen() {
        objects.clear();
        objectsWithCollisionDetection.clear();
        GameManager.numberOfObjectsDrawn = 0;

        InspectorPanel.Instance.updateGameObjectJList();
        InspectorPanel.Instance.updatePropertiesValues(null);

        // initialised ids back to 1
        CircleObject.id = 1;
        RectangleObject.id = 1;
        LineObject.id = 1;
        PolygonObject.id = 1;

        GameManager.instructionCounter = 0;
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

        // for dev.joe.StatsPanel class
        GameManager.mouseX1 = mouseX1;
        GameManager.mouseY1 = mouseY1;
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

    public int getFPS() {
        return FPS;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public static void setEditorGrid(Grid editorGrid) {
        EditorWindow.editorGrid = editorGrid;
    }

    public ObjectHighLighter getHighlighter() {
        return highlighter;
    }

    public void setHighlighter(ObjectHighLighter highlighter) {
        this.highlighter = highlighter;
    }

    public EditorWindow getEditorWindow(){
        return this;
    }

    private int[] getSnapPoints() {

        int gridBoxSizeX = editorGrid.getCellWidth();
        int gridBoxSizeY = editorGrid.getCellHeight();
        int snapX1 = gridBoxSizeX * (mouseX1 / gridBoxSizeX);
        int snapY1 = gridBoxSizeY * (mouseY1 / gridBoxSizeY);
        int snapX2 = gridBoxSizeX * (mouseX2 / gridBoxSizeX) + gridBoxSizeX;
        int snapY2 = gridBoxSizeY * (mouseY2 / gridBoxSizeY) + gridBoxSizeY;

        return new int[]{snapX1, snapY1, snapX2, snapY2};

    }


    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
