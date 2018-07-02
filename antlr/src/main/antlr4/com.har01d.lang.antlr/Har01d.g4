grammar Har01d;

compilationUnit : ( statement | function | classDeclaration )* EOF ;
statement : variableDeclaration
            | valueDeclaration
            | assignment
            | print
            | block
            | ifStatement
            | forStatement
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
             |  '('expression '^' expression')' #Power
             | expression '^' expression  #Power
             |  '('expression op=('*' | '/' | '%') expression')' #Multiply
             | expression op=('*' | '/' | '%') expression  #Multiply
             | '(' expression op=('+' | '-') expression ')' #Add
             | expression op=('+' | '-') expression #Add
             | expression cmp=('>' | '<' | '>=' | '<=') expression #RelationalExpression
             | expression cmp=('==' | '!=') expression #RelationalExpression
             ;

literal : NUMBER | BOOL | STRING ;
returnStatement : 'return' expression #ReturnWithValue
                | 'return' #ReturnVoid ;
ifStatement :  'if'  ('(')? expression (')')? trueStatement=statement ('else' falseStatement=statement)?;
forStatement : 'for' ('(')? forConditions (')')? statement ;
forConditions : iterator=variableReference  'from' startExpr=expression range='to' endExpr=expression ;

variableReference: ID ;
function : functionDeclaration block ;
functionDeclaration : 'fun' functionName '(' parametersList? ')' (':' type)? ;
parametersList:  parameter (',' parameter)* (',' parameterWithDefaultValue)*
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
NUMBER : ('+'|'-')?[0-9]+(.[0-9]+)? ;
BOOL : 'true' | 'false' ;
STRING : '"'.*?'"' | '\''.*?'\'' ;
ID: NameStartChar NameChar* ;
fragment
NameChar
   : NameStartChar
   | '0'..'9'
   | '_'
   | '\u00B7'
   | '\u0300'..'\u036F'
   | '\u203F'..'\u2040'
   ;
fragment
NameStartChar
   : 'A'..'Z' | 'a'..'z'
   | '\u00C0'..'\u00D6'
   | '\u00D8'..'\u00F6'
   | '\u00F8'..'\u02FF'
   | '\u0370'..'\u037D'
   | '\u037F'..'\u1FFF'
   | '\u200C'..'\u200D'
   | '\u2070'..'\u218F'
   | '\u2C00'..'\u2FEF'
   | '\u3001'..'\uD7FF'
   | '\uF900'..'\uFDCF'
   | '\uFDF0'..'\uFFFD'
   ;

WS: [ \t\r\n\f]+ -> skip ;
ShebangLine
    : '#!' ~[\r\n]*
      -> channel(HIDDEN)
    ;

DelimitedComment
    : '/*' ( DelimitedComment | . )*? '*/'
      -> channel(HIDDEN)
    ;

LineComment
    : ('//' | '#') ~[\r\n]*
      -> channel(HIDDEN)
    ;
