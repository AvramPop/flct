/* A Bison parser, made by GNU Bison 3.5.1.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2020 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Undocumented macros, especially those whose name start with YY_,
   are private implementation details.  Do not rely on them.  */

#ifndef YY_YY_Y_TAB_H_INCLUDED
# define YY_YY_Y_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token type.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    BEGINN = 258,
    IF = 259,
    ELSE = 260,
    PRINT = 261,
    READ = 262,
    WHILE = 263,
    L_BRACKET = 264,
    R_BRACKET = 265,
    L_SBRACKET = 266,
    R_SBRACKET = 267,
    L_CURLY = 268,
    R_CURLY = 269,
    COMMA = 270,
    DOT = 271,
    SEMICOLON = 272,
    ID = 273,
    CONST = 274,
    STRING = 275,
    INT = 276,
    DOUBLE = 277,
    ASSIGNMENT = 278,
    EE = 279,
    NE = 280,
    LE = 281,
    L = 282,
    GE = 283,
    G = 284,
    SPACE = 285,
    PLUS = 286,
    MINUS = 287,
    TIMES = 288,
    DIV = 289,
    MOD = 290,
    OR = 291,
    AND = 292,
    NOT = 293
  };
#endif
/* Tokens.  */
#define BEGINN 258
#define IF 259
#define ELSE 260
#define PRINT 261
#define READ 262
#define WHILE 263
#define L_BRACKET 264
#define R_BRACKET 265
#define L_SBRACKET 266
#define R_SBRACKET 267
#define L_CURLY 268
#define R_CURLY 269
#define COMMA 270
#define DOT 271
#define SEMICOLON 272
#define ID 273
#define CONST 274
#define STRING 275
#define INT 276
#define DOUBLE 277
#define ASSIGNMENT 278
#define EE 279
#define NE 280
#define LE 281
#define L 282
#define GE 283
#define G 284
#define SPACE 285
#define PLUS 286
#define MINUS 287
#define TIMES 288
#define DIV 289
#define MOD 290
#define OR 291
#define AND 292
#define NOT 293

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */
