package symboltable;

public class Variable
{
    private String name;
    private Type type;


    public Variable(String name,Type type){
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return this.name;
    }

    public Type getType(){
        return type;
    }

    @Override
    public String toString(){
        // return type+" "+name+";";
        return name;
    }
}