package com.example.n01710904_poorvapatel_test4ims.controllers;

import com.example.n01710904_poorvapatel_test4ims.models.Product;
import com.example.n01710904_poorvapatel_test4ims.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String getProductDetails(@PathVariable Long id, Model model) {
        productService.getProductById(id)
                .ifPresent(product -> model.addAttribute("product", product));
        return "product-details";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        productService.getProductById(id)
                .ifPresent(product -> model.addAttribute("product", product));
        return "product-edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        productService.updateProduct(id, product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @PostMapping("/stock/{id}")
    public String updateStock(@PathVariable Long id, @RequestParam int stock) {
        productService.updateStock(id, stock);
        return "redirect:/products";
    }
    @GetMapping("/stock/edit/{id}")
    public String showStockForm(@PathVariable Long id, Model model) {
        productService.getProductById(id)
                .ifPresent(product -> model.addAttribute("product", product));
        return "product-stock";
    }
}