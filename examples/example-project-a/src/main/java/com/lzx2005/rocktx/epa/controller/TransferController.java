package com.lzx2005.rocktx.epa.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzx2005.rocktx.epa.dto.Resp;
import com.lzx2005.rocktx.epa.dto.TransferReq;
import com.lzx2005.rocktx.epa.entity.Account;
import com.lzx2005.rocktx.epa.feign.RemoteService;
import com.lzx2005.rocktx.epa.service.AccountService;
import com.lzx2005.rocktx.epa.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 作为一个服务发布在Eureka上
 * Created by hzlizx on 2019/3/5
 */
@RestController
@RequestMapping("/account")
public class TransferController {

    private final TransferService transferService;

    private final AccountService accountService;

    private final RemoteService remoteService;

    @Autowired
    public TransferController(TransferService transferService, AccountService accountService, RemoteService remoteService) {
        this.transferService = transferService;
        this.accountService = accountService;
        this.remoteService = remoteService;
    }

    @GetMapping("/info")
    public Resp info() {
        Resp amount = accountService.amount(1);
        Resp resp = remoteService.get(1);
        JSONArray array = new JSONArray();
        array.add(amount.getData());
        array.add(resp.getData());
        return Resp.success(array);
    }

    @GetMapping("/reset")
    public Resp reset() {
        remoteService.reset();
        return accountService.reset();
    }

    @GetMapping("/{id}")
    public Resp get(@PathVariable int id) {
        return accountService.amount(id);
    }


    @PostMapping("/transfer")
    public Resp increaseAmount(@RequestBody TransferReq transferReq) {
        return transferService.transfer(
                transferReq.getIda(),
                transferReq.getIdb(),
                transferReq.getAmount(),
                transferReq.isTransferOut()
        );
    }

}
