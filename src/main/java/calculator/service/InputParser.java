package calculator.service;

import calculator.model.ParsedInput;

public class InputParser {
    private static final String CUSTOM_PREFIX = "//";
    private static final String CUSTOM_SUFFIX = "\\n";

    private String delimiterPart;
    private String contentPart;

    // 사용자 입력을 커스텀구분자 파트와 계산요청 본문 파트로 구분.
    public ParsedInput parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("입력값이 null 입니다.");
        }

        if (input.isEmpty()) {
            return new ParsedInput("", "");
        }

        if (input.startsWith(CUSTOM_PREFIX)) {
            return parseWithCustomDelimiter(input);
        }

        return new ParsedInput("", input);
    }

    private ParsedInput parseWithCustomDelimiter(String input) {
        int suffixIndex = input.indexOf(CUSTOM_SUFFIX);

        // "//"는 있지만 "\n"이 없는 경우
        if (suffixIndex == -1) {
            throw new IllegalArgumentException("커스텀 구분자 패턴이 올바르지 않습니다.");
        }

        String delimiterPart = input.substring(0, suffixIndex + CUSTOM_SUFFIX.length());
        String contentPart = input.substring(suffixIndex + CUSTOM_SUFFIX.length());

        return new ParsedInput(delimiterPart, contentPart);
    }
}
