package ir;

public class UnaryAssignmentQuadruple extends Quadruple{

    public UnaryAssignmentQuadruple(Object op, Object arg1, Object r){
        super(op,arg1,null,r);

    }
    @Override
    public String toString()
	{
		return super.getResult() + " := " + super.getOp() + " " + super.getArg1();
	}
}