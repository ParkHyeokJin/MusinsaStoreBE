package com.musinsa.shop.musinsashop.exception;

import com.musinsa.shop.musinsashop.brand.application.exception.AlreadyBrandException;
import com.musinsa.shop.musinsashop.brand.application.exception.BrandNotFoundException;
import com.musinsa.shop.musinsashop.category.application.exception.AlreadyCategoryException;
import com.musinsa.shop.musinsashop.category.application.exception.CategoryNotFoundException;
import com.musinsa.shop.musinsashop.exception.dto.ExceptionResponse;
import com.musinsa.shop.musinsashop.product.application.exception.AlreadyProductException;
import com.musinsa.shop.musinsashop.product.application.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBrandNotFoundException(final BrandNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(AlreadyBrandException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyBrandException(final AlreadyBrandException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCategoryNotFoundException(final CategoryNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(AlreadyCategoryException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyCategoryException(final AlreadyCategoryException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFoundException(final ProductNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(AlreadyProductException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyProductException(final AlreadyProductException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(ex.getMessage()));
    }
}
