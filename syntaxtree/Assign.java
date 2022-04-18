package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class Assign extends Statement {
  public Identifier i;
  public Exp e;
  public int rowNum;
  public int colNum;

  public Assign(Identifier ai, Exp ae) {
    i=ai; e=ae; 
  }
  public Assign(Identifier ai, Exp ae,int rowNum,int colNum) {
    i=ai; e=ae; 
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

