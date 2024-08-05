package org.example;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        // Add a new book
        library.addBook(new Book("The Catcher in the Rye", "J.D. Salinger", 1951, 277, "Fiction"));

        // Remove a book by title
        library.removeBookByTitle("1984");

        // Find books published in a specific year
        List<Book> books1960 = library.findBooksByYear(1960);
        books1960.forEach(book -> System.out.println("Published in 1960: " + book.getTitle()));

        // Find books by a specific author
        List<Book> booksByFitzgerald = library.findBooksByAuthor("F. Scott Fitzgerald");
        booksByFitzgerald.forEach(book -> System.out.println("By F. Scott Fitzgerald: " + book.getTitle()));

        // Find the book with the most pages
        library.findBookWithMostPages()
                .ifPresent(book -> System.out.println("Book with most pages: " + book.getTitle()));

        // Find books with more than 200 pages
        List<Book> longBooks = library.findBooksWithMoreThanNPages(200);
        longBooks.forEach(book -> System.out.println("Book with more than 200 pages: " + book.getTitle()));

        // Print all book titles sorted alphabetically
        System.out.println("All book titles sorted alphabetically:");
        library.printAllBookTitlesSorted();

        // Create a user
        User user = new User("Alice", "123456");

        // Loan out a book
        boolean loanSuccess = user.loanBook(library, "The Great Gatsby");
        if (loanSuccess) {
            System.out.println("Successfully loaned out 'The Great Gatsby'.");
        } else {
            System.out.println("Failed to loan out 'The Great Gatsby'.");
        }

        // Attempt to loan out a book that's already on loan
        loanSuccess = user.loanBook(library, "The Great Gatsby");
        if (loanSuccess) {
            System.out.println("Successfully loaned out 'The Great Gatsby'.");
        } else {
            System.out.println("Failed to loan out 'The Great Gatsby'.");
        }

        // Simulate the passage of time by setting the due date to the past
        List<Book> books = library.findBooksByAuthor("F. Scott Fitzgerald");
        if (!books.isEmpty()) {
            Book book = books.get(0);
            book.setDueDate(LocalDate.now().minusDays(15)); // Assume overdue
        }

        // Return the book
        boolean returnSuccess = user.returnBook(library, "The Great Gatsby");
        if (returnSuccess) {
            System.out.println("Successfully returned 'The Great Gatsby'.");
        } else {
            System.out.println("Failed to return 'The Great Gatsby'.");
        }

        // Calculate late fees
        double lateFees = user.calculateTotalLateFees(library);
        System.out.println("Total late fees: $" + lateFees);
    }
}
