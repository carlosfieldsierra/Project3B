package ir;

public class IndexedAssignmentTwoQuadruple extends Quadruple {
    public IndexedAssignmentTwoQuadruple(Object arg1,Object arg2, Object r){
        super(null,arg1,arg2,r);
    }

    @Override 
    public String toString()
	{
		return super.getResult() + "[" + super.getArg2() + "]" + " := " + super.getArg1();
	}
}
