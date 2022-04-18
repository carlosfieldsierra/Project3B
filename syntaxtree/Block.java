package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class Block extends Statement {
  public StatementList sl;
  public int rowNum;
  public int colNum;

  public Block(StatementList asl) {
    sl=asl;
  }

  public Block(StatementList asl,int rowNum,int colNum) {
    sl=asl;
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

