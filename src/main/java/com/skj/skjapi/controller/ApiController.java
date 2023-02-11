package com.skj.skjapi.controller;


import com.skj.skjapi.models.Product;
import com.skj.skjapi.models.Shirt;
import com.skj.skjapi.repos.Products;
import com.skj.skjapi.repos.Shirts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("skj/products")
public class ApiController {

    //    private final Shirts shirts;
    @Autowired
    private Products products;
//    public ApiController(Shirts shirts) {
//        this.shirts = shirts;
//    }

    @GetMapping("/all-products")
    public List<Product> allProducts() {
        return products.findAll();
    }

//    @PostMapping("/add-product")
//    public Shirt addShirt(@RequestBody Shirt shirt) {
//        return shirts.save(shirt);
//    }

//    @DeleteMapping("/delete/{n}")
//    public void deleteShirt(@PathVariable(name = "n") Long id) {
//        Shirt shirt = shirts.getReferenceById(id);
//        shirts.delete(shirt);
//    }

//    @PutMapping("/edit/{n}")
//    public Integer editShirt(@RequestBody Shirt shirt, @PathVariable(name = "n") Long id) {
//        return shirts.updateShirt(id, shirt.getName(), shirt.getSize(), shirt.getPrice());
//    }

}
