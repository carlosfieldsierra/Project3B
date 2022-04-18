package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class ArrayAssign extends Statement {
  public Identifier i;
  public Exp e1,e2;
  public int rowNum;
  public int colNum;

  public ArrayAssign(Identifier ai, Exp ae1, Exp ae2) {
    i=ai; e1=ae1; e2=ae2;
  }
  public ArrayAssign(Identifier ai, Exp ae1, Exp ae2, int rowNum,int colNum) {
    i=ai; e1=ae1; e2=ae2;
    this.rowNum = rowNum;
    this.colNum = colNum;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }

}

