package com.lzx2005.rocktx.epa.service.impl;

import com.lzx2005.rocktx.epa.dao.AccountRepository;
import com.lzx2005.rocktx.epa.dto.Resp;
import com.lzx2005.rocktx.epa.entity.Account;
import com.lzx2005.rocktx.epa.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 模拟业务逻辑
 * Created by hzlizx on 2019/3/5
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Resp amount(int id) {
        if (id <= 0) {
            return Resp.fail(1, "参数不正确");
        }
        Optional<Account> account = accountRepository.findById(id);
        return account.map(Resp::success).orElseGet(() -> Resp.fail(2, "找不到账号"));
    }

    @Override
    public Resp increaseAmount(int id, int amount) {
        if (id <= 0 || amount <= 0) {
            return Resp.fail(1, "参数不正确");
        }
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setAmount(account.getAmount() + amount);
            accountRepository.save(account);
            return Resp.success(account);
        } else {
            return Resp.fail(2, "找不到账号");
        }
    }
}
