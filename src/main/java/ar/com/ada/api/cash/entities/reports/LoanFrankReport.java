package ar.com.ada.api.cash.entities.reports;

import java.math.BigDecimal;

public interface LoanFrankReport {
    //l.loan_id, l.user_id, l.total, u.first_name, u.last_name, u.email
    public Integer getLoan_id();
    public Integer getUser_id();
    public BigDecimal getTotal();
    public String getFirst_name();
    public String getLast_name();
    public String getEmail();
}