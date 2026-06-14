package org.henick.spendings.mapper;

import org.henick.spendings.dto.CategoryResponse;
import org.henick.spendings.dto.ExpenseRequest;
import org.henick.spendings.dto.ExpenseResponse;
import org.henick.spendings.model.Category;
import org.henick.spendings.model.Expense;
import org.henick.spendings.model.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapperImpl implements ExpenseMapper {

    private final CategoryMapper categoryMapper;

    public ExpenseMapperImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ExpenseResponse mapToResponse(Expense expense) {
        CategoryResponse category = categoryMapper.mapToResponse(expense.getCategory());
        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getName(),
                expense.getDescription(),
                expense.getPaymentMethod().name(),
                category,
                (expense.getUser() == null) ? null : expense.getUser().getId(),
                expense.getCreatedAt(),
                expense.getUpdatedAt()
        );
    }

    @Override
    public Expense mapFromResponse(ExpenseResponse response) {
        Category category = categoryMapper.mapFromResponse(response.getCategory());
        return new Expense(
                response.getId(),
                response.getAmount(),
                response.getName(),
                response.getDescription(),
                PaymentMethod.valueOf(response.getPaymentMethod()),
                category
        );
    }

    @Override
    public Expense mapFromRequest(ExpenseRequest request) {
        Category category = new Category(request.getCategoryId());
        return new Expense(
                request.getAmount(),
                request.getName(),
                request.getDescription(),
                request.getPaymentMethod(),
                category
        );
    }
}
