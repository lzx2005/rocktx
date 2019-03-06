package com.lzx2005.rocktx.epa.service;

import com.lzx2005.rocktx.epa.dto.Resp;
import com.lzx2005.rocktx.epa.entity.Account;

/**
 * 模拟用户创建
 * Created by hzlizx on 2019/3/5
 */
public interface AccountService {

    /**
     * 查询余额
     * @param id  账号ID
     * @return    结果
     */
    Resp amount(int id);


    /**
     * 增加余额
     * @param id        账号ID
     * @param amount    增加的金额
     * @return          是否成功
     */
    Resp increaseAmount(int id, int amount);

    /**
     * 减少余额
     * @param id        账号ID
     * @param amount    减少的金额
     * @return          是否成功
     */
    Resp decreaseAmount(int id, int amount);

}
