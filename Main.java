import java_cup.runtime.Symbol;
import syntaxtree.Program;
import visitor.SymbolTableVistor;
import visitor.IRVistor;
import visitor.TypeCheckVistor;
import symboltable.SymbolTable;
import ir.*;
import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
      if(args.length != 1) {
        System.err.println("ERROR: Invalid number of command line arguments.");
        System.err.println("Usage: java Calc file.asm");
        System.exit(1);
		  }
      /* 
        Parse 
      */
      Symbol parse_tree = null;
      try {
        /* 
          Run the parser
        */
        ExprParser parser_obj = new ExprParser(new ExprLex(new FileReader(args[0])));
        parse_tree = parser_obj.parse();
        
        /* 
          Get the program
        */
        Program program = parser_obj.getProgram();
    

        if (parser_obj.hasError){
          return;
        }
        /*
          Create the Symbol Table
        */
        SymbolTableVistor symbolTableVistor = new SymbolTableVistor();
        symbolTableVistor.visit(program);

        /* 
          Create Type Check Vistor
        */
        // TypeCheckVistor typeCheckVistor = new TypeCheckVistor(symbolTableVistor.getSymbolTable());
        // typeCheckVistor.visit(program);

        /* 
          Create IR if no errors were detected
        */
        if (!symbolTableVistor.hasError){
          /*  
            Create IR list
          */
          IRVistor irVistor = new IRVistor(symbolTableVistor.getSymbolTable());
          irVistor.visit(program);
        }

      } catch (IOException e) {
        System.err.println("ERROR: Unable to open file: " + args[0]);
      } catch (Exception e) {
        e.printStackTrace(System.err);
      }
    }
}
