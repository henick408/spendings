package org.henick.spendings.service;

import org.henick.spendings.dto.ExpenseRequest;
import org.henick.spendings.dto.ExpenseResponse;
import org.henick.spendings.exception.NoSuchExpenseExistsException;
import org.henick.spendings.mapper.ExpenseMapper;
import org.henick.spendings.model.Expense;
import org.henick.spendings.model.User;
import org.henick.spendings.model.UserRole;
import org.henick.spendings.repository.ExpenseRepository;
import org.henick.spendings.security.AuthUser;
import org.henick.spendings.security.CurrentUserProvider;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<Expense> expenses = expenseRepository.findAll();
        if (currentUserProvider.hasRole(UserRole.EMPLOYEE)) {
            return expenses.stream()
                    .map(expenseMapper::mapToResponse)
                    .toList();
        }
        User user = new User(currentUserProvider.getCurrentUser());
        return expenses.stream()
                .filter(expense -> expense.getUser() == null || expense.getUser().equals(user))
                .map(expenseMapper::mapToResponse)
                .toList();
    }

    // user nie ma dostępu do nie swoich wydatków
    @Override
    public ExpenseResponse getExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                // 404
                .orElseThrow(() -> new NoSuchExpenseExistsException("No such expense exists"));
        if (!currentUserProvider.hasRole(UserRole.EMPLOYEE)) {
            if (expense.getUser() == null || currentUserProvider.isCurrentUser(expense.getUser())) {
                throw new AccessDeniedException("Unauthorized access to expense");
            }
        }
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

    // user nie może aktualizować nie swoje wydatki
    @Override
    public ExpenseResponse updateExpense(Long id, ExpenseRequest expenseRequest) {
        Expense expense = expenseMapper.mapFromRequest(expenseRequest);
        expense.setId(id);
        Expense createdExpense = expenseRepository.save(expense);
        return expenseMapper.mapToResponse(createdExpense);
    }

    // user nie może usuwać nie swoje wydatki
    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public boolean existsExpenseById(Long id) {
        return expenseRepository.existsById(id);
    }
}
