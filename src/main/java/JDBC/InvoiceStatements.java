package JDBC;

public class InvoiceStatements {

    protected static final String TABLE_NAME = "test";
    protected static final String ID = "ID";
    protected static final String BOOK_AUTHOR = "AUTHOR";
    protected static final String BOOK_TITLE = "TITLE";

    protected static final int ADD_BOOK_PARAM_TITLE = 1;
    protected static final int ADD_BOOK_PARAM_AUTHOR = 2;

    protected static final String CREATE_TABLE = String.format("CREATE TABLE %s ("
                    + "%s INTEGER AUTO_INCREMENT, "
                    + "%s VARCHAR(30), "
                    + "%s VARCHAR(30),"
                    + " primary key(%s) );",
            TABLE_NAME, ID, BOOK_TITLE, BOOK_AUTHOR, ID);

    protected static final String INSERT_BOOK = "INSERT INTO " + TABLE_NAME
            + " (" + BOOK_TITLE + ", " + BOOK_AUTHOR + " ) VALUES(?,?)";
}
