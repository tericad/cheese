package org.launchcode.cheesemvc.controllers;

import org.launchcode.cheesemvc.models.Cheese;
import org.launchcode.cheesemvc.models.CheeseData;
import org.launchcode.cheesemvc.models.CheeseType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;



@Controller
@RequestMapping("cheese")
public class CheeseController {



    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", CheeseData.getAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("cheeseTypes", CheeseType.values());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute @Valid Cheese newCheese,
                                        Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Add Cheese");
            return "cheese/add";
        }
        CheeseData.add(newCheese);

        //Redirect to /
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", CheeseData.getAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {
        ArrayList<Cheese> cheesesDelete = new ArrayList<>();

        for (int cheeseId : cheeseIds){
          CheeseData.remove(cheeseId);
        }
        return "redirect:";
    }

    @RequestMapping(value="/edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditForm(@ModelAttribute Cheese thisCheese, Model model,
                                  @PathVariable int cheeseId){
        Cheese editCheese = CheeseData.getById(cheeseId);
        model.addAttribute("cheese", editCheese);
        model.addAttribute("title", "Edit Cheese");
        model.addAttribute("cheeseTypes", CheeseType.values());
        return "cheese/edit";

    }

    @RequestMapping(value="edit/{cheeseId}", method = RequestMethod.POST)
    public String processEditForm(@ModelAttribute @Valid Cheese editCheese,
                                  Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Edit Cheese");
            return "cheese/edit";
        }
        //   int cheeseId, String name, String description, type){
        Cheese originalCheese = CheeseData.getById(editCheese.getCheeseId());
        originalCheese.setName(editCheese.getName());
        originalCheese.setDescription(editCheese.getDescription());
        originalCheese.setType(editCheese.getType());
        originalCheese.setRating(editCheese.getRating());
        return "redirect:/cheese/";


    }
}