package org.henick.spendings.service;

import org.henick.spendings.model.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> getAll();
    Expense getById(Long id);
    Expense create(Expense expense);
    Expense update(Long id, Expense expense);
    void delete(Expense expense);
    void deleteById(Long id);
    boolean existsById(Long id);

}
