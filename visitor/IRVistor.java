package visitor;
import java.util.ArrayList;
import java.util.HashMap;

import syntaxtree.*;
import ir.*;
import symboltable.*;

public class IRVistor implements Visitor {


    /* 
        Use to give temps a unique id
    */
    private TempNum tempNum = new TempNum();
    /* 
        Quadruple to label HashMap
    */
    private HashMap<Quadruple,ArrayList<Label>> labelsMap = new HashMap<>();
    /* 
        To keep track of the block number
    */
    private int blockNum = 0;
    /* 
        List of Ir instructions
    */
    private ArrayList<Quadruple> IR = new ArrayList<Quadruple>();
    /* 
        Current Scope
    */
    private Scope currScope;

    public String addLabel(Quadruple quadruple, Label label)
	{
		ArrayList<Label> labelLst = labelsMap.get(quadruple);
		
		if(labelLst == null)
		{
			labelLst = new ArrayList<Label>();
		}
		
		labelLst.add(label);
		labelsMap.put(quadruple, labelLst);
		
		return label.getLabel();
	}
    


    public void printIR(){
        for (Quadruple q:IR){
            if (labelsMap.containsKey(q)){
                Label label = labelsMap.get(q).get(0);
                if (label.isTrue){
                    System.out.println(label+":");
                    System.out.println(q);
                } else {
                    System.out.println(q);
                    System.out.println(label+":");
                }
            } else {
                System.out.println(q);
            }
        }
    }

    public IRVistor(SymbolTable symbolTable){
        currScope = symbolTable;
    }

    /* 
        Get IR List
    */
    public ArrayList<Quadruple> getIR(){
        return this.IR;
    }

    /* 
        Used to reset the Vistor
    */  
    public void reset() {
        tempNum = new TempNum();
        IR = new ArrayList<Quadruple>();
    }

    /* 
        Visit Program
    */
    public void visit(Program n){


   
        /* 
            Visit Main Class
        */
        n.m.accept(this);
        /* 
            Visit classes
        */
        for (int i=0;n.cl.size()>i;i++){
            ClassDecl classDecl = n.cl.elementAt(i);
            classDecl.accept(this); 
        }
    
        System.out.println("\n\n");
        printIR();
        System.out.println("\n\n");
       
       

    }
    
    
    /* 
        Visit MainClass
    */
    public void visit(MainClass n){
        /* 
            Enter main method scope
        */
        currScope = currScope.enterScope(n.i1.toString());
        currScope = currScope.enterScope("main");
        /* 
            Visist sub tree nodes
        */
        n.i1.accept(this);
        n.i2.accept(this);
        n.s.accept(this);

        /* 
            Exit the scope
        */  
        currScope = currScope.exitScope();
        currScope = currScope.exitScope();
    }

    /* 
        Visit ClassDeclSimple
    */
    public void visit(ClassDeclSimple n){
       
        /* 
            Enter Class Scope
        */
        currScope = currScope.enterScope(n.i.toString());
        n.i.accept(this);
        /* 
            Visit VarDecl
        */
        for ( int i = 0; n.vl.size()>i; i++ ) 
		{
       		 n.vl.elementAt(i).accept(this);
    	}
        /* 
            Visit MethodDecl
        */
        for (int i=0;n.ml.size()>i;i++){
            n.ml.elementAt(i).accept(this);
        }

        /*      
            Exit Scope
        */
        currScope = currScope.exitScope();
    }
  
    /* 
        Visit ClassDeclExtends
    */
    public void visit(ClassDeclExtends n){
        
        /* 
            Enter Class Scope
        */
        currScope = currScope.enterScope(n.i.toString());
        /*
            Visit ids
        */
        n.i.accept(this);
    	n.j.accept(this);


        /* 
            Visit VarDecl
        */
        for ( int i = 0; n.vl.size()>i; i++ ) 
		{
       		 n.vl.elementAt(i).accept(this);
    	}
        /* 
            Visit MethodDecl
        */
        for (int i=0;n.ml.size()>i;i++){
            n.ml.elementAt(i).accept(this);
        }

        /*      
            Exit Scope
        */
        currScope = currScope.exitScope();
    }

