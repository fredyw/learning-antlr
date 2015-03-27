package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import profiler.JavaBaseListener;
import profiler.JavaLexer;
import profiler.JavaParser;
import profiler.JavaParser.BlockStatementContext;
import profiler.JavaParser.ClassBodyDeclarationContext;
import profiler.JavaParser.ClassOrInterfaceModifierContext;
import profiler.JavaParser.GenericMethodDeclarationContext;
import profiler.JavaParser.ImportDeclarationContext;
import profiler.JavaParser.MethodDeclarationContext;
import profiler.JavaParser.ModifierContext;
import profiler.JavaParser.PackageDeclarationContext;
import profiler.JavaParser.TypeContext;
import profiler.JavaParser.TypeDeclarationContext;

public class Profiler extends JavaBaseListener {
    private JavaParser parser;
    
    public Profiler(JavaParser parser) {
        this.parser = parser;
    }
    
    @Override
    public void enterImportDeclaration(ImportDeclarationContext ctx) {
        System.out.println(parser.getTokenStream().getText(ctx));
    }
    
    @Override
    public void enterPackageDeclaration(PackageDeclarationContext ctx) {
        System.out.println(parser.getTokenStream().getText(ctx));
    }

    public void exitPackageDeclaration(PackageDeclarationContext ctx) {
        System.out.println();
    };
    
    @Override
    public void enterGenericMethodDeclaration(GenericMethodDeclarationContext ctx) {
    }
    
    @Override
    public void enterClassBodyDeclaration(ClassBodyDeclarationContext ctx) {
        System.out.print("    ");
        for (ModifierContext mc : ctx.modifier()) {
            System.out.print(mc.getText() + " ");
        }
    }
    
    @Override
    public void enterMethodDeclaration(MethodDeclarationContext ctx) {
        TokenStream tokens = parser.getTokenStream();
        String type = "void";
        if (ctx.type() != null) {
            type = tokens.getText(ctx.type());
        }
        String args = tokens.getText(ctx.formalParameters());
        System.out.println(type + " " + ctx.Identifier() + args + " {");
        System.out.println("        String __name = Thread.currentThread().getStackTrace()[1].getClassName() + \".\" + Thread.currentThread().getStackTrace()[1].getMethodName();");
        System.out.println("        System.out.println(\"BEGIN: \" + __name);");
        System.out.println("        long __startTime = System.currentTimeMillis();");
    }

    @Override
    public void exitMethodDeclaration(MethodDeclarationContext ctx) {
        System.out.println("        System.out.println(System.currentTimeMillis() - __startTime);");
        System.out.println("        System.out.println(\"END: \" + __name);");
        System.out.println("    }");
        System.out.println();
    }

    @Override
    public void enterBlockStatement(BlockStatementContext ctx) {
        System.out.println("        " + parser.getTokenStream().getText(ctx));
    }
    
    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof TerminalNode) {
                System.out.print(ctx.getChild(i).getText() + " ");
            }
        }
        if (ctx.typeList() != null && !ctx.typeList().isEmpty()) {
            int i = 0;
            for (TypeContext tc : ctx.typeList().type()) {
                if (i == 0) {
                    System.out.print(tc.getText());
                } else {
                    System.out.print(", " + tc.getText() + " ");
                }
                i++;
            }
        }
        System.out.println(" {");
    }

    @Override
    public void enterTypeDeclaration(TypeDeclarationContext ctx) {
        System.out.println();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof ClassOrInterfaceModifierContext) {
                System.out.print(ctx.getChild(i).getText() + " ");
            }
        }
    }
    
    @Override
    public void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        System.out.println("}");
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        try (FileInputStream fis = new FileInputStream(new File("HelloWorld.java"))) {
            ANTLRInputStream input = new ANTLRInputStream(fis);
            JavaLexer lexer = new JavaLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaParser parser = new JavaParser(tokens);
            ParseTree tree = parser.compilationUnit();
            
            ParseTreeWalker walker = new ParseTreeWalker(); 
            Profiler profiler = new Profiler(parser);
            walker.walk(profiler, tree);
        }
    }
}
