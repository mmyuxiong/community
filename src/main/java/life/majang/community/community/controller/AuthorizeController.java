package life.majang.community.community.controller;


import life.majang.community.community.Dto.AccessTokenDTO;
import life.majang.community.community.Dto.GithubUser;
import life.majang.community.community.Mapper.UserMapper;
import life.majang.community.community.Service.UserService;
import life.majang.community.community.model.User;
import life.majang.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AuthorizeController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.clientSecrect}")
    private String clientSecrect;
    @Value("${github.clientId}")
    private String clientId;
    @Value("${github.redirectUri}")
    private String redirectUri;
    @Autowired
    private UserService userService;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest requset,
                           HttpServletResponse response
    ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecrect);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser gethubuser = githubProvider.getUser(accessToken);
        if(gethubuser!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gethubuser.getName());
            user.setAccountId(Integer.parseInt(String.valueOf(gethubuser.getId())));
            user.setAvatarUrl(gethubuser.getAvatarUrl());
            userService.createOrUpdate(user);
            requset.getSession().setAttribute("user",gethubuser);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response
    ){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
