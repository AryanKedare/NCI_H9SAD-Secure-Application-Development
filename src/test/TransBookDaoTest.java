package mainlibrary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.*;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;

@ExtendWith(MockitoExtension.class)
class TransBookDaoTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, Integer.MAX_VALUE})
    public void testBookValidation_EdgeCases(int bookId) throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        Optional<Boolean> result = TransBookDao.bookValidate(bookId);
        assertThat(result).isPresent().contains(false);
    }

    @Test
    public void testReturnBook_SQLInjectionAttempt() throws SQLException {
        String maliciousInput = "1; DROP TABLE IssuedBook;--";
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("SQL syntax error"));
        assertThatThrownBy(() -> TransBookDao.returnBook(Integer.parseInt(maliciousInput), 1))
            .isInstanceOf(SQLException.class)
            .hasMessageContaining("SQL syntax error");
    }

    @Test
    public void testIssueBook_Success() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        Optional<Integer> result = TransBookDao.issueBook(1, 1, new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()));
        assertThat(result).isPresent().contains(1);
    }

    @Test
    public void testCheckIssuedBook_Valid() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        Optional<Boolean> result = TransBookDao.checkIssuedBook(1);
        assertThat(result).isPresent().contains(true);
    }

    @Test
    public void testCheck_ValidUserId() {
        Optional<Integer> result = TransBookDao.check(1);
        assertThat(result).isPresent().contains(1);
    }

    @Test
    public void testCheck_InvalidUserId() {
        assertThatThrownBy(() -> TransBookDao.check(-1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid UserID");
    }

    @Test
    public void testIssueBook_ValidInputs() {
        Optional<Integer> result = TransBookDao.issueBook(1, 1, java.sql.Date.valueOf("2023-10-01"), java.sql.Date.valueOf("2023-10-15"));
        assertThat(result).isPresent().contains(1);
    }

    @Test
    public void testIssueBook_InvalidInputs() {
        assertThatThrownBy(() -> TransBookDao.issueBook(-1, 1, java.sql.Date.valueOf("2023-10-01"), java.sql.Date.valueOf("2023-10-15")))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid BookID or UserID");
    }

    @Test
    public void testCheckIssuedBook_ValidBookId() {
        Optional<Boolean> result = TransBookDao.checkIssuedBook(1);
        assertThat(result).isPresent().contains(true);
    }

    @Test
    public void testCheckIssuedBook_InvalidBookId() {
        assertThatThrownBy(() -> TransBookDao.checkIssuedBook(-1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid BookID");
    }
}
