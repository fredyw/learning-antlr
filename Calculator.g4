grammar Calculator;

prog : stat+ ;

stat : expr # Expression
     | expr NEWLINE # ExpressionNewLine
     | NEWLINE # NewLine
     ;
     
expr : expr op=(MUL | DIV | ADD | SUB) expr # MulDivAddSub
     | '(' expr ')' # Parenthesis
     | INT # Int
     ;

MUL     : '*' ;
DIV     : '/' ;
ADD     : '+' ;
SUB     : '-' ;
INT     : [0-9]+ ;
NEWLINE : '\r'? '\n' ;
WS      : [ \t]+ -> skip ; // toss out whitespace
