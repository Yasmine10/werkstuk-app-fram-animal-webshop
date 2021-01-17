package be.ehb.backend.controllers;

import be.ehb.backend.entities.Animal;
import be.ehb.backend.repositories.AnimalRepository;
import be.ehb.backend.repositories.CategoryRepository;
import be.ehb.backend.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AnimalRepository animalRepository;

    /*
     * GET method: /
     * to get all categories
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    /*
     * GET method: /{id}
     * to get a specific category
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Category> getCategoryById(@PathVariable(value = "id") Long id) {
        return categoryRepository.findById(id);   // if no category => returns null
    }

    /*
     * POST method: /
     * to save a new category
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String saveCategory(@Valid @RequestBody Category category) {
        try {
            // check if the animal exists
            Optional<Animal> existingAnimal = animalRepository.findById(category.getAnimal().getId());
            if(existingAnimal.isEmpty()) {
                throw new Exception("The animal from the new category doesn't exist.");
            } else {
                // check if the category already exists for that animal
                Optional<Category> existingCategory = categoryRepository.findByNameAndAnimalId(category.getName(), existingAnimal.get().getId());
                if(existingCategory.isPresent()) {
                    throw new Exception("Category already exists.");
                } else {
                    categoryRepository.save(category);
                }
            }
        } catch (Exception e) {
            return "Error saving the new category: " + e.toString();
        }
        return "Category successfully created!";
    }

    /*
     * PUT method:
     * to update an existing category
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public String updateCategory(@Valid @RequestBody Category category) {
        try {
            // check if the category exists
            Optional<Category> existingCategory = categoryRepository.findById(category.getId());
            if(existingCategory.isPresent()) {
                categoryRepository.save(category);
            } else {
                throw new Exception("Category doesn't exist.");
            }
        } catch (Exception e) {
            return "Error updating the category: " + e.toString();
        }
        return "Category successfully updated!";
    }

    /*
     * DELETE method: /{id}
     * to delete an existing category
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteCategory(@PathVariable(value = "id") Long id) {
        try {
            // check if the category exists
            Optional<Category> existingCategory = categoryRepository.findById(id);
            if(existingCategory.isPresent()) {
                categoryRepository.delete(existingCategory.get());
            } else {
                throw new Exception("Category doesn't exist.");
            }
        } catch (Exception e) {
            return "Error deleting the category: " + e.toString();
        }
        return "Category successfully deleted!";
    }

}
