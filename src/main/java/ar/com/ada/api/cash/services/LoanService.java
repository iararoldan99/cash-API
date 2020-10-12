package ar.com.ada.api.cash.services;

import ar.com.ada.api.cash.entities.*;
import ar.com.ada.api.cash.entities.reports.LoanFrankReport;
import ar.com.ada.api.cash.repos.LoanRepository;
import ar.com.ada.api.cash.services.base.GenericService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LoanService extends GenericService<Loan> {

    @Autowired
    SessionFactory sessionFactory;

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
        // SELECT * from loans
        // #Fila ID Loan e Importe
        // 1 L1 100 - offset 0 (offset desplazamiento)
        // 2 L2 20 - offset 1
        // 3 L3 150 - offset 2
        // 4 L5 200 - offset 3
        // 5 L6 158 - offset 4
        // 6 L7 300 - offset 5
        // 7 L8 400 - offset 6
        // 8 L9 200 - offset 7

        // paginacion de a 2
        // SELECT * FROM loans LIMIT 0,2 -> offSet 0, 2 registros
        // page 1, size 2 -> 1 y 2 => offset 0, count 2 ((page-1) * size) = 0
        // page 2, size 2 -> 3 y 4 => offset 2, count 2 ((page-1) * size) = 2
        // page 3, size 2 -> 5 y 6 => offset 4, count 2 ((page-1) * size) = 4
        // page 4, size 2 -> 7 y 8 => offset 6, count 2

        // Aca de paginacion de a 3
        // SELECT * FROM loans LIMIT 0,3 -> offSet 0, 3 registros
        // page 1, size 3 -> 1,2,3 => offset 0, count 3
        // page 2, size 3 -> 4,5,6 => offset 3, count 3
        // page 3, size 3 -> 7,8 => offset 6, count 3

        // SELECT * FROM LOAN LIMIT (page-1)*size, size

        // La palabra clave LIMIT de se usa para limitar el número de filas devueltas de
        // un conjunto de resultados.
        // El valor OFF SET nos permite especificar qué fila comenzar a partir de la
        // recuperación de datos

        return this.repo().findAllByOffset((page - 1) * size, size);

    }

    public List<Loan> listLoanByPage(Integer userId, Integer page, Integer size) {

        return this.repo().findAllByOffsetByUser(userId, (page - 1) * size, size);

    }

    public List<Loan> listLoanByPageV2(Integer page, Integer size) {

        Pageable paginacion = PageRequest.of(page - 1, size);
        return this.repo().findAll(paginacion).toList();

    }

    public long countByUserId(Integer userId) {
        return this.repo().countByUserId(userId);
    }

    /**
     * Con este metodo ejecuto el SP, recorro los items(LoanFrankReport) y lo
     * transformamos a un LOAN.
     * 
     * @return
     */

    public List<Loan> getAllUsingSP() {

        // ATENCION: ESTE CODIGO NO ESTA PROBADO, hacer un controller en tal caso.

        List<Loan> resultado = new ArrayList<>();
        List<LoanFrankReport> resultadoSP = new ArrayList<>();

        resultado = resultadoSP.stream().map(r -> {
            Loan l = new Loan();
            l.setId(r.getLoan_id());
            l.setTotal(r.getTotal());
            User user = new User();
            user.setId(r.getUser_id());
            user.setEmail(r.getEmail());
            user.setFirstName(r.getFirst_name());
            user.setLastName(r.getLast_name());
            user.addLoan(l);
            return l;
        }).collect(Collectors.toList());

        return resultado;

    }

    public List<Loan> getAllUsingSPVersionAntigua() {

        // ATENCION!!!: PSEUDOCODIGO: No necesariamente es un codigo funcional

        Session s = sessionFactory.openSession();
        Query q = s.createQuery("CALL GET_ALL_LOANS()");

        // Recorrer el result List(que era una lista de lista de objetos)

        List<List<Object>> rawData = q.getResultList();

        // Estoy en la fila 1(o sea 0), item 3, que es la columa 4 que es el firstName
        String firstName = rawData.get(0).get(3).toString();
        List<Loan> resultado = new ArrayList<>();

        // RECORRER el rawdata y andar leyendo los items. Usar el watch para ver el
        // formato.
        return resultado;

    }

}
