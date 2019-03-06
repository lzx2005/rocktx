package com.lzx2005.rocktx.epb.dto;

import lombok.Data;

/**
 * Created by hzlizx on 2019/3/5
 */
@Data
public class Resp {

    private int code;
    private String msg;
    private Object data;

    private Resp(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean ok() {
        return this.code == 200;
    }

    public static Resp custom(int code, String msg, Object data) {
        return new Resp(code, msg, data);
    }

    public static Resp success(Object data) {
        return new Resp(200, "请求成功", data);
    }

    public static Resp fail(int code, String msg) {
        return new Resp(code, msg, null);
    }
}
