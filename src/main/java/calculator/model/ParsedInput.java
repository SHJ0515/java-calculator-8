package calculator.model;

public class ParsedInput {
    private final String delimiterPart;
    private final String contentPart;

    public ParsedInput(String delimiterPart, String contentPart) {
        this.delimiterPart = delimiterPart;
        this.contentPart = contentPart;
    }

    public String getDelimiterPart() {
        return delimiterPart;
    }

    public String getContentPart() {
        return contentPart;
    }

    public boolean hasCustomDelimiter() {
        return !delimiterPart.isEmpty();
    }
}