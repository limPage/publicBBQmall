package com.hiponya.bbqmall.controllers;

import com.hiponya.bbqmall.entities.product.CategoryEntity;
import com.hiponya.bbqmall.entities.product.ProductEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.services.CategoryService;
import jdk.jfr.Category;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.print.attribute.standard.Media;

@Controller
@RequestMapping(value = "/")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "category", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getCategory(@RequestParam(value = "cid") String cid, CategoryEntity category, ProductEntity product) {
        ModelAndView modelAndView = new ModelAndView("home/category");
        ProductEntity[] products = this.categoryService.getProducts();

        modelAndView.addObject("products", products);
        modelAndView.addObject("category", category);
        modelAndView.addObject("product", product);
        modelAndView.addObject("index",category.getIndex());
        modelAndView.addObject("title", category.getTitle());
        modelAndView.addObject("cid", cid);
        return modelAndView;
    }

    @RequestMapping(value = "view", method=RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getView(@RequestParam(value = "cid") int cid, CategoryEntity category,ProductEntity product) {
        ModelAndView modelAndView = new ModelAndView("home/view");

        CategoryEntity category1 = this.categoryService.getCategoryIndex(cid);
        modelAndView.addObject("category1", category1);

        ProductEntity[] products = this.categoryService.getProducts();
        modelAndView.addObject("products", products);

        JSONObject commentObject = new JSONObject();
        commentObject.put("productName", product.getProductName());
        commentObject.put("price", product.getPrice());

        return modelAndView;
    }

    @GetMapping(value = "list", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getList(@RequestParam(value = "cid") int cid, CategoryEntity category,ProductEntity product) {
        ModelAndView modelAndView = new ModelAndView("home/list");

        this.categoryService.getCategoryIndex(cid);
        modelAndView.addObject("category", category);
        modelAndView.addObject("product", product);

        ProductEntity[] products = this.categoryService.getProducts();
        modelAndView.addObject("products", products);

        JSONObject commentObject = new JSONObject();
        commentObject.put("productName", product.getProductName());
        commentObject.put("price", product.getPrice());

        return modelAndView;
    }

    @RequestMapping(value = "view", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String postCategoryQuantity(@RequestParam(value = "cid") int cid, CategoryEntity category, ProductEntity product) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result =this.categoryService.getProductQuantity(cid);
        responseObject.put("result", result.name().toLowerCase());
        responseObject.put("index", category.getIndex());
        responseObject.put("title", category.getTitle());


        return responseObject.toString();
    }

    @PostMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String postCategory(CategoryEntity category, ProductEntity product) {
        JSONObject responseObject = new JSONObject();

        Enum<?> result =this.categoryService.sendCategoryIndex(category, product);

        responseObject.put("result", result.name().toLowerCase());
        responseObject.put("category", category);
        responseObject.put("product", product);
        responseObject.put("title", category.getTitle());
        responseObject.put("index", category.getIndex());
        responseObject.put("price", product.getPrice());

        return responseObject.toString();
    }
}
