package com.tuumsolutions.bankaccount.domain.account.api.external;

import com.tuumsolutions.bankaccount.common.model.Response;
import com.tuumsolutions.bankaccount.domain.account.api.external.model.UserAccountRequest;
import com.tuumsolutions.bankaccount.domain.account.api.external.model.UserAccountResponse;
import com.tuumsolutions.bankaccount.domain.account.command.CreateUserAccountCommand;
import com.tuumsolutions.bankaccount.domain.account.command.GetUserAccountCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController("AccountController")
@RequestMapping(path = "/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final GetUserAccountCommand getUserAccountCommand;
    private final CreateUserAccountCommand createUserAccountCommand;

    @GetMapping("/{userAccountId}")
    public Response<UserAccountResponse> getUserAccount(@PathVariable(name = "userAccountId") Long userAccountId) {
        return new Response<>(toResponse(getUserAccountCommand.execute(userAccountId)));
    }

    @PostMapping
    public Response<UserAccountResponse> getUserAccount(@RequestBody @Valid UserAccountRequest request
    ) {
        return new Response<>(toResponse(createUserAccountCommand.execute(
                CreateUserAccountCommand.Parameters.builder()
                        .countryCode(request.getCountryCode())
                        .customerId(request.getCustomerId())
                        .currencies(request.getCurrencies())
                        .build())));
    }

    private UserAccountResponse toResponse(CreateUserAccountCommand.Output output) {
        return UserAccountResponse.builder()
                .accountId(output.getUserAccountId())
                .customerId(output.getCustomerId())
                .accounts(output.getAccounts().stream().map(
                        account -> UserAccountResponse.Account.builder()
                                .amount(account.getAmount())
                                .currency(account.getCurrency())
                                .build()).collect(Collectors.toList()))
                .build();
    }


    private UserAccountResponse toResponse(GetUserAccountCommand.Output output) {
        return UserAccountResponse.builder()
                .accountId(output.getUserAccountId())
                .customerId(output.getCustomerId())
                .accounts(output.getAccounts().stream().map(
                        account -> UserAccountResponse.Account.builder()
                                .amount(account.getAmount())
                                .currency(account.getCurrency())
                                .build()).collect(Collectors.toList()))
                .build();
    }


}
