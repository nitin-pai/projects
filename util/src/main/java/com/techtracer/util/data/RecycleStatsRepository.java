package com.techtracer.util.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecycleStatsRepository extends CrudRepository<RecycleStats, Long> {
    
    RecycleStats findByAppServer(String appServer);

}
