package com.gabrielsousa.dscatalog.dto;

import com.gabrielsousa.dscatalog.entities.Category;
import com.gabrielsousa.dscatalog.entities.Product;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Campo requerido")
    @Size(min = 5, max = 60, message = "Mínimo de caracteres 5 e o máximo de 60")
    private String name;

    @NotBlank(message = "Preencha o campo requerido")
    private String description;

    @Positive(message = "Informe um valor positivo")
    private Double price;
    private String imgUrl;

    @PastOrPresent(message = "A data do produto não pode ser futura")
    private Instant moment;

    @NotEmpty(message = "Produto sem categoria não é permito")
    List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant moment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.moment = moment;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.moment = entity.getMoment();
    }

    public ProductDTO(Product entity, Set<Category> categories) {
        this(entity);
        categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}
