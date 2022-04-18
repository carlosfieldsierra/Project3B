import java.util.*;
import java_cup.runtime.*;
import syntaxtree.LineTracker;
%% 
%cup
%class ExprLex
%implements sym
%line
%column
%eofclose
%{
   
%}

/* Must start with a letter and then can be any number of letters or numbers and under scores  */
Identifier = [a-zA-Z_][a-zA-Z0-9_]*

/* Can be zero or a number that starts with 1-9 and then any number of numbers*/
Integer = 0 | [1-9][0-9]* 

/* 
    Matches new line character or carriage return character 
    or  carriage return  and new line character
*/
LineTerminator = \r|\n|\r\n

/* This matches tabs \f which skips the start of the page*/
WhiteSpace = {LineTerminator} | [ \t\f]

/* InputCharacter can be anything thats not a carriage return or new line */
InputCharacter = [^\r\n]

/* 
    Starts and end with multiline comment 
    [^*] this ensures that theres no documenation
    comments, and the next or is for the case wheres
    theres no content in the comment
*/
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"

// Comment can be the last line of the file, without line terminator.
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?

Comment = {TraditionalComment} | {EndOfLineComment} 

%% 
{Comment} { /* Do Nothing */}
"+"  {  return new Symbol(PLUS,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1));}
";"  { return new Symbol(SEMICOLON,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"("  { return new Symbol(LPAREN,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
")"  { return new Symbol(RPAREN,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"{"  { return new Symbol(LBRACE,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"}"  { return new Symbol(RBRACE,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"["  { return new Symbol(LBRACKET,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"]"  { return new Symbol(RBRACKET,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
","  { return new Symbol(COMMA,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"="  { return new Symbol(ASSIGN,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"&&" { return new Symbol(AND,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"<"  { return new Symbol(LESSTHAN,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"-"  { return new Symbol(MINUS,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"*"  { return new Symbol(MULTIPY,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"."  { return new Symbol(DOT,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"!"  { return new Symbol(NOT,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"class" { return new Symbol(CLASS,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"public" { return new Symbol(PUBLIC,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"static" { return new Symbol(STATIC,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"void" { return new Symbol(VOID,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"main" { return new Symbol(MAIN,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"String" { return new Symbol(STRING,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"extends" { return new Symbol(EXTENDS,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"return" { return new Symbol(RETURN,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"int" { return new Symbol(INT,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"boolean" { return new Symbol(BOOLEAN,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"if" { return new Symbol(IF,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"else" { return new Symbol(ELSE,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"while" { return new Symbol(WHILE,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"System.out.println" { return new Symbol(PRINT,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"length" { return new Symbol(LENGTH,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"true" { return new Symbol(TRUE,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"false" { return new Symbol(FALSE,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"this" { return new Symbol(THIS,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
"new" { return new Symbol(NEW,yyline+1, yycolumn+1, new LineTracker(yyline+1,yycolumn+1)); }
{Integer} {
    String strVal = yytext();
    int value = Integer.parseInt(yytext());
    return new Symbol(INTEGER_LITERAL,yyline+1, yycolumn+1, new LineTracker(value,yyline+1,yycolumn+1));
}
{Identifier} {
    String strVal = yytext();
    return new Symbol(IDENTIFIER,yyline+1, yycolumn+1, new LineTracker(strVal,yyline+1,yycolumn+1));
}
\n {/* Do Nothing */}
\s { /* Do Nothing this \s matches whit space */}
. {
   
}