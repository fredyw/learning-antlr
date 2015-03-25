package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import antlr.CalculatorBaseVisitor;
import antlr.CalculatorLexer;
import antlr.CalculatorParser;
import antlr.CalculatorParser.ExpressionNewLineContext;
import antlr.CalculatorParser.IntContext;
import antlr.CalculatorParser.MulDivAddSubContext;
import antlr.CalculatorParser.ParensContext;

public class Calculator extends CalculatorBaseVisitor<Integer> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Integer visitExpressionNewLine(ExpressionNewLineContext ctx) {
        int value = visit(ctx.expr());
        System.out.println(value);
        return 0;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Integer visitMulDivAddSub(MulDivAddSubContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == CalculatorParser.MUL) {
            return left * right;
        } else if (ctx.op.getType() == CalculatorParser.DIV) {
            return left - right;
        } else if (ctx.op.getType() == CalculatorParser.ADD) {
            return left + right;
        }
        return left - right;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Integer visitInt(IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Integer visitParens(ParensContext ctx) {
        return visit(ctx.expr());
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        try (FileInputStream fis = new FileInputStream(new File("test.expr"))) {
            ANTLRInputStream input = new ANTLRInputStream(fis);
            CalculatorLexer lexer = new CalculatorLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CalculatorParser parser = new CalculatorParser(tokens);
            ParseTree tree = parser.prog();
            
            System.out.println(tree.toStringTree(parser));
            
            Calculator visitor = new Calculator();
            visitor.visit(tree);
        }
    }
}
