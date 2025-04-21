package mainlibrary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import javax.xml.bind.ValidationException;
import org.junit.jupiter.api.DisplayName;

@ExtendWith(MockitoExtension.class)
@DisplayName("IssueBookFormTest")
public class IssueBookFormTest {

    @Test
    @DisplayName("Context Loads Test")
    void contextLoads() {
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "1; DROP TABLE Users;--"})
    @DisplayName("Invalid Input Validation Test")
    public void testValidateInput_InvalidInputs(String input) {
        assertThatThrownBy(() -> IssueBookForm.validateInput(input))
            .isInstanceOf(ValidationException.class)
            .hasMessageContaining("Invalid characters");
    }

    @Test
    @DisplayName("Valid Input Validation Test")
    public void testValidateInput_ValidInput() {
        assertThatCode(() -> IssueBookForm.validateInput("ValidInput123"))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Invalid Date Handling Test")
    public void testDateHandling_InvalidDate() {
        assertThatThrownBy(() -> IssueBookForm.validateDate("2023-02-30"))
            .isInstanceOf(ValidationException.class)
            .hasMessageContaining("Invalid date");
    }

    @Test
    @DisplayName("Valid Date Handling Test")
    public void testDateHandling_ValidDate() {
        assertThatCode(() -> IssueBookForm.validateDate("2023-10-15"))
            .doesNotThrowAnyException();
    }
}
