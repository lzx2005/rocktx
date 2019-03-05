package com.lzx2005.rocktx.epa;

import com.lzx2005.rocktx.epa.dao.AccountRepository;
import com.lzx2005.rocktx.epa.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableEurekaClient
public class ExampleProjectAApplication {

    private final AccountRepository accountRepository;

    @Autowired
    public ExampleProjectAApplication(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleProjectAApplication.class, args);
    }

    @PostConstruct
    public void initialize(){
        // 初始化创建一个用户，因为是H2数据库，所以关闭项目后，数据清空
        Account account = new Account();
        account.setId(1);
        account.setAmount(1000);
        accountRepository.save(account);
    }
}
