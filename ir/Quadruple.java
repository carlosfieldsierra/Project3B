package ir;

/* 
    Assignment x := y op z
    Unary Assignment x := op y
    Copy x := y
    Unconditional Jump goto LABEL
    Conditional Jump iffalse x goto LABEL
    Parameter param x
    Call x := call f, NUMPARAMS
    Return return y
    Indexed assignment x := y[i]
                        y[i] := x
    New x := new TYPE
    New Array x := new TYPE, SIZE
    Length x := length y
*/


public abstract class Quadruple {
    private Object operator;
    private Object argument1;
    private Object argument2;
    private Object result;

    public Quadruple(Object op, Object arg1, Object arg2, Object r){
        operator = op;
        argument1 = arg1;
        argument2 = arg2;
        result = r;
    }       
    
    public Object getOp(){
        return this.operator;
    }

    public Object getArg1(){
        return this.argument1;
    }

    public Object getArg2(){
        return this.argument2;
    }

    public Object getResult(){
        return this.result;
    }

    



}
