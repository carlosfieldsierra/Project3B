package ir;

public class ConditionalJumpQuadruple  extends Quadruple{
    public ConditionalJumpQuadruple(Object arg1,Object label){
        super("iffalse",arg1,"goto",label);
    }

    @Override
    public String toString()
	{
		return super.getOp() + " " + super.getArg1() + " " + super.getArg2() + " " + super.getResult();
	}
    
}
