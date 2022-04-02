package com.privado.repository;

import com.privado.entities.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group, String> {
  List<Group> findByBatchIdContaining(String batchId);
}
