grammar Har01d;

compilationUnit : ( variable | assign | print )* EOF ;
variable : VARIABLE ID EQUALS value ;
assign : ID EQUALS value ;
print : PRINT expression ;
expression : ID | value ;
value : NUMBER | BOOL | STRING ;

VARIABLE : 'var' ;
PRINT : 'print' ;
EQUALS : '=' ;
NUMBER : [0-9]+ ;
BOOL : 'true' | 'false' ;
STRING : '"'.*?'"' | '\''.*?'\'' ;
ID: [_a-zA-Z][_a-zA-Z0-9]* ;
WS: [ \t\n\r]+ -> skip ;