    /* 
        Visit VarDecl
    */
    public void visit(VarDecl n){
        n.t.accept(this);
        n.i.accept(this);
    }
    /*  
        Visist MethodDecl
    */
    public void visit(MethodDecl n){
        /*      
            Enter Scope
        */
        currScope = currScope.enterScope(n.i.toString());

        /*
            Visit Type & ID
        */
        n.t.accept(this);
        n.i.accept(this);
        /* 
            Index before method 
        */
        int indexBeforeMethod = IR.size();
        /* 
            Visit Formals
        */
    	for ( int i = 0;  n.fl.size()>i; i++ ) 
		{
       		 n.fl.elementAt(i).accept(this);
    	}
        /*  
            Visit VarDecl
        */
    	for ( int i = 0;  n.vl.size()>i; i++ ) 
		{
        	n.vl.elementAt(i).accept(this);
    	}
        /* 
            Visit Staments
        */
    	for ( int i = 0;  n.sl.size()>i; i++ )
		{
        	n.sl.elementAt(i).accept(this);
		}
        /* 
            Add  Return Exp Ir
        */
        n.e.accept(this);
        IR.add(new ReturnQuadruple(n.e.getVar(tempNum)));
       
        /* 
            Exit Scope
        */
        currScope = currScope.exitScope();
    }
    /*      
        Visit Formal
    */
    public void visit(Formal n){
        n.t.accept(this);
		n.i.accept(this);
    }
    /* 
        Visit Block
    */
    public void visit(Block n){
        /* 
            Get block name
        */
        String blockName = ""+blockNum;
        blockNum++;
        /*  
            Enter block scope
        */
        currScope = currScope.enterScope(blockName); 
        
        /* 
            Visit Staments
        */
        for ( int i = 0; i < n.sl.size(); i++ ) 
		{
			n.sl.elementAt(i).accept(this);
		}
        /*
            Exit Block Scope
        */
        currScope = currScope.exitScope();

    }
    /* 
        Visit NewArray
    */
    public void visit(NewArray n){
        n.e.accept(this);
        IR.add(new  NewArrayQuadruple(
            "int",
            n.e.getVar(tempNum),
            n.getVar(tempNum)
        ));
    }
    /* 
        Visit Assign
    */
    public void visit(Assign n){
        n.i.accept(this);
		n.e.accept(this);
        IR.add(new CopyQuadruple(
            n.e.getVar(tempNum),
            currScope.findVariable(n.i.toString())
        ));
    }
    /* 
        Visit While
    */
    public void visit(While n){
        /* 
            Make labels
        */
        Label labelOne = new Label(false);
		Label labelTwo = new Label(false);
        /* 
            Visit expression
        */
        n.e.accept(this);
        /* 
            Add label
        */
        int lastIndex = IR.size()-1;
        if (lastIndex>0){
            addLabel(IR.get(lastIndex), labelOne);
        }
        
        /* 
            Add condonital Jump
        */  
        IR.add(new ConditionalJumpQuadruple(
            n.e.getVar(tempNum),
            labelTwo
        ));
        /* 
            Visit Stament
        */
        n.s.accept(this);
        /* 
            Add uncondonitial Jump
        */
        IR.add(new UnconditionalJumpQuadruple(
            labelOne
        ));
        /* 
            Add label
        */
        addLabel(IR.get(IR.size()-1), labelTwo);
    }
    /* 
        Visit if
    */
    public void visit(If n){
        /* 
            Create True & False Labels
        */
        Label trueLabel = new Label(true);
        Label falseLabel = new Label(false);
        /*
            Visit Expression
        */
        n.e.accept(this);
        /*  
            Add if false jump ir
        */
        Quadruple q1 = new ConditionalJumpQuadruple(
            n.e.getVar(tempNum),
            trueLabel
        ); 
        IR.add(q1);
        /* 
            Visit Stament One
        */
        n.s1.accept(this);
        /* 
            Add else Jump
        */
        Quadruple q2 = new UnconditionalJumpQuadruple(
            falseLabel
        );
        IR.add(q2);
        /* 
            Visit s2
        */
        int firstStamtIndex = IR.size();
        n.s2.accept(this);
        int lastStamtIndex = IR.size()-1;
        
        /* 
            Add labels to labelsMap
        */
        if (firstStamtIndex==IR.size()){
            firstStamtIndex = firstStamtIndex-1;
            lastStamtIndex = firstStamtIndex-1;
        }
        addLabel(IR.get(firstStamtIndex), trueLabel);
        addLabel(IR.get(lastStamtIndex), falseLabel);

    }
    /* 
        Visit Call
    */
    public void visit(Call n){
        n.e.accept(this);
		n.i.accept(this);
        /* 
            Visit all exps
        */
        for (int i=0;n.el.size()>i;i++){
            n.el.elementAt(i).accept(this);
        }
        /*
            Add the caller as the first param to call
        */
       
        IR.add(new ParameterQuadruple(n.e.getVar(tempNum)));
        /* 
            Add all the exps
        */
        for (int i=0;n.el.size()>i;i++){
            IR.add(new ParameterQuadruple(
                n.el.elementAt(i).getVar(tempNum)
            ));
        }
        /* 
            Add the call exp
        */
        IR.add(new CallQuadruple(
            n.i.toString(),
            ""+(n.el.size()+1), // Plus one beacuse you add the caller as the first param
            n.getVar(tempNum)
        ));
    }
    /* 
        Visist Print
    */
    public void visit(Print n){
        n.e.accept(this);
        /* 
            Add parameter
        */
        Variable var = n.e.getVar(tempNum);
        IR.add(new ParameterQuadruple(var));
        /* 
            Add call for System.out.println
        */
        IR.add(new CallQuadruple("System.out.println","1",null));
    }
    /* 
        Visit ArrayAssign
    */
    public void visit(ArrayAssign n){
        n.i.accept(this);
		n.e1.accept(this);
		n.e2.accept(this);
       
        IR.add(new IndexedAssignmentTwoQuadruple(
            n.e2.getVar(tempNum),
            n.e1.getVar(tempNum),
            currScope.findVariable(n.i.toString())
        ));

       
    } 
    /* 
        Visit And
    */
    public void visit(And n){
        n.e1.accept(this);	
		n.e2.accept(this);
        IR.add(new AssignmentQuadruple(
            "&&",
            n.e1.getVar(tempNum),
            n.e2.getVar(tempNum),
            n.getVar(tempNum)
        ));
    }
    /* 
        Visit LessThan
    */
    public void visit(LessThan n){
        n.e1.accept(this);
		n.e2.accept(this);
        IR.add(new AssignmentQuadruple(
            "<",
            n.e1.getVar(tempNum),
            n.e2.getVar(tempNum),
            n.getVar(tempNum)
        ));
    }
    /* 
        Visit Plus
    */
    public void visit(Plus n){
        n.e1.accept(this);
		n.e2.accept(this);
        IR.add(new AssignmentQuadruple(
            "+",
            n.e1.getVar(tempNum),
            n.e2.getVar(tempNum),
            n.getVar(tempNum)
        ));
    }
    /* 
        Visit Minus
    */
    public void visit(Minus n){
        n.e1.accept(this);
		n.e2.accept(this);
        IR.add(new AssignmentQuadruple(
            "-",
            n.e1.getVar(tempNum),
            n.e2.getVar(tempNum),
            n.getVar(tempNum)
        ));
    }
    /* 
        Visit Times
    */
    public void visit(Times n){
        n.e1.accept(this);
		n.e2.accept(this);
        IR.add(new AssignmentQuadruple(
            "*",
            n.e1.getVar(tempNum),
            n.e2.getVar(tempNum),
            n.getVar(tempNum)
        ));
    }
    /* 
        Visit Not
    */
    public void visit(Not n){
        n.e.accept(this);
        IR.add(new UnaryAssignmentQuadruple("!", n.e.getVar(tempNum), n.getVar(tempNum)));
    }
    /* 
        Visit ArrayLookup
    */
    public void visit(ArrayLookup n){
        n.e1.accept(this);
		n.e2.accept(this);
        IR.add(new IndexedAssignmentOneQuadruple(n.e1.getVar(tempNum),n.e2.getVar(tempNum),n.getVar(tempNum)));
    }
    /* 
        Visit ArrayLength
    */
    public void visit(ArrayLength n){
        IR.add(new  LengthQuadruple(n.e.getVar(tempNum),n.getVar(tempNum)));
    }
    /* 
        Visit IdentifierExp
    */
    public void visit(IdentifierExp n){
        /*  
            Set the var to be the one in the symbol table
        */
        n.var = currScope.findVariable(n.s);
    }
    /*
        Visit NewObject
    */
    public void visit(NewObject n){
        IR.add(new  NewQuadruple(n.i.toString(),n.getVar(tempNum)));
    }
    

    /* 
        Do nothing
    */
    public void visit(IntArrayType n){}
    public void visit(BooleanType n){}
    public void visit(IntegerType n){}
    public void visit(IdentifierType n){}
    public void visit(Identifier n){}
    public void visit(This n){}
    public void visit(IntegerLiteral n){}
    public void visit(True n){}
    public void visit(False n){}

}
