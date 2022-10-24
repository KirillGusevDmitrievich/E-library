package ru.gusev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.gusev.springcourse.models.Books;
import ru.gusev.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BooksDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BooksDao (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<Books> index () {

        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Books.class));
    }

    public Books show (int id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE book_id =?", new Object[]{id}, new BeanPropertyRowMapper<>(Books.class))
                .stream().findAny().orElse(null);

    }
    public void save (Books books) {

        jdbcTemplate.update("INSERT INTO books (title, autor, year) values (?,?,?)",books.getTitle(), books.getAutor(),books.getYear());
    }


    public void update (int id, Books updateBooks) {

        jdbcTemplate.update("UPDATE books SET title=?, autor=?, year=? WHERE book_id=?",updateBooks.getTitle(),
                updateBooks.getAutor(), updateBooks.getYear(), id);

    }

    public void delete (int id) {
        jdbcTemplate.update( "DELETE FROM books WHERE book_id=?", id);

    }

    public Optional<Person> getBookOwner (int id) {
        return jdbcTemplate.query ("SELECT * FROM books JOIN person ON books.person_id=person.person_id WHERE books.book_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void release (int id) {jdbcTemplate.update("UPDATE books SET person_id = NULL WHERE book_id=?", id);}

    public void assign (int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE books SET person_id=? WHERE book_id=?", selectedPerson.getPerson_id(), id);
    }

}


