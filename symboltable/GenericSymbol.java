package symboltable;
import java.util.HashMap;;
public class GenericSymbol implements Scope{
    /* 
        Parent
    */
    public Scope parent;
    /* 
        List of Variables
    */
    private HashMap<String,Variable> varMap;
    /* 
        List of sub symbols scope in symbol table
    */
    public HashMap<String,GenericSymbol> tableLst;


    public GenericSymbol(Scope parent){
        this.parent = parent;
        varMap = new HashMap<>();
        tableLst = new HashMap<>();
    }

      
    public void addTable(String name)
	{   
		this.tableLst.put(name, new GenericSymbol(this));
	}

    public Scope enterScope(String name){
        return tableLst.get(name);
    };

    /* 
        Gets the map of Variables
    */
    public HashMap<String,Variable> getVarMap(){
        return this.varMap;
    }

    /* 
        Adds variable to varMap
    */
    public void addVar(String name, Type type){
        varMap.put(name,new Variable(name,type));
    }

    public Variable findLocalVariable(String name){
        return varMap.get(name);
    }

	public Variable findVariable(String name){
        Variable var = findLocalVariable(name);
        /* 
            If var is null search in parent
        */
        if (var==null){
            return parent.findVariable(name);
        } 
        return var;
    };

    
	public boolean findMethod(String name,  HashMap<String,Variable> args, Type returnType){
        return parent.findMethod(name,args,returnType);
    }
	public Scope exitScope(){
        return parent;
    }
  
}
