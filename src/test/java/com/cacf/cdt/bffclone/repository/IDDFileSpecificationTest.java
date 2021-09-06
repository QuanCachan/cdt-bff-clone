package com.cacf.cdt.bffclone.repository;

import com.cacf.cdt.bffclone.dto.common.LocalDateRangeDTO;
import com.cacf.cdt.bffclone.dto.idd.filter.IDDFileFilterAmountRange;
import com.cacf.cdt.bffclone.dto.idd.filter.IDDFileFilterDTO;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTask;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskPriority;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskType;
import com.cacf.cdt.bffclone.entity.cdt.user.CDTUser;
import com.cacf.cdt.bffclone.entity.cdt.user.CDTUserRole;
import com.cacf.cdt.bffclone.entity.idd.IDDEgdCode;
import com.cacf.cdt.bffclone.entity.idd.IDDFile;
import com.cacf.cdt.bffclone.service.IDDFileSpecification;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("IDDFileSpecification tests using IDDFileFilterDTO")
@DataJpaTest
class IDDFileSpecificationTest {

    private final IDDFile.IDDFileBuilder iddFileBuilder = IDDFile.builder()
            .number("1")
            .receivedDate(LocalDateTime.now());
    @Autowired
    IDDFileRepository iddFileRepository;
    @Autowired
    CDTUserRepository cdtUserRepository;

    private List<IDDFile> findAll(IDDFileFilterDTO filter) {
        Specification<IDDFile> spec = new IDDFileSpecification(filter);
        Page<IDDFile> all = iddFileRepository.findAll(spec, Pageable.unpaged());
        return all.getContent();
    }

    @Test
    @DisplayName("Test with empty filter")
    void emptyFilter() {
        iddFileRepository.save(iddFileBuilder.build());
        IDDFileFilterDTO emptyFilter = new IDDFileFilterDTO();
        assertThat(findAll(emptyFilter)).isNotEmpty();
    }

