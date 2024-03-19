# Veteriner Yönetim Sistemi API Dokümantasyonu
Bu API, bir veteriner kliniğinin kendi işlerini yönetebilmesi için kullanılır. API, veteriner doktorları, müşterileri, hayvanları, aşıları ve randevuları yönetmek için çeşitli endpoint'ler sağlar.

* API kök URL'i: http://localhost:8080 
* Bu projenin dokümantasyonunda Postman ve json üzerinden sorgular kullanılmıştır



# Doctor Entity Operasyonları
## Doctor Bilgisi Getirme
#### Endpoint: GET http://localhost:8080/doctor/{doctorId}
#### Description: ID kullanarak belirli bir doktorun bilgilerini getirir.
#### Path Variables:
   doctorId (Long) - Doktorun kimlik numarası.
#### Request:
   Örnek: http://localhost:8080/doctor/2
####  Response:
   HTTP Status: 200 OK
####  Body:

```
   {
    "doctorId": 2,
    "doctorName": "Dr. Christopher Black",
    "doctorPhone": "+9998887777",
    "doctorMail": "christopher.black@example.com",
    "doctorAddress": "456 Maple Avenue",
    "doctorCity": "Gotham"
}


```

   HTTP Status: 404 Not Found (Eğer doktor bulunamazsa)
## Yeni Doktor Ekleme
#### Endpoint: POST http://localhost:8080/doctor/save
#### Description: Yeni bir doktor ekler.
#### Request:
####  Body:
   ```
   {
    "doctorId": 1,
    "doctorName": "Dr. Emily White",
    "doctorPhone": "+1112223333",
    "doctorMail": "emily.white@example.com",
    "doctorAddress": "123 Oak Street",
    "doctorCity": "Metropolis"
}
   ```
####   Response:
   HTTP Status: 201 Created
####  Body:

```
   {
   "id": 2,
   "name": "Dr. Jane Smith",
   "phone": "+9876543210",
   "mail": "jane.smith@example.com",
   "address": "456 Oak St",
   "city": "New City"
   }
   ```
## Doktor Bilgisi Güncelleme
####  Endpoint: PUT http://localhost:8080/doctor/save
#### Description: Belirli bir doktorun bilgilerini günceller.
#### Path Variables:
   doctorId (Long) - Güncellenecek doktorun kimlik numarası verilmeli.
#### Request:
####  Body:
   ```
   {
   "doctorId" : 2
   "name": "Dr. Jane Doe",
   "phone": "+1234567890",
   "mail": "jane.doe@example.com",
   "address": "789 Pine St",
   "city": "Another City"
   }
   ```
####  Response:
   HTTP Status: 200 OK
####  Body:


```
   {
   "id": 2,
   "name": "Dr. Jane Doe",
   "phone": "+1234567890",
   "mail": "jane.doe@example.com",
   "address": "789 Pine St",
   "city": "Another City"
 
   }
   ```
   HTTP Status: 404 Not Found (Eğer doktor bulunamazsa)

# Animal Entity Operasyonları
## Hayvan Bilgisi Getirme
####  Endpoint: GET http://localhost:8080/animal/{animalId}
####  Description: Belirli bir hayvanın bilgilerini getirir.
####  Path Variables:
   animalId (Long) - Hayvanın kimlik numarası.
####  Request:
   Örnek: http://localhost:8080/animal/3
####  Response:
   HTTP Status: 200 OK
####  Body:
   
```
   {
    "animalId": 3,
    "animalName": "Rocky",
    "species": "Hamster",
    "breed": "Syrian",
    "gender": "Male",
    "color": "Brown",
    "birthDate": "2021-02-28",
    "customer": {
        "customerId": 3,
        "customerName": "Charlie Brown",
        "customerPhone": "+1112223333",
        "customerMail": "charlie.brown@example.com",
        "customerAddress": "789 Pine Road",
        "customerCity": "Smallville"
    }
}
   ```
   HTTP Status: 404 Not Found (Eğer hayvan bulunamazsa)
