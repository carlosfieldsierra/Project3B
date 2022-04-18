package ir;

public class CopyQuadruple extends Quadruple {
    public CopyQuadruple(Object arg1,Object r){
        super(null,arg1,null,r);
    }

    @Override
    public String toString()
	{
		return super.getResult() + " := " + super.getArg1();
	}
}
