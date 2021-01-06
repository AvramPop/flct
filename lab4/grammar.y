%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define YYDEBUG 1

%}

%token BEGINN
%token IF
%token ELSE
%token PRINT
%token READ
%token WHILE

%token L_BRACKET
%token R_BRACKET
%token L_SBRACKET
%token R_SBRACKET
%token L_CURLY
%token R_CURLY

%token COMMA
%token DOT
%token SEMICOLON

%token ID
%token CONST

%token STRING
%token INT
%token DOUBLE

%token ASSIGNMENT
%token EE
%token NE
%token LE
%token L
%token GE
%token G
%token SPACE

%left PLUS MINUS
%left TIMES DIV MOD
%left OR
%left AND
%left NOT

%%
program : BEGINN cmpdstmt
	;
declaration :  type ID
	    ;
type :  INT | STRING | DOUBLE
	   ;
cmpdstmt : L_CURLY stmtlist R_CURLY
	 ;
stmtlist :  stmt stmtTemp
	 ;
stmtTemp : /*Empty*/ | stmtlist
	 ;
stmt :  simplstmt SEMICOLON | structstmt
     ;
simplstmt :  assignstmt | iostmt | declaration
	 ; 
structstmt :  cmpdstmt | ifstmt | whilestmt
	   ;
ifstmt :  IF condition cmpdstmt tempIf
       ;
tempIf : /*Empty*/ | ELSE cmpdstmt
       ;	
whilestmt :  WHILE condition cmpdstmt
	  ;
assignstmt :  ID ASSIGNMENT expression
	   ;
expression : arithmetic2 arithmetic1
	   ;
arithmetic1 : PLUS arithmetic2 arithmetic1 | MINUS arithmetic2 arithmetic1 | /*Empty*/
	    ;
arithmetic2 : multiply2 multiply1
	    ;
multiply1 : TIMES multiply2 multiply1 | DIV multiply2 multiply1 | MOD multiply2 multiply1 | /*Empty*/ 
	  ;
multiply2 : L_BRACKET expression R_BRACKET | CONST | ID | IndexedIdentifier
	  ;
IndexedIdentifier :  ID L_SBRACKET CONST L_SBRACKET
		  ;
iostmt :  READ L_BRACKET ID R_BRACKET | PRINT L_BRACKET ID R_BRACKET | PRINT L_BRACKET CONST R_BRACKET 
      ; 
condition :  
L_BRACKET expression G expression R_BRACKET |
	 L_BRACKET expression GE expression R_BRACKET | 
	 L_BRACKET expression L expression R_BRACKET |
	 L_BRACKET expression LE expression R_BRACKET | 
	 L_BRACKET expression EE expression R_BRACKET |
	 L_BRACKET expression NE expression R_BRACKET 
	  ; 

%%

yyerror(char *s)
{
  printf("%s\n", s);
}

extern FILE *yyin;

main(int argc, char **argv)
{
  if(argc>1) yyin = fopen(argv[1], "r");
  if((argc>2)&&(!strcmp(argv[2],"-d"))) yydebug = 1;
  if(!yyparse()) fprintf(stderr,"\tO.K.\n");
}