## Yeni Hayvan Ekleme
#### Endpoint: POST http://localhost:8080/animal/save
#### Description: Yeni bir hayvan ekler.
####  Request:
#### Body:
   ```
   {
  "animalName": "Buddy",
  "animalSpecies": "Dog",
  "animalBreed": "Golden Retriever",
  "animalGender": "Male",
  "animalColor": "Golden",
  "dateOfBirth": "2019-05-15",
  "customer": {
    "customerId": 1
  }
}
   ```
#### Response:
   HTTP Status: 201 Created
####  Body:
  ```
   {
    "animalId": 1,
    "animalName": "Buddy",
    "species": "Dog",
    "breed": "Golden Retriever",
    "gender": "Male",
    "color": "Golden",
    "birthDate": "2019-05-15",
    "customer": {
        "customerId": 1,
        "customerName": "Alice Johnson",
        "customerPhone": "+1234567890",
        "customerMail": "alice.johnson@example.com",
        "customerAddress": "123 Oak Street",
        "customerCity": "Metropolis"
    }
}
   ```
## Hayvan Bilgisi Güncelleme
#### Endpoint: PUT http://localhost:8080/animal/save
#### Description: Belirli bir hayvanın bilgilerini günceller.
####  Path Variables:
   animalId (Long) - Güncellenecek hayvanın kimlik numarası gerekli.
#### Request:
####  Body:
   
```
   {
   "animalId": 1
   "name": "Max",
   "species": "Dog",
   "breed": "German Shepherd",
   "gender": "Male",
   "colour": "Black and Tan",
   "dateOfBirth": "2018-08-10",
   "customer": {
   "customerId": 1
   }
   }
   ```
#### Response:
   HTTP Status: 200 OK
####  Body:
   
```
   {
   "id": 1,
   "name": "Max",
   "species": "Dog",
   "breed": "German Shepherd",
   "gender": "Male",
   "colour": "Black and Tan",
   "dateOfBirth": "2018-08-10",
   "customer": {
   "customerId": 1,
   "customerName": null,
   "customerPhone": null,
   "customerMail": null,
   "customerAddress": null,
   "customerCity": null
   }
   }
   ```
   HTTP Status: 404 Not Found (Eğer hayvan bulunamazsa)

## Tüm Hayvanları listeleme
#### Endpoint: http://localhost:8080/animal/find-all
#### Description: Tüm hayvanları listeler.

#### Response:

####  Body:
```
[
{
"animalId": 1,
"animalName": "Buddy",
"species": "Dog",
"breed": "Golden Retriever",
"gender": "Male",
"color": "Golden",
"birthDate": "2019-05-15",
"customer": {
"customerId": 1,
"customerName": "Alice Johnson",
"customerPhone": "+1234567890",
"customerMail": "alice.johnson@example.com",
"customerAddress": "123 Oak Street",
"customerCity": "Metropolis"
}
},
{
"animalId": 2,
"animalName": "Whiskers",
"species": "Cat",
"breed": "Siamese",
"gender": "Female",
"color": "White",
"birthDate": "2020-08-10",
"customer": {
"customerId": 2,
"customerName": "Bob Smith",
"customerPhone": "+9876543210",
"customerMail": "bob.smith@example.com",
"customerAddress": "456 Maple Avenue",
"customerCity": "Gotham"
}
},
{
"animalId": 3,
"animalName": "Rocky",
"species": "Hamster",
"breed": "Syrian",
"gender": "Male",
"color": "Brown",
"birthDate": "2021-02-28",
"customer": {
"customerId": 3,
"customerName": "Charlie Brown",
"customerPhone": "+1112223333",
"customerMail": "charlie.brown@example.com",
"customerAddress": "789 Pine Road",
"customerCity": "Smallville"
}
},
{
"animalId": 4,
"animalName": "Tigger",
"species": "Rabbit",
"breed": "Holland Lop",
"gender": "Male",
"color": "Spotted",
"birthDate": "2018-11-20",
"customer": {
"customerId": 3,
"customerName": "Charlie Brown",
"customerPhone": "+1112223333",
"customerMail": "charlie.brown@example.com",
"customerAddress": "789 Pine Road",
"customerCity": "Smallville"
}
},
{
"animalId": 5,
"animalName": "Mittens",
"species": "Cat",
"breed": "Persian",
"gender": "Female",
"color": "Gray",
"birthDate": "2017-04-12",
"customer": {
"customerId": 5,
"customerName": "Eva Davis",
"customerPhone": "+4445556666",
"customerMail": "eva.davis@example.com",
"customerAddress": "202 Elm Street",
"customerCity": "Central City"
}
}
]
```

