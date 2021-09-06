package com.cacf.cdt.bffclone.repository;

import com.cacf.cdt.bffclone.entity.idd.IDDFile;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IDDFileRepository extends PagingAndSortingRepository<IDDFile, String>, JpaSpecificationExecutor<IDDFile> {
}
