package org.henick.spendings.service;

import org.henick.spendings.dto.ExpenseRequest;
import org.henick.spendings.dto.ExpenseResponse;
import org.henick.spendings.mapper.ExpenseMapper;
import org.henick.spendings.model.Expense;
import org.henick.spendings.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
    }

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream().map(expenseMapper::mapToResponse).toList();
    }

    @Override
    public ExpenseResponse getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        return expenseMapper.mapToResponse(expense);
    }

    @Override
    public ExpenseResponse createExpense(ExpenseRequest expenseRequest) {
        Expense expense = expenseMapper.mapFromRequest(expenseRequest);
        Expense createdExpense = expenseRepository.save(expense);
        return expenseMapper.mapToResponse(createdExpense);
    }

    @Override
    public ExpenseResponse updateExpense(Long id, ExpenseRequest expenseRequest) {
        Expense expense = expenseMapper.mapFromRequest(expenseRequest);
        expense.setId(id);
        Expense createdExpense = expenseRepository.save(expense);
        return expenseMapper.mapToResponse(createdExpense);
    }

    @Override
    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public boolean existsExpenseById(Long id) {
        return expenseRepository.existsById(id);
    }
}
