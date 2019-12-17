package com.infoshareacademy.bookmanagement;

import com.infoshareacademy.Book;
import com.infoshareacademy.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class BookAdder {

    private Scanner scanner = new Scanner(System.in);
    private Book book = new Book();
    private static final Logger stdout = LoggerFactory.getLogger("CONSOLE_OUT");

    public void addBook() {
        ManageBooks idChecker = new ManageBooks();
        book.setId(idChecker.getSequenceId());
        stdout.info("Podaj rodzaj\n");
        String kind = scanner.next();
        book.setKind(kind);
        stdout.info("Podaj autora\n");
        String author = scanner.next();
        book.setAuthor(author);
        stdout.info("Podaj epoke\n");
        String epoch = scanner.next();
        book.setEpoch(epoch);
        stdout.info("Podaj tytuł \n");
        String title = scanner.next();
        book.setTitle(title);
        stdout.info("Podaj rodzaj literacki \n");
        String genre = scanner.next();
        book.setGenre(genre);
        String checkAudio;
        stdout.info("Czy ma audio t/n :");
        checkAudio = scanner.next().toLowerCase();
        if (checkAudio.equals("t")) {
            book.setHasAudio(true);
        } else if (checkAudio.equals("n")) {
            book.setHasAudio(false);
        } else {
            stdout.info("Zle wprowadziłeś");
        }
        BookRepository.getInstance().getBooks().add(book);

    }
}