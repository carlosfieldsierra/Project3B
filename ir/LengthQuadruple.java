package ir;

public class LengthQuadruple extends Quadruple {
    public LengthQuadruple(Object arg1,Object r){
        super("length",arg1,null,r);
    }

    @Override
    public String toString()
	{
		return super.getResult() + " := " + super.getOp() + " " + super.getArg1();
	}
}
