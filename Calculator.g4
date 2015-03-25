grammar Calculator;

prog : expr+ ;
     
expr : expr op=(MUL | DIV | ADD | SUB) expr # MulDivAddSub
     | '(' expr ')' # Parens
     | INT # Int
     | expr NEWLINE # ExpressionNewLine
     | NEWLINE # Blank
     ;

MUL     : '*' ;
DIV     : '/' ;
ADD     : '+' ;
SUB     : '-' ;
INT     : [0-9]+ ;
NEWLINE : '\r'? '\n' ;
WS      : [ \t]+ -> skip ; // toss out whitespace
