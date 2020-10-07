package ar.com.ada.api.cash.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;

import ar.com.ada.api.cash.entities.*;
import ar.com.ada.api.cash.models.request.LoanRequest;
import ar.com.ada.api.cash.models.response.GenericResponse;
import ar.com.ada.api.cash.models.response.InfoLoans;
import ar.com.ada.api.cash.services.*;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    UserService userService;

    // /loans
    @GetMapping("")
    public ResponseEntity<InfoLoans> getLoans(@RequestParam(value = "page", required = true) Integer page,
            @RequestParam(value = "size", required = true) Integer size,
            @RequestParam(value = "user_id", required = false) Integer userId) {

        InfoLoans info = new InfoLoans();

        List<Loan> loans = new ArrayList<>();
        if (userId == null) {
            loans = loanService.listLoanByPageV2(page, size);
            info.paging.total = loanService.count();
        } else {
            loans = loanService.listLoanByPage(userId, page, size);
            info.paging.total = loanService.countByUserId(userId);
        }
        /*
         * esta es con la version MAP info.items = loans.stream().map(lo -> { ItemLoan
         * iL = new ItemLoan(); iL.id = lo.getId(); iL.total = lo.getTotal(); iL.userId
         * = lo.getUser().getId(); return iL; }).collect(Collectors.toList());
         */

        info.items = loans;
        info.paging.page = page;
        info.paging.size = size;

        return ResponseEntity.ok(info);

    }

    @PostMapping("")
    public ResponseEntity<GenericResponse> createLoan(@RequestBody LoanRequest loanReq) {

        Loan loan = loanService.createLoan(loanReq.amount, userService.findById(loanReq.userId));

        if (loan == null)
            return ResponseEntity.badRequest().build();
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.message = "Loan created";
        gR.id = loan.getId();

        return ResponseEntity.ok(gR);
    }

}