public class SourceLocation {
    public final int line;
    public final int column;

    public SourceLocation(int line, int column) {
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return line + ":" + column;
    }
}
