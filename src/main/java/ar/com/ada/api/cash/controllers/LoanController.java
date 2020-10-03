package ar.com.ada.api.cash.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.com.ada.api.cash.entities.*;
import ar.com.ada.api.cash.models.request.LoanRequest;
import ar.com.ada.api.cash.models.response.GenericResponse;
import ar.com.ada.api.cash.services.*;
import jdk.javadoc.doclet.Reporter;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    LoanService loanService;

    // /loans
    @GetMapping("/")
    public ResponseEntity<InfoLoans> getLoans(@RequestParam(value = "page", required = true) Integer page,
            @RequestParam(value = "size", required = true) Integer size,
            @RequestParam(value = "user_id", required = false) Integer userId) {

        List<Loan> loans = loanService.listLoanByPage(page, size);
        infoLoans.items = loans;
        return ResponseEntity.ok(loans);

    }

    @PostMapping("/")
    public ResponseEntity<GenericResponse> createLoan(@RequestBody LoanRequest loanReq) {

        Loan loan = loanService.create(loanReq.);

        if(loan == null)
            return ResponseEntity.badRequest().build();
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.message = "Loan created";
        gR.id = loan.getId();

        return ResponseEntity.ok(gR);
    }