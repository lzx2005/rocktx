package com.lzx2005.rocktx.epa.service.impl;

import com.lzx2005.rocktx.epa.dao.AccountDao;
import com.lzx2005.rocktx.epa.dto.Resp;
import com.lzx2005.rocktx.epa.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 模拟业务逻辑
 * Created by hzlizx on 2019/3/5
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    @Autowired
    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Resp amount(int id) {
        if (id <= 0) {
            return Resp.fail(1, "参数不正确");
        }
        return Resp.success(accountDao.amount(id));
    }

    @Override
    public Resp increaseAmount(int id, int amount) {
        if (id <= 0 || amount <= 0) {
            return Resp.fail(1, "参数不正确");
        }
        boolean success = accountDao.increase(id, amount);
        if (success) {
            return Resp.success(null);
        } else {
            return Resp.fail(2, "更新出错");
        }
    }
}
