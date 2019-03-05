package com.lzx2005.rocktx.epa.dao;

import com.lzx2005.rocktx.epa.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hzlizx on 2019/3/5
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Transactional
    @Modifying
    @Query("update Account a set a.amount = a.amount + :amount where a.id = :id")
    public void increase(@Param("id") Integer id, @Param("amount") int amount);

    @Transactional
    @Modifying
    @Query("update Account a set a.amount = a.amount - :amount where a.id = :id")
    public void decrease(@Param("id") Integer id, @Param("amount") int amount);

}
