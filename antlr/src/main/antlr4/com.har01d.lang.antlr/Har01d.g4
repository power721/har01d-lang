grammar Har01d;

compilationUnit : ( statement | function | classDeclaration )* EOF ;
statement : variableDeclaration | valueDeclaration | assignment | print ;
classDeclaration : 'class' className '{' classBody '}' ;
className : ID ;
classBody : field* function* ;
field : type name ;
variableDeclaration : VARIABLE name EQUALS expression ;
valueDeclaration : VALUE name EQUALS expression ;
assignment : name EQUALS expression ;
print : PRINT expression ;
expression : variableReference | literal ;
literal : NUMBER | BOOL | STRING ;

variableReference: ID ;
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

qualifiedName : ID ('.' ID)* ;
name : ID ;

VARIABLE : 'var' ;
VALUE : 'val' ;
PRINT : 'print' ;
EQUALS : '=' ;
NUMBER : [0-9]+ ;
BOOL : 'true' | 'false' ;
STRING : '"'.*?'"' | '\''.*?'\'' ;
ID: [_a-zA-Z][_a-zA-Z0-9]* ;
WS: [ \t\n\r]+ -> skip ;
COMMENT: ('//' | '#').*?[\r\n] -> skip ;