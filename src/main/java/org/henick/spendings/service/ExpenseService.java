package org.henick.spendings.service;

import org.henick.spendings.dto.ExpenseRequest;
import org.henick.spendings.dto.ExpenseResponse;

import java.util.List;

public interface ExpenseService {

    List<ExpenseResponse> getAllExpenses();
    ExpenseResponse getExpense(Long id);
    ExpenseResponse createExpense(ExpenseRequest expenseRequest);
    ExpenseResponse updateExpense(Long id, ExpenseRequest expenseRequest);
    void deleteExpense(Long id);
    boolean existsExpenseById(Long id);

}
