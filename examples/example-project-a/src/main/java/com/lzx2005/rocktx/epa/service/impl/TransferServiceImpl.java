package com.lzx2005.rocktx.epa.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lzx2005.rocktx.epa.dto.Resp;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

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
        try {
            TransactionListener transactionListener = new TransactionListenerImpl();
            TransactionMQProducer producer = new TransactionMQProducer("unique_group_name1");
            ExecutorService executorService = new ThreadPoolExecutor(
                    2,
                    5,
                    100,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(2000),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            Thread thread = new Thread(r);
                            thread.setName("client-transaction-msg-check-thread");
                            return thread;
                        }
                    });
            producer.setNamesrvAddr("localhost:9876");
            producer.setExecutorService(executorService);
            producer.setTransactionListener(transactionListener);
            producer.start();

            String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
            for (int i = 0; i < 10; i++) {
                Message msg =
                        new Message("TopicTest1234", tags[i % tags.length], "KEY" + i,
                                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction(msg, null);
                System.out.printf("%s%n", sendResult);

                Thread.sleep(10);
            }

            for (int i = 0; i < 100000; i++) {
                Thread.sleep(1000);
            }
            producer.shutdown();

        } catch (MQClientException | UnsupportedEncodingException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return Resp.success(null);


//        if (transferOut) {
//            Resp decrease = accountService.decreaseAmount(aId, amount);
//            Account account = new Account();
//            account.setId(bId);
//            account.setAmount(amount);
//            Resp increase = remoteService.increase(account);
//            if (increase.ok() && decrease.ok()) {
//                return Resp.success(null);
//            } else {
//                throw new RuntimeException("转账失败");
//            }
//        } else {
//            Resp increase = accountService.increaseAmount(aId, amount);
//            Account account = new Account();
//            account.setId(bId);
//            account.setAmount(amount);
//            Resp decrease = remoteService.decrease(account);
//            if (decrease.ok() && increase.ok()) {
//                return Resp.success(null);
//            } else {
//                throw new RuntimeException("转账失败");
//            }
//        }
    }
}
