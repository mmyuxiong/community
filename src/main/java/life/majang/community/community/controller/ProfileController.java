package life.majang.community.community.controller;


import life.majang.community.community.Dto.PaginationDTO;
import life.majang.community.community.Mapper.UserMapper;
import life.majang.community.community.Service.QuestionService;
import life.majang.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
            @PathVariable("action")String action, Model model,
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "5") Integer size
    ){
        User user=null;
        Cookie[] cookies=request.getCookies();
        if(cookies!=null && cookies.length!=0)
            for (Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token=cookie.getValue();
                     user=userMapper.findByToken("cff30d0d-d812-42c6-98a7-5c58d576e125");
                    if(user!=null){
                        request.setAttribute("user",user);
                    }
                    break;
                }
            }
        if(user==null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO paginationDTO = questionService.list(user.getAccountId(),page,size);
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
