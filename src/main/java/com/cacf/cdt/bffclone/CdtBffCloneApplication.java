package com.cacf.cdt.bffclone;

import com.cacf.cdt.bffclone.entity.cdt.user.CDTUser;
import com.cacf.cdt.bffclone.entity.cdt.user.CDTUserRole;
import com.cacf.cdt.bffclone.repository.CDTUserRepository;
import com.cacf.cdt.bffclone.repository.IDDFileRepository;
import com.github.javafaker.Faker;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                )
                .collect(Collectors.toUnmodifiableList());
    }

}
