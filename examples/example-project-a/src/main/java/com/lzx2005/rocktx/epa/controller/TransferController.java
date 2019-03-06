package com.lzx2005.rocktx.epa.controller;

import com.lzx2005.rocktx.epa.dto.Resp;
import com.lzx2005.rocktx.epa.dto.TransferReq;
import com.lzx2005.rocktx.epa.entity.Account;
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

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public Resp increaseAmount(@RequestBody TransferReq transferReq) {
        return transferService.transfer(
                transferReq.getAId(),
                transferReq.getBId(),
                transferReq.getAmount(),
                transferReq.isTransferOut()
        );
    }

}
