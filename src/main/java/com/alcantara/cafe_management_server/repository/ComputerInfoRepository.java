package com.alcantara.cafe_management_server.repository;

import com.alcantara.cafe_management_server.entity.ComputerInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerInfoRepository extends JpaRepository<ComputerInfo, Long> {

    ComputerInfo findByIpAddress(String ipAddress);

    List<ComputerInfo> findByIsDeletedFalse();
}
