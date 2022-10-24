package ru.gusev.springcourse.controllers;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gusev.springcourse.dao.PeopleDao;
import ru.gusev.springcourse.models.Person;

import java.sql.SQLException;




@Controller
@RequestMapping("/people")
public class PeopleControllers {
    private PeopleDao peopleDao;

   public PeopleControllers(PeopleDao peopleDao) {

       this.peopleDao=peopleDao;
   }

    @GetMapping()
    public String index (Model model) throws SQLException {
        model.addAttribute("people", peopleDao.index());
        return "people/index";
   }


    @GetMapping ("/{id}")
    public String  show (@PathVariable ("id") int id, Model model) {
      model.addAttribute("personId", peopleDao.show(id));
      model.addAttribute("books", peopleDao.getBooksByPersonId(id));
      return "people/show";
       }


     @GetMapping("/new")
     public String newPerson (Model model) {
       model.addAttribute("person", new Person());
       return "people/new";}


     @PostMapping()
     public String create (@ModelAttribute ("person") Person person) {
       peopleDao.save(person);
       return "redirect:/people";}


      @GetMapping("/{id}/edit")
      public String edit (Model model, @PathVariable("id") int id) {
      model.addAttribute("person", peopleDao.show(id));
      return "people/edit";
       }

      @PatchMapping ("/{id}")
      public String update (@ModelAttribute("city")  Person person,  @PathVariable ("id") int id) {
           peopleDao.update (id,person);
           return "redirect:/people";
    }

       @DeleteMapping("/{id}")
       public String delete (@PathVariable ("id") int id){
       peopleDao.delete (id);
       return "redirect:/people";

    }

}
