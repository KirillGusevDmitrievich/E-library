package ru.gusev.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gusev.springcourse.dao.BooksDao;
import ru.gusev.springcourse.dao.PeopleDao;
import ru.gusev.springcourse.models.Books;
import ru.gusev.springcourse.models.Person;

import java.sql.SQLException;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

        private final BooksDao booksDao;
        private final PeopleDao peopleDao;

        public BooksController (BooksDao booksDao, PeopleDao peopleDao) {
            this.booksDao=booksDao;
            this.peopleDao=peopleDao;
        }

        @GetMapping()
        public String index (Model model) throws SQLException {
            model.addAttribute("book", booksDao.index());
            return "books/index";
        }


        @GetMapping ("/{id}")
        public String  show (@PathVariable("id") int id, Model model, @ModelAttribute ("person") Person person) {
            model.addAttribute("bookId", booksDao.show(id));
            Optional <Person> bookOwner = booksDao.getBookOwner(id);
            if (bookOwner.isPresent())
                model.addAttribute("owner", bookOwner.get());
            else
               model.addAttribute("people", peopleDao.index());

            return "books/show";
        }


        @GetMapping("/new")
        public String newBook (Model model) {
            model.addAttribute("book", new Books());
            return "books/new";}


        @PostMapping()
        public String create (@ModelAttribute("books") Books books) {
            booksDao.save(books);
            return "redirect:/books";}


        @GetMapping("/{id}/edit")
        public String edit (Model model, @PathVariable("id") int id) {
            model.addAttribute("book", booksDao.show(id));
            return "books/edit";
        }

        @PatchMapping ("/{id}")
        public String update (@ModelAttribute("book")  Books books,  @PathVariable ("id") int id) {
            booksDao.update (id,books);
            return "redirect:/books";
        }

        @DeleteMapping("/{id}")
        public String delete (@PathVariable ("id") int id){
            booksDao.delete (id);
            return "redirect:/books";

        }

        @PatchMapping ("/{id}/release")
        public String release (@PathVariable("id") int id) {
            booksDao.release (id);
            return "redirect:/books/"+id;
       }

        @PatchMapping ("/{id}/assign")
        public String assign (@PathVariable("id") int id, @ModelAttribute ("person") Person selectedPerson) {
           booksDao.assign (id, selectedPerson);
           return "redirect:/books/" +id;
        }

    }


