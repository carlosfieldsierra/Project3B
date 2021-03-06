package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import symboltable.Variable;
import symboltable.Type;

public class NewObject extends Exp {
  public Identifier i;
  public Variable var;
  
  public NewObject(Identifier ai) {
    i=ai;
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
