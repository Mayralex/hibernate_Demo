import JDBC.DatabaseManager;
import Model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Book> books = new ArrayList<>();

        Book book = new Book();
        book.setTitle("This is a test title");
        book.setAuthor("Test Author");

        Book book2 = new Book();
        book2.setAuthor("Sun Tzu");
        book2.setTitle("The art of war");
        books.add(book2);

        Book book3 = new Book();
        book3.setAuthor("Marcel Proust");
        book3.setTitle("In Search of Lost Time");
        books.add(book3);

        Book book4 = new Book();
        book4.setAuthor("James Joyce");
        book4.setTitle("Ulysses");
        books.add(book4);

        Book book5 = new Book();
        book5.setAuthor("Miguel de Cervantes");
        book5.setTitle("Don Quixote");
        books.add(book5);

        Book book6 = new Book();
        book6.setAuthor("Sun Tzu");
        book6.setTitle("The art of war");
        books.add(book6);

        Book book7 = new Book();
        book7.setAuthor("Gabriel Garcia Marquez");
        book7.setTitle("One Hundred Years of Solitude");
        books.add(book7);

        Book book8 = new Book();
        book8.setAuthor("F. Scott Fitzgerald");
        book8.setTitle("The Great Gatsby");
        books.add(book8);

        Book book9 = new Book();
        book9.setAuthor("Sun Tzu");
        book9.setTitle("The art of war");
        books.add(book9);

        Book book10 = new Book();
        book10.setAuthor("Herman Melville");
        book10.setTitle("Moby Dick");
        books.add(book10);



        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        //entityManager.persist(book3);
        //entityManager.persist(book4);
        //entityManager.persist(book5);
        //entityManager.persist(book2);
        //entityManager.persist(book6);
        //entityManager.persist(book7);
        //entityManager.persist(book8);
        //entityManager.persist(book9);
        //entityManager.persist(book10);

        entityManager.getTransaction().commit();

        System.out.println(entityManager.find(Book.class, book.getId()).toString());

        System.out.println(entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList());

        entityManager.close();
        entityManagerFactory.close();


        Book newBook = new Book();
        newBook.setAuthor("JDBC AUTHOR");
        newBook.setTitle("JDBC");
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.buildConnection();
        databaseManager.insertBook(newBook);
        databaseManager.closeConnection();
    }

}
