package calculator.validator;

import calculator.model.ParsedInput;

public class InputValidator {
    private static final String CUSTOM_PREFIX = "//";
    private static final String CUSTOM_SUFFIX = "\\n";
    private static final String DEFAULT_DELIMITER_COMMA = ",";
    private static final String DEFAULT_DELIMITER_COLON = ":";

    public void validate(ParsedInput parsedInput){
        String customDelimiter = findCustomDelimiter(parsedInput);

        if (customDelimiter != null){
            validateDelimiterPart(parsedInput, customDelimiter);
        }

        validateContentPart(parsedInput, customDelimiter);
    }

    private void validateDelimiterPart(ParsedInput parsedInput, String customDelimiter) {

        // 커스텀 구분자가 비어있는지 검증
        if (customDelimiter.isEmpty()) {
            throw new IllegalArgumentException("커스텀 구분자는 비어있을 수 없습니다.");
        }

        // 커스텀 구분자가 한 글자인지 검증
        if (customDelimiter.length() != 1) {
            throw new IllegalArgumentException("커스텀 구분자는 한 글자여야 합니다.");
        }

        // 커스텀 구분자가 숫자인지 검증
        if (Character.isDigit(customDelimiter.charAt(0))) {
            throw new IllegalArgumentException("커스텀 구분자는 숫자일 수 없습니다.");
        }

        // 기본 구분자와 겹치는지 검증
        if (customDelimiter.equals(",") || customDelimiter.equals(":")) {
            throw new IllegalArgumentException("커스텀 구분자는 기본 구분자와 겹칠 수 없습니다.");
        }
    }

    private void validateContentPart(ParsedInput parsedInput, String customDelimiter) {
        String contentPart = parsedInput.getContentPart();

        if (contentPart.isEmpty()) {
            return;
        }
        validateMinusSymbolIsCustomDelimiter(contentPart, customDelimiter);
        validateNoConsecutiveDelimiters(contentPart, customDelimiter);
        validateNotStartOrEndWithDelimiter(contentPart, customDelimiter);
        validateOnlyNumbersAndDelimiters(contentPart, customDelimiter);
    }

    // 음수 검증: "-"가 있지만 커스텀 구분자가 아닌 경우
    private void validateMinusSymbolIsCustomDelimiter(String content, String customDelimiter){
        if (content.contains("-") && (customDelimiter == null || !customDelimiter.equals("-"))) {
            throw new IllegalArgumentException("입력값은 양수만 가능합니다.");
        }
    }

    private void validateNoConsecutiveDelimiters(String content, String customDelimiter) {
        // 기본 구분자 연속 검증
        if (content.contains(",,") || content.contains("::") ||
                content.contains(",:") || content.contains(":,")) {
            throw new IllegalArgumentException("구분자가 연속으로 입력될 수 없습니다.");
        }

        // 커스텀 구분자가 없으면 종료
        if (customDelimiter == null) {
            return;
        }

        // 커스텀 구분자 연속 검증
        String doubleDelimiter = customDelimiter + customDelimiter;
        if (content.contains(doubleDelimiter)) {
            throw new IllegalArgumentException("구분자가 연속으로 입력될 수 없습니다.");
        }

        // 기본 구분자와 커스텀 구분자 혼합 검증
        if (content.contains(DEFAULT_DELIMITER_COMMA + customDelimiter) ||
                content.contains(customDelimiter + DEFAULT_DELIMITER_COMMA) ||
                content.contains(DEFAULT_DELIMITER_COLON + customDelimiter) ||
                content.contains(customDelimiter + DEFAULT_DELIMITER_COLON)) {
            throw new IllegalArgumentException("구분자가 연속으로 입력될 수 없습니다.");
        }
    }

    private void validateNotStartOrEndWithDelimiter(String content, String customDelimiter) {
        char first = content.charAt(0);
        char last = content.charAt(content.length() - 1);

        if (isDelimiter(first, customDelimiter) || isDelimiter(last, customDelimiter)) {
            throw new IllegalArgumentException("문자열은 구분자로 시작하거나 끝날 수 없습니다.");
        }
    }

    private void validateOnlyNumbersAndDelimiters(String content, String customDelimiter) {
        for (char c : content.toCharArray()) {
            if (!Character.isDigit(c) && !isDelimiter(c, customDelimiter)) {
                throw new IllegalArgumentException("숫자와 구분자만 입력 가능합니다.");
            }
        }
    }

    private boolean isDelimiter(char c, String customDelimiter) {
        boolean isDefault = (c == ',' || c == ':');
        boolean isCustom = (customDelimiter != null && customDelimiter.charAt(0) == c);
        return (isDefault || isCustom);
    }

    private String findCustomDelimiter(ParsedInput parsedInput) {
        if (!parsedInput.hasCustomDelimiter()) {
            return null;
        }
        int prefixLength = CUSTOM_PREFIX.length();
        int suffixIndex = parsedInput.getDelimiterPart().indexOf(CUSTOM_SUFFIX);

        return parsedInput.getDelimiterPart().substring(prefixLength, suffixIndex);
    }
}
