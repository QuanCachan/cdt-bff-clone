package com.cacf.cdt.bffclone.repository;

import com.cacf.cdt.bffclone.entity.idd.IDDFile;
import com.cacf.cdt.bffclone.entity.idd.IDDFile_;
import com.cacf.cdt.bffclone.repository.vo.DateRangeVO;
import com.cacf.cdt.bffclone.repository.vo.MinMaxVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IDDFileRepository extends PagingAndSortingRepository<IDDFile, String>, JpaSpecificationExecutor<IDDFile> {
    @Override
    @EntityGraph(attributePaths = {IDDFile_.ENTERED_BY})
    Page<IDDFile> findAll(Specification<IDDFile> spec, Pageable pageable);

    @EntityGraph(attributePaths = {IDDFile_.ADVISOR, IDDFile_.ENTERED_BY, IDDFile_.SUPPORTED_BY, IDDFile_.UNDER_STUDY_BY})
    Optional<IDDFile> findByNumber(String number);

    // FILTERS

    @Query("select distinct f.productType from IDDFile f order by f.productType asc")
    List<String> findAllProductTypes();

    @Query("select distinct f.enteredInputChannel from IDDFile f order by f.enteredInputChannel asc")
    List<String> findAllEnteredInputChannels();

    @Query("select distinct f.dlgNumber from IDDFile f order by f.dlgNumber asc")
    List<Integer> findAllDlgNumbers();

    @Query("select new com.cacf.cdt.bffclone.repository.vo.MinMaxVO(min(f.amount), max(f.amount)) from IDDFile f")
    MinMaxVO<BigDecimal> findMinMaxAmount();

    @Query("select new com.cacf.cdt.bffclone.repository.vo.DateRangeVO(min(f.enteredDate), max(f.enteredDate)) from IDDFile f")
    DateRangeVO<LocalDate> findEnteredDateRange();
}
