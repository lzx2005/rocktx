package com.lzx2005.rocktx.epb;

import com.lzx2005.rocktx.epb.dao.AccountRepository;
import com.lzx2005.rocktx.epb.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class ExampleProjectBApplication {

    private final AccountRepository accountRepository;

    @Autowired
    public ExampleProjectBApplication(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleProjectBApplication.class, args);
    }


    @PostConstruct
    public void initialize(){
        // 初始化创建一个用户，因为是H2数据库，所以关闭项目后，数据清空
        Account account = new Account();
        account.setId(1);
        account.setAmount(1000);
        account.setName("李四");
        accountRepository.save(account);
    }
}
