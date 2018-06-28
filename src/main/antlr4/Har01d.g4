grammar Har01d;

compilationUnit : ( variable | assign | print )* EOF ;
variable : VARIABLE ID EQUALS value ;
assign : ID EQUALS value ;
print : PRINT ID ;
value : NUMBER
      | STRING ;

VARIABLE : 'var' ;
PRINT : 'print' ;
EQUALS : '=' ;
NUMBER : [0-9]+ ;
STRING : '"'.*?'"' | '\''.*?'\'' ;
ID: [_a-zA-Z][_a-zA-Z0-9]* ;
WS: [ \t\n\r]+ -> skip ;