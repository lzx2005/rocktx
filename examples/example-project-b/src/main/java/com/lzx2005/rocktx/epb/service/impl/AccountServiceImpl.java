package com.lzx2005.rocktx.epb.service.impl;

import com.lzx2005.rocktx.epb.dao.AccountRepository;
import com.lzx2005.rocktx.epb.dto.Resp;
import com.lzx2005.rocktx.epb.entity.Account;
import com.lzx2005.rocktx.epb.service.AccountService;
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
        int increase = accountRepository.increase(id, amount);
        if (increase > 0) {
            return Resp.success(null);
        } else {
            return Resp.fail(3, "没有更新成功");
        }
    }

    @Override
    public Resp decreaseAmount(int id, int amount) {
        if (id <= 0 || amount <= 0) {
            return Resp.fail(1, "参数不正确");
        }
        int decrease = accountRepository.decrease(id, amount);
        if (decrease > 0) {
            return Resp.success(null);
        } else {
            return Resp.fail(3, "没有更新成功");
        }
    }
}
