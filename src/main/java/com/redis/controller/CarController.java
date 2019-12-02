package com.redis.controller;

import com.redis.models.Car;
import com.redis.service.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @RequestMapping(value = "/carsView", method = RequestMethod.GET)
    public ModelAndView carsView(ModelAndView model) {
        Iterable<Car> cars = carRepository.findAll();
        model.setViewName("cars");
        model.addObject("list", cars);
        return model;
    }

    @RequestMapping(value = "/saveCar", method = RequestMethod.POST)
    public String saveCar(@RequestParam String key, @RequestParam String model, @RequestParam String brand) {

        try {
            Car car = new Car(key, model, brand);
            carRepository.save(car);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return "redirect:/carsView";
        }
    }

    @GetMapping(value = "/deleteCar/{id}")
    public String deleteCar(@PathVariable String id) {
        carRepository.deleteById(id);
        return "redirect:/carsView";
    }

}
