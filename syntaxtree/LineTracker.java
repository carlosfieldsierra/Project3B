package syntaxtree;
public class LineTracker{
    private int rowNum;
    private int colNum;
    private Object val;

    public LineTracker(int rowNum,int colNum){
        this.rowNum = rowNum;
        this.colNum = colNum;
    }
    public LineTracker(Object val,int rowNum,int colNum){
        this.val = val;
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    public Object getVal(){
        return this.val;
    }

    public int getRow(){
        return this.rowNum;
    }

    public int getCol(){
        return this.colNum;
    }
}