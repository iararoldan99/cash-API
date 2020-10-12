package ar.com.ada.api.cash.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import ar.com.ada.api.cash.entities.*;
import ar.com.ada.api.cash.entities.reports.LoanFrankReport;

import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    @Query(value = "select * from loan lo LIMIT ?, ?", nativeQuery = true)
    List<Loan> findAllByOffset(Integer offSet, Integer count);

    @Query(value = "select * from user u inner join loan lo on u.user_id = lo.user_id where u.user_id = ? LIMIT ?, ?", nativeQuery = true)
    List<Loan> findAllByOffsetByUser(Integer userId, Integer offSet, Integer count);

    @Query("select count(lo) from Loan lo where lo.user.id = :userId")
    long countByUserId(Integer userId);

    //@Query(value = "CALL GET_ALL_LOANS()", nativeQuery =  true)
    @Procedure("GET_ALL_LOANS")
    List<LoanFrankReport> findAllLoansUsingStoredProcedure();

}