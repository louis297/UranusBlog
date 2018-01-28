package UranusBlog.DAO;

import UranusBlog.DB.Database;
import UranusBlog.Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements AutoCloseable{

    private final Connection conn;

    /**
     * Creates a new DAO with a {@link Connection} from the given {@link Database}.
     *
     * @param db the {@link Database} from which to establish a {@link Connection}
     * @throws SQLException if something went wrong.
     */
    public AccountDAO(Database db) throws SQLException {
        this.conn = db.getConnection();
    }


    public Account getUserById(int userId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("CALL GetUserInfo(?)")) {
            stmt.setInt(1, userId);
            try (ResultSet r = stmt.executeQuery()) {
                if(r.next())
                    return  accountFromResultSet(r);
                else
                    return null;
            }
        }
    }

    public List<Account> getUserList() throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("CALL GetUserList()")) {
            try (ResultSet r = stmt.executeQuery()) {
                List<Account> accounts = new ArrayList<>();
                while(r.next())
                    accounts.add(accountFromResultSet(r));
                return accounts;
            }
        }
    }

    public Account login(String username, String password) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("CALL LoginValidate(?,?)")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet r = stmt.executeQuery()) {
                if(r.next()){
                    return accountFromResultSet(r);
                }
            }
        }
        return null;
    }

    /**
     * Please be extremely careful to delete a user
     * Validate if the user has right to operate
     *
     * @param userId
     * @throws SQLException
     */
    public void deleteUser(int userId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("CALL GetUserInfo(?)")) {
            stmt.setInt(1, userId);
            stmt.executeQuery();
        }
    }

    public void addUser(String username, String password, String firstname, String lastname, String middlename, String email, LocalDate birthday, String nation, String avatar_path, int role) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("CALL InsertAccount(?,?,?,?,?,?,?,?,?,?)")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, firstname);
            stmt.setString(4, lastname);
            stmt.setString(5, middlename);
            stmt.setString(6, email);
            stmt.setObject(7, birthday);
            stmt.setString(8, nation);
            stmt.setString(9, avatar_path);
            stmt.setInt(10, role);
            stmt.executeQuery();
        }
    }

    public void modifyUser(int userID, String username, String firstname, String lastname, String middlename, String email, LocalDate birthday, String nation, String avatar_path, int role) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("CALL UpdateAccount(?,?,?,?,?,?,?,?,?)")) {
            stmt.setInt(1, userID);
            stmt.setString(2, username);
            stmt.setString(3, firstname);
            stmt.setString(4, lastname);
            stmt.setString(5, middlename);
            stmt.setString(6, email);
            stmt.setObject(7, birthday);
            stmt.setString(8, nation);
            stmt.setString(9, avatar_path);
            stmt.executeQuery();
        }
    }

    public void changePassword(int userID, String password) throws SQLException{
        try (PreparedStatement stmt = conn.prepareStatement("CALL UpdatePassword(?,?)")) {
            stmt.setInt(1, userID);
            stmt.setString(2, password);
            stmt.executeQuery();
        }
    }


    private Account accountFromResultSet(ResultSet r)  throws SQLException {
        Account account = new Account(r.getInt(1), r.getString(2), r.getString(3),
                r.getString(4), r.getString(5), r.getString(6),
                r.getString(7), r.getDate(8), r.getString(9),
                r.getString(13), r.getString(10), r.getString(12),
                r.getInt(11), r.getString(14));
        return account;
    }

    @Override
    public void close() throws Exception {
        conn.close();
    }
}
