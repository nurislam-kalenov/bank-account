package com.tuumsolutions.bankaccount.domain.account.api.external;

import com.tuumsolutions.bankaccount.common.model.Response;
import com.tuumsolutions.bankaccount.domain.account.api.external.model.UserAccountRequest;
import com.tuumsolutions.bankaccount.domain.account.api.external.model.UserAccountResponse;
import com.tuumsolutions.bankaccount.domain.account.command.CreateUserAccountCommand;
import com.tuumsolutions.bankaccount.domain.account.command.GetUserAccountCommand;
import com.tuumsolutions.bankaccount.domain.account.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController("AccountController")
@RequestMapping(path = "/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final GetUserAccountCommand getUserAccountCommand;
    private final CreateUserAccountCommand createUserAccountCommand;
    private final AccountMapper accountMapper;

    @GetMapping("/{userAccountId}")
    public Response<UserAccountResponse> getUserAccount(@PathVariable(name = "userAccountId") Long userAccountId) {
        return new Response<>(accountMapper.toResponse(getUserAccountCommand.execute(userAccountId)));
    }

    @PostMapping
    public Response<UserAccountResponse> getUserAccount(@RequestBody @Valid UserAccountRequest request
    ) {
        return new Response<>(accountMapper.toResponse(
                createUserAccountCommand.execute(
                        accountMapper.toAccountCommandParameters(request))));
    }

}