## İsime Göre Hayvan Filtreleme
#### Endpoint: http://localhost:8080/animal/name/{name}
#### Description: O isime sahip hayvanları listeler. Aynı isimden birden çok olabileceği için hepsini listelemeyi seçtim. Aynı zamanda ignorecase kullanıyor.
#### Path Variables:
name (String) - Hayvanın adı.

#### Request:
#### Örnek: http://localhost:8080/animal/name/buddy
#### Response:
HTTP Status: 200 OK
#### Body:
```
[
    {
        "animalId": 1,
        "animalName": "Buddy",
        "species": "Dog",
        "breed": "Golden Retriever",
        "gender": "Male",
        "color": "Golden",
        "birthDate": "2019-05-15",
        "customer": {
            "customerId": 1,
            "customerName": "Alice Johnson",
            "customerPhone": "+1234567890",
            "customerMail": "alice.johnson@example.com",
            "customerAddress": "123 Oak Street",
            "customerCity": "Metropolis"
        }
    }
]
```
## Belirli Bir Hayvanı Silme
####  Endpoint: DELETE http://localhost:8080/animal/delete/{id}
####  Description: Belirli bir hayvanı siler.
####  Path Variables:
   id (Long) - Hayvanın kimlik numarası.
####  Request:
####  Örnek: http://localhost:8080/animal/delete/1
#### Response:
   HTTP Status: 200 OK
#### Body:
   ```
   "1 numaralı hayvan silindi."
```
# Appointment Entity Operasyonları
## Yeni Randevu Oluşturma
#### Endpoint: POST http://localhost:8080/appointment/create
####  Description: Yeni bir randevu oluşturur.
####  Request:
   Body:
   ```
   {
  
  "appointmentDate": "2023-10-05T17:00:00",
  "doctor": {
    "doctorId": 1
  },
  "animal": {
    "animalId": 3
  }
}

   ```
#### Response:
   HTTP Status: 201 Created
####  Body:
   ```
   {
    "appointmentId": 2,
    "appointmentDate": "2023-10-05T17:00:00",
    "doctor": {
        "doctorId": 1,
        "doctorName": "Dr. Emily White",
        "doctorPhone": "+1112223333",
        "doctorMail": "emily.white@example.com",
        "doctorAddress": "123 Oak Street",
        "doctorCity": "Metropolis"
    },
    "animal": {
        "animalId": 3,
        "animalName": "Rocky",
        "species": "Hamster",
        "breed": "Syrian",
        "gender": "Male",
        "color": "Brown",
        "birthDate": "2021-02-28",
        "customer": {
            "customerId": 3,
            "customerName": "Charlie Brown",
            "customerPhone": "+1112223333",
            "customerMail": "charlie.brown@example.com",
            "customerAddress": "789 Pine Road",
            "customerCity": "Smallville"
        }
    }
}
   ```
## Randevu Bilgisi Güncelleme
####  Endpoint: PUT http://localhost:8080/appointment/update
####  Description: Belirli bir randevunun bilgilerini günceller.
####  Request:
####  Body:
   ```
   {
   "id": 1,
   "animalId": 1,
   "doctorId": 1,
   "appointmentDate": "2023-01-20T14:00:00"
   }
   ```
