package south.bank.tst.maven.reccomendationsystem.controllers;

import south.bank.tst.maven.reccomendationsystem.beans.LoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import south.bank.tst.maven.reccomendationsystem.beans.RegisterBean;
import south.bank.tst.maven.reccomendationsystem.converters.RegisterConverter;
import south.bank.tst.maven.reccomendationsystem.dao.UserDao;

import south.bank.tst.maven.reccomendationsystem.entities.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mar_9
 */
@Controller
public class AccountEntryController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RegisterConverter registerConverter;

    @GetMapping("/login")
    public String greeting(Model model) {
        model.addAttribute("login", new LoginBean());

        return "login";
    }

    @PostMapping("/login")
    public String greetingSubmit(@ModelAttribute LoginBean login, Model model, RedirectAttributes attr) {

        User user = userDao.findByNickname(login.getName());

        if (user == null) {
            login.setErrorMessage("No user with nickname: " + login.getName());
            model.addAttribute("login", login);
            return "login";
        }

        attr.addAttribute("nickname", login.getName());

        return "redirect:/userdashboard";
    }

    @GetMapping("/register")
    public String loadRegister(Model model) {
        model.addAttribute("register", new RegisterBean());

        return "register";
    }

    @PostMapping("/register")
    public String executeRegister(@ModelAttribute RegisterBean register, Model model, RedirectAttributes attr) {
        if (register.getEmail() == null || register.getEmail().isEmpty()
                || register.getNickname() == null || register.getNickname().isEmpty()
                || register.getPassword() == null || register.getPassword().isEmpty()
                || register.getConfirmedPassword() == null || register.getConfirmedPassword().isEmpty()) {
            register.setErrorMessage("Fill all mandatory fields!");

            model.addAttribute("register", register);
            return "register";
        }

        if (!register.getPassword().equals(register.getConfirmedPassword())) {
            register.setErrorMessage("Password and confirm password fields should be equals!");

            model.addAttribute("register", register);
            return "register";
        }

        User user = userDao.findByEmail(register.getEmail());
        if (user != null) {
            register.setErrorMessage("There is already an existing user with that email. "
                    + "Please, use the forgot password link if you need to reset passrd");

            model.addAttribute("register", register);
            return "register";
        }

        user = userDao.findByNickname(register.getNickname());
        if (user != null) {
            register.setErrorMessage("There is already an existing user with that nickname. "
                    + "Please, use the forgot password link if you need to reset passrd");

            model.addAttribute("register", register);
            return "register";
        }

        user = new User();
        registerConverter.convert(register, user);

        userDao.save(user);

        LoginBean login = new LoginBean();
        login.setName(register.getNickname());
        login.setEmail(register.getEmail());
        model.addAttribute("login", login);

        attr.addAttribute("nickname", login.getName());

        return "redirect:userdashboard";
    }

}
