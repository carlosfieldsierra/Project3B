package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import symboltable.Variable;
import symboltable.Type;


public class IntegerLiteral extends Exp {
  public int i;
  public int rowNum;
  public int colNum;

  public IntegerLiteral(int ai) {
    i=ai;
  }
  
  public IntegerLiteral(int ai,int rowNum,int colNum) {
    i=ai;
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
      return new Variable(Integer.toString(i),symboltable.Type.CONSTANT);
  }

}
