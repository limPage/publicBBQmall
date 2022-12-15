package com.hiponya.bbqmall.interceptors;

import com.hiponya.bbqmall.entities.product.CategoryEntity;
import com.hiponya.bbqmall.services.CategoryService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class CommonInterceptor implements HandlerInterceptor{
    @Resource
    private CategoryService categoryService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        CategoryEntity[] categories = this.categoryService.getBoards();
        request.setAttribute("categories", categories);
        System.out.println("카테고리 " + categories.length + "개 있음");
        return true;
    }
}
