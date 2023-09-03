package com.tuumsolutions.bankaccount.domain.transaction.api.external;

import com.tuumsolutions.bankaccount.common.model.Response;
import com.tuumsolutions.bankaccount.domain.transaction.api.external.model.TransactionRequest;
import com.tuumsolutions.bankaccount.domain.transaction.api.external.model.TransactionResponse;
import com.tuumsolutions.bankaccount.domain.transaction.command.CreateTransactionCommand;
import com.tuumsolutions.bankaccount.domain.transaction.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController("TransactionController")
@RequestMapping(path = "/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final CreateTransactionCommand createTransactionCommand;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public Response<TransactionResponse> createTransaction(
            @RequestBody @Valid TransactionRequest request
    ) {
        return new Response<>(transactionMapper.toResponse(
                createTransactionCommand.execute(
                        transactionMapper.toParams(request))));

    }

}
