#!/bin/bash
java -jar  java-cup-11b.jar -parser ExprParser -interface ExprParser.cup 
jflex ExprLex.flex
javac -classpath .:java-cup-11b.jar  -Xlint:deprecation Main.java 
# clear
# cd symboltable
# javac *.java
# cd ..
# cd ir
# javac *.java
# cd ..
# cd syntaxtree
# javac *.java
# cd ..
java -classpath .:java-cup-11b-runtime.jar Main $1
rm ExprParser.java
rm ExprParser.class
rm ExprLex.java
rm ExprLex.class
rm Main.class
rm ExprLex.java~
rm *.class 
cd symboltable
rm *.class
cd ..
cd ir
rm *.class
cd ..
cd syntaxtree
rm *.class
cd ..


# javac TestAst.java
# java TestAst
# rm *.class


# jflex ExprLex.flex
# javac -classpath .:java-cup-11b.jar Main.java 
# java -classpath .:java-cup-11b-runtime.jar Main Test/fib.asm