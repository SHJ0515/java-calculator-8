package calculator.service;

import calculator.model.ParsedInput;
import calculator.validator.InputValidator;

import static java.util.regex.Pattern.quote;

public class CalculatorService {
    private static final String DEFAULT_DELIMITER_COMMA = ",";
    private static final String DEFAULT_DELIMITER_COLON = ":";
    private static final String CUSTOM_PREFIX = "//";
    private static final String CUSTOM_SUFFIX = "\\n";

    private final InputValidator inputValidator;
    private final InputParser inputParser;

    public CalculatorService() {
        this.inputValidator = new InputValidator();
        this.inputParser = new InputParser();
    }

    public int calculate(String input) {
        // 사용자입력이 "" 인 경우 0 반환.
        if (input.isEmpty()) {
            return 0;
        }

        ParsedInput parsedInput = inputParser.parse(input);
        String customDelimiter = findCustomDelimiter(parsedInput);
        inputValidator.validate(parsedInput);
        return calculateSum(parsedInput.getContentPart(), customDelimiter);

    }

    private String findCustomDelimiter(ParsedInput parsedInput) {
        if (!parsedInput.hasCustomDelimiter()) {
            return null;
        }
        int prefixLength = CUSTOM_PREFIX.length();
        int suffixIndex = parsedInput.getDelimiterPart().indexOf(CUSTOM_SUFFIX);

        return parsedInput.getDelimiterPart().substring(prefixLength, suffixIndex);
    }

    private int calculateSum(String content, String customDelimiter) {
        if (content.isEmpty()) {
            return 0;
        }

        //정규식에 따라 입력 본문 분할
        String[] splitNums = content.split(createRegex(customDelimiter));
        int sum = 0;

        for (String splitNum : splitNums) {
            sum += Integer.parseInt(splitNum);
        }

        return sum;
    }

    //정규식 생성
    private String createRegex(String customDelimiter) {
        if (customDelimiter != null) {
            return String.format("%s|%s|%s",
                    quote(DEFAULT_DELIMITER_COLON),
                    quote(DEFAULT_DELIMITER_COMMA),
                    quote(customDelimiter)
            );
        }

        return String.format("%s|%s",
                quote(DEFAULT_DELIMITER_COMMA),
                quote(DEFAULT_DELIMITER_COLON)
        );
    }

}
