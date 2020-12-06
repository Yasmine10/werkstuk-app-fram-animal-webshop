package be.ehb.backend.controllers;

import be.ehb.backend.daos.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductDAO productDAO;



}
