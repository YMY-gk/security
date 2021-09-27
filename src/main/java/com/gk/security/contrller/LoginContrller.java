package com.gk.security.contrller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guokui
 * @class security
 * @date 2021/9/2 16:50
 */
@Slf4j
@RestController
public class LoginContrller {

    @GetMapping("/user/index")
    public String index(Authentication authorization){
        log.error("------------------------------------------------"+authorization.getPrincipal());
        return "success";
    }
    @PostMapping("/user/login")
    public String login(String username,String password){
        log.error(username+"------------"+password);
        return "success";
    }
    @GetMapping("/role")
    public String role(){
        log.error("------role------");
        return "success";
    }
    @GetMapping("/role1")
    public String role1(){
        log.error("------role1------");
        return "success";
    }
    @GetMapping("/user/role")
    public String role01(){
        log.error("----/user/role--------");
        return "success00001";
    }
    @GetMapping("/user/role1")
    public String role011(){
        log.error("----/user/role1--------");
        return "success00001";
    }
    @GetMapping("/user/role2")
    public String role012(){
        log.error("----/user/role2--------");
        return "测试权限";
    }
    @GetMapping("/user/role3")
    public String role013(){
        log.error("----/user/role3--------");
        return "测试权限未附权限";
    }
}
