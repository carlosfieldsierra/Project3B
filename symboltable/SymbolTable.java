package symboltable;

import java.util.HashMap;

public class SymbolTable implements Scope {
    private HashMap<String, ClassSymbolTable> classes;
	
	public SymbolTable()
	{
		classes = new HashMap<>();
    }

	public void print(){
		System.out.println("here");
		for (String key:classes.keySet()){
			ClassSymbolTable cls = classes.get(key);
			System.out.println(cls);
			
		}

	}

	

	/* 
		Adds a simple class
	*/	
	public void addClass(String name){
		this.classes.put(name,new ClassSymbolTable(this,name));
	}

	/* 
		Adds an extend class
	*/
	public void addClass(String name,String superClass){
		this.classes.put(name,new ClassSymbolTable(this,name,superClass));
	}	

	public ClassSymbolTable getClass(String name){
		if (classes.containsKey(name)){
			return classes.get(name);
		}
		return null;
	}
	/*
		Scope Interface methods
	*/
    public Scope enterScope(String name)
	{
		return classes.get(name);
	}
    public Variable findVariable(String name){
        return null;
    }
	public boolean findMethod(String name, HashMap<String,Variable> argsLst, Type returnType){
        return false;
    }
	
	public Scope exitScope()
	{
		return null;
	}


}
