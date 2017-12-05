package org.launchcode.cheesemvc.controllers;

import org.launchcode.cheesemvc.models.CheeseData;
import org.launchcode.cheesemvc.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model, @ModelAttribute User user) {
        model.addAttribute(new User());
        model.addAttribute("title", "New User");

        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid User user,
                      Errors errors, String verify){

        if(errors.hasErrors()){
            model.addAttribute(user);
            model.addAttribute("title", "New User");
            return "user/add";
        }
        if(user.getPassword().equals(verify)){
            return "user/index";
        }
        else{
            model.addAttribute(user);
            model.addAttribute("title", "New User");
            model.addAttribute("problem", "Password and Verify Password did not match.");
            return "user/add";
        }
    }
}
