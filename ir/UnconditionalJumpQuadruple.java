package ir;

public class UnconditionalJumpQuadruple  extends Quadruple{
    public UnconditionalJumpQuadruple(Object label){
        super("goto",null,null,label);
    }

    public String toString()
	{
		return super.getOp() + " " + super.getResult();
	}
}
