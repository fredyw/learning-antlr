// Generated from INI.g4 by ANTLR 4.4
package ini;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link INIParser}.
 */
public interface INIListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link INIParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(@NotNull INIParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link INIParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(@NotNull INIParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link INIParser#ini}.
	 * @param ctx the parse tree
	 */
	void enterIni(@NotNull INIParser.IniContext ctx);
	/**
	 * Exit a parse tree produced by {@link INIParser#ini}.
	 * @param ctx the parse tree
	 */
	void exitIni(@NotNull INIParser.IniContext ctx);
	/**
	 * Enter a parse tree produced by {@link INIParser#section}.
	 * @param ctx the parse tree
	 */
	void enterSection(@NotNull INIParser.SectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link INIParser#section}.
	 * @param ctx the parse tree
	 */
	void exitSection(@NotNull INIParser.SectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link INIParser#option}.
	 * @param ctx the parse tree
	 */
	void enterOption(@NotNull INIParser.OptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link INIParser#option}.
	 * @param ctx the parse tree
	 */
	void exitOption(@NotNull INIParser.OptionContext ctx);
}