####   Response:
   HTTP Status: 200 OK
####  Body:
   ```
   {
   "id": 1,
   "animalId": 1,
   "doctorId": 1,
   "appointmentDate": "2023-01-20T14:00:00"
   }
   ```
   HTTP Status: 404 Not Found (Eğer randevu bulunamazsa)
## Belirli Bir Randevu Bilgisini Getirme
####  Endpoint: GET http://localhost:8080/appointment/{appointmentId}
####  Description: Belirli bir randevunun bilgilerini getirir.
####  Path Variables:
   appointmentId (Long) - Randevunun kimlik numarası.
   Request:
####  Örnek: http://localhost:8080/appointment/5
####  Response:
   HTTP Status: 200 OK
####  Body:
   ```
   {
    "appointmentId": 5,
    "appointmentDate": "2023-08-25T09:00:00",
    "doctor": {
        "doctorId": 2,
        "doctorName": "Dr. Christopher Black",
        "doctorPhone": "+9998887777",
        "doctorMail": "christopher.black@example.com",
        "doctorAddress": "456 Maple Avenue",
        "doctorCity": "Gotham"
    },
    "animal": {
        "animalId": 5,
        "animalName": "Mittens",
        "species": "Cat",
        "breed": "Persian",
        "gender": "Female",
        "color": "Gray",
        "birthDate": "2017-04-12",
        "customer": {
            "customerId": 5,
            "customerName": "Eva Davis",
            "customerPhone": "+4445556666",
            "customerMail": "eva.davis@example.com",
            "customerAddress": "202 Elm Street",
            "customerCity": "Central City"
        }
    }
}
   ```
   HTTP Status: 404 Not Found (Eğer randevu bulunamazsa)
## Belirli Tarih Aralığına ve Doktora Göre Randevu Arama
####  Endpoint: GET http://localhost:8080/appointment/findByDateAndDoctor
####  Description: Belirli bir tarih aralığına ve doktora göre randevuları getirir.
####  Query Parameters:
   startDate (String) - Arama başlangıç tarihi (ISO formatında).
   endDate (String) - Arama bitiş tarihi (ISO formatında).
   doctorId (Long) - Doktorun kimlik numarası.
####  Request:
#### Örnek: http://localhost:8080/appointment/findByDateAndDoctor?startDate=2023-08-15&endDate=2023-08-25&doctorId=2
####  Response:
   HTTP Status: 200 OK
####  Body:
   ```
  [
    {
        "appointmentId": 5,
        "appointmentDate": "2023-08-25T09:00:00",
        "doctor": {
            "doctorId": 2,
            "doctorName": "Dr. Christopher Black",
            "doctorPhone": "+9998887777",
            "doctorMail": "christopher.black@example.com",
            "doctorAddress": "456 Maple Avenue",
            "doctorCity": "Gotham"
        },
        "animal": {
            "animalId": 5,
            "animalName": "Mittens",
            "species": "Cat",
            "breed": "Persian",
            "gender": "Female",
            "color": "Gray",
            "birthDate": "2017-04-12",
            "customer": {
                "customerId": 5,
                "customerName": "Eva Davis",
                "customerPhone": "+4445556666",
                "customerMail": "eva.davis@example.com",
                "customerAddress": "202 Elm Street",
                "customerCity": "Central City"
            }
        }
    }
]
   ```
## Tüm Randevuları Getirme
####  Endpoint: GET http://localhost:8080/appointment/find-all
####  Description: Tüm randevuları getirir.
#### Request:
####  Örnek: http://localhost:8080/appointment/find-all
####  Response:
   HTTP Status: 200 OK
