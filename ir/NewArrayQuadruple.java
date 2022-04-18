package ir;

public class NewArrayQuadruple extends Quadruple {
    public NewArrayQuadruple(Object arg1,Object arg2, Object r){
        super("new",arg1,arg2,r);
    }

    @Override
    public String toString()
	{
		return super.getResult() + " := " + super.getOp() + " " + super.getArg1() + ", " + super.getArg2();
	}
    
}
