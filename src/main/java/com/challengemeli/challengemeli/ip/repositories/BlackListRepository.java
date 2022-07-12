package com.challengemeli.challengemeli.ip.repositories;

import com.challengemeli.challengemeli.ip.entity.BlackListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRepository extends JpaRepository<BlackListEntity, String> {
}
