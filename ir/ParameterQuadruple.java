package ir;

public class ParameterQuadruple extends Quadruple {
    public ParameterQuadruple(Object arg1){
        super("param",arg1,null,null);

    }

    @Override
    public String toString(){
       

        return ((String) super.getOp())+" "+super.getArg1();
    }
}
