package org.launchcode.cheesemvc.controllers;

import org.launchcode.cheesemvc.models.CheeseData;
import org.launchcode.cheesemvc.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model, @ModelAttribute User user) {

        model.addAttribute("title", "Add Cheese");

        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute User user, String verify){
      if(user.getPassword().equals(verify)){

          return "user/index";
      }
      else{

          model.addAttribute("problem", "Password and Verify Password did not match.");
          return "user/add";
      }
    }
}
