package org.henick.spendings.controller;

import org.henick.spendings.dto.ExpenseRequest;
import org.henick.spendings.dto.ExpenseResponse;
import org.henick.spendings.model.UserRole;
import org.henick.spendings.security.AuthUser;
import org.henick.spendings.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/expenses")
@PreAuthorize("hasAnyRole('USER', 'EMPLOYEE')")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
        List<ExpenseResponse> expenseResponses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenseResponses);
    }

    // wrzucić logikę autentykacji, nieistniejącego exception do serwisu
    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id, Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        ExpenseResponse expenseResponse;
        if (!(id.equals(authUser.getId()) || authUser.getRole().equals(UserRole.EMPLOYEE))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        expenseResponse = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expenseResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createExpense(@RequestBody ExpenseRequest expenseRequest) {
        ExpenseResponse createdExpense = expenseService.createExpense(expenseRequest);
        return ResponseEntity
                .created(URI.create("/api/expenses/" + createdExpense.getId()))
                .body(createdExpense);
    }

    // wrzucić do serwisu
    @PutMapping("/{id}")
    public ResponseEntity<String> updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseRequest expenseRequest
    ) {
        if (!expenseService.existsExpenseById(id)) {
            return ResponseEntity.notFound().build();
        }
        expenseService.updateExpense(id, expenseRequest);
        return ResponseEntity.ok("Expense updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        if (!expenseService.existsExpenseById(id)) {
            return ResponseEntity.notFound().build();
        }
        expenseService.deleteExpenseById(id);
        return ResponseEntity.noContent().build();
    }

}
