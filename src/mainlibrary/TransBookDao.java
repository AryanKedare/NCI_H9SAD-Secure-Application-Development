package mainlibrary;

import java.sql.*;
import javax.swing.JTextField;

public class TransBookDao {

    public static boolean checkBook(String bookcallno) {
        boolean status = false;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Books WHERE BookID = ?")) {
            ps.setString(1, bookcallno);
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static boolean BookValidate(String BookID) {
        boolean status = false;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Books WHERE BookID = ?")) {
            ps.setString(1, BookID);
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static boolean UserValidate(String UserID) {
        boolean status = false;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Users WHERE UserID = ?")) {
            ps.setString(1, UserID);
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static int updatebook(String bookcallno) {
        int status = 0;
        int quantity = 0, issued = 0;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT quantity, issued FROM books WHERE callno = ?")) {
            ps.setString(1, bookcallno);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    quantity = rs.getInt("quantity");
                    issued = rs.getInt("issued");
                }
            }

            if (quantity > 0) {
                try (PreparedStatement ps2 = con.prepareStatement("UPDATE books SET quantity = ?, issued = ? WHERE callno = ?")) {
                    ps2.setInt(1, quantity - 1);
                    ps2.setInt(2, issued + 1);
                    ps2.setString(3, bookcallno);
                    status = ps2.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static int IssueBook(int BookID, int UserID, String IDate, String RDate) {
        int status = 0;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO IssuedBook VALUES(?, ?, ?, ?)")) {
            ps.setInt(1, BookID);
            ps.setInt(2, UserID);
            ps.setString(3, IDate);
            ps.setString(4, RDate);
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static int ReturnBook(int BookID, int UserID) {
        int status = 0;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM IssuedBook WHERE BookID = ? AND UserID = ?")) {
            ps.setInt(1, BookID);
            ps.setInt(2, UserID);
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static boolean CheckIssuedBook(int BookID) {
        boolean status = false;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM IssuedBook WHERE BookID = ?")) {
            ps.setInt(1, BookID);
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static int Check(int UserID) {
        int num = 0;
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT BookNo FROM Book_Count WHERE UserID = ?")) {
            ps.setInt(1, UserID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    num = rs.getInt("BookNo");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return (num == 5) ? 0 : 1;
    }
}
