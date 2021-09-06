package com.cacf.cdt.bffclone.repository;

import com.cacf.cdt.bffclone.entity.cdt.user.CDTUser;
import org.springframework.data.repository.CrudRepository;

public interface CDTUserRepository extends CrudRepository<CDTUser, String> {
}
