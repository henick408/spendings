package org.henick.spendings.mapper;

import org.henick.spendings.dto.CategoryResponse;
import org.henick.spendings.dto.ExpenseResponse;
import org.henick.spendings.model.Category;
import org.henick.spendings.model.Expense;

public interface ExpenseMapper {

    ExpenseResponse mapToResponse(Expense expense);
    Expense mapFromResponse(ExpenseResponse response);

}
