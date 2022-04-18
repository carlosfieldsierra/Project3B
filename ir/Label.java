package ir;
public class Label {
    private static int globalId =0;
    private int id =0;
    public boolean isTrue;
    public Label(Boolean isTrue){
        /* 
            Give Unique Id and Inc the global id
        */
        id = globalId;
        globalId++;
        this.isTrue = isTrue;
    }

    /* 
        Get unique label name
    */
    public String getLabel(){
        return "L"+id;
    }

    /* 
        Get id
    */
    public int getId(){
        return this.id;
    }


    @Override
    public String toString(){
        return this.getLabel();
    }
}
