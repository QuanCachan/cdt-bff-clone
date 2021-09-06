package com.cacf.cdt.bffclone;

import com.cacf.cdt.bffclone.entity.cdt.task.CDTTask;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskCategory;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskPriority;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskType;
import com.cacf.cdt.bffclone.entity.cdt.user.CDTUser;
import com.cacf.cdt.bffclone.entity.cdt.user.CDTUserRole;
import com.cacf.cdt.bffclone.entity.idd.CollectionWrapper;
import com.cacf.cdt.bffclone.entity.idd.IDDBorrower;
import com.cacf.cdt.bffclone.entity.idd.IDDDocument;
import com.cacf.cdt.bffclone.entity.idd.IDDEgdCode;
import com.cacf.cdt.bffclone.entity.idd.IDDFile;
import com.cacf.cdt.bffclone.repository.CDTUserRepository;
import com.cacf.cdt.bffclone.repository.IDDFileRepository;
import com.github.javafaker.Faker;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class CdtBffCloneApplication {

    private static final Faker FAKER = new Faker();
    private static final Integer NB_ADVISORS = 1000;
    private static final Integer NB_FILES = 1000;

    public static <T> T randomInList(List<T> list) {
        return list.get(FAKER.random().nextInt(0, list.size() - 1));
    }

    public static void main(String[] args) {
        val ctx = SpringApplication.run(CdtBffCloneApplication.class, args);
        val iddFileRepo = ctx.getBean(IDDFileRepository.class);
        val cdtUserRepo = ctx.getBean(CDTUserRepository.class);

        val nord = List.of("Nantes", "Roubaix", "Massy", "Roissy", "Paris", "Nancy", "Dijon", "Strasbourg", "Rouen");
        val sud = List.of("Méditéranée", "Lyon", "Grenoble", "Toulouse", "Bordeaux", "Bastia");

        Map<String, String> regionsByAgency = new HashMap<>();
        nord.forEach(city -> regionsByAgency.put(city, "A"));
        sud.forEach(city -> regionsByAgency.put(city, "B"));
        val agencies = List.copyOf(regionsByAgency.keySet());

        val advisors = IntStream.range(1, NB_ADVISORS)
                .mapToObj(n ->
                        CDTUser.builder()
                                .number(String.format("S%05d", n))
                                .role(CDTUserRole.ADVISOR)
                                .firstName(FAKER.name().firstName())
                                .lastName(FAKER.name().lastName())
                                .agency(randomInList(agencies))
                                .build()
                ).collect(Collectors.toList());
        cdtUserRepo.saveAll(advisors);

        val carolineGreen = CDTUser.builder()
                .number(String.format("S%05d", 0))
                .role(CDTUserRole.ADVISOR)
                .firstName("Caroline")
                .lastName("Green")
                .agency("Roubaix")
                .build();
        cdtUserRepo.save(carolineGreen);

        val productType = List.of("PB", "RAC", "CR");
        val egd = List.of(IDDEgdCode.values());
        val inputChannel = List.of("WEB", "TEL");
        val subscriptionModes = List.of("SE", "PAPER");
        val documentStatus = List.of("TO_VERIFY", "NON_COMPLIANT", "COMPLIANT");

        Supplier<IDDBorrower> borrower = () -> IDDBorrower.builder()
                .number(FAKER.number().digits(12))
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .email(FAKER.internet().emailAddress())
                .phoneNumber(FAKER.phoneNumber().phoneNumber())
                .attachments(CollectionWrapper.of(
                                IDDDocument.builder()
                                        .code("CNI")
                                        .status(randomInList(documentStatus))
                                        .build(),
                                IDDDocument.builder()
                                        .code("JDD")
                                        .status(randomInList(documentStatus))
                                        .build(),
                                IDDDocument.builder()
                                        .code("IMP")
                                        .status(randomInList(documentStatus))
                                        .build()
                        )
                )
                .build();

        iddFileRepo.saveAll(IntStream.rangeClosed(1, NB_FILES)
                .mapToObj(n -> {
                    val advisor = randomInList(advisors);
                    val iddFile = IDDFile.builder()
                            .number(String.format("S%011d", n))
                            .borrower(borrower.get())
                            .coBorrower(borrower.get())
                            .documents(
                                    CollectionWrapper.of(
                                            IDDDocument.builder()
                                                    .code("ELECTRONIC_CONTRACT")
                                                    .status(randomInList(documentStatus))
                                                    .build()
                                    )
                            )
                            .advisor(advisor)
                            .amount(BigDecimal.valueOf(FAKER.number().randomDouble(2, 1_000, 50_000)))
                            .agency(advisor.getAgency())
                            .agencyRegion(regionsByAgency.get(advisor.getAgency()))
                            .productType(randomInList(productType)) // PB, RAC, CR
                            .subProductType("Type de sous-produit")
                            .dlgNumber(FAKER.random().nextInt(1, 5)) // 1,2,3,4
                            .egdCode(randomInList(egd)) //
                            .ade(FAKER.random().nextBoolean())
                            .enteredDate(
                                    FAKER
                                            .date()
                                            .between(
                                                    Date.from(LocalDate.now().minusMonths(3).atTime(LocalTime.MIN).toInstant(ZoneOffset.UTC)),
                                                    Date.from(Instant.now())
                                            ).toInstant()
                                            .atZone(ZoneOffset.UTC.normalized())
                                            .toLocalDate()
                            )
                            .enteredBy(randomInList(advisors))
                            .supportedBy(randomInList(advisors))
                            .underStudyBy(randomInList(advisors))
                            .enteredInputChannel(randomInList(inputChannel))
                            .subscriptionMode(randomInList(subscriptionModes))
                            .build();
                    iddFile.setReceivedDate(LocalDateTime.now());

                    val task = CDTTask.builder()
                            .affectedTo(advisor)
                            .category(randomInList(List.of(CDTTaskCategory.values())))
                            .type(randomInList(List.of(CDTTaskType.values())))
                            .priority(randomInList(List.of(CDTTaskPriority.values())))
                            .file(iddFile)
                            .build();
                    iddFile.setTask(task);
                    return iddFile;
                }).collect(Collectors.toList())
        );
    }

}
