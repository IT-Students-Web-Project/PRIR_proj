package com.polsl.prir_proj.repositories;

import com.polsl.prir_proj.models.File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<File, String> {
}
