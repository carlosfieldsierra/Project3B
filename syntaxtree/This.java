package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import symboltable.Variable;
import symboltable.Type;

public class This extends Exp {
  public Variable var;
  public int rowNum;
  public int colNum;


  public This(){

  }

  public This(int rowNum,int colNum){
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
      var = new Variable("this",Type.THIS);
    }
    return var;
  }
}