    @Test
    @DisplayName("Filter by agencies")
    void agenciesFilter() {
        val agencies = List.of("agency A", "agency B", "agency C");
        val iddFiles = new ArrayList<IDDFile>();
        for (int i = 1; i <= agencies.size(); i++) {
            val agency = agencies.get(i - 1);
            for (int j = 1; j <= i; j++) {
                iddFiles.add(iddFileBuilder.number(String.format("%d%d", i, j)).agency(agency).build());
            }
        }
        // iddFiles = 1 agency A, 2 agency B, 3 agency C
        iddFileRepository.saveAll(iddFiles);

        val agenciesFilter = new HashSet<String>();
        val filter = IDDFileFilterDTO.builder().agencies(agenciesFilter).build();
        assertThat(findAll(filter)).hasSameSizeAs(iddFiles);
        agenciesFilter.add("unknown agency");
        assertThat(findAll(filter)).isEmpty();
        agenciesFilter.add("agency A");
        assertThat(findAll(filter)).hasSize(1)
                .extracting(IDDFile::getAgency)
                .allMatch(agenciesFilter::contains);
        agenciesFilter.add("agency B");
        assertThat(findAll(filter)).hasSize(3)
                .extracting(IDDFile::getAgency)
                .allMatch(agenciesFilter::contains);
        agenciesFilter.add("agency C");
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFiles);
    }

    @Test
    @DisplayName("Filter by advisor numbers")
    void advisorNumbersFilter() {
        val advisorNumbers = List.of("advisor A", "advisor B", "advisor C");
        val iddFiles = new ArrayList<IDDFile>();
        for (int i = 1; i <= advisorNumbers.size(); i++) {
            val advisorNumber = advisorNumbers.get(i - 1);
            val advisor = cdtUserRepository.save(
                    CDTUser.builder()
                            .number(advisorNumber).firstName(advisorNumber).lastName(advisorNumber)
                            .role(CDTUserRole.ADVISOR)
                            .build()
            );
            for (int j = 1; j <= i; j++) {
                iddFiles.add(iddFileBuilder.number(String.format("%d%d", i, j)).advisor(advisor).build());
            }
        }
        // iddFiles = 1 advisor A, 2 advisor B, 3 advisor C
        iddFileRepository.saveAll(iddFiles);

        val advisorsFilter = new HashSet<String>();
        val filter = IDDFileFilterDTO.builder().advisors(advisorsFilter).build();
        assertThat(findAll(filter)).hasSameSizeAs(iddFiles);
        advisorsFilter.add("unknown advisor");
        assertThat(findAll(filter)).isEmpty();
        advisorsFilter.add("advisor A");
        assertThat(findAll(filter)).hasSize(1)
                .extracting(IDDFile::getAdvisor).extracting(CDTUser::getNumber)
                .allMatch(advisorsFilter::contains);
        advisorsFilter.add("advisor B");
        assertThat(findAll(filter)).hasSize(3)
                .extracting(IDDFile::getAdvisor).extracting(CDTUser::getNumber)
                .allMatch(advisorsFilter::contains);
        advisorsFilter.add("advisor C");
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFiles);
    }

    @Test
    @DisplayName("Filter by product types")
    void productTypesFilter() {
        val productTypes = List.of("product A", "product B", "product C");
        val iddFiles = new ArrayList<IDDFile>();
        for (int i = 1; i <= productTypes.size(); i++) {
            val productType = productTypes.get(i - 1);
            for (int j = 1; j <= i; j++) {
                iddFiles.add(iddFileBuilder.number(String.format("%d%d", i, j)).productType(productType).build());
            }
        }
        // iddFiles = 1 productType A, 2 productType B, 3 productType C
        iddFileRepository.saveAll(iddFiles);

        val productTypesFilter = new HashSet<String>();
        val filter = IDDFileFilterDTO.builder().productTypes(productTypesFilter).build();
        assertThat(findAll(filter)).hasSameSizeAs(iddFiles);
        productTypesFilter.add("unknown product");
        assertThat(findAll(filter)).isEmpty();
        productTypesFilter.add("product A");
        assertThat(findAll(filter)).hasSize(1)
                .extracting(IDDFile::getProductType)
                .allMatch(productTypesFilter::contains);
        productTypesFilter.add("product B");
        assertThat(findAll(filter)).hasSize(3)
                .extracting(IDDFile::getProductType)
                .allMatch(productTypesFilter::contains);
        productTypesFilter.add("product C");
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFiles);
    }

    @Test
    @DisplayName("Filter by entered date")
    void enteredDateFilter() {
        AtomicInteger count = new AtomicInteger();
        BiFunction<LocalDate, LocalDate, List<IDDFile>> iddFilesWithDates = (from, to) -> IntStream.rangeClosed(1, Period.between(from, to).getDays())
                .mapToObj(from::plusDays)
                .map(iddFileBuilder::enteredDate)
                .map(builder -> builder.number(String.valueOf(count.getAndIncrement())))
                .map(IDDFile.IDDFileBuilder::build)
                .collect(Collectors.toList());

        val now = LocalDate.now();
        val iddFilesLower = iddFileRepository.saveAll(iddFilesWithDates.apply(now.minusDays(1), now.minusDays(10)));
        val iddFilesWithin = iddFileRepository.saveAll(iddFilesWithDates.apply(now, now.plusDays(10)));
        val iddFilesGreater = iddFileRepository.saveAll(iddFilesWithDates.apply(now.plusDays(11), now.plusDays(20)));
        val all = iddFileRepository.findAll();

        val range = new LocalDateRangeDTO();
        val filter = IDDFileFilterDTO.builder().enteredDate(range).build();

        range.setFrom(null);
        range.setTo(null);
        assertThat(findAll(filter)).containsExactlyElementsOf(all);
        range.setFrom(now.minusDays(10));
        range.setTo(null);
        assertThat(findAll(filter)).containsExactlyElementsOf(all);
        range.setFrom(null);
        range.setTo(now.plusDays(20));
        assertThat(findAll(filter)).containsExactlyElementsOf(all);
        range.setFrom(now.minusDays(10));
        range.setTo(now.plusDays(20));
        assertThat(findAll(filter)).containsExactlyElementsOf(all);

        range.setFrom(now.minusDays(10));
        range.setTo(now.minusDays(1));
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFilesLower);
        range.setFrom(null);
        range.setTo(now.minusDays(1));
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFilesLower);

        range.setFrom(now);
        range.setTo(now.plusDays(10));
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFilesWithin);

        range.setFrom(now.plusDays(11));
        range.setTo(now.plusDays(20));
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFilesGreater);
        range.setFrom(now.plusDays(11));
        range.setTo(null);
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFilesGreater);

        range.setFrom(null);
        range.setTo(now.minusDays(10));
        assertThat(findAll(filter)).isEmpty();
        range.setFrom(now.plusDays(21));
        range.setTo(null);
        assertThat(findAll(filter)).isEmpty();
    }

    @Test
    @DisplayName("Filter by amount min and max")
    void amountFilter() {
        BiFunction<Integer, Integer, List<IDDFile>> iddFilesWithAmount = (from, to) -> IntStream.rangeClosed(from, to)
                .mapToObj(BigDecimal::valueOf)
                .map(amount -> iddFileBuilder.number(String.valueOf(amount)).amount(amount).build())
                .collect(Collectors.toList());

        val iddFilesLower = iddFileRepository.saveAll(iddFilesWithAmount.apply(1, 9));
        val iddFilesWithin = iddFileRepository.saveAll(iddFilesWithAmount.apply(10, 20));
        val iddFilesGreater = iddFileRepository.saveAll(iddFilesWithAmount.apply(21, 30));
        val all = iddFileRepository.findAll();

        val minMax = new IDDFileFilterAmountRange();
        val filter = IDDFileFilterDTO.builder().amount(minMax).build();

        minMax.setMin(null);
        minMax.setMax(null);
        assertThat(findAll(filter)).containsExactlyElementsOf(all);
        minMax.setMin(BigDecimal.ZERO);
        minMax.setMax(null);
        assertThat(findAll(filter)).containsExactlyElementsOf(all);
        minMax.setMin(null);
        minMax.setMax(BigDecimal.valueOf(30));
        assertThat(findAll(filter)).containsExactlyElementsOf(all);
        minMax.setMin(BigDecimal.ZERO);
        minMax.setMax(BigDecimal.valueOf(30));
        assertThat(findAll(filter)).containsExactlyElementsOf(all);

        minMax.setMin(BigDecimal.ZERO);
        minMax.setMax(BigDecimal.valueOf(9));
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFilesLower);
        minMax.setMin(null);
        minMax.setMax(BigDecimal.valueOf(9));
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFilesLower);

        minMax.setMin(BigDecimal.valueOf(10));
        minMax.setMax(BigDecimal.valueOf(20));
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFilesWithin);

        minMax.setMin(BigDecimal.valueOf(21));
        minMax.setMax(BigDecimal.valueOf(30));
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFilesGreater);
        minMax.setMin(BigDecimal.valueOf(21));
        minMax.setMax(null);
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFilesGreater);

        minMax.setMin(null);
        minMax.setMax(BigDecimal.ZERO);
        assertThat(findAll(filter)).isEmpty();
        minMax.setMin(BigDecimal.valueOf(31));
        minMax.setMax(null);
        assertThat(findAll(filter)).isEmpty();
    }

    @Test
    @DisplayName("Filter by subscription mode")
    void subscriptionModesFilter() {
        val subscriptionModes = List.of("mode A", "mode B", "mode C");
        val iddFiles = new ArrayList<IDDFile>();
        for (int i = 1; i <= subscriptionModes.size(); i++) {
            val subscriptionMode = subscriptionModes.get(i - 1);
            for (int j = 1; j <= i; j++) {
                iddFiles.add(iddFileBuilder.number(String.format("%d%d", i, j)).subscriptionMode(subscriptionMode).build());
            }
        }
        // iddFiles = 1 mode A, 2 mode B, 3 mode C
        iddFileRepository.saveAll(iddFiles);

        val subscriptionModesFilter = new HashSet<String>();
        val filter = IDDFileFilterDTO.builder().subscriptionModes(subscriptionModesFilter).build();
        assertThat(findAll(filter)).hasSameSizeAs(iddFiles);
        subscriptionModesFilter.add("unknown mode");
        assertThat(findAll(filter)).isEmpty();
        subscriptionModesFilter.add("mode A");
        assertThat(findAll(filter)).hasSize(1)
                .extracting(IDDFile::getSubscriptionMode)
                .allMatch(subscriptionModesFilter::contains);
        subscriptionModesFilter.add("mode B");
        assertThat(findAll(filter)).hasSize(3)
                .extracting(IDDFile::getSubscriptionMode)
                .allMatch(subscriptionModesFilter::contains);
        subscriptionModesFilter.add("mode C");
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFiles);
    }

    @Test
    @DisplayName("Filter by EGD codes")
    void egdCodesFilter() {
        val egdCodes = Arrays.stream(IDDEgdCode.values()).limit(3).collect(Collectors.toList());
        val iddFiles = new ArrayList<IDDFile>();
        for (int i = 1; i <= egdCodes.size(); i++) {
            val egdCode = egdCodes.get(i - 1);
            for (int j = 1; j <= i; j++) {
                iddFiles.add(iddFileBuilder.number(String.format("%d%d", i, j)).egdCode(egdCode).build());
            }
        }
        iddFileRepository.saveAll(iddFiles);

        val egdCodesFilter = new HashSet<String>();
        val filter = IDDFileFilterDTO.builder().egdCodes(egdCodesFilter).build();
        assertThat(findAll(filter)).hasSameSizeAs(iddFiles);
        egdCodesFilter.add("unknown code");
        assertThat(findAll(filter)).isEmpty();
        egdCodesFilter.add(egdCodes.get(0).name());
        assertThat(findAll(filter)).hasSize(1)
                .extracting(IDDFile::getEgdCode).map(Objects::toString)
                .allMatch(egdCodesFilter::contains);
        egdCodesFilter.add(egdCodes.get(1).name());
        assertThat(findAll(filter)).hasSize(3)
                .extracting(IDDFile::getEgdCode).map(Objects::toString)
                .allMatch(egdCodesFilter::contains);
        egdCodesFilter.add(egdCodes.get(2).name());
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFiles);
    }

    @Test
    @DisplayName("Filter by DLG numbers")
    void dlgNumbersFilter() {
        val dlgNumbers = IntStream.rangeClosed(1, 3).boxed().collect(Collectors.toList());
        val iddFiles = new ArrayList<IDDFile>();
        for (int i = 1; i <= dlgNumbers.size(); i++) {
            val dlgNumber = dlgNumbers.get(i - 1);
            for (int j = 1; j <= i; j++) {
                iddFiles.add(iddFileBuilder.number(String.format("%d%d", i, j)).dlgNumber(dlgNumber).build());
            }
        }
        iddFileRepository.saveAll(iddFiles);

        val dlgNumbersFilter = new HashSet<Integer>();
        val filter = IDDFileFilterDTO.builder().dlgNumbers(dlgNumbersFilter).build();
        assertThat(findAll(filter)).hasSameSizeAs(iddFiles);
        dlgNumbersFilter.add(0);
        assertThat(findAll(filter)).isEmpty();
        dlgNumbersFilter.add(1);
        assertThat(findAll(filter)).hasSize(1)
                .extracting(IDDFile::getDlgNumber)
                .allMatch(dlgNumbersFilter::contains);
        dlgNumbersFilter.add(2);
        assertThat(findAll(filter)).hasSize(3)
                .extracting(IDDFile::getDlgNumber)
                .allMatch(dlgNumbersFilter::contains);
        dlgNumbersFilter.add(3);
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFiles);
    }

    @Test
    @DisplayName("Filter by entered by advisor numbers")
    void enteredByAdvisorNumbersFilter() {
        val advisorNumbers = List.of("advisor A", "advisor B", "advisor C");
        val iddFiles = new ArrayList<IDDFile>();
        for (int i = 1; i <= advisorNumbers.size(); i++) {
            val advisorNumber = advisorNumbers.get(i - 1);
            val advisor = cdtUserRepository.save(
                    CDTUser.builder()
                            .number(advisorNumber).firstName(advisorNumber).lastName(advisorNumber)
                            .role(CDTUserRole.ADVISOR)
                            .build()
            );
            for (int j = 1; j <= i; j++) {
                iddFiles.add(iddFileBuilder.number(String.format("%d%d", i, j)).enteredBy(advisor).build());
            }
        }
        // iddFiles = 1 advisor A, 2 advisor B, 3 advisor C
        iddFileRepository.saveAll(iddFiles);

        val enteredByAdvisorNumbersFilter = new HashSet<String>();
        val filter = IDDFileFilterDTO.builder().enteredBy(enteredByAdvisorNumbersFilter).build();
        assertThat(findAll(filter)).hasSameSizeAs(iddFiles);
        enteredByAdvisorNumbersFilter.add("unknown advisor");
        assertThat(findAll(filter)).isEmpty();
        enteredByAdvisorNumbersFilter.add("advisor A");
        assertThat(findAll(filter)).hasSize(1)
                .extracting(IDDFile::getEnteredBy).extracting(CDTUser::getNumber)
                .allMatch(enteredByAdvisorNumbersFilter::contains);
        enteredByAdvisorNumbersFilter.add("advisor B");
        assertThat(findAll(filter)).hasSize(3)
                .extracting(IDDFile::getEnteredBy).extracting(CDTUser::getNumber)
                .allMatch(enteredByAdvisorNumbersFilter::contains);
        enteredByAdvisorNumbersFilter.add("advisor C");
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFiles);
    }

    @Test
    @DisplayName("Filter by task priority")
    void taskPrioritiesFilter() {
        val taskPriorities = List.of(CDTTaskPriority.values());
        val iddFiles = new ArrayList<IDDFile>();
        for (int i = 1; i <= taskPriorities.size(); i++) {
            val taskPriority = taskPriorities.get(i - 1);
            for (int j = 1; j <= i; j++) {
                val task = CDTTask.builder()
                        .priority(taskPriority)
                        .build();
                val iddFile = iddFileBuilder.number(String.format("%d%d", i, j)).task(task).build();
                task.setFile(iddFile);
                iddFiles.add(iddFile);
            }
        }
        iddFileRepository.saveAll(iddFiles);

        val taskPrioritiesFilter = new HashSet<String>();
        val filter = IDDFileFilterDTO.builder().taskPriorities(taskPrioritiesFilter).build();
        assertThat(findAll(filter)).hasSameSizeAs(iddFiles);
        taskPrioritiesFilter.add("unknown priority");
        assertThat(findAll(filter)).isEmpty();
        taskPrioritiesFilter.add(taskPriorities.get(0).name());
        assertThat(findAll(filter)).hasSize(1)
                .extracting(IDDFile::getTask).extracting(CDTTask::getPriority)
                .map(Objects::toString)
                .allMatch(taskPrioritiesFilter::contains);
        taskPrioritiesFilter.add(taskPriorities.get(1).name());
        assertThat(findAll(filter)).hasSize(3)
                .extracting(IDDFile::getTask).extracting(CDTTask::getPriority)
                .map(Objects::toString)
                .allMatch(taskPrioritiesFilter::contains);
        taskPrioritiesFilter.add(taskPriorities.get(2).name());
        assertThat(findAll(filter)).hasSize(6)
                .extracting(IDDFile::getTask).extracting(CDTTask::getPriority)
                .map(Objects::toString)
                .allMatch(taskPrioritiesFilter::contains);
        taskPrioritiesFilter.add(taskPriorities.get(3).name());
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFiles);
    }

    @Test
    @DisplayName("Filter by task type")
    void taskTypesFilter() {
        val taskTypes = List.of(CDTTaskType.values());
        val iddFiles = new ArrayList<IDDFile>();
        for (int i = 1; i <= taskTypes.size(); i++) {
            val taskType = taskTypes.get(i - 1);
            for (int j = 1; j <= i; j++) {
                val task = CDTTask.builder()
                        .type(taskType)
                        .build();
                val iddFile = iddFileBuilder.number(String.format("%d%d", i, j)).task(task).build();
                task.setFile(iddFile);
                iddFiles.add(iddFile);
            }
        }
        iddFileRepository.saveAll(iddFiles);

        val taskTypesFilter = new HashSet<String>();
        val filter = IDDFileFilterDTO.builder().taskTypes(taskTypesFilter).build();
        assertThat(findAll(filter)).hasSameSizeAs(iddFiles);
        taskTypesFilter.add("unknown type");
        assertThat(findAll(filter)).isEmpty();
        taskTypesFilter.add(taskTypes.get(0).name());
        assertThat(findAll(filter)).hasSize(1)
                .extracting(IDDFile::getTask).extracting(CDTTask::getType)
                .map(Objects::toString)
                .allMatch(taskTypesFilter::contains);
        taskTypesFilter.add(taskTypes.get(1).name());
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFiles);
    }

    @Test
    @DisplayName("Filter by entered by task affected by advisor numbers")
    void taskAffectedToAdvisorNumbersFilter() {
        val advisorNumbers = List.of("advisor A", "advisor B", "advisor C");
        val iddFiles = new ArrayList<IDDFile>();
        for (int i = 1; i <= advisorNumbers.size(); i++) {
            val advisorNumber = advisorNumbers.get(i - 1);
            val advisor = cdtUserRepository.save(
                    CDTUser.builder()
                            .number(advisorNumber).firstName(advisorNumber).lastName(advisorNumber)
                            .role(CDTUserRole.ADVISOR)
                            .build()
            );
            for (int j = 1; j <= i; j++) {
                val task = CDTTask.builder().affectedTo(advisor).build();
                val iddFile = iddFileBuilder.number(String.format("%d%d", i, j)).task(task).build();
                task.setFile(iddFile);
                iddFiles.add(iddFile);
            }
        }
        // iddFiles = 1 advisor A, 2 advisor B, 3 advisor C
        iddFileRepository.saveAll(iddFiles);

        val taskAffectedToAdvisorNumbersFilter = new HashSet<String>();
        val filter = IDDFileFilterDTO.builder().taskAffectedTo(taskAffectedToAdvisorNumbersFilter).build();
        assertThat(findAll(filter)).hasSameSizeAs(iddFiles);
        taskAffectedToAdvisorNumbersFilter.add("unknown advisor");
        assertThat(findAll(filter)).isEmpty();
        taskAffectedToAdvisorNumbersFilter.add("advisor A");
        assertThat(findAll(filter)).hasSize(1)
                .extracting(IDDFile::getTask).extracting(CDTTask::getAffectedTo).extracting(CDTUser::getNumber)
                .allMatch(taskAffectedToAdvisorNumbersFilter::contains);
        taskAffectedToAdvisorNumbersFilter.add("advisor B");
        assertThat(findAll(filter)).hasSize(3)
                .extracting(IDDFile::getTask).extracting(CDTTask::getAffectedTo).extracting(CDTUser::getNumber)
                .allMatch(taskAffectedToAdvisorNumbersFilter::contains);
        taskAffectedToAdvisorNumbersFilter.add("advisor C");
        assertThat(findAll(filter)).containsExactlyElementsOf(iddFiles);
    }
}
