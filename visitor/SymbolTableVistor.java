package visitor;


import syntaxtree.*;

import java.util.HashMap;


import symboltable.*;
import symboltable.Type;


public class SymbolTableVistor  implements Visitor{
    /* 
        SymbolTable tree type structure
    */  
    private SymbolTable symbolTable;
    /*
        Current scope the vistor is in
    */
    private Scope currScope;
    /* 
        To keep track of the block number
    */
    private int blockNum = 0;

    /* 
        Says if theres an error
    */
    public boolean hasError = false;

    /* 
		Mutiply defined Variable error
	*/
	public void mutiplyDefinedError(String name,int row,int col){
		System.out.println(
			"Multiply defined identifier "+name+" at line "+row+", character "+col
		);
        hasError = true;
	}

    /* 
        Undefined Variable Error
    */
    public void undefinedVariableError(String name,int row,int col){
        System.out.println(
            "Use of undefined "+name+" at line "+row+", character "+col
        );
        hasError = true;
    }



    public SymbolTableVistor(){
        this.symbolTable = new SymbolTable();
        this.currScope = this.symbolTable;
    }

    private void clear(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    /*  
        This a getter method the symbol table
    */
    public SymbolTable getSymbolTable(){
        return this.symbolTable;
    }

    /* 
        Converts syntax type Type to symbol tabe type
    */
    private symboltable.Type getType(syntaxtree.Type type){
        if (type instanceof IntegerType){
            return symboltable.Type.INT;
        } else if (type instanceof IntArrayType){
            return symboltable.Type.INTARR;
        } else if (type instanceof BooleanType){
            return symboltable.Type.BOOLEAN;
        } else if (type instanceof IdentifierType){
            return symboltable.Type.IDENTIFIER;
        }
        return null;
    }


    public void visit(Program n){
        /*  
            Visist Main Method
        */
        n.m.accept(this);
        

        /* 
            Visist Class Decl
        */
        int size = n.cl.size();
        for (int i=0;size>i;i++){
            ClassDecl classDecl = n.cl.elementAt(i);
            classDecl.accept(this); 
        }
    }

    public void visit(MainClass n){
        /*  
            Add Main Class to Symbol Tree
        */
        String className = n.i1.toString();
        symbolTable.addClass(className);
        /* 
            Get class
        */
        ClassSymbolTable classSymbolTable =  symbolTable.getClass(className);
        /* 
            Add Main Method
        */
        HashMap<String,Variable> args = new HashMap<>(); 
        String paramName = n.i2.toString();
        args.put(paramName,new Variable(paramName,Type.STRARR));
        classSymbolTable.addMethod(classSymbolTable,"main",args,Type.VOID);

        /* 
            Enter Scope
            1) First Main Class
            2) Next main method
        */
        currScope = currScope.enterScope(className);
        currScope = currScope.enterScope("main");
        n.s.accept(this);
        currScope = currScope.exitScope();
        currScope = currScope.exitScope(); 

    }


    public void visit(ClassDeclSimple n){
        /* 
            Add class
        */
        String className = n.i.toString();
        symbolTable.addClass(className);
        /* 
            Enter Class Scope
        */
        currScope = currScope.enterScope(className);

        ClassSymbolTable classSymbolTable = (ClassSymbolTable) currScope; // get scope as class
        /* 
            Add the vars
        */
        for (int i=0;n.vl.size()>i;i++){
            n.vl.elementAt(i).accept(this);
        }
        /* 
            Add the methods 
        */
        for (int i=0;n.ml.size()>i;i++){
            n.ml.elementAt(i).accept(this);
        }
        /* 
            Exit the scope
        */
        currScope= currScope.exitScope();

    }


    public void visit(ClassDeclExtends n){
        /*
            public Identifier i;
            public Identifier j;
            public VarDeclList vl;  
            public MethodDeclList ml;
        */

        /* 
            Add class
        */
        String className = n.i.toString();
        String parentClassName = n.j.toString();
        symbolTable.addClass(className,parentClassName);
        /* 
            Enter Class Scope
        */
        currScope = currScope.enterScope(className);
        
        ClassSymbolTable classSymbolTable = (ClassSymbolTable) currScope; // get scope as class
        /* 
            Add the vars
        */
        for (int i=0;n.vl.size()>i;i++){
            n.vl.elementAt(i).accept(this);
        }
        /* 
            Add the methods 
        */
        for (int i=0;n.ml.size()>i;i++){
            n.ml.elementAt(i).accept(this);
        }

        /* 
            Exit Class Scope
        */
        currScope = currScope.exitScope();


    }

    /*  
        Adds the variables to the current scope
    */
    public void visit(VarDecl n){
       /* 
            Get name and type
       */
       String name = n.i.toString();
       symboltable.Type type = getType(n.t);
       /* 
            Get current scope
       */
       GenericSymbol genericSymbol = (GenericSymbol) currScope;
       /* 
        Check for re defined Variable error
       */
       if (genericSymbol.findVariable(name)!=null){
           mutiplyDefinedError(name,n.i.rowNum,n.i.colNum);
       }

       /* 
        Add variable
       */
       genericSymbol.addVar(name,type);

       n.t.accept(this);
       n.i.accept(this);
    }
    /* 
    
    */
    public void visit(MethodDecl n){
        // public Type t;
        // public Identifier i;
        // public FormalList fl;
        // public VarDeclList vl;
        // public StatementList sl;
        // public Exp e;
        ClassSymbolTable classSymbolTable = (ClassSymbolTable) currScope;
        /*
            Get the Return Type & name
        */
        symboltable.Type returnType = getType(n.t);
        String name = n.i.toString();
        /* 
            Get args
        */
        HashMap<String,Variable> args = new HashMap<>(); 
        for (int i=0;n.fl.size()>i;i++){
            Formal formal = n.fl.elementAt(i);
            symboltable.Type formalType = getType(formal.t);
            String formalName = formal.i.toString();
            /*
                Check for mutiply defined arguments
            */
            if (args.containsKey(formalName)){
                mutiplyDefinedError(formalName,formal.i.rowNum,formal.i.colNum);
            }
            args.put(formalName,new Variable(formalName,formalType));
        }
        /* 
            Add method to symbol table
        */
        classSymbolTable.addMethod(classSymbolTable,name,args,returnType);

        /*
            Go into method scope
        */
        currScope = currScope.enterScope(name);

        /*
            Visit Var
        */
        for (int i=0;n.vl.size()>i;i++){ 
            n.vl.elementAt(i).accept(this);
        }
        /* 
            Visit staments
        */
        for ( int i = 0; i < n.sl.size(); i++ ) 
		{
			n.sl.elementAt(i).accept(this);
		}
    
        
        /* 
            Exit scope
        */
        currScope = currScope.exitScope();
    }

    public void visit(Block n){
        /* 
            Get current scope as genric Symbol
        */
        GenericSymbol genericSymbol = (GenericSymbol) currScope;
        /*  
            Get next blockId
        */
        String blockName = ""+blockNum;
        blockNum++;
        /* 
            Add Block to scope
        */
        genericSymbol.addTable(blockName);
        /* 
            Enter Block Scope
        */
        currScope = genericSymbol.enterScope(blockName);
        /* 
            Visit all staments
        */
        for ( int i = 0; i < n.sl.size(); i++ ) 
		{
			n.sl.elementAt(i).accept(this);
		}
        /* 
            Exit back to current block scope
        */
		currScope = genericSymbol;

    }

    public void visit(Formal n){
        n.t.accept(this);
		n.i.accept(this);
    }
    public void visit(IntArrayType n){}
    public void visit(BooleanType n){}
    public void visit(IntegerType n){}
    public void visit(IdentifierType n){}
    public void visit(If n){
        n.e.accept(this);
        n.s1.accept(this);
        n.s2.accept(this);
    } 
    public void visit(While n){
        n.e.accept(this);
		n.s.accept(this);
    }
    public void visit(Print n){
        n.e.accept(this);
    }
    public void visit(Assign n){
        n.i.accept(this);
		n.e.accept(this);
        if (currScope.findVariable(n.i.s)==null){
            undefinedVariableError(n.i.s,n.i.rowNum,n.i.colNum);
        }
    }
    public void visit(ArrayAssign n){
        n.i.accept(this);
		n.e1.accept(this);
		n.e2.accept(this);
        if (currScope.findVariable(n.i.s)==null){
            undefinedVariableError(n.i.s,n.i.rowNum,n.i.colNum);
        }
    }
    public void visit(And n){
        n.e1.accept(this);
		n.e2.accept(this);
    }
    public void visit(LessThan n){
        n.e1.accept(this);
		n.e2.accept(this);
    }
    public void visit(Plus n){
        n.e1.accept(this);
		n.e2.accept(this);
    }
    public void visit(Minus n){
        n.e1.accept(this);
		n.e2.accept(this);
    }
    public void visit(Times n){
        n.e1.accept(this);
		n.e2.accept(this);
    }
    
    public void visit(ArrayLookup n){
        n.e1.accept(this);
		n.e2.accept(this);
    }
    public void visit(ArrayLength n){
        n.e.accept(this);
    }
    public void visit(Call n){
        n.e.accept(this);
		n.i.accept(this);
    
		for ( int i = 0; i < n.el.size(); i++ )
		{
			n.el.elementAt(i).accept(this);
		}
    }
    public void visit(IntegerLiteral n){}
    public void visit(True n){}
    public void visit(False n){}
    public void visit(IdentifierExp n){
        if (currScope.findVariable(n.s)==null){
            undefinedVariableError(n.s,n.rowNum,n.colNum);
        }
    }
    public void visit(This n){}
    public void visit(NewArray n){
        n.e.accept(this);
    }
    public void visit(NewObject n){}
    public void visit(Not n){
        n.e.accept(this);
    }
    public void visit(Identifier n){}
}
