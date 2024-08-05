package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String libraryCardNumber;
    private List<Book> loanedBooks;

    public User(String name, String libraryCardNumber) {
        this.name = name;
        this.libraryCardNumber = libraryCardNumber;
        this.loanedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLibraryCardNumber() {
        return libraryCardNumber;
    }

    public void setLibraryCardNumber(String libraryCardNumber) {
        this.libraryCardNumber = libraryCardNumber;
    }

    public List<Book> getLoanedBooks() {
        return loanedBooks;
    }

    public boolean loanBook(Library library, String title) {
        boolean success = library.loanBook(title);
        if (success) {
            Book book = library.stream()
                    .filter(b -> b.getTitle().equalsIgnoreCase(title))
                    .findFirst()
                    .orElse(null);
            if (book != null) {
                loanedBooks.add(book);
            }
        }
        return success;
    }

    public boolean returnBook(Library library, String title) {
        boolean success = library.returnBook(title);
        if (success) {
            Book book = loanedBooks.stream()
                    .filter(b -> b.getTitle().equalsIgnoreCase(title))
                    .findFirst()
                    .orElse(null);
            if (book != null) {
                loanedBooks.remove(book);
            }
        }
        return success;
    }

    public double calculateTotalLateFees(Library library) {
        return loanedBooks.stream()
                .mapToDouble(library::calculateLateFees)
                .sum();
    }
}


