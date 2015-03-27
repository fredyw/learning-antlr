package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import profiler.JavaBaseListener;
import profiler.JavaLexer;
import profiler.JavaParser;
import profiler.JavaParser.MethodBodyContext;

public class Profiler2 extends JavaBaseListener {
    private TokenStreamRewriter rewriter;
    
    public Profiler2(CommonTokenStream tokens) {
        this.rewriter = new TokenStreamRewriter(tokens);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void enterMethodBody(MethodBodyContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("        try {");
        sb.append("\n");
        sb.append("            String __name = Thread.currentThread().getStackTrace()[1].getClassName() + \".\" + Thread.currentThread().getStackTrace()[1].getMethodName();");
        sb.append("\n");
        sb.append("            System.out.println(\"BEGIN: \" + __name);");
        sb.append("\n");
        sb.append("            long __startTime = System.currentTimeMillis();");
        
        rewriter.insertAfter(ctx.start, sb.toString());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void exitMethodBody(MethodBodyContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("    } finally {");
        sb.append("\n");
        sb.append("            System.out.println(System.currentTimeMillis() - __startTime);");
        sb.append("\n");
        sb.append("            System.out.println(\"END: \" + __name);");
        sb.append("\n");
        sb.append("        }");
        sb.append("\n");
        
        rewriter.insertBefore(ctx.stop, sb.toString());
        rewriter.replace(ctx.stop, "    }");
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        try (FileInputStream fis = new FileInputStream(new File("HelloWorld.java"))) {
            ANTLRInputStream input = new ANTLRInputStream(fis);
            JavaLexer lexer = new JavaLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaParser parser = new JavaParser(tokens);
            ParseTree tree = parser.compilationUnit();
            
            ParseTreeWalker walker = new ParseTreeWalker();
            Profiler2 profiler2 = new Profiler2(tokens);
            walker.walk(profiler2, tree);
            
            System.out.println(profiler2.rewriter.getText());
        }
    }
}
