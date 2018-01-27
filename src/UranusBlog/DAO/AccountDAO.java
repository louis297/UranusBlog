package UranusBlog.DAO;

import UranusBlog.DB.Database;

import java.sql.Connection;
import java.sql.SQLException;

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


    @Override
    public void close() throws Exception {
        conn.close();
    }
}
