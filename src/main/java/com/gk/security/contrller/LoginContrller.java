package com.gk.security.contrller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guokui
 * @class security
 * @date 2021/9/2 16:50
 */
@Slf4j
@RestController
public class LoginContrller {

    @GetMapping("index")
    public String index(){
        return "success";
    }

}
