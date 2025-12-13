package com.outsourcing.domain.activities.repository;

import com.outsourcing.common.entity.Activity;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
