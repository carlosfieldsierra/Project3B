package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class While extends Statement {
  public Exp e;
  public Statement s;
  public int rowNum;
  public int colNum;

  public While(Exp ae, Statement as) {
    e=ae; s=as; 
  }
  public While(Exp ae, Statement as,int rowNum,int colNum) {
    e=ae; s=as; 
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

