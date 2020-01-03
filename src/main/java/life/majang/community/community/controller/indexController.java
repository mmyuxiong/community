package life.majang.community.community.controller;

import life.majang.community.community.Dto.PaginationDTO;
import life.majang.community.community.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class indexController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(@RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        Model model
    ){
        PaginationDTO pagination=questionService.list(page,size);
        model.addAttribute("pagination",pagination);

        return "index";
    }

}