####  Body:
   ```
   [
    {
        "appointmentId": 1,
        "appointmentDate": "2023-10-05T16:00:00",
        "doctor": {
            "doctorId": 1,
            "doctorName": "Dr. Emily White",
            "doctorPhone": "+1112223333",
            "doctorMail": "emily.white@example.com",
            "doctorAddress": "123 Oak Street",
            "doctorCity": "Metropolis"
        },
        "animal": {
            "animalId": 2,
            "animalName": "Whiskers",
            "species": "Cat",
            "breed": "Siamese",
            "gender": "Female",
            "color": "White",
            "birthDate": "2020-08-10",
            "customer": {
                "customerId": 2,
                "customerName": "Bob Smith",
                "customerPhone": "+9876543210",
                "customerMail": "bob.smith@example.com",
                "customerAddress": "456 Maple Avenue",
                "customerCity": "Gotham"
            }
        }
    },
    {
        "appointmentId": 2,
        "appointmentDate": "2023-10-05T17:00:00",
        "doctor": {
            "doctorId": 1,
            "doctorName": "Dr. Emily White",
            "doctorPhone": "+1112223333",
            "doctorMail": "emily.white@example.com",
            "doctorAddress": "123 Oak Street",
            "doctorCity": "Metropolis"
        },
        "animal": {
            "animalId": 3,
            "animalName": "Rocky",
            "species": "Hamster",
            "breed": "Syrian",
            "gender": "Male",
            "color": "Brown",
            "birthDate": "2021-02-28",
            "customer": {
                "customerId": 3,
                "customerName": "Charlie Brown",
                "customerPhone": "+1112223333",
                "customerMail": "charlie.brown@example.com",
                "customerAddress": "789 Pine Road",
                "customerCity": "Smallville"
            }
        }
    },
    {
        "appointmentId": 3,
        "appointmentDate": "2023-09-30T11:00:00",
        "doctor": {
            "doctorId": 2,
            "doctorName": "Dr. Christopher Black",
            "doctorPhone": "+9998887777",
            "doctorMail": "christopher.black@example.com",
            "doctorAddress": "456 Maple Avenue",
            "doctorCity": "Gotham"
        },
        "animal": {
            "animalId": 3,
            "animalName": "Rocky",
            "species": "Hamster",
            "breed": "Syrian",
            "gender": "Male",
            "color": "Brown",
            "birthDate": "2021-02-28",
            "customer": {
                "customerId": 3,
                "customerName": "Charlie Brown",
                "customerPhone": "+1112223333",
                "customerMail": "charlie.brown@example.com",
                "customerAddress": "789 Pine Road",
                "customerCity": "Smallville"
            }
        }
    },
    {
        "appointmentId": 4,
        "appointmentDate": "2023-06-15T09:00:00",
        "doctor": {
            "doctorId": 4,
            "doctorName": "Dr. Ryan Brown",
            "doctorPhone": "+9876543210",
            "doctorMail": "ryan.brown@example.com",
            "doctorAddress": "101 Cedar Lane",
            "doctorCity": "Star City"
        },
        "animal": {
            "animalId": 3,
            "animalName": "Rocky",
            "species": "Hamster",
            "breed": "Syrian",
            "gender": "Male",
            "color": "Brown",
            "birthDate": "2021-02-28",
            "customer": {
                "customerId": 3,
                "customerName": "Charlie Brown",
                "customerPhone": "+1112223333",
                "customerMail": "charlie.brown@example.com",
                "customerAddress": "789 Pine Road",
                "customerCity": "Smallville"
            }
        }
    },
    {
        "appointmentId": 5,
        "appointmentDate": "2023-08-25T09:00:00",
        "doctor": {
            "doctorId": 2,
            "doctorName": "Dr. Christopher Black",
            "doctorPhone": "+9998887777",
            "doctorMail": "christopher.black@example.com",
            "doctorAddress": "456 Maple Avenue",
            "doctorCity": "Gotham"
        },
        "animal": {
            "animalId": 5,
            "animalName": "Mittens",
            "species": "Cat",
            "breed": "Persian",
            "gender": "Female",
            "color": "Gray",
            "birthDate": "2017-04-12",
            "customer": {
                "customerId": 5,
                "customerName": "Eva Davis",
                "customerPhone": "+4445556666",
                "customerMail": "eva.davis@example.com",
                "customerAddress": "202 Elm Street",
                "customerCity": "Central City"
            }
        }
    }
]
   ```
