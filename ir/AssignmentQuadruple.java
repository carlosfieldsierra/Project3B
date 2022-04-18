package ir;

public class AssignmentQuadruple extends Quadruple {
    public AssignmentQuadruple(Object op, Object arg1, Object arg2, Object r){
        super(op,arg1,arg2,r);
    }  

    @Override
    public String toString()
	{
		return super.getResult() + " := " + super.getArg1() + " " + super.getOp() + " " + super.getArg2();
	}
}
