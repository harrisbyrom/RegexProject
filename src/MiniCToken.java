public class MiniCToken {
    public final MiniCTokenType type;
    public final String lexeme;
    public final SourceLocation location;

    public MiniCToken(MiniCTokenType type, String lexeme, SourceLocation location) {
        this.type = type;
        this.lexeme = lexeme;
        this.location = location;
    }
}
