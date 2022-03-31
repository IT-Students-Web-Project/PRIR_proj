package com.polsl.prir_proj.repositories;

import com.polsl.prir_proj.models.File;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, String> {
}
