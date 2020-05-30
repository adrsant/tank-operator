package com.devtools.repository;

import com.devtools.entity.QueueAws;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface QueueAwsRepository extends CrudRepository<QueueAws, UUID> {

  Page<QueueAws> findByNameContaining(String name, Pageable pageable);
}
