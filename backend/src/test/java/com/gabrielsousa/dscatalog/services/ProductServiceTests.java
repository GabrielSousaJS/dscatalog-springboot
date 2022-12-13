package com.gabrielsousa.dscatalog.services;

import com.gabrielsousa.dscatalog.dto.ProductDTO;
import com.gabrielsousa.dscatalog.entities.Category;
import com.gabrielsousa.dscatalog.entities.Product;
import com.gabrielsousa.dscatalog.repositories.CategoryRepository;
import com.gabrielsousa.dscatalog.repositories.ProductRepository;
import com.gabrielsousa.dscatalog.services.exceptions.DatabaseException;
import com.gabrielsousa.dscatalog.services.exceptions.ResourceNotFoundException;
import com.gabrielsousa.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    private long existingId;
    private long nonExistingId;
    private long dependetId;
    private Product product;
    private ProductDTO productDto;
    private PageImpl<Product> page;
    private Category category;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = -1L;
        dependetId = 2L;
        product = Factory.createProduct();
        productDto = Factory.createProductDto();
        page = new PageImpl<>(List.of(product));
        category = Factory.createCategory();

        // Find All
        when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        // Save
        when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);

        // Find by id
        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Get reference by id
        when(productRepository.getReferenceById(existingId)).thenReturn(product);
        when(productRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        // Get references by id de category
        when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
        when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        // Delete by id
        doNothing().when(productRepository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
        doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependetId);
    }

    @Test
    public void findAllPagedShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProductDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);

        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExists() {
        ProductDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);

        verify(productRepository, times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           ProductDTO result = service.findById(nonExistingId);
        });

        verify(productRepository, times(1)).findById(nonExistingId);
    }

    @Test
    public void updateShouldReturnProductDtoWhenIdExists() {
        ProductDTO result = service.update(existingId, productDto);

        Assertions.assertNotNull(result);

        verify(productRepository, times(1)).save(product);
        verify(productRepository, times(1)).getReferenceById(existingId);
        verify(categoryRepository, times(1)).getReferenceById(existingId);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           ProductDTO result = service.update(nonExistingId, productDto);
        });

        verify(productRepository, times(1)).getReferenceById(nonExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.deleteById(existingId);
        });

        verify(productRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           service.deleteById(nonExistingId);
        });

        verify(productRepository, times(1)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.deleteById(dependetId);
        });

        verify(productRepository, times(1)).deleteById(dependetId);
    }
}
