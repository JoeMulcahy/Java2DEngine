import java.awt.*;

public class Grid2 {

    private int width;
    private int height;
    private int numberOfColumns;
    private int numberOfRows;
    private int[] numberOfRowsAndColumns;
    private int gridLineWidth;
    private boolean isVisible;
    private int cellWidth;
    private int cellHeight;
    private int transparency;
    public static Grid2 Instance;

    public Grid2(int width, int height, int numberOfColumns, int numberOfRows, int gridLineWidth, int transparency, boolean isVisible) {
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

        Instance = this;
    }

    public Grid2() {
        this(Settings.editorPanelWidth, Settings.editorPanelHeight, GameManager.gridNumberOfRowsAndCols[0], GameManager.gridNumberOfRowsAndCols[1], 1, 50, true);
    }

    public void drawGrid(Graphics g){

        Stroke gridWidth = new BasicStroke(gridLineWidth);

        Graphics2D g2 = (Graphics2D) g;

        if (isVisible) {
            g2.setColor(new Color(183, 186, 190, transparency));

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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getCellWidth() {
        return width / numberOfColumns;
    }

    public int getCellHeight() {
        return height / numberOfRows;
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

    public int[] getNumberOfRowsAndColumns(){
        return numberOfRowsAndColumns;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getGridLineWidth() {
        return gridLineWidth;
    }

    public void setGridLineWidth(int gridLineWidth) {
        this.gridLineWidth = gridLineWidth;
    }


}
