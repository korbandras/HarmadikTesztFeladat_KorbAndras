package org.example.harmadiktesztfeladat_korbandras;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private DataService dataService;

    @GetMapping("/")
    public String showForm() {
        return "form";
    }

    @GetMapping("/process")
    public String processData(@RequestParam(required = false) String startsWith,
                              @RequestParam(defaultValue = "nameAsc") String sortOrder,
                              Model model) {

        List<NameFrequency> frequencies = dataService.dataFromXML(startsWith, sortOrder);
        model.addAttribute("frequencies", frequencies);

        return "result";
    }
}
