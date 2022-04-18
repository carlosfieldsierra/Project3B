package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import symboltable.Variable;
import symboltable.Type;

public class Not extends Exp {
  public Exp e;
  public Variable var;
  public int rowNum;
  public int colNum;
  
  public Not(Exp ae) {
    e=ae; 
  }
  public Not(Exp ae,int rowNum,int colNum) {
    e=ae; 
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
      var = new Variable("t"+tempNum.get(),Type.TEMP);
    }
    return var;
  }
}
