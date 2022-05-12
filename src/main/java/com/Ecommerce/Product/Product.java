package com.Ecommerce.Product;

import com.Ecommerce.Category.Category;
import com.Ecommerce.ProductImg.ProductImg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String Description;

    @Column(nullable = false)
    private String price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "categorie_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductImg> productImgs;
}
