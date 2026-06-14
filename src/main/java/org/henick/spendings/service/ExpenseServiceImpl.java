package org.henick.spendings.service;

import org.henick.spendings.dto.ExpenseRequest;
import org.henick.spendings.dto.ExpenseResponse;
import org.henick.spendings.mapper.ExpenseMapper;
import org.henick.spendings.model.Expense;
import org.henick.spendings.model.User;
import org.henick.spendings.model.UserRole;
import org.henick.spendings.repository.ExpenseRepository;
import org.henick.spendings.security.AuthUser;
import org.henick.spendings.security.CurrentUserProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final CurrentUserProvider currentUserProvider;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper, CurrentUserProvider currentUserProvider) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        AuthUser authUser = currentUserProvider.getCurrentUser();
        List<Expense> expenses = expenseRepository.findAll();
        List<ExpenseResponse> expenseResponses = expenses.stream().map(expenseMapper::mapToResponse).toList();
        if (authUser.getRole() == UserRole.EMPLOYEE) {
            return expenseResponses;
        }
        return expenseResponses.stream()
                .filter(response -> response.getUserId().equals(authUser.getId()))
                .toList();
    }

    @Override
    public ExpenseResponse getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        return expenseMapper.mapToResponse(expense);
    }

    @Override
    public ExpenseResponse createExpense(ExpenseRequest expenseRequest) {
        AuthUser authUser = currentUserProvider.getCurrentUser();
        Expense expense = expenseMapper.mapFromRequest(expenseRequest);
        if (authUser.getRole() == UserRole.EMPLOYEE) {
            Expense createdExpense = expenseRepository.save(expense);
            return expenseMapper.mapToResponse(createdExpense);
        }
        User user = new User(authUser.getId());
        expense.setUser(user);
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
