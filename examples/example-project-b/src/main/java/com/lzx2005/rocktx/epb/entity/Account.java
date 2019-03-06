package com.lzx2005.rocktx.epb.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 模拟账号
 * Created by hzlizx on 2019/3/5
 */
@Data
@Entity
@Table(name = "account")
public class Account {
    /**
     * 账号ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 姓名
     */
    @Column
    private String name;

    /**
     * 账号余额
     */
    @Column
    private int amount;
}
