package com.progresssoft.assignment.clustered_data_warehouse.repository;

import com.progresssoft.assignment.clustered_data_warehouse.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DealRepository extends JpaRepository<Deal, String> {

    boolean existsByDealUniqueId(String dealUniqueId);

}
