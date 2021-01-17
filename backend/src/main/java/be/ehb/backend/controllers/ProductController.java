package be.ehb.backend.controllers;

import be.ehb.backend.entities.Animal;
import be.ehb.backend.entities.Category;
import be.ehb.backend.repositories.AnimalRepository;
import be.ehb.backend.repositories.CategoryRepository;
import be.ehb.backend.repositories.ProductRepository;
import be.ehb.backend.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /*
     * GET method: /
     * to get all products
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> getAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        productRepository.findAll().forEach(productList::add);
        return productList;
    }

    /*
     * GET method: /{id}
     * to get a specific product by id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Product> getProductById(@PathVariable(value = "id") Long id) {
        return productRepository.findById(id);
    }

//    /*
//     * GET method: /animal={id}
//     * to get all the products for a specific animal
//     */
//    @RequestMapping(value = "/animal={id}", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Product> getAllProductsByAnimalId(@PathVariable(value = "id") Long animalId) {
//        ArrayList<Product> products = new ArrayList<>();
//        productRepository.findAllByAnimal_Id(animalId).forEach(products::add);
//        return products;
//    }

    /*
     * GET method: /animal={name}
     * to get all the products for a specific animal
     */
    @RequestMapping(value = "/animal={name}", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> getAllProductsByAnimalName(@PathVariable(value = "name") String animalName) {
        ArrayList<Product> products = new ArrayList<>();
        productRepository.findAllByAnimal_Name(animalName).forEach(products::add);
        return products;
    }

    /*
     * GET method: /category={id}
     * to get all the products for a specific category
     */
    @RequestMapping(value = "/category={id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> getAllProductsByCategoryId(@PathVariable(value = "id") Long categoryId) {
        ArrayList<Product> products = new ArrayList<>();
        productRepository.findAllByCategory_Id(categoryId).forEach(products::add);
        return products;
    }

    @RequestMapping(value = "/animal={nameAnimal}/brand={nameBrand}", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> getAllProductsByBrandAndAnimal(@PathVariable(value = "nameBrand") String nameBrand, @PathVariable(value = "nameAnimal") String nameAnimal) {
        ArrayList<Product> products = new ArrayList<>();
        productRepository.findAllByBrandAndAnimal_Name(nameBrand, nameAnimal).forEach(products::add);
        return products;
    }

    @RequestMapping(value = "/animal={nameAnimal}/brands", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> getAllBrandsByAnimal(@PathVariable(value = "nameAnimal") String nameAnimal) {
        ArrayList<Product> products = new ArrayList<>();
        productRepository.findAllBrands(nameAnimal).forEach(products::add);
        return products;
    }

    /*
     * POST method: /
     * to save a new product
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String saveProduct(@Valid @RequestBody Product product) {
        try {
            //check if the category and animal exist
            Optional<Animal> existingAnimal = animalRepository.findById(product.getAnimal().getId());
            Optional<Category> existingCategory = categoryRepository.findById(product.getCategory().getId());
            if(existingAnimal.isPresent() && existingCategory.isPresent()) {
                // check if the product exists
                Optional<Product> existingProduct = productRepository.findProductByNameAndBrandAndAndAnimal_IdAndCategory_Id(product.getName(), product.getBrand(), product.getAnimal().getId(), product.getCategory().getId());
                if(existingProduct.isPresent()) {
                    throw new Exception("Product already exists.");
                } else {
                    productRepository.save(product);
                }
            } else {
                throw new Exception("There is no animal and/or category like that in the database.");
            }
        } catch (Exception e) {
            return "Error saving the product: " + e.toString();
        }
        return "Product successfully created!";
    }

    /*
     * PUT method: /
     * to update an existing product
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public String updateProduct(@Valid @RequestBody Product product) {
        try {
            // check if the product exists
            Optional<Product> existingProduct = productRepository.findById(product.getId());
            if(existingProduct.isPresent()) {
                productRepository.save(product);
            } else {
                throw new Exception("Product doesn't exist.");
            }
        } catch (Exception e) {
            return "Error updating the product: " + e.toString();
        }
        return "Product successfully updated!";
    }

    /*
     * DELETE method:
     * it's not possible to delete a product because the product is linked to the users' orders
     */
}
