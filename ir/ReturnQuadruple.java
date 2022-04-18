package ir;

public class ReturnQuadruple extends Quadruple {
    public ReturnQuadruple(Object arg1){
        super("return",arg1,null,null);
    }

    @Override
    public String toString(){
        return super.getOp()+" "+super.getArg1();
    }
    
}
