package symboltable;
import java.util.HashMap;

public class ClassSymbolTable extends GenericSymbol implements Scope{
    private String name;
	private String parentClassName;
	private HashMap<String, MethodSymbolTable> methodsLst;
    private HashMap<String,Variable> varLst;

    /*
        Extend class constructor
    */
    public ClassSymbolTable(Scope parent,String name,String parentClassName){
        super(parent);
        this.name = name;
        this.parentClassName = parentClassName;
        this.methodsLst = new HashMap<>();
        this.varLst = new HashMap<>();
    }

    /* 
        Simple class constructor
    */
    public ClassSymbolTable(Scope parent,String name){
        super(parent);
        this.name = name;
        this.methodsLst = new HashMap<>();
        this.varLst = new HashMap<>();
    }

    public void addMethod(Scope parent, String name, HashMap<String,Variable> args, Type returnType){
        methodsLst.put(name,new MethodSymbolTable(parent,name,args,returnType));
    }

    /* 
        Scope methods
    */

    public Scope enterScope(String name)
	{
		return methodsLst.get(name);
	}


    @Override
	public Variable findLocalVariable(String name){
        /* 
            Check in varMap
        */
        Variable var = super.getVarMap().get(name);
        if (var!=null) return var;
        /* 
            Check in extend class
        */
        if (parentClassName!=null){
            Scope parentClassScope = super.parent.enterScope(parentClassName);
            if (parentClassScope!=null){
                return parentClassScope.findVariable(name);
            }
        }

		return null;
    }


    /* 
        Find method functions
    */

    public boolean findParentMethod(String name, HashMap<String,Variable> argsLst, Type returnType){
        // If null return false
        if (this.parentClassName==null) return false;

        Scope parentClassScope = super.parent.enterScope(parentClassName);
        if (parentClassScope!=null){
            return parentClassScope.findMethod(name,argsLst,returnType);
        }
        return false;
    }



    @Override
	public boolean findMethod(String name, HashMap<String,Variable> argsLst, Type returnType){
        MethodSymbolTable methodSymbolTable = methodsLst.get(name);
        /* 
            Find parents method
        */
        if (
            methodSymbolTable==null
            ||
            !methodSymbolTable.getReturnType().equals(returnType)
            ||
            argsLst.size()!=methodSymbolTable.getArgs().size()
            ){
            return findParentMethod(name, argsLst, returnType);
        }

        /* 
            Check if variable are the same
        */
        for (String key:argsLst.keySet()){
            
        }
        


        return true;
    }





    @Override
    public String toString(){

        /*
            Arg String
        */
        String varString = "";
        for (String key: super.getVarMap().keySet()){
            Variable var = super.getVarMap().get(key);
            varString+="\n\t"+var.toString();
        }

        /* 
            Method String
        */
        String methodString = "\n";
        for (String key: methodsLst.keySet()){
            MethodSymbolTable m = methodsLst.get(key);
            methodString+=m.toString();
        }
        
        if (parentClassName!=null){
            return "class "+this.name+" extends "+parentClassName+"{"+
            varString+"\n"+
            methodString+
            "\n}";
        }
        return "class "+this.name+"{"+
        varString+"\n"+
        methodString+
        "\n}";
    }

    
}
