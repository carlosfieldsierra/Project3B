package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import symboltable.Variable;
import symboltable.Type;

public class NewArray extends Exp {
  public Exp e;
  public Variable var;
  
  public NewArray(Exp ae) {
    e=ae; 
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
