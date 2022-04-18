package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import symboltable.Variable;
import symboltable.Type;

public class IdentifierExp extends Exp {
  public String s;
  public Variable var;
  public int rowNum;
  public int colNum;

  public IdentifierExp(String as) { 
    s=as;
  }
  public IdentifierExp(String as,int rowNum,int colNum) { 
    s=as;
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
    if (var==null){
      var= new Variable(s,Type.ID);
    }
    return var;
  }
}
