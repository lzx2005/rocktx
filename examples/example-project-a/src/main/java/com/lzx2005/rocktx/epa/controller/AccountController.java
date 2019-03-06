package com.lzx2005.rocktx.epa.controller;

import com.lzx2005.rocktx.epa.dto.Resp;
import com.lzx2005.rocktx.epa.entity.Account;
import com.lzx2005.rocktx.epa.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 作为一个服务发布在Eureka上
 * Created by hzlizx on 2019/3/5
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/{id}")
    public Resp hello(@PathVariable int id) {
        return accountService.amount(id);
    }

    @PostMapping("/increase")
    public Resp increaseAmount(@RequestBody Account account) {
        return accountService.increaseAmount(account.getId(), account.getAmount());
    }

    @PostMapping("/decrease")
    public Resp decreaseAmount(@RequestBody Account account) {
        return accountService.decreaseAmount(account.getId(), account.getAmount());
    }

}
