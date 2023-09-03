package com.tuumsolutions.bankaccount.domain.account.mapper;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AccountMapperUnitTest {
    private final AccountMapper accountMapper;

    AccountMapperUnitTest() {
        this.accountMapper = new AccountMapper();
    }

    @Test
    void givenHarmonizedSystemCnFromHarmonizedSystemCnReturnsHarmonizedSystemResult() {
        var givenHarmonizedSystem = getHarmonizedSystemCn();

        var actualHarmonizedSystemResult = cnHarmonizedSystemResultMapper.fromHarmonizedSystemCn(givenHarmonizedSystem);

        assertAll(
                () -> assertThat(actualHarmonizedSystemResult.getCode()).isEqualTo(givenHarmonizedSystem.getCode()),
                () -> assertThat(actualHarmonizedSystemResult.getId()).isEqualTo(givenHarmonizedSystem.getId()),
                () -> assertThat(actualHarmonizedSystemResult.getStartDate()).isEqualTo(givenHarmonizedSystem.getStartDate()),
                () -> assertThat(actualHarmonizedSystemResult.getDescription().getChapter()).isNull(),
                () -> assertThat(actualHarmonizedSystemResult.getDescription().getCommodity()).isNull(),
                () -> assertThat(actualHarmonizedSystemResult.getDescription().getHeading()).isNull(),
                () -> assertThat(actualHarmonizedSystemResult.getDescription().getSubheading()).isNull(),
                () -> assertThat(actualHarmonizedSystemResult.getDescription().getTaric())
                        .isEqualTo(givenHarmonizedSystem.getDescription())

        );
    }


    private HarmonizedSystemCn getHarmonizedSystemCn() {
        var harmonizedSystemCn = new HarmonizedSystemCn();
        harmonizedSystemCn.setDescription("cotton shirt");
        harmonizedSystemCn.setCode("6205202200");
        harmonizedSystemCn.setStartDate(LocalDate.now());
        harmonizedSystemCn.setEndDate(LocalDate.now().plusDays(1));
        harmonizedSystemCn.setId(111L);
        return harmonizedSystemCn;
    }
}
