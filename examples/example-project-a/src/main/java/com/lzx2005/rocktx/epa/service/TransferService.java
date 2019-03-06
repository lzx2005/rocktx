package com.lzx2005.rocktx.epa.service;

import com.lzx2005.rocktx.epa.dto.Resp;

/**
 * Created by hzlizx on 2019/3/6
 */
public interface TransferService {

    /**
     * 转账
     * @param aId           A银行账户的id
     * @param bId           B银行账号的id
     * @param amount        转出数额
     * @param transferOut   是否为转出（true：转出，false：转入）
     * @return              结果
     */
    Resp transfer(int aId,int bId, int amount, boolean transferOut);

}
