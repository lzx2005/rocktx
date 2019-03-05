package com.lzx2005.rocktx.epa.dao;

import com.lzx2005.rocktx.epa.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hzlizx on 2019/3/5
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
