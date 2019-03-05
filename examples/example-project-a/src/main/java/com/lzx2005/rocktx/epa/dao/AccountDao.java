package com.lzx2005.rocktx.epa.dao;

import com.lzx2005.rocktx.epa.entity.Account;
import org.springframework.stereotype.Service;

/**
 * 模拟用户更新
 * Created by hzlizx on 2019/3/5
 */
@Service
public class AccountDao {

    /**
     * 查询余额
     * @param id    账户ID
     * @return      余额
     */
    public int amount(int id){
        return 1000;
    }

    /**
     * 给某个账号增加余额
     * @param id        账号ID
     * @param amount    要增加的余额
     * @return          是否成功
     */
    public boolean increase(int id, int amount){
        return true;
    }

}
