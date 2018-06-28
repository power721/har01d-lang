grammar Har01d;

compilationUnit : ( statement | function | classDeclaration )* EOF ;
statement : variable | assign | print ;
classDeclaration : 'class' className '{' classBody '}' ;
className : ID ;
classBody : field* function* ;
field : type name;
variable : VARIABLE ID EQUALS value ;
assign : ID EQUALS value ;
print : PRINT expression ;
expression : ID | value ;
value : NUMBER | BOOL | STRING ;

function : functionDeclaration block ;
functionDeclaration : 'fun' functionName '(' parametersList? ')' (':' type)? ;
parametersList:  parameter (',' parameter)*
          |  parameter (',' parameterWithDefaultValue)*
          |  parameterWithDefaultValue (',' parameterWithDefaultValue)* ;
functionName : ID ;
parameter : ID ':' type ;
parameterWithDefaultValue : ID ':' type '=' defaultValue=expression ;

type : primitiveType
     | classType ;

primitiveType :  'boolean' ('[' ']')*
                | 'string' ('[' ']')*
                | 'char' ('[' ']')*
                | 'byte' ('[' ']')*
                | 'short' ('[' ']')*
                | 'int' ('[' ']')*
                | 'long' ('[' ']')*
                | 'float' ('[' ']')*
                | 'double' ('[' ']')*
                | 'void' ('[' ']')* ;
classType : qualifiedName ('[' ']')* ;

block : '{' statement* '}' ;

qualifiedName : ID ('.' ID)*;
name : ID ;

VARIABLE : 'var' ;
PRINT : 'print' ;
EQUALS : '=' ;
NUMBER : [0-9]+ ;
BOOL : 'true' | 'false' ;
STRING : '"'.*?'"' | '\''.*?'\'' ;
ID: [_a-zA-Z][_a-zA-Z0-9]* ;
WS: [ \t\n\r]+ -> skip ;