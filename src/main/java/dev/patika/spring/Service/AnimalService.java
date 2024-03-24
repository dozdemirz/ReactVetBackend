package dev.patika.spring.Service;

import dev.patika.spring.Dto.request.AnimalRequest;
import dev.patika.spring.Entities.Animal;
import dev.patika.spring.Entities.Customer;
import dev.patika.spring.Repositories.AnimalRepo;
import dev.patika.spring.Repositories.CustomerRepo;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepo animalRepository;
    private final CustomerRepo customerRepo;

    @Autowired
    public AnimalService(AnimalRepo animalRepository, CustomerRepo customerRepo) {
        this.animalRepository = animalRepository;
        this.customerRepo = customerRepo;
    }

    public Animal saveAnimal(AnimalRequest animalRequest) {

        if (animalRequest.getAnimalGender() == null || animalRequest.getAnimalGender().isEmpty() ||
                animalRequest.getAnimalName() == null || animalRequest.getAnimalName().isEmpty() ||
                animalRequest.getAnimalColor() == null || animalRequest.getAnimalColor().isEmpty() ||
                animalRequest.getAnimalBreed() == null || animalRequest.getAnimalBreed().isEmpty() ||
                animalRequest.getAnimalSpecies() == null || animalRequest.getAnimalSpecies().isEmpty()||
                animalRequest.getBirthDate() == null ||
                animalRequest.getCustomer() == null ||
                animalRequest.getCustomer().getCustomerId() == null ||
                animalRequest.getCustomer().getCustomerName() == null
        ) {
            throw new IllegalArgumentException("Hayvana ait alanlar boş olamaz.");
        }
        // AnimalRequest'ten Animal'a dönüşüm yapılıyor
        Animal animal = convertToAnimal(animalRequest);

        // Eğer hayvanın adı ve müşterisi ile aynı isimde bir hayvan varsa hata döndür
        if (animalRepository.existsByAnimalNameAndCustomer(animalRequest.getAnimalName(), animalRequest.getCustomer())) {
            throw new IllegalArgumentException("Bu müşteriye ait aynı isimde bir hayvan zaten var.");
        }

        // Eğer Customer'ın ID'si null değilse, Customer'ın geri kalan bilgilerini getir ve set et
        if (animal.getCustomer() != null && animal.getCustomer().getCustomerId() != null) {
            Optional<Customer> optionalCustomer = customerRepo.findById(animal.getCustomer().getCustomerId());
            optionalCustomer.ifPresent(customer -> {
                animal.getCustomer().setCustomerName(customer.getCustomerName());
                animal.getCustomer().setCustomerPhone(customer.getCustomerPhone());
                animal.getCustomer().setCustomerMail(customer.getCustomerMail());
                animal.getCustomer().setCustomerAddress(customer.getCustomerAddress());
                animal.getCustomer().setCustomerCity(customer.getCustomerCity());
            });
        }

        validateAnimal(animal); // Hayvanı kontrol et

        return animalRepository.save(animal); // Hayvanı kaydet
    }

    //Mapper kullanmadığım için dto'yu animal nesnesine çeviriyorum
    private Animal convertToAnimal(AnimalRequest animalRequest) {
        Animal animal = new Animal();
        animal.setAnimalName(animalRequest.getAnimalName());
        animal.setSpecies(animalRequest.getAnimalSpecies());
        animal.setBreed(animalRequest.getAnimalBreed());
        animal.setGender(animalRequest.getAnimalGender());
        animal.setColor(animalRequest.getAnimalColor());
        animal.setBirthDate(animalRequest.getBirthDate());

        if (animalRequest.getCustomer() != null && animalRequest.getCustomer().getCustomerId() != null) {
            Customer customer = new Customer();
            customer.setCustomerId(animalRequest.getCustomer().getCustomerId());
            animal.setCustomer(customer);
        }

        return animal;
    }

    //Hayvanla alakalı eklerken sıkıntı yaratabilecek sorunları burada topladım
    private void validateAnimal(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Hayvan objesi boş olamaz");
        }

        if (animal.getAnimalName() == null || animal.getSpecies() == null || animal.getBreed() == null ||
                animal.getGender() == null || animal.getBirthDate() == null) {
            throw new IllegalArgumentException("Hayvan alanları boş olamaz" + animal.getGender() + animal.getBirthDate() +animal.getAnimalName());
        }

        if (animal.getCustomer() == null || animal.getCustomer().getCustomerId() == null) {
            throw new IllegalArgumentException("Müşteri ve müşteri ID boş olamaz");
        }
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public Optional<Animal> getAnimalById(Long id) {
        return animalRepository.findById(id);
    }

    public List<Animal> getAnimalsByCustomer(Customer customer) {
        return animalRepository.findByCustomer_CustomerId(customer.getCustomerId());
    }

    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
    }


    public boolean isCustomerExist(Long customerId) {
        return customerRepo.existsById(customerId);
    }
    public AnimalRequest convertToAnimalRequest(Animal animal) {
        AnimalRequest animalRequest = new AnimalRequest();
        animalRequest.setAnimalName(animal.getAnimalName());
        animalRequest.setAnimalSpecies(animal.getSpecies());
        animalRequest.setAnimalBreed(animal.getBreed());
        animalRequest.setAnimalGender(animal.getGender());
        animalRequest.setAnimalColor(animal.getColor());
        animalRequest.setBirthDate(animal.getBirthDate());
        animalRequest.setCustomer(animal.getCustomer());


        return animalRequest;
    }

    public Animal updateAnimal(long id, AnimalRequest animalRequest) {

        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip hayvan bulunamadı."));

        // Eğer güncellenecek hayvan bulunamadıysa, hata fırlatılır
        if (animal == null) {
            throw new IllegalArgumentException("Güncellenecek hayvan bulunamadı.");
        }

        // Güncelleme isteğindeki alanları kontrol et
        if (animalRequest.getAnimalName() == null || animalRequest.getAnimalName().isEmpty() ||
                animalRequest.getAnimalSpecies() == null || animalRequest.getAnimalSpecies().isEmpty() ||
                animalRequest.getAnimalBreed() == null || animalRequest.getAnimalBreed().isEmpty() ||
                animalRequest.getAnimalGender() == null || animalRequest.getAnimalGender().isEmpty() ||
                animalRequest.getAnimalColor() == null || animalRequest.getAnimalColor().isEmpty() ||
                animalRequest.getBirthDate() == null) {
            throw new IllegalArgumentException("Hayvana ait alanlar boş olamaz.");
        }

        // Güncelleme isteğindeki müşteri bilgisini kontrol et
        if (animalRequest.getCustomer() == null || animalRequest.getCustomer().getCustomerId() == null) {
            throw new IllegalArgumentException("Güncelleme isteğinde geçersiz müşteri bilgisi.");
        }

        // Güncelleme isteğindeki müşterinin mevcut olup olmadığını kontrol et
        if (!customerRepo.existsById(animalRequest.getCustomer().getCustomerId())) {
            throw new IllegalArgumentException("Belirtilen müşteri bulunamadı.");
        }

        // Eğer hayvanın adı ve müşterisi ile aynı isimde bir hayvan varsa, hata fırlat
        if (!animal.getAnimalName().equals(animalRequest.getAnimalName()) || !animal.getCustomer().equals(animalRequest.getCustomer())) {
            if (animalRepository.existsByAnimalNameAndCustomer(animalRequest.getAnimalName(), animalRequest.getCustomer())) {
                throw new IllegalArgumentException("Bu müşteriye ait aynı isimde bir hayvan zaten var.");
            }
        }

        // Güncelleme isteğindeki müşteriyi al
        Customer customer = customerRepo.findById(animalRequest.getCustomer().getCustomerId())
                .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip müşteri bulunamadı."));

        // Hayvanın bilgilerini güncelle
        animal.setAnimalName(animalRequest.getAnimalName());
        animal.setSpecies(animalRequest.getAnimalSpecies());
        animal.setBreed(animalRequest.getAnimalBreed());
        animal.setGender(animalRequest.getAnimalGender());
        animal.setColor(animalRequest.getAnimalColor());
        animal.setBirthDate(animalRequest.getBirthDate());
        animal.setCustomer(customer);

        // Hayvanı kaydet
        return animalRepository.save(animal);
    }


}
