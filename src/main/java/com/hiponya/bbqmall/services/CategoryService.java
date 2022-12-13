package com.hiponya.bbqmall.services;

import com.hiponya.bbqmall.interfaces.IResult;
import com.hiponya.bbqmall.mappers.ICategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.hiponya.bbqmall.services.CategoryService")
public class CategoryService {

    private final ICategoryMapper categoryMapper;

    @Autowired
    public CategoryService(ICategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

//    @Transactional
//    public Enum<? extends IResult>

}
