package ru.gusev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.gusev.springcourse.models.Books;
import ru.gusev.springcourse.models.Person;

import java.util.List;

@Component
public class PeopleDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PeopleDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }


    public List <Person> index () {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show (int id) {
    return jdbcTemplate.query("SELECT * FROM person WHERE person_id =?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
          .stream().findAny().orElse(null);

    }
    public void save (Person person) {

    jdbcTemplate.update("INSERT INTO person VALUES (DEFAULT,?,?)",person.getName(), person.getDayBirthday());
                                     }


    public void update (int id, Person updatePerson) {

      jdbcTemplate.update("UPDATE person SET name=?, dayBirthday=? WHERE person_id=?",updatePerson.getName(),
              updatePerson.getDayBirthday(), id);

    }

    public void delete (int id) {
     jdbcTemplate.update( "DELETE FROM person WHERE person_id=?", id);

    }

    public List<Books> getBooksByPersonId (int id) {
        return jdbcTemplate.query ("SELECT * FROM books WHERE person_id=?", new Object[]{id},
                                 new BeanPropertyRowMapper<>(Books.class));
    }
}
