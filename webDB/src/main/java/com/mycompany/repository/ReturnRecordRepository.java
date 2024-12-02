package com.mycompany.repository;

import com.mycompany.entity.ReturnRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnRecordRepository extends JpaRepository<ReturnRecord, Long> {}
