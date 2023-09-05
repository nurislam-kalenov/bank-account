package com.tuumsolutions.bankaccount.domain.transaction.api.external;

import com.tuumsolutions.bankaccount.TestDatabaseHelper;
import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.account.AccountTestHelper;
import com.tuumsolutions.bankaccount.domain.transaction.TransactionTestHelper;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.util.List;

import static com.tuumsolutions.bankaccount.domain.account.AccountTestHelper.ACCOUNT_ID;
import static com.tuumsolutions.bankaccount.domain.account.AccountTestHelper.USER_ACCOUNT_ID;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GetTransactionControllerIntegrationTests {

    private static final String URL = "/v1/transaction";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountTestHelper accountTestHelper;

    @Autowired
    private TransactionTestHelper transactionTestHelper;

    @Autowired
    private TestDatabaseHelper testDatabaseHelper;

    @BeforeEach
    void setup() {
        testDatabaseHelper.cleanDatabase();
        accountTestHelper.insertUserAccount(accountTestHelper.createUserAccount(
                List.of(accountTestHelper.createAccount(ACCOUNT_ID, BigDecimal.ONE, Currency.EUR),
                        accountTestHelper.createAccount(10L, BigDecimal.TEN, Currency.USD),
                        accountTestHelper.createAccount(11L, BigDecimal.ZERO, Currency.GBP)
                )));
        transactionTestHelper.insert(transactionTestHelper.createTransaction(transactionTestHelper.TRANSACTION_ID_1, ACCOUNT_ID, TransactionType.IN));
        transactionTestHelper.insert(transactionTestHelper.createTransaction(transactionTestHelper.TRANSACTION_ID_2, ACCOUNT_ID, TransactionType.OUT));

    }

    @Test
    void onMissedUserAccountShouldBeReturnedEmptyList() throws Exception {
        var givenUserAccountId = 99L;
        MockHttpServletRequestBuilder mockMvcBuilder = get(String.format("%s/%s", URL, givenUserAccountId))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(1)))
                .andExpect(jsonPath("$.data", empty()));
    }


    @Test
    void onOnExistUserAccountIdShouldReturnList() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = get(String.format("%s/%s", URL, USER_ACCOUNT_ID))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(1)))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].transactionId", is(2)))
                .andExpect(jsonPath("$.data[0].transactionType", is(TransactionType.OUT.toString())))

                .andExpect(jsonPath("$.data[1].transactionId", is(1)))
                .andExpect(jsonPath("$.data[1].transactionType", is(TransactionType.IN.toString())))
        ;
    }

}
