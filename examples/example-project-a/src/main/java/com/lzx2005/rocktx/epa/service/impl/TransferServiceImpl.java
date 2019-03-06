package com.lzx2005.rocktx.epa.service.impl;

import com.lzx2005.rocktx.epa.dto.Resp;
import com.lzx2005.rocktx.epa.entity.Account;
import com.lzx2005.rocktx.epa.feign.RemoteService;
import com.lzx2005.rocktx.epa.service.AccountService;
import com.lzx2005.rocktx.epa.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hzlizx on 2019/3/6
 */
@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RemoteService remoteService;


    @Override
    @Transactional
    public Resp transfer(int aId, int bId, int amount, boolean transferOut) {
        if(transferOut){
            Resp decrease = accountService.decreaseAmount(aId, amount);
            Account account = new Account();
            account.setId(bId);
            account.setAmount(amount);
            Resp increase = remoteService.increase(account);
            if(increase.ok() && decrease.ok()){
                return Resp.success(null);
            }else {
                throw new RuntimeException("转账失败");
            }
        }else {
            Resp increase = accountService.increaseAmount(aId, amount);
            Account account = new Account();
            account.setId(bId);
            account.setAmount(amount);
            Resp decrease = remoteService.decrease(account);
            if(decrease.ok() && increase.ok()){
                return Resp.success(null);
            }else {
                throw new RuntimeException("转账失败");
            }
        }
    }
}
