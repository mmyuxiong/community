package life.majang.community.community.controller;

import life.majang.community.community.Dto.QuestionDTO;
import life.majang.community.community.Mapper.QuestionMapper;
import life.majang.community.community.Service.QuestionService;
import life.majang.community.community.Service.UserService;
import life.majang.community.community.model.Question;
import life.majang.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,Model model){
        QuestionDTO question= questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";

    }
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tag,
            @RequestParam("id") Integer id,
            HttpServletRequest request,
            Model model
    ){
        if(title==null || title == ""){
            model.addAttribute("error","标题不能为空！");
            return "publish";
        }
        if(description==null || description == ""){
            model.addAttribute("error","问题补充不能为空！");
            return "publish";
        }
        if(tag==null || tag == ""){
            model.addAttribute("error","标签至少为一个！");
            return "publish";
        }
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        User user=(User) request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setId(user.getId());
        question.setCreator( user.getAccountId());
        question.setId(id);
        question.setGmtModified(user.getGmtModified());
        question.setGmtCreate(user.getGmtCreate());
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
