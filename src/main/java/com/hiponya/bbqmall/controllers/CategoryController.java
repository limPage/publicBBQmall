package com.hiponya.bbqmall.controllers;

import com.hiponya.bbqmall.entities.product.CategoryEntity;
import com.hiponya.bbqmall.entities.product.ProductEntity;
import com.hiponya.bbqmall.entities.product.SortEntity;
import com.hiponya.bbqmall.enums.CommonResult;
import com.hiponya.bbqmall.enums.member.CategoryResult;
import com.hiponya.bbqmall.interfaces.IResult;
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
    public ModelAndView getCategory(@RequestParam(value = "cid") int cid,  CategoryEntity category, ProductEntity product, SortEntity sort) {
        ModelAndView modelAndView = new ModelAndView("home/category");
        CategoryEntity[] categories = this.categoryService.getCategories();
        ProductEntity[] products = this.categoryService.getProducts(cid);
        CategoryEntity categories2 = this.categoryService.getCategories2(cid);
        SortEntity[] sorts = this.categoryService.getSorts();

        category.setIndex(category.getIndex());

        category.setTitle(category.getTitle());

        modelAndView.addObject("sort", sort);
        modelAndView.addObject("sorts", sorts);

        modelAndView.addObject("products", products);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("category", category);
        modelAndView.addObject("product", product);
        modelAndView.addObject("index",category.getIndex());
        modelAndView.addObject("cid", cid);

        if(product != null) {
            modelAndView.addObject("title", categories2.getTitle());
        }

        System.out.println(categories2.getTitle());
        return modelAndView;
    }

    @RequestMapping(value = "view", method=RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getView(@RequestParam(value = "cid") int cid, CategoryEntity category,ProductEntity product) {
        ModelAndView modelAndView = new ModelAndView("home/view");

        CategoryEntity category1 = this.categoryService.getCategoryIndex(cid);
        modelAndView.addObject("category1", category1);

        ProductEntity[] products = this.categoryService.getProducts(cid);
        modelAndView.addObject("products", products);

        JSONObject commentObject = new JSONObject();
        commentObject.put("productName", product.getProductName());
        commentObject.put("price", product.getPrice());

        return modelAndView;
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getList(@RequestParam(value = "cid") int cid, CategoryEntity category,ProductEntity product) {
        ModelAndView modelAndView = new ModelAndView("home/list");

        this.categoryService.getCategoryIndex(cid);
        modelAndView.addObject("category", category);
        modelAndView.addObject("product", product);

        ProductEntity[] products = this.categoryService.getProducts(cid);
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
    public String postCategory(@RequestParam(value = "cid") int cid, CategoryEntity category, ProductEntity product) {
        JSONObject responseObject = new JSONObject();

        Enum<? extends IResult> result =this.categoryService.sendCategoryIndex(category, product);

        if (category == null) {
            result = CategoryResult.NO_CATEGORY;
        } else {
            category.setIndex(cid);
            category.setTitle(category.getTitle());
            product.setPrice(product.getPrice());
            product.setDetailIndex(category.getIndex());

            result = this.categoryService.getProductQuantity(category.getIndex());
            if (result == CommonResult.SUCCESS) {
                responseObject.put("cid", category.getIndex());
            }
        }

        responseObject.put("result", result.name().toLowerCase());
        responseObject.put("category", category);
        responseObject.put("product", product);
        responseObject.put("price", product.getPrice());


        return responseObject.toString();
    }
}
