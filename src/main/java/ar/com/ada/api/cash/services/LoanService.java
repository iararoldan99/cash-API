package ar.com.ada.api.cash.services;

import ar.com.ada.api.cash.entities.*;
import ar.com.ada.api.cash.repos.LoanRepository;
import ar.com.ada.api.cash.services.base.GenericService;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LoanService extends GenericService<Loan> {

    private LoanRepository repo() {
        return (LoanRepository) repository;
    }

    public Loan createLoan(BigDecimal total, User user) {
        Loan loan = new Loan();
        loan.setTotal(total);
        loan.setUser(user);
        this.create(loan);
        return loan;
    }

    public List<Loan> listLoanByPage(Integer page, Integer size) {
        // 1 - offset 0
        // 2 - offset 1
        // 3 - offset 2
        // 4 - offset 3
        // 5 - offset 4
        // 6 - offset 5
        // 7 - offset 6
        // 8 - offset 7

        // paginacion de a 2
        // page 1, size 2 -> 1 y 2 => offset 0, count 2 ((page-1) * size) = 0
        // page 2, size 2 -> 3 y 4 => offset 2, count 2 ((page-1) * size) = 2
        // page 3, size 2 -> 5 y 6 => offset 4, count 2 ((page-1) * size) = 4
        // page 4, size 2 -> 7 y 8 => offset 6, count 2

        // Aca de paginacion de a 3
        // page 1, size 3 -> 1,2,3 => offset 0, count 3
        // page 2, size 3 -> 4,5,6 => offset 3, count 3
        // page 3, size 3 -> 7,8 => offset 6, count 3

        // La palabra clave LIMIT de se usa para limitar el número de filas devueltas de
        // un conjunto de resultados.
        // El valor OFF SET nos permite especificar qué fila comenzar a partir de la
        // recuperación de datos

        return this.repo().findAllByOffset((page - 1) * size, size);

    }

}
