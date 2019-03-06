package com.lzx2005.rocktx.epa.feign;

import com.lzx2005.rocktx.epa.dto.Resp;
import com.lzx2005.rocktx.epa.entity.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 调用服务B的feign
 * Created by hzlizx on 2019/3/6
 */
@FeignClient(value = "EXAMPLE-PROJECT-B")
public interface RemoteService {

    @RequestMapping(value = "/account/reset", method = RequestMethod.GET)
    Resp reset();

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    Resp get(@PathVariable int id);

    @RequestMapping(value = "/account/increase", method = RequestMethod.POST)
    Resp increase(@RequestBody Account account);

    @RequestMapping(value = "/account/decrease", method = RequestMethod.POST)
    Resp decrease(@RequestBody Account account);
}
