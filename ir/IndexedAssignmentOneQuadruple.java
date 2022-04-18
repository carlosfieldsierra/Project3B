package ir;

public class IndexedAssignmentOneQuadruple extends Quadruple {
    public IndexedAssignmentOneQuadruple(Object arg1,Object arg2, Object r){
        super(null,arg1,arg2,r);
    }

    @Override
    public String toString()
	{
		return super.getResult() + " := " + super.getArg1() + "[" + super.getArg2() + "]";
	}
}
