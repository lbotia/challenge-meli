package com.challengemeli.challengemeli.ip.repositories;

import com.challengemeli.challengemeli.ip.entity.IpInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IpInfoRepository  extends JpaRepository<IpInfoEntity, String> {

    @Query(nativeQuery = true, value = "SELECT SYSDATE()")
    String dateInDb();

}
