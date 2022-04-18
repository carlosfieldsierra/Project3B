package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import symboltable.Variable;
import symboltable.Type;

public class True extends Exp {
  public void accept(Visitor v) {
    v.visit(this);
  }

  public syntaxtree.Type accept(TypeVisitor v) {
    return v.visit(this);
  }
  public  Variable getVar(TempNum tempNum){
    return new Variable("true",Type.CONSTANT);
  }
}
