package symboltable;

import java.util.HashMap;

public class MethodSymbolTable extends GenericSymbol implements Scope {
    private String name;
    private Type returnType;
    private HashMap<String,Variable> argsMap;

    public MethodSymbolTable(Scope parent, String name, HashMap<String,Variable> args, Type returnType){
        super(parent);
        this.name = name;
        this.argsMap = args;
        this.returnType = returnType;
    }

    public Type getReturnType(){
        return this.returnType;
    }

    public HashMap<String,Variable> getArgs(){
        return this.argsMap;
    }


    @Override
    public Variable findLocalVariable(String name){
        Variable var = super.getVarMap().get(name);
        if (var!=null){
            return var;
        }
        return argsMap.get(name);
    }
    
    @Override
    public String toString(){
        /* 
            Arg String
        */
        String argsString = "";
        for (String key:argsMap.keySet()){
            argsString+=argsMap.get(key).toString();
        }
        /* 
            Var String
        */  
        String varString = "";
        HashMap<String,Variable> varMap = super.getVarMap();
        for (String key:varMap.keySet()){
            varString+="\t\t"+varMap.get(key)+"\n";
        }

        return "\t"+returnType+" "+name+"("+argsString+")"+"{\n"+
        varString
        +"\n\t}\n";
    }
}
