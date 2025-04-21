package mainlibrary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import mainlibrary.UsersDao;

public class UsersDaoTest {

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

    @Test
    public void testAddUser() throws SQLException {
        boolean result = UsersDao.addUser("testuser", "password", "test@example.com", "Test User");
        assertThat(result).isTrue();
    }
}
