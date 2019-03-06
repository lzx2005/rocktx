package com.lzx2005.rocktx.epa.dto;

import lombok.Data;

/**
 * Created by hzlizx on 2019/3/6
 */
@Data
public class TransferReq {
    private int ida;
    private int idb;
    private int amount;
    private boolean transferOut;
}

