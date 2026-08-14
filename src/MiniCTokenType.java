public enum MiniCTokenType {
    KW_INT(1),
    KW_VOID(1),
    KW_IF(1),
    KW_WHILE(1),
    KW_ELSE(1),
    KW_RETURN(1),

    OP_EQ(2), // ==
    OP_NEQ(2), // !=
    OP_LEQ(2), // <=
    OP_GEQ(2), // >=
    OP_ASSIGN(2), // =
    OP_PLUS(2), // +
    OP_MINUS(2), // -
    OP_MUL(2), // *
    OP_DIV(2), // /
    OP_LT(2), // <
    OP_GT(2), // >
    SEMICOLON(2), // ;
    COMMA(2), // ,
    LPAREN(2), // (
    RPAREN(2), // )
    LBRACE(2), // {
    RBRACE(2), // }

    INT_LITERAL(3), // eg 123
    STR_LITERAL(3), // eg "hello"

    IDENTIFIER(4), // eg myVariable

    WHITESPACE(5), // spaces, tabs, newlines
    COMMENT(5),

    EOF(99); // special end of file marker

    private final int priority;

    private MiniCTokenType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
