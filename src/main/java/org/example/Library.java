package org.example;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Library extends ArrayList<Book> {

    private static final int LOAN_PERIOD_DAYS = 14; // 2 weeks
    private static final double LATE_FEE_PER_DAY = 0.50; // $0.50 per day

    public Library() {
        addBooks();
    }

    private void addBooks() {
        this.add(new Book("To Kill a Mockingbird", "Harper Lee", 1960, 281, "Fiction"));
        this.add(new Book("1984", "George Orwell", 1949, 328, "Dystopian"));
        this.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 180, "Classic"));
        this.add(new Book("Pride and Prejudice", "Jane Austen", 1813, 279, "Romance"));
    }

    public Library addBook(Book book) {
        this.add(book);
        return this;
    }

    public void removeBookByTitle(String title) {
        this.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    public List<Book> findBooksByYear(int year) {
        return this.stream()
                .filter(book -> book.getPublicationYear() == year)
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByAuthor(String author) {
        return this.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public Optional<Book> findBookWithMostPages() {
        return this.stream()
                .max(Comparator.comparingInt(Book::getPages));
    }

    public List<Book> findBooksWithMoreThanNPages(int n) {
        return this.stream()
                .filter(book -> book.getPages() > n)
                .collect(Collectors.toList());
    }

    public void printAllBookTitlesSorted() {
        this.stream()
                .map(Book::getTitle)
                .sorted(String::compareToIgnoreCase)
                .forEach(System.out::println);
    }

    public List<Book> findBooksByCategory(String category) {
        return this.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public boolean loanBook(String title) {
        Optional<Book> bookOpt = this.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title) && !book.isOnLoan())
                .findFirst();

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setOnLoan(true);
            book.setLoanDate(LocalDate.now());
            book.setDueDate(LocalDate.now().plusDays(LOAN_PERIOD_DAYS));
            return true;
        }
        return false;
    }

    public boolean returnBook(String title) {
        Optional<Book> bookOpt = this.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title) && book.isOnLoan())
                .findFirst();

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setOnLoan(false);
            book.setLoanDate(null);
            book.setDueDate(null);
            return true;
        }
        return false;
    }

    public double calculateLateFees(Book book) {
        if (!book.isOnLoan() || book.getDueDate() == null) {
            return 0.0;
        }
        long daysLate = ChronoUnit.DAYS.between(book.getDueDate(), LocalDate.now());
        return daysLate > 0 ? daysLate * LATE_FEE_PER_DAY : 0.0;
    }
}

