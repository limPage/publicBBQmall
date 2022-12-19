package com.hiponya.bbqmall.controllers;

import com.hiponya.bbqmall.entities.product.CategoryEntity;
import com.hiponya.bbqmall.entities.product.ProductEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.services.CategoryService;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.print.attribute.standard.Media;

@Controller
@RequestMapping(value = "category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getCategory(@RequestParam(value = "cid") String cid, CategoryEntity category, ProductEntity product) {
        ModelAndView modelAndView = new ModelAndView("home/category");

//        CategoryEntity cid category== null ? null : this.categoryService.getCategoryIndex(cid.getIndex());
        modelAndView.addObject("category", category);
        modelAndView.addObject("product", product);
        modelAndView.addObject("cid", cid);
        return modelAndView;
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getList(@RequestParam(value = "cid") int cid, CategoryEntity category, ProductEntity product) {
        ModelAndView modelAndView = new ModelAndView("category/list");

        category = this.categoryService.getCategoryIndex(cid);
        modelAndView.addObject("category", category);

        ProductEntity[] products = this.categoryService.getProducts();

        modelAndView.addObject("products", products);

        return modelAndView;
    }

    @PostMapping(value="category", produces = MediaType.APPLICATION_JSON_VALUE)
    public String postCategory(CategoryEntity category, ProductEntity product) {
        JSONObject responseObject = new JSONObject();

        Enum<?> result =this.categoryService.sendCategoryIndex(category, product);

                /*public BoardEntity getBoard(String id) {
        return this.bbsMapper.selectBoardById(id);
    }*/
        responseObject.put("result", result.name().toLowerCase());
        responseObject.put("title", category.getTitle());
        responseObject.put("index", category.getIndex());

        return responseObject.toString();
    }

}
