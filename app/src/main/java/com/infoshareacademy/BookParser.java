package com.infoshareacademy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BookParser {

    private static final Logger stdout = LoggerFactory.getLogger("CONSOLE_OUT");
    private static final String PATHNAME = "baza.json";

    public List<Book> loadBooks() {
        return parseJsonFileToObject();
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    private List<Book> parseJsonFileToObject() {
        List<Book> bookList = List.of();

        try {
            bookList = objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(new File(PATHNAME), new TypeReference<>() {
                    });
        } catch (IOException e) {
            stdout.info("\nNie wczytano bazy z pliku!");
        }
        return bookList;
    }
    public void saveObjectsToFile() {
        try {
            objectMapper.writeValue(new File(PATHNAME), BookRepository.getInstance().getBooks());
        } catch (IOException e) {
            stdout.info("Problem z zapisem do pliku!");
        }
    }
}