package ir;

public class NewQuadruple extends Quadruple {
    public NewQuadruple(Object arg1,Object r){
        super("new",arg1,null,r);
    }
    
    @Override
    public String toString()
	{
		return super.getResult() + " := " + super.getOp() + " " + super.getArg1();
	}
}
