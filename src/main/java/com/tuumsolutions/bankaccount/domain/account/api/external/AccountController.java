package com.tuumsolutions.bankaccount.domain.account.api.external;

import com.tuumsolutions.bankaccount.domain.account.api.external.model.AccountRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController("AccountController")
@RequestMapping(path = "/v1/account")
@RequiredArgsConstructor
public class AccountController {

    @GetMapping
    public AccountRequest calculate() {

        return new AccountRequest("ewf");
    }
}
