package bridge.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import bridge.domain.errorenum.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {
    private static final String ERROR_HEAD = "[ERROR]";

    @DisplayName("다리 길이 입력 시 숫자 외의 값이 포함된 경우 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"ㄱ", "1-", "-1", ",", " ", ""})
    void createInputNotNumber(String input) {
        assertThatThrownBy(() -> InputValidator.validateNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.NOT_NUMBER.printMessage())
                .hasMessageStartingWith(ERROR_HEAD);
    }
}