## Belirli Tarih Aralığına ve Hayvana Göre Randevu Arama
####  Endpoint: GET http://localhost:8080/appointment/findByDateAndAnimal
#### Description: Belirli bir tarih aralığına ve hayvana göre randevuları getirir.
####  Query Parameters:
   startDate (String) - Arama başlangıç tarihi (ISO formatında).
   endDate (String) - Arama bitiş tarihi (ISO formatında).
   animalId (Long) - Hayvanın kimlik numarası.
####  Request:
####  Örnek: http://localhost:8080/appointment/findByDateAndAnimal?startDate=2023-08-15&endDate=2023-08-25&animalId=5
####  Response:
   HTTP Status: 200 OK
####  Body:
   ```
   [
    {
        "appointmentId": 5,
        "appointmentDate": "2023-08-25T09:00:00",
        "doctor": {
            "doctorId": 2,
            "doctorName": "Dr. Christopher Black",
            "doctorPhone": "+9998887777",
            "doctorMail": "christopher.black@example.com",
            "doctorAddress": "456 Maple Avenue",
            "doctorCity": "Gotham"
        },
        "animal": {
            "animalId": 5,
            "animalName": "Mittens",
            "species": "Cat",
            "breed": "Persian",
            "gender": "Female",
            "color": "Gray",
            "birthDate": "2017-04-12",
            "customer": {
                "customerId": 5,
                "customerName": "Eva Davis",
                "customerPhone": "+4445556666",
                "customerMail": "eva.davis@example.com",
                "customerAddress": "202 Elm Street",
                "customerCity": "Central City"
            }
        }
    }
]
```

# AvailableDate Entity Operasyonları
## Doktorun Müsait Günlerini Getirme
#### Endpoint: GET http://localhost:8080/available-date/{doctorId}
#### Description: Belirli bir doktorun müsait günlerini getirir.
#### Path Variables:
   doctorId (Long) - Doktorun kimlik numarası.
#### Request:
####  Örnek: http://localhost:8080/available-date/2
####  Response:
   HTTP Status: 200 OK
####  Body:
   ```
   [
    {
        "availableDateId": 2,
        "availableDate": "2023-09-30",
        "doctor": {
            "doctorId": 2,
            "doctorName": "Dr. Christopher Black",
            "doctorPhone": "+9998887777",
            "doctorMail": "christopher.black@example.com",
            "doctorAddress": "456 Maple Avenue",
            "doctorCity": "Gotham"
        }
    },
    {
        "availableDateId": 3,
        "availableDate": "2023-08-25",
        "doctor": {
            "doctorId": 2,
            "doctorName": "Dr. Christopher Black",
            "doctorPhone": "+9998887777",
            "doctorMail": "christopher.black@example.com",
            "doctorAddress": "456 Maple Avenue",
            "doctorCity": "Gotham"
        }
    }
]
   ```
## Yeni Müsait Gün Ekleme
####  Endpoint: POST http://localhost:8080/available-date/save
#### Description: Belirli bir doktora yeni bir müsait gün ekler.
####  Request:
#### Body:
   ```
   {
  "availableDate": "2023-10-05",
  "doctor": {
    "doctorId": 1
  }
}

   ```
####  Response:
   HTTP Status: 200 OK
####  Body:
   ```
   {
    "availableDateId": 1,
    "availableDate": "2023-10-05",
    "doctor": {
        "doctorId": 1,
        "doctorName": "Dr. Emily White",
        "doctorPhone": "+1112223333",
        "doctorMail": "emily.white@example.com",
        "doctorAddress": "123 Oak Street",
        "doctorCity": "Metropolis"
    }
}
   ```
   HTTP Status: 400 Bad Request (Eğer belirtilen doktor bulunamazsa veya aynı tarih için kayıt mevcutsa)

