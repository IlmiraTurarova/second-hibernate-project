package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.dao.*;
import org.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Main {
    private final SessionFactory sessionFactory;

    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    public Main() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/movie");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "mysql-root-password");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");

        sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(FilmText.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .buildSessionFactory();

        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        filmTextDAO = new FilmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);
    }

    public static void main(String[] args) {
        Main main = new Main();
        Customer customer = main.createCustomer();
        main.returnInventory();
        main.customerRentInventory(customer);
        main.makeNewFilm();
    }

    //сняли новый фильм
    private void makeNewFilm() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Language language = languageDAO.getItems(0,20).stream().unordered().findAny().get();
            List<Category> categories = categoryDAO.getItems(0,5);
            List<Actor> actors = actorDAO.getItems(0,10);

            Film film = new Film();
            film.setTitle("The ship");
            film.setDescription("American epic romance and disaster film directed, written, produced, and co-edited by James Cameron");
            film.setReleaseYear(Year.now());
            film.setLanguage(language);
            film.setOriginalLanguage(language);
            film.setRentalDuration((byte)14);
            film.setRentalRate(BigDecimal.valueOf(4.8));
            film.setLength((short)100);
            film.setReplacementCost(BigDecimal.valueOf(20.02));
            film.setRating(Rating.NC17);
            film.setSpecialFeatures(Set.of(Feature.BEHIND_THE_SCENES, Feature.COMMENTARIES));
            film.setActors(new HashSet<>(actors));
            film.setCategories(new HashSet<>(categories));
            filmDAO.save(film);

            FilmText filmText = new FilmText();
            filmText.setFilm(film);
            filmText.setId(film.getId());
            filmText.setTitle("The ship");
            filmText.setDescription("American epic romance and disaster film directed, written, produced, and co-edited by James Cameron");
            filmTextDAO.save(filmText);

            session.getTransaction().commit();
        }
    }

    //покупатель арендовал инвентарь
    private void customerRentInventory(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Film film = filmDAO.getFirstAvailableFilmForRent();
            Store store = storeDAO.getItems(0, 1).get(0);

            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(store);
            inventoryDAO.save(inventory);

            Staff staff = store.getStaff();

            Rental rental = new Rental();
            rental.setRentalDate(LocalDateTime.now());
            rental.setInventory(inventory);
            rental.setCustomer(customer);
            rental.setStaff(staff);
            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setCustomer(customer);
            payment.setStaff(staff);
            payment.setRental(rental);
            payment.setAmount(BigDecimal.valueOf(12.23));
            payment.setPaymentDate(LocalDateTime.now());
            paymentDAO.save(payment);

            session.getTransaction().commit();
        }
    }

    //покупатель возвращает ранее арендованный фильм
    private void returnInventory() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Rental rental = rentalDAO.getUnreturnedFilm();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);

            session.getTransaction().commit();
        }

    }

    //создание нового покупателя
    private Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Store store = storeDAO.getItems(0, 1).get(0);

            City city = cityDAO.getByName("Barcelona");

            Address address = new Address();
            address.setAddress("Alikhanova str., 37/3");
            address.setAddress2("Nazarbayeva str., 51/1");
            address.setDistrict("Stepnoy");
            address.setCity(city);
            address.setPostalCode("101400");
            address.setPhone("777-666-55-44");
            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setFirstName("Ilmira");
            customer.setLastName("Turarova");
            customer.setEmail("it@gmail.com");
            customer.setAddress(address);
            customer.setStore(store);
            customer.setActive(true);
            customerDAO.save(customer);

            session.getTransaction().commit();
            return customer;
        }
    }
}