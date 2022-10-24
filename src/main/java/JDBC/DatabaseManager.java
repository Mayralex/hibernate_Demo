package JDBC;

import Model.Book;

import java.sql.*;

import static JDBC.Statements.*;

public class DatabaseManager {

    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://ec2-18-198-210-127.eu-central-1.compute.amazonaws.com:3306/test";
    private static final String DB_USER = "remoteu";
    private static final String DB_PWD = "password";

    private Connection dbConnection;

    public void buildConnection() {
        try {
            Class.forName(DRIVER);
            System.out.println(DRIVER + " loaded");
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException:");
            System.err.print(e.getMessage());
            System.out.println("\n \t >>>> Please check CLASSPATH variable <<<<\n");
            return;
        }

        if (dbConnection != null) {
            throw new IllegalStateException("\t >>>> Connection to database could not be build <<<<");
        }

        try {
            dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
            System.out.println("Connected to database");

            if (tableExists(dbConnection)) {
                System.out.println("\t >>>> Table exists <<<<");
            } else createTables();


        } catch (SQLException e) {
            System.err.println("\t >>>> Connection to database failed <<<<");
            System.err.print(e.getMessage());
            System.err.println("\n \t >>>> Please check connection drive <<<<<");
        }
    }

    private boolean tableExists(Connection connection) {
        boolean tableExists = false;
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            String[] names = {"TABLE"};
            ResultSet tables = metaData.getTables(null, null, null, names);

            while (tables.next()) {
                String table = tables.getString("TABLE_NAME");
                if (table.toLowerCase().equals(Statements.TABLE_NAME)) tableExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableExists;
    }

    private void createTables() {
        try (Statement statement = dbConnection.createStatement()) {
            statement.execute(CREATE_TABLE);
            System.out.println("\t >>>> Created tables <<<<");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertBook (Book book) {
        try (PreparedStatement insertStatement = dbConnection.prepareStatement(INSERT_BOOK,
                Statement.RETURN_GENERATED_KEYS)) {
            String title = book.getTitle();
            String author = book.getAuthor();


            insertStatement.setString(ADD_BOOK_PARAM_TITLE, title);
            insertStatement.setString(ADD_BOOK_PARAM_AUTHOR, author);

            final int affectedRows = insertStatement.executeUpdate();
            if (affectedRows != 1)
                throw new RuntimeException("\t >>>> Failed to add new book into database <<<<");
            System.out.println("\t >>>> Added new book to database <<<<");
            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            dbConnection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            System.err.println("\t >>>> Closing connection failed <<<<");
            System.err.print(e.getMessage());
        }
    }
}
