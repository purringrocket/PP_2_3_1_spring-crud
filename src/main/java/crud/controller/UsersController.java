package crud.controller;

import crud.model.User;
import crud.service.UserServiceWithRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("users")
public class UsersController {

    private UserServiceWithRepo userService;

    @Autowired
    public void setUserService(UserServiceWithRepo userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());

        //test
        userService.testCustomQueries();

        return "users/index";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "users/profile";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        System.out.println("bindingResult.hasErrors() " + bindingResult.hasErrors());
        System.out.println("bindingResult.hasFieldErrors()" + bindingResult.hasFieldErrors());
        if (bindingResult.hasErrors()) {
            return "users/new";
        }

        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") Long id) {
        System.out.println("bindingResult.hasErrors() " + bindingResult.hasErrors());
        System.out.println("bindingResult.hasFieldErrors()" + bindingResult.hasFieldErrors());
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }

        userService.update(user.getId(), user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }

}
