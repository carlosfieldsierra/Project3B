package ir;

public class CallQuadruple extends Quadruple {
    public CallQuadruple(Object arg1,Object arg2,Object r){
        super("call",arg1,arg2,r);
    }

    @Override
    public String toString()
	{
		if(super.getResult() != null)
		{
			return super.getResult() + " := " + super.getOp() + " " + super.getArg1() + ", " + super.getArg2();
		}
		else
		{
			return super.getOp() + " " + super.getArg1() + ", " + super.getArg2();
		}
	}
}
