package com.lzx2005.rocktx.epa.service.impl;

import com.lzx2005.rocktx.epa.dto.Resp;
import com.lzx2005.rocktx.epa.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hzlizx on 2019/3/6
 */
@Service
public class TransactionListenerImpl implements TransactionListener {

    private ConcurrentHashMap<String, LocalTransactionState> localTrans = new ConcurrentHashMap<>();

    @Autowired
    private AccountService accountService;

    @Override
    @Transactional
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        byte[] body = msg.getBody();
        try {
            String msgStr = new String(body, RemotingHelper.DEFAULT_CHARSET);
            String[] split = msgStr.split(",");
            int aId = Integer.parseInt(split[0]);
            int bId = Integer.valueOf(split[1]);
            int amount = Integer.parseInt(split[2]);
            boolean transferOut = Boolean.valueOf(split[3]);
            Resp resp = accountService.decreaseAmount(aId, amount);
            if (resp.ok()) {
                localTrans.put(msg.getTransactionId(), LocalTransactionState.COMMIT_MESSAGE);
                return LocalTransactionState.COMMIT_MESSAGE;
            } else {
                localTrans.put(msg.getTransactionId(), LocalTransactionState.ROLLBACK_MESSAGE);
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            localTrans.put(msg.getTransactionId(), LocalTransactionState.ROLLBACK_MESSAGE);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        return localTrans.get(msg.getTransactionId());
    }
}
