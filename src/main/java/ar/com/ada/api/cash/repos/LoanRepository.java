package ar.com.ada.api.cash.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.com.ada.api.cash.entities.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    @Query("select lo from Loan lo LIMIT :offSet, :count")
    List<Loan> findAllByOffset(Integer offSet, Integer count);

    @Query("select lo from Loan lo where lo.user.id = :userId LIMIT :offSet, :count")
    List<Loan> findAllByOffsetByUser(Integer userId, Integer offSet, Integer count);
}
