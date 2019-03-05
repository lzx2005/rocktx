package com.lzx2005.rocktx.epa.entity;

import lombok.Data;

/**
 * 模拟账号
 * Created by hzlizx on 2019/3/5
 */
@Data
public class Account {
    /**
     * 账号ID
     */
    private Integer id;

    /**
     * 账号余额
     */
    private int amount;
}
