# Veteriner Uygulaması

## Açıklama

Bu proje, hem frontend hem backend kullanarak veterinerlerin müşteri ve hayvan verilerini yönetmelerine olanak sağlayan bir uygulamadır. Veterinerler müşterilerini ve müşterilere ait hayvanları ve doktorları ekleyebilir, güncelleyebilir, silebilir ve arayabilirler. Ayrıca randevu oluşturabilir, aşı ekleyebilir ve takip edebilirler.

## Kullanılan Teknolojiler

- Spring Boot
- React
- PostgreSQL
- Axios
- Lombok

## Frontend Deploy Linki (Backend bağlantılı)

https://react-vet-frontend.vercel.app/

## Backend Deploy Linki

https://reactvetbackend.onrender.com

## Frontend Repo Linki

https://github.com/dozdemirz/ReactVetFrontend


## API Temel Özellikleri

### Projede Bulunan Entity'ler
Animal
Customer
Vaccine
Doctor
AvailableDate
Appointment

### Hayvanların ve Sahiplerinin Yönetimi

- Hayvanları kaydetme, bilgilerini güncelleme, görüntüleme ve silme.
- Hayvan sahiplerini kaydetme, bilgilerini güncelleme, görüntüleme ve silme.
- Hayvan sahiplerini isme göre filtreleme.
- Hayvanları isme göre filtreleme.
- Hayvan sahibinin sistemde kayıtlı tüm hayvanlarını görüntüleme.
### Uygulanan Aşıların Yönetimi

- Hayvanlara uygulanan aşıları kaydetme, bilgilerini güncelleme, görüntüleme ve silme.
- Hayvan id’sine göre belirli bir hayvana ait tüm aşı kayıtlarını listeleme.
- Kullanıcının aşı koruyuculuk bitiş tarihi yaklaşan hayvanları listeleyebilmesi.
### Randevu Yönetimi

- Hayvanların aşı ve muayene randevularının oluşturulması, bilgilerinin güncellenmesi, görüntülenmesi ve silinmesi.
- Randevuların tarih ve saat içerecek şekilde kaydedilmesi.
- Randevu kaydı oluştururken doktorun müsait günleri ve saatleri kontrolü.
- Randevuların kullanıcı tarafından girilen tarih aralığına ve doktora göre filtrelenmesi.
- Randevuların kullanıcı tarafından girilen tarih aralığına ve hayvana göre filtrelenmesi.
### Veteriner Doktor Yönetimi

- Veteriner doktorların kaydedilmesi, bilgilerinin güncellenmesi, görüntülenmesi ve silinmesi.
### Doktorların Müsait Günlerinin Yönetimi
- Doktorların müsait günlerini ekleme, bilgilerini güncelleme, görüntüleme ve silme.