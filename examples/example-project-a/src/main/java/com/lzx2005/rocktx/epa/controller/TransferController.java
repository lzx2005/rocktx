package com.lzx2005.rocktx.epa.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzx2005.rocktx.epa.dto.Resp;
import com.lzx2005.rocktx.epa.dto.TransferReq;
import com.lzx2005.rocktx.epa.entity.Account;
import com.lzx2005.rocktx.epa.feign.RemoteService;
import com.lzx2005.rocktx.epa.service.AccountService;
import com.lzx2005.rocktx.epa.service.TransferService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private TransactionListener transactionListener;

    private TransactionMQProducer producer = new TransactionMQProducer("unique_group_name");

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

        try {
            ExecutorService executorService = new ThreadPoolExecutor(
                    2, 5, 100,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(2000),
                    r -> {
                        Thread thread = new Thread(r);
                        thread.setName("client-transaction-msg-check-thread");
                        return thread;
                    });
            producer.setNamesrvAddr("10.200.4.213:9876");
            producer.setExecutorService(executorService);
            producer.setTransactionListener(transactionListener);
            producer.start();
            Message msg = new Message(
                    "transfer-tx",
                    "transfer",
                    "KEY",
                    JSONObject.toJSONString(transferReq).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            System.out.printf("%s%n", sendResult);
            for (int i = 0; i < 100000; i++) {
                Thread.sleep(1000);
            }
            producer.shutdown();

        } catch (MQClientException | UnsupportedEncodingException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            producer.shutdown();
        }

        return transferService.transfer(
                transferReq.getIda(),
                transferReq.getIdb(),
                transferReq.getAmount(),
                transferReq.isTransferOut()
        );
    }

}
