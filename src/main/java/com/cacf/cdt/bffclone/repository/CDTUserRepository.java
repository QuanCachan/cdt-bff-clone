package com.cacf.cdt.bffclone.repository;

import com.cacf.cdt.bffclone.entity.cdt.user.CDTUser;
import com.cacf.cdt.bffclone.entity.cdt.user.CDTUserRole;
import com.cacf.cdt.bffclone.repository.vo.TupleVO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CDTUserRepository extends CrudRepository<CDTUser, String> {
    @Query("select distinct new com.cacf.cdt.bffclone.repository.vo.TupleVO(f.agencyRegion, f.agency) from IDDFile f")
    List<TupleVO<String, String>> findAgencies();

    @Query("select distinct a from CDTUser a join IDDFile f on f.advisor.number = a.number where a.role = :role and f.agency = :agency order by a.lastName, a.firstName")
    List<CDTUser> findAllByAgencyAndRole(@Param("agency") String agency, @Param("role") CDTUserRole role);

    default List<CDTUser> findAllAdvisorsByAgency(String agency) {
        return findAllByAgencyAndRole(agency, CDTUserRole.ADVISOR);
    }
}
