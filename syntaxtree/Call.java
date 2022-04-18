package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import symboltable.Variable;
import symboltable.Type;

public class Call extends Exp {
  public Exp e;
  public Identifier i;
  public ExpList el;
  public Variable var;
  public int rowNum;
  public int colNum;
  
  
  public Call(Exp ae, Identifier ai, ExpList ael) {
    e=ae; i=ai; el=ael;
  }
  public Call(Exp ae, Identifier ai, ExpList ael,int rowNum,int colNum) {
    e=ae; i=ai; el=ael;
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
      var = new Variable("t"+tempNum.get(),Type.CONSTANT);
    }
    return var;
  }
}
