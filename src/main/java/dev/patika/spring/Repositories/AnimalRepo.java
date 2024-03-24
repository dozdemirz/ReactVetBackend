package dev.patika.spring.Repositories;

import dev.patika.spring.Entities.Animal;
import dev.patika.spring.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository <Animal, Long>{
        List<Animal> findByAnimalName(String animalName);

        List<Animal> findByAnimalId(Long animalId);

        List<Animal> findByAnimalNameStartingWithIgnoreCase(String name);
        List<Animal> findByCustomer_CustomerId(Long customerId);

        List<Animal> findByCustomer_CustomerNameLikeIgnoreCase(String name);

        public List<Animal> findByAnimalNameLikeIgnoreCase(String name);

        boolean existsByAnimalNameAndCustomer_CustomerId(String animalName, Long customerId);

        boolean existsByAnimalNameAndCustomer(String animalName, Customer customer);


        List<Animal> findByAnimalNameStartingWithIgnoreCaseAndCustomer_CustomerNameStartingWithIgnoreCase(String animalName,String customerName);
}
