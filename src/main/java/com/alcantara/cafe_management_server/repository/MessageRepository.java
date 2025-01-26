package com.alcantara.cafe_management_server.repository;

import java.util.List;

import com.alcantara.cafe_management_server.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByComputerInfoIpAddressOrderByCreatedOnDesc(String ipAddress);

    Message findTopByComputerInfoIpAddressOrderByCreatedOnDesc(String ipAddress);
}
