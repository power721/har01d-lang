grammar Har01d;

compilationUnit : ( statement | function | classDeclaration )* EOF ;
statement : variableDeclaration
            | valueDeclaration
            | assignment
            | print
            | block
            | expression
            | returnStatement ;
classDeclaration : 'class' className '{' classBody '}' ;
className : ID ;
classBody : field* function* ;
field : type name ;
variableDeclaration : VARIABLE name EQUALS expression ;
valueDeclaration : VALUE name EQUALS expression ;
assignment : name EQUALS expression ;
print : PRINT expression ;
expression : variableReference #varReference
             | literal #literalExpr
             | functionName '(' argumentList ')' #FunctionCall
             |  '('expression op=('*' | '/' | '%') expression')' #Multiply
             | expression op=('*' | '/' | '%') expression  #Multiply
             | '(' expression op=('+' | '-') expression ')' #Add
             | expression op=('+' | '-') expression #Add
             | expression cmp=('>' | '<' | '>=' | '<=') expression #ConditionalExpression
             | expression cmp=('==' | '!=') expression #ConditionalExpression
             ;

literal : NUMBER | BOOL | STRING ;
returnStatement : 'return' expression #ReturnWithValue
                | 'return' #ReturnVoid ;

variableReference: ID ;
function : functionDeclaration block ;
functionDeclaration : 'fun' functionName '(' parametersList? ')' (':' type)? ;
parametersList:  parameter (',' parameter)*
          |  parameter (',' parameterWithDefaultValue)*
          |  parameterWithDefaultValue (',' parameterWithDefaultValue)* ;
functionName : ID ;
parameter : ID ':' type ;
parameterWithDefaultValue : ID ':' type '=' defaultValue=expression ;

argumentList : argument? (',' a=argument)* #UnnamedArgumentsList
             | namedArgument? (',' namedArgument)* #NamedArgumentsList ;
argument : expression ;
namedArgument : name '=' expression ;

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