# Customer Entity Operasyonları
## Müşteri Bilgilerini Getirme
####  Endpoint: GET http://localhost:8080/customer/{id}
####  Description: Belirli bir müşterinin bilgilerini getirir.
####   Path Variables:
   id (Long) - Müşterinin kimlik numarası.
####  Request:
####   Örnek: http://localhost:8080/customer/2
####   Response:
   HTTP Status: 200 OK
####   Body:
   ```
   {
    "customerId": 2,
    "customerName": "Bob Smith",
    "customerPhone": "+9876543210",
    "customerMail": "bob.smith@example.com",
    "customerAddress": "456 Maple Avenue",
    "customerCity": "Gotham"
}
   ```
## Yeni Müşteri Ekleme
####   Endpoint: POST http://localhost:8080/customer/save
####  Description: Yeni bir müşteri ekler.
####   Request:
####   Body:
   ```
   {
  "customerName": "Eva Davis",
  "customerPhone": "+4445556666",
  "customerMail": "eva.davis@example.com",
  "customerAddress": "202 Elm Street",
  "customerCity": "Central City"
   }
   ```
####   Response:
   HTTP Status: 200 OK
####    Body:
   ```
   {
    "customerId": 5,
    "customerName": "Eva Davis",
    "customerPhone": "+4445556666",
    "customerMail": "eva.davis@example.com",
    "customerAddress": "202 Elm Street",
    "customerCity": "Central City"
}
   ```
## Tüm Müşterileri Listeleme
####   Endpoint: GET http://localhost:8080/customer/find-all
####    Description: Sistemde kayıtlı tüm müşterileri getirir.
####    Request:
####   Örnek: http://localhost:8080/customer/find-all
####  Response:
   HTTP Status: 200 OK
####   Body:
   ```
   [
    {
        "customerId": 1,
        "customerName": "Alice Johnson",
        "customerPhone": "+1234567890",
        "customerMail": "alice.johnson@example.com",
        "customerAddress": "123 Oak Street",
        "customerCity": "Metropolis"
    },
    {
        "customerId": 2,
        "customerName": "Bob Smith",
        "customerPhone": "+9876543210",
        "customerMail": "bob.smith@example.com",
        "customerAddress": "456 Maple Avenue",
        "customerCity": "Gotham"
    },
    {
        "customerId": 3,
        "customerName": "Charlie Brown",
        "customerPhone": "+1112223333",
        "customerMail": "charlie.brown@example.com",
        "customerAddress": "789 Pine Road",
        "customerCity": "Smallville"
    },
    {
        "customerId": 4,
        "customerName": "Diana Wilson",
        "customerPhone": "+9998887777",
        "customerMail": "diana.wilson@example.com",
        "customerAddress": "101 Cedar Lane",
        "customerCity": "Star City"
    },
    {
        "customerId": 5,
        "customerName": "Eva Davis",
        "customerPhone": "+4445556666",
        "customerMail": "eva.davis@example.com",
        "customerAddress": "202 Elm Street",
        "customerCity": "Central City"
    }
]
   ```
## Müşteriye Ait Hayvanları Getirme
####   Endpoint: GET http://localhost:8080/customer/{customerId}/animals
####   Description: Belirli bir müşteriye ait hayvanları getirir.
####   Path Variables:
   customerId (Long) - Müşterinin kimlik numarası.
####  Request:
####  Örnek: http://localhost:8080/customer/2/animals
####   Response:
   HTTP Status: 200 OK
####  Body:
   ```
  [
    {
        "animalId": 2,
        "animalName": "Whiskers",
        "species": "Cat",
        "breed": "Siamese",
        "gender": "Female",
        "color": "White",
        "birthDate": "2020-08-10",
        "customer": {
            "customerId": 2,
            "customerName": "Bob Smith",
            "customerPhone": "+9876543210",
            "customerMail": "bob.smith@example.com",
            "customerAddress": "456 Maple Avenue",
            "customerCity": "Gotham"
        }
    }
]
   ```
