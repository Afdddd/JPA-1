package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.dtype;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private dtype type = dtype.B;
    private Long id;
    private String name;
    private int price;
    private int StockQuantity;
    private String author;
    private String isbn;

}
