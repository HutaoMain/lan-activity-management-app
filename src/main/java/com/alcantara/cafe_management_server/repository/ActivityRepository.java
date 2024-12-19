package com.alcantara.cafe_management_server.repository;

import com.alcantara.cafe_management_server.entity.Activity;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByComputerInfoId(Long computerInfoId, Sort sort);
}