## İsimle Müşteri Arama
####   Endpoint: GET http://localhost:8080/customer/name/{name}
####  Description: Belirli bir isimle müşteri arar.
####   Path Variables:
   name (String) - Müşterinin adı.
####   Request:
####   Örnek: http://localhost:8080/customer/name/alice%20johnson
####   Response:
   HTTP Status: 200 OK
####   Body:
   ```
   [
    {
        "customerId": 1,
        "customerName": "Alice Johnson",
        "customerPhone": "+1234567890",
        "customerMail": "alice.johnson@example.com",
        "customerAddress": "123 Oak Street",
        "customerCity": "Metropolis"
    }
]
```

# Vaccine Entity Operasyonları
## Aşı Oluşturma
#### Endpoint: POST http://localhost:8080/vaccine/create
####  Description: Belirli bir hayvana aşı ekler.
####  Request:
####   Body:
   ```
   {
    "vaccineName": "Vaccine A",
    "vaccineCode": "VC001",
    "protectionStartDate": "2023-12-20",
    "protectionFinishDate": "2024-01-10",
    "animalId": 1
}
   ```
####   Response:
   HTTP Status: 201 Created
####   Body:
   ```
   {
    "animalId": 1,
    "vaccineId": 1,
    "vaccineName": "Vaccine A",
    "vaccineCode": "VC001",
    "protectionStartDate": "2023-12-20",
    "protectionFinishDate": "2024-01-10",
    "protectionExpired": false
}
   ```
## Hayvana Ait Aşı Bilgilerini Getirme
####   Endpoint: GET http://localhost:8080/vaccine/{animalId}
####   Description: Belirli bir hayvana ait aşı bilgilerini getirir.
####  Path Variables:
   animalId (Long) - Hayvanın kimlik numarası.
####   Request:
####   Örnek: http://localhost:8080/vaccine/3
####   Response:
   HTTP Status: 200 OK
####   Body:
   ```
   [
    {
        "vaccineId": 3,
        "vaccineName": "Canine Distemper Vaccine",
        "vaccineCode": "CV002",
        "protectionStartDate": "2023-11-15",
        "protectionFinishDate": "2024-01-01",
        "animal": {
            "animalId": 3,
            "animalName": "Rocky",
            "species": "Hamster",
            "breed": "Syrian",
            "gender": "Male",
            "color": "Brown",
            "birthDate": "2021-02-28",
            "customer": {
                "customerId": 3,
                "customerName": "Charlie Brown",
                "customerPhone": "+1112223333",
                "customerMail": "charlie.brown@example.com",
                "customerAddress": "789 Pine Road",
                "customerCity": "Smallville"
            }
        }
    }
]
   ```
## Belirli Tarih Aralığında Aşıları Sona Eren Hayvanları Getirme
####  Endpoint: GET http://localhost:8080/vaccine/expiring?startDate=2023-01-01&endDate=2023-12-31
####  Description: Belirli bir tarih aralığında aşıları sona eren hayvanları getirir.
####   Request:
####   Örnek: http://localhost:8080/vaccine/expiring?startDate=2023-01-01&endDate=2023-12-31
####  Response:
   HTTP Status: 200 OK
####  Body:
   ```
   [
    {
        "animalId": 5,
        "animalName": "Mittens",
        "species": "Cat",
        "breed": "Persian",
        "gender": "Female",
        "color": "Gray",
        "birthDate": "2017-04-12",
        "customer": {
            "customerId": 5,
            "customerName": "Eva Davis",
            "customerPhone": "+4445556666",
            "customerMail": "eva.davis@example.com",
            "customerAddress": "202 Elm Street",
            "customerCity": "Central City"
        }
    }
]
```