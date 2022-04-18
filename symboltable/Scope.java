package symboltable;

import java.util.HashMap;

public interface Scope
{
	public Scope enterScope(String name);
	public Variable findVariable(String name);
	public boolean findMethod(String name, HashMap<String,Variable> argsLst, Type returnType);
	public Scope exitScope();
}