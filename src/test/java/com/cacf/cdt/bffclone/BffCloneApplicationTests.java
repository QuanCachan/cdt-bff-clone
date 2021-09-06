package com.cacf.cdt.bffclone;

import com.cacf.cdt.bffclone.entity.idd.IDDBorrower;
import com.cacf.cdt.bffclone.entity.idd.IDDFile;
import com.cacf.cdt.bffclone.repository.IDDFileRepository;
import com.cacf.cdt.bffclone.service.IDDFileService;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BffCloneApplicationTests {

    @Autowired
    IDDFileService iddFileService;
    @Autowired
    IDDFileRepository iddFileRepository;

    @BeforeEach
    void deleteAll() {
        iddFileRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        val number = "S0001";
        val iddFile = iddFileRepository.save(IDDFile.builder()
                .number(number)
                .receivedDate(LocalDateTime.now())
                .borrower(IDDBorrower.builder()
                        .firstName("Caroline")
                        .lastName("Green")
                        .build())
                .build());
        assertThat(iddFile.getId()).isNotNull();
        val found = iddFileService.findByNumber(number);
        assertThat(found.blockOptional()).isPresent();
    }

}
