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
import static org.hamcrest.Matchers.is;
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
class CreateTransactionControllerIntegrationTests {

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
        JSONObject request = new JSONObject();

        request.put("accountId", null);
        request.put("transactionType", null);
        request.put("amount", null);
        request.put("currency", null);
        request.put("description", null);

        MockHttpServletRequestBuilder mockMvcBuilder = post(URL)
                .content(request.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", aMapWithSize(2)))
                .andExpect(jsonPath("$.rows", hasSize(5)))
                .andExpect(jsonPath("$.type", is("ARGUMENT_NOT_VALID")))
                .andExpect(jsonPath("$.rows[0].field", is("accountId")))
                .andExpect(jsonPath("$.rows[0].reason", is("NotNull")))
                .andExpect(jsonPath("$.rows[0].message", is("must not be null")))

                .andExpect(jsonPath("$.rows[1].field", is("amount")))
                .andExpect(jsonPath("$.rows[1].reason", is("NotNull")))
                .andExpect(jsonPath("$.rows[1].message", is("must not be null")))

                .andExpect(jsonPath("$.rows[2].field", is("currency")))
                .andExpect(jsonPath("$.rows[2].reason", is("NotNull")))
                .andExpect(jsonPath("$.rows[2].message", is("must not be null")))

                .andExpect(jsonPath("$.rows[3].field", is("description")))
                .andExpect(jsonPath("$.rows[3].reason", is("NotBlank")))
                .andExpect(jsonPath("$.rows[3].message", is("must not be blank")))

                .andExpect(jsonPath("$.rows[4].field", is("transactionType")))
                .andExpect(jsonPath("$.rows[4].reason", is("NotNull")))
                .andExpect(jsonPath("$.rows[4].message", is("must not be null")));
    }

    @Test
    void onAmountLessOrEqualToZeroFieldReturnsBadRequest() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = post(URL)
                .content(createRequest(USER_ACCOUNT_ID, "0.0").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", aMapWithSize(2)))
                .andExpect(jsonPath("$.rows", hasSize(1)))
                .andExpect(jsonPath("$.type", is("ARGUMENT_NOT_VALID")))
                .andExpect(jsonPath("$.rows[0].field", is("amount")))
                .andExpect(jsonPath("$.rows[0].reason", is("DecimalMin")))
                .andExpect(jsonPath("$.rows[0].message", is("must be greater than 0.0")));
    }

    @Test
    void onInvalidAccountIdShouldReturnNotFound() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = post(URL)
                .content(createRequest(123L, "10.0").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.entity", is("UserAccount")))
                .andExpect(jsonPath("$.field", is("id")))
                .andExpect(jsonPath("$.value", is("123")))
                .andExpect(jsonPath("$.message", is("Account not found")));
    }

    @Test
    void onCorrectRequestShouldReturnResponse() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = post(URL)
                .content(createRequest(USER_ACCOUNT_ID, "10.10").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        String contentAsString = mockMvc
                .perform(mockMvcBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONObject expectedResponse = new JSONObject()
                .put("data", new JSONObject()
                        .put("accountId", USER_ACCOUNT_ID)
                        .put("transactionType", TransactionType.IN.toString())
                        .put("amount", 10.10)
                        .put("amountAfterTransaction", 11.1)
                        .put("currency", Currency.EUR.toString())
                        .put("description", "coffee"));

        JSONAssert.assertEquals(
                expectedResponse,
                new JSONObject(contentAsString),
                JSONCompareMode.LENIENT
        );
    }

    public static JSONObject createRequest(Long accountId, String amount) throws Exception {
        JSONObject request = new JSONObject();

        request.put("accountId", accountId);
        request.put("transactionType", "IN");
        request.put("amount", amount);
        request.put("currency", "EUR");
        request.put("description", "coffee");

        return request;
    }

}
