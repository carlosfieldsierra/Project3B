package syntaxtree;

public class TempNum{
    private int tempNum;
    public TempNum(){
        tempNum = 0;
    }

    public int get(){
        int old  = tempNum;
        tempNum++;
        return old;
    }

    
}
