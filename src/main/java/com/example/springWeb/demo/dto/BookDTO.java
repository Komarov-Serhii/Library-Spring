package com.example.springWeb.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
//@Builder
//@NoArgsConstructor
public class BookDTO {

    private long id;
    private String name;
    private String author;
    private String publisher;
    private String publisherDate;
    private String description;
    private int price;
    private String genre;

    public static class AuthorComparator implements Comparator<BookDTO> {
        @Override
        public int compare(BookDTO p1, BookDTO p2) {
            return p1.getAuthor().compareTo(p2.getAuthor());
        }
    }

    public static class PublisherComparator implements Comparator<BookDTO> {
        @Override
        public int compare(BookDTO p1, BookDTO p2) {
            return p1.getPublisher().compareTo(p2.getPublisher());
        }
    }

    public static class PublisherDateComparator implements Comparator<BookDTO> {
        @Override
        public int compare(BookDTO p1, BookDTO p2) {
            return p1.getPublisherDate().compareTo(p2.getPublisherDate());
        }
    }

}
