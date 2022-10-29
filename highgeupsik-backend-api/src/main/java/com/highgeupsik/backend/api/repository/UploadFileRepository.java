package com.highgeupsik.backend.api.repository;

import com.highgeupsik.backend.core.model.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {

}
