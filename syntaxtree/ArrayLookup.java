package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import symboltable.Variable;
import symboltable.Type;

public class ArrayLookup extends Exp {
  public Exp e1,e2;
  public Variable var;
  public int rowNum;
  public int colNum;
  
  public ArrayLookup(Exp ae1, Exp ae2) { 
    e1=ae1; e2=ae2;
  }
  public ArrayLookup(Exp ae1, Exp ae2,int rowNum,int colNum) { 
    e1=ae1; e2=ae2;
    this.rowNum = rowNum;
    this.colNum = colNum;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public syntaxtree.Type accept(TypeVisitor v) {
    return v.visit(this);
  }


  public  Variable getVar(TempNum tempNum){
    if(var == null)
    {
        var = new Variable("t"+tempNum.get(), Type.TEMP);
    }
    return var;
  }
}
