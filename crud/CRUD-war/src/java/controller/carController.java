package controller;

import entities.Car;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import sessionbean.CarFacadeLocal;

@Named(value = "carController")
@SessionScoped
public class carController implements Serializable {

    @EJB
    private CarFacadeLocal carsFacade;
    
    private Car selectedCar;

    private Car car = new Car();
    private String name;
    private String author;
    private Integer year;
    private String category;
    private long price;

    public carController() {
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public List<Car> getAllCars() {
        return this.carsFacade.findAll();
    }

    public void emptyVariables() {
        this.author = "";
        this.category = "";
        this.name = "";
        this.price = 0;
        this.year = 0;
    }

    public String createCar() {
        this.car.setAuthor(this.author);
        this.car.setCategory(this.category);
        this.car.setName(this.name);
        this.car.setPrice(this.price);
        this.car.setYear(this.year);
        this.carsFacade.create(this.car);
        this.emptyVariables();
        return "index.xhtml?faces-redirect=true";
    }

    public String updateCar() {
        this.carsFacade.edit(this.selectedCar);
        return "index.xhtml?faces-redirect=true";
    }

    public String deleteCar(Car car) {
        this.carsFacade.remove(car);
        return "index.xhtml?faces-redirect=true";
    }
}
