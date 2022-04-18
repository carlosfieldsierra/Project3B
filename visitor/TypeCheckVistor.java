package visitor;

import syntaxtree.*;
import symboltable.*;

public class TypeCheckVistor implements TypeVisitor {
    /* 
        Has Error
    */
    public boolean hasError = false;
    /* 
        The symbol table
    */
    private SymbolTable symbolTable;
    /* 
        Current Scope
    */
    private Scope currScope;
    /* 
        Current Class Scope
    */
	private ClassSymbolTable currClass;
    /* 
        Current Method Scope
    */
	private MethodSymbolTable currMethod;
    /* 
        Keep track of block number
    */
    private int blockNum =0;


    public TypeCheckVistor(SymbolTable symbolTable){
        this.currScope = symbolTable;
        this.symbolTable = symbolTable;

    }   


    /* 
        Checks if Type is boolean
    */
    private boolean isBool(syntaxtree.Type t){
        /* 
            If type boolean trivaly true
        */
        if (t instanceof syntaxtree.BooleanType) return true;

        /* 
        
        */
        if (t instanceof syntaxtree.IdentifierType){
            System.out.println(t);
        }
        System.out.println(t);
        
        return true;
    }

    /*
        Visit Program
    */
    public syntaxtree.Type visit(Program n){
        /* 
            Visit Main Class
        */
        n.m.accept(this);
        /* 
            Visit Classes
        */
        for (int i=0;n.cl.size()>i;i++){
            n.cl.elementAt(i).accept(this);
        }
        return null;
    }
    /* 
        Visit MainClass
    */
    public syntaxtree.Type visit(MainClass n){
        /* 
            Enter ClassScope
        */
        currScope = currScope.enterScope(n.i1.toString());
        currClass = (ClassSymbolTable) currScope;
        /* 
            Enter Method Scope
        */
        currScope = currScope.enterScope("main");
        currMethod = (MethodSymbolTable) currScope;
        /* 
            Visit Children
        */
        n.i1.accept(this);
        n.i2.accept(this);
        n.s.accept(this);
        /*  
            Exit Scope
        */
        currScope = currScope.exitScope();
        currScope = currScope.exitScope();
        return null;
    }
    /* 
        Visit ClassDeclSimple
    */
    public syntaxtree.Type visit(ClassDeclSimple n){
        /* 
            Enter Class Scope
        */
        currScope = currScope.enterScope(n.i.toString());
        currClass = (ClassSymbolTable) currScope;
        /* 
            Visit Children
        */
        n.i.accept(this);
        // Visit Vars
        for (int i=0;n.vl.size()>i;i++){
            n.vl.elementAt(i).accept(this);
        }
        // Visit Methods
        for (int i=0;n.ml.size()>i;i++){
            n.ml.elementAt(i).accept(this);
        }

        /* 
            Exit Scope
        */
        currScope = currScope.exitScope();

        return null;
    }

    /* 
        Visit ClassDeclExtends
    */
    public syntaxtree.Type visit(ClassDeclExtends n){
         /* 
            Enter Class Scope
        */
        currScope = currScope.enterScope(n.i.toString());
        currClass = (ClassSymbolTable) currScope;
        /* 
            Visit Children
        */
        n.i.accept(this);
        n.j.accept(this);
        // Visit Vars
        for (int i=0;n.vl.size()>i;i++){
            n.vl.elementAt(i).accept(this);
        }
        // Visit Methods
        for (int i=0;n.ml.size()>i;i++){
            n.ml.elementAt(i).accept(this);
        }

        /* 
            Exit Scope
        */
        currScope = currScope.exitScope();

        return null;
    }
    /* 
        Visit VarDecl
    */
    public syntaxtree.Type visit(VarDecl n){
        n.t.accept(this);
        n.i.accept(this);
        return null;
    }
    /* 
        Visit MethodDecl
    */
    public syntaxtree.Type visit(MethodDecl n){
        /* 
            Enter Method Scope
        */
        currScope = currScope.enterScope(n.i.toString());
        currMethod = (MethodSymbolTable) currScope;
        /* 
            Visit Children
        */
        n.t.accept(this);
    	n.i.accept(this);
        for ( int i = 0; n.fl.size()>i; i++ ) {
       		 n.fl.elementAt(i).accept(this);
    	}
		
    	for ( int i = 0; n.vl.size()>i; i++ ) {
        	n.vl.elementAt(i).accept(this);
    	}
		
    	for ( int i = 0; n.sl.size()>i; i++ ) {
        	n.sl.elementAt(i).accept(this);
    	}
    	
    	n.e.accept(this);
        /* 
            Exit Scope
        */
        currScope = currScope.exitScope();

        return null;    
    }
    /*
        Visit Formal 
    */
    public syntaxtree.Type visit(Formal n){
        n.t.accept(this);
        n.i.accept(this);
        return null;
    }
    /* 
        Visit Types
    */
    public syntaxtree.Type visit(IntArrayType n){return null;}
    public syntaxtree.Type visit(BooleanType n){return null;}
    public syntaxtree.Type visit(IntegerType n){return null;}
    public syntaxtree.Type visit(IdentifierType n){return null;}
    /* 
        Visit Block
    */
    public syntaxtree.Type visit(Block n){
        /* 
            Get Block Name
        */
        String blockName = ""+blockNum;
        blockNum++;
        /* 
            Enter Block Scope
        */
        currScope = currScope.enterScope(blockName);
        /* 
            Visit Staments
        */
        for (int i=0;n.sl.size()>i;i++){
            n.sl.elementAt(i).accept(this);
        }
        /* 
            Exit Scope
        */
        currScope = currScope.exitScope();

        return null;
    }
    public syntaxtree.Type visit(If n){

        syntaxtree.Type exprType = n.e.accept(this);
        System.out.println(n.e);
        if (!isBool(exprType)){
            hasError = true;
            System.out.println(
                "Non-boolean expression used as the condition of "+n+"statement at line "+n.rowNum+", character "+n.colNum
            );
        }

        n.s1.accept(this);
        n.s2.accept(this);
        return null;
    }
    public syntaxtree.Type visit(While n){return null;}
    public syntaxtree.Type visit(Print n){return null;}
    public syntaxtree.Type visit(Assign n){return null;}
    public syntaxtree.Type visit(ArrayAssign n){return null;}
    public syntaxtree.Type visit(And n){return null;}
    /* 
        Visit LessThan
    */
    public syntaxtree.Type visit(LessThan n){
        syntaxtree.Type typeOne = n.e1.accept(this);
        syntaxtree.Type typeTwo = n.e2.accept(this);
        
        return null;
    }
    public syntaxtree.Type visit(Plus n){return null;}
    public syntaxtree.Type visit(Minus n){return null;}
    public syntaxtree.Type visit(Times n){return null;}
    public syntaxtree.Type visit(ArrayLookup n){return null;}
    public syntaxtree.Type visit(ArrayLength n){return null;}
    public syntaxtree.Type visit(Call n){return null;}
    public syntaxtree.Type visit(IntegerLiteral n){return new IntegerType();}
    public syntaxtree.Type visit(True n){return new BooleanType();}
    public syntaxtree.Type visit(False n){return new BooleanType();}
    public syntaxtree.Type visit(IdentifierExp n){return null;}
    public syntaxtree.Type visit(This n){return null;}
    public syntaxtree.Type visit(NewArray n){return null;}
    public syntaxtree.Type visit(NewObject n){return null;}
    public syntaxtree.Type visit(Not n){return null;}
    public syntaxtree.Type visit(Identifier n){return null;}


}
