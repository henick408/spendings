package org.henick.spendings.controller;

import org.henick.spendings.dto.ExpenseRequest;
import org.henick.spendings.dto.ExpenseResponse;
import org.henick.spendings.service.CategoryService;
import org.henick.spendings.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
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

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id) {
        ExpenseResponse createdResponse = expenseService.getExpenseById(id);
        return ResponseEntity.ok(createdResponse);
    }

    @PostMapping
    public ResponseEntity<?> createExpense(@RequestBody ExpenseRequest expenseRequest) {
        ExpenseResponse createdExpense = expenseService.createExpense(expenseRequest);
        return ResponseEntity
                .created(URI.create("/api/expenses/" + createdExpense.getId()))
                .body(createdExpense);
    }

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
