package be.ehb.backend.controllers;

import be.ehb.backend.entities.Category;
import be.ehb.backend.repositories.AnimalRepository;
import be.ehb.backend.entities.Animal;
import be.ehb.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/animal")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /*
     * GET method: /
     * to get all animals
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Animal> getAllAnimals() {
        ArrayList<Animal> animals = new ArrayList<>();
        animalRepository.findAll().forEach(animals::add);
        return animals;
    }

    /*
     * GET method: /{id}
     * to get a specific animal by id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Animal> getAnimalById(@PathVariable(value = "id") Long id) {
        return animalRepository.findById(id);   // if no animal => returns null
    }

    /*
     * GET method: /{name}
     * to get a specific animal by name
     */
    @RequestMapping(value = "/name={name}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Animal> getAnimalByName(@PathVariable(value = "name") String name) {
        return animalRepository.findAnimalByName(name);   // if no animal => returns null
    }

    /*
     * GET method: /{id}/categories
     * to get all the categories of a specific animal
     */
//    @RequestMapping(value = "/{id}/categories", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Category> getAllCategoriesByAnimalId(@PathVariable(value = "id") Long id) {
//        // check if the animal exists
//        Optional<Animal> existingAnimal = animalRepository.findById(id);
//        return existingAnimal.map(animal -> categoryRepository.findAllByAnimal(animal)).orElse(null);
//    }

    /*
     * GET method: /{name}/categories
     * to get all the categories of a specific animal
     */
    @RequestMapping(value = "/{name}/categories", method = RequestMethod.GET)
    @ResponseBody
    public List<Category> getAllCategoriesByAnimalName(@PathVariable(value = "name") String name) {
        // check if the animal exists
        Optional<Animal> existingAnimal = animalRepository.findAnimalByName(name);
        return existingAnimal.map(animal -> categoryRepository.findAllByAnimal(animal)).orElse(null);
    }

    /*
     * POST method: /
     * to save a new animal
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String saveAnimal(@Valid @RequestBody Animal animal) {
        try {
            // check if the animal already exists
            Optional<Animal> existingAnimal = animalRepository.findAnimalByName(animal.getName());
            if(existingAnimal.isPresent()) {
                throw new Exception("Animal already exists.");
            } else {
                animalRepository.save(animal);
            }
        } catch (Exception e) {
            return "Error saving the new animal: " + e.toString();
        }
        return "Animal successfully created!";
    }

    /*
     * PUT method:
     * to update an existing animal
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public String updateAnimal(@Valid @RequestBody Animal animal){
        try {
            // check if the animal exists
            Optional<Animal> existingAnimal = animalRepository.findById(animal.getId());
            if(existingAnimal.isPresent()) {
                animalRepository.save(animal);
            } else {
                throw new Exception("Animal doesn't exist.");
            }

        } catch (Exception e) {
            return "Error updating the animal: " + e.toString();
        }
        return "Animal successfully updated!";
    }

    /*
     * DELETE method:
     * to delete an existing animal
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteAnimal(@PathVariable(value = "id") Long id) {
        try {
            // check if the animal exists
            Optional<Animal> existingAnimal = animalRepository.findById(id);
            if(existingAnimal.isPresent()) {
                List<Category> categoriesAnimal = categoryRepository.findAllByAnimal(existingAnimal.get());
                if(!categoriesAnimal.isEmpty()) {
                    categoryRepository.deleteAll(categoriesAnimal);
                }
                animalRepository.delete(existingAnimal.get());
            } else {
                throw new Exception("Animal doesn't exist.");
            }
        } catch (Exception e) {
            return "Error deleting the animal: " + e.toString();
        }
        return "Animal successfully deleted!";
    }
}
