import java.awt.*;

public class Grid {

    private int width;
    private int height;
    private int numberOfColumns;
    private int numberOfRows;
    private int[] numberOfRowsAndColumns;
    private int gridLineWidth;
    private boolean isVisible;
    private int cellWidth;
    private int cellHeight;
    private Color gridLineColor;
    private int transparency;
    public static Grid Instance;

    public Grid(int width, int height, int numberOfColumns, int numberOfRows, int gridLineWidth, int transparency, boolean isVisible) {
        this.width = width;
        this.height = height;
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.gridLineWidth = gridLineWidth;
        this.isVisible = isVisible;
        this.cellWidth = width / numberOfColumns;
        this.cellHeight = height / numberOfRows;
        this.transparency = transparency;
        this.numberOfRowsAndColumns = new int[]{numberOfColumns, numberOfRows};

        this.gridLineColor = new Color(183, 186, 190, transparency);

        Instance = this;
    }

    public Grid() {
        this(Settings.editorPanelWidth, Settings.editorPanelHeight, GameManager.gridNumberOfRowsAndCols[0], GameManager.gridNumberOfRowsAndCols[1], 1, 50, true);
    }

    public void drawGrid(Graphics g){

        Stroke gridWidth = new BasicStroke(gridLineWidth);

        Graphics2D g2 = (Graphics2D) g;

        if (isVisible) {
            g2.setColor(gridLineColor);
            g2.setStroke(gridWidth);

            if(numberOfRows == numberOfColumns){
                for(int i = 0; i <= numberOfRows; i++){
                    g2.drawLine(i * cellWidth, 0, i * cellWidth, height);
                    g2.drawLine(0, i * cellHeight, width, i * cellHeight);
                }
            }else{
                for (int i = 0; i <= numberOfColumns; i++) {
                    g2.drawLine(i * cellWidth, 0, i * cellWidth, height);
                }
                for(int j = 0; j <= numberOfRows; j++){
                    g2.drawLine(0, j * cellHeight, width, j * cellHeight);
                }
            }
        }
    }

    public boolean getIsVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    private void setCellWidth(){
        cellWidth = width / numberOfColumns;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    private void setCellHeight(){
        cellHeight = height / numberOfRows;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
        setCellWidth();
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
        setCellHeight();
    }

    public void snapObjectToNearestGrid(GameObject o){
        snapObjectToNearestGridX(o);
        snapObjectToNearestGridY(o);
    }

    public void snapObjectToNearestGridX(GameObject o){
        int objectWidth = o.x2 - o.x1;

        int x1c = o.x1 + objectWidth / 2;

        int gridNumberX = x1c / cellWidth;
        int XPosInGrid = x1c % cellWidth;

        if(XPosInGrid <= cellWidth / 2){
            o.x1 = gridNumberX * cellWidth;
        }else{
            o.x1 = ((gridNumberX + 1) * cellWidth) - objectWidth;
        }
        o.x2 = o.x1 + objectWidth;
    }

    public void snapObjectToNearestGridY(GameObject o){
        int objectHeight = o.y2 - o.y1;

        int y1c = o.y1 + objectHeight / 2;

        int gridNumberY = y1c / cellHeight;
        int YPosInGrid = y1c % cellHeight;

        if(YPosInGrid <= cellHeight / 2){
            o.y1 = gridNumberY * cellHeight;
        }else{
            o.y1 = ((gridNumberY + 1) * cellHeight) - objectHeight;
        }
        o.y2 = o.y1 + objectHeight;
    }

    public void adjustObjectEdge(GameObject o, String direction){
        switch(direction){
            case "left" -> {
                o.x1 -= EditorWindow.getEditorGrid().getCellWidth();
            }
            case "right" -> {
                o.x1 += EditorWindow.getEditorGrid().getCellWidth();
            }
            case "up" -> {
                o.y1 -= EditorWindow.getEditorGrid().getCellHeight();;
            }
            case "down" -> {
                o.y1 += EditorWindow.getEditorGrid().getCellHeight();;
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public void adjustObjectSize(GameObject o, String direction){

        switch(direction){
            case "increase" -> {
                o.x1 -= EditorWindow.getEditorGrid().getCellWidth();
                o.x2 += EditorWindow.getEditorGrid().getCellWidth();
                o.y1 -= EditorWindow.getEditorGrid().getCellHeight();
                o.y2 += EditorWindow.getEditorGrid().getCellHeight();
            }
            case "decrease" -> {
                o.x1 += EditorWindow.getEditorGrid().getCellWidth();
                o.x2 -= EditorWindow.getEditorGrid().getCellWidth();
                o.y1 += EditorWindow.getEditorGrid().getCellHeight();
                o.y2 -= EditorWindow.getEditorGrid().getCellHeight();
            }

            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public void moveObject(GameObject o, String direction){
        switch(direction){
            case "left" -> {
                o.x1 -= EditorWindow.getEditorGrid().getCellWidth();
                o.x2 -= EditorWindow.getEditorGrid().getCellWidth();
            }
            case "right" -> {
                o.x1 += EditorWindow.getEditorGrid().getCellWidth();
                o.x2 += EditorWindow.getEditorGrid().getCellWidth();
            }
            case "up" -> {
                o.y1 -= EditorWindow.getEditorGrid().getCellHeight();
                o.y2 -= EditorWindow.getEditorGrid().getCellHeight();
            }
            case "down" -> {
                o.y1 += EditorWindow.getEditorGrid().getCellHeight();
                o.y2 += EditorWindow.getEditorGrid().getCellHeight();
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public int getGridLineWidth() {
        return gridLineWidth;
    }

    public void setGridLineWidth(int gridLineWidth) {
        this.gridLineWidth = gridLineWidth;
    }

    public Color getGridLineColor() {
        return gridLineColor;
    }

    public void setGridLineColor(Color c){
        this.gridLineColor = c;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }
    public void setNumberOfRowsAndColumns(int[] numberOfRowsAndColumns) {
        this.numberOfRowsAndColumns = numberOfRowsAndColumns;
    }

    public int[] getNumberOfRowsAndColumns(){
        return numberOfRowsAndColumns;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Grid getGrid(){
        return this;
    }

}
