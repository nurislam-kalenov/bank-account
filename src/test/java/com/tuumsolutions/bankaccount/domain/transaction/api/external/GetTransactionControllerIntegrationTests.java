package com.tuumsolutions.bankaccount.domain.transaction.api.external;

import com.tuumsolutions.bankaccount.TestDatabaseHelper;
import com.tuumsolutions.bankaccount.common.model.Currency;
import com.tuumsolutions.bankaccount.domain.account.AccountTestHelper;
import com.tuumsolutions.bankaccount.domain.transaction.TransactionTestHelper;
import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionType;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;

import static com.tuumsolutions.bankaccount.domain.account.AccountTestHelper.USER_ACCOUNT_ID;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        accountTestHelper.insertUserAccount(accountTestHelper.createUserAccount(BigDecimal.ONE, Currency.EUR));
    }

    @Test
    void onUndefinedFieldReturnsBadRequest() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = get(String.format("%s/%s", URL,99L))
                .accept(MediaType.APPLICATION_JSON);
    }


}
