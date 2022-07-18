##					USER
###			http://localhost:8081/users

|Method          |Argument                       |Url                          |
|----------------|-------------------------------|-----------------------------|
|createUser           |(UserDto)      |/                  |
|updateUser           |(UserDto)      |/                  |
|deleteUser           |(UserDto)      |/                  |
|getUser              |(Long id)      |/{id}              |
|authenticateUser     |(LoginModel)   |/signin            |
|transferUserSummary  |(Long id)      |/usersummary/{id}  |
|isAuthenticated      |()             |/isAuthenticated   |
|getCurrentAdmin      |(UserPrincipal)|/meAdmin           |
|getCurrentUser       |(UserPrincipal)|/me                |

###			http://localhost:8081/roles

|Method          |Argument                       |Url                          |
|----------------|-------------------------------|-----------------------------|
|createRole           |(Role)      |/                  |
|updateRole           |(Role)      |/                  |
|deleteRole           |(Long)      |/{id}              |
|getRole              |(Long)      |/{id}              |

##					ADVERTISEMENT
###			http://localhost:8082/advertisements

|Method          |Argument                       |Url                          |
|----------------|-------------------------------|-----------------------------|
|getAllOrderByCreatedAt           |(HttpHeaders)                       |/                                          |
|createAdvertisement              |(HttpHeaders,AdvertisementDto)      |/                                          |
|updateAdvertisement              |(HttpHeaders,Long,AdvertisementDto) |/{id}                                      |
|findById                         |(Long id)                           |/{id}                                      |
|findAllByUser                    |(String)                            |/findbyuser/{username}                     |
|findByPriceBetween               |(Long,Long)                         |/findbypricebetween	@RequestParam min,max  |
|findByTitleOrDetailedMessage     |(String)                            |/findbytext/{text}                         |
|getAllByStatusPassive            |(HttpHeaders)	                     |/status                                    |
|setStatusActive                  |(HttpHeaders,Long)                  |/status/{id}                               |


##					MESSAGE QUEUE
###			http://localhost:8083/messages

|Method          |Argument                       |Url                          |
|----------------|-------------------------------|-----------------------------|
|produceMessage  |(AdvertisementDto)             |/                            |

##					User

- Öncelikle http://localhost:8081/users adresine UserDto gövdesi ile post isteği atılarak kullanıcı oluşturulur.
- UserDto, içerisinde username, password, name, surname, phonenumber, email ve boolean admin verilerini ister.
- Her kullanıcıya USER rolü standart olarak eklenir. boolean admin değişkeninin değerine göre kullanıcıya ADMIN rolü de eklenebilir.

- Kullanıcı oluşturulduktan sonra localhost:8081/users/signin adresine LoginModel gövdesi ile post isteği atılarak sisteme giriş yapılır.
- Login Dto, içerisinde username ve password verilerini ister.
- Giriş yapıldığında kullanıcı için bir token oluşturulur.

- localhost:8081/users/me adresine, ilgili token bilgisi ile get isteği atıldığında sistem tokenın ait olduğu kullanıcı bilgilerini verir.

- localhost:8081/users/isAuthenticated adresine bir kullanıcı tokenı ile get isteği yapıldığında sistem kullanıcının admin olup olmamasına göre bir boolean değer döner.
- Advertisement mikroservisi bu değeri sadece admin kullanıcının yapması beklenen işlemler için kullanır.

##					Role

- http://localhost:8081/roles adresine Role gövdesi ile post isteği atılarak role oluşturulabilir.
- Role, içerisinde name verisini ister.
- Post, Put, Delete, ve Get istekleri ile role öğeleri için CRUD işlemleri yapılabilir

##					Advertisement

- http://localhost:8082/advertisements adresine Header'da kullanıcının token verisi de olacak şekilde AdvertisementDto gövdesi Post isteği ile gönderilir.
- AdvertisementDto, içerisinde title, detailedmessage ve price bilgilerini ister. Oluşturulan ilanın hangi kullanıcıya ait olduğu header ile birlikte 
- gelen token bilgisinin, http://localhost:8081/users/me adresine istek göndermesi sayesinde belirlenir. Böylece kullanıcı girişi yapılmadan 
- ilan oluşturulamaz.

- CRUD işlemleri genel olarak benzer şekilde gerçekleştirilebilir. Put isteği ile ilan güncellenmek istendiğinde, bu isteği yapanın ilan sahibi
- olup olmadığı kontrol edilir.

- Oluşturulan ilanlar ilk olarak passive durumdadır. Ancak admin onayından geçtikten sonra herkes tarafından görülebilir olmaktadır.
- Admin tokenı ile http://localhost:8082/advertisements/status adresine get isteği atıldığında passive durumdaki tüm ilanlar listelenir.
- İlgili ilanın id'si PathVariable olarak ilgili adrese eklenip put isteği gönderilirse ilan aktif duruma geçer.
- Bu durumda advertisement mikroservisi, message mikroservisine ilgili ilanın bilgilerini gönderir.

##					Message

- advertisement mikroservisinden gönderilen ilan bilgileri alındığında veriler queue'ya eklenir.
- Queue message consume edildiğinde 'active' duruma geçen ilan verileri ile bir rapor oluşturulur ve database'e eklenir.
