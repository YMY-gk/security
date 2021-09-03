package com.gk.security.contrller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guokui
 * @class security
 * @date 2021/9/2 16:50
 */
@Slf4j
@Controller
public class LoginContrller {

    @GetMapping("index")
    public String index(){
        return "success";
    }
    @PostMapping("user/login")
    public String login(String username,String password){
        log.error(username+"------------"+password);
        return "success";
    }
}
