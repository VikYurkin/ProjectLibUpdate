package ru.VYurkin.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.VYurkin.models.Book;
import ru.VYurkin.models.Person;
import ru.VYurkin.repositories.BooksRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> indexBook() {
        return booksRepository.findAll();
    }

    public Book showBook(int bookId) {
        return booksRepository.findById(bookId).orElse(null);
    }

    public List<Book> showBooks(String nameAuthor, String nameBook) {
        return booksRepository.findByNameAuthorAndNameBook(nameAuthor,nameBook);
    }

    @Transactional
    public void saveBook(Book book) {
        booksRepository.save(book);
        }

    @Transactional
    public void updateBook(int bookId, Book updatedBook) {
        updatedBook.setBookId(bookId);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void deleteBook(int bookId) {
        booksRepository.deleteById(bookId);
    }
    public Person owner(int bookId){
        Optional<Book> book = booksRepository.findById(bookId).stream().findAny();
        if(book.isPresent()){
            Hibernate.initialize(book.get().getOwner());
            return book.get().getOwner();
        } else {return null;}
    }


    @Transactional
    public void personGetBook (int bookId, Person owner){
        Book book = booksRepository.findById(bookId).orElse(null);
        if(book.getOwner()!=null){return;}
          book.setOwner(owner);
    }

    @Transactional
    public void personReturnBook(int bookId){
        Book book = booksRepository.findById(bookId).orElse(null);
        if(book.getOwner()==null){return;}
        book.getOwner().getBooks().remove(book);
        book.setOwner(null);
    }

}
