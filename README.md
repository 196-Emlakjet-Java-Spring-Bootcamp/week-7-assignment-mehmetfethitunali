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

- ??ncelikle http://localhost:8081/users adresine UserDto g??vdesi ile post iste??i at??larak kullan??c?? olu??turulur.
- UserDto, i??erisinde username, password, name, surname, phonenumber, email ve boolean admin verilerini ister.
- Her kullan??c??ya USER rol?? standart olarak eklenir. boolean admin de??i??keninin de??erine g??re kullan??c??ya ADMIN rol?? de eklenebilir.

- Kullan??c?? olu??turulduktan sonra localhost:8081/users/signin adresine LoginModel g??vdesi ile post iste??i at??larak sisteme giri?? yap??l??r.
- Login Dto, i??erisinde username ve password verilerini ister.
- Giri?? yap??ld??????nda kullan??c?? i??in bir token olu??turulur.

- localhost:8081/users/me adresine, ilgili token bilgisi ile get iste??i at??ld??????nda sistem token??n ait oldu??u kullan??c?? bilgilerini verir.

- localhost:8081/users/isAuthenticated adresine bir kullan??c?? token?? ile get iste??i yap??ld??????nda sistem kullan??c??n??n admin olup olmamas??na g??re bir boolean de??er d??ner.
- Advertisement mikroservisi bu de??eri sadece admin kullan??c??n??n yapmas?? beklenen i??lemler i??in kullan??r.

##					Role

- http://localhost:8081/roles adresine Role g??vdesi ile post iste??i at??larak role olu??turulabilir.
- Role, i??erisinde name verisini ister.
- Post, Put, Delete, ve Get istekleri ile role ????eleri i??in CRUD i??lemleri yap??labilir

##					Advertisement

- http://localhost:8082/advertisements adresine Header'da kullan??c??n??n token verisi de olacak ??ekilde AdvertisementDto g??vdesi Post iste??i ile g??nderilir.
- AdvertisementDto, i??erisinde title, detailedmessage ve price bilgilerini ister. 
- Olu??turulan ilan??n hangi kullan??c??ya ait oldu??u header ile birlikte gelen token bilgisinin, http://localhost:8081/users/me adresine istek g??ndermesi sayesinde belirlenir. 
- B??ylece kullan??c?? giri??i yap??lmadan ilan olu??turulamaz.

- CRUD i??lemleri genel olarak benzer ??ekilde ger??ekle??tirilebilir. Put iste??i ile ilan g??ncellenmek istendi??inde, bu iste??i yapan??n ilan sahibi olup olmad?????? kontrol edilir.

- Olu??turulan ilanlar ilk olarak passive durumdad??r. Ancak admin onay??ndan ge??tikten sonra herkes taraf??ndan g??r??lebilir olmaktad??r.
- Admin token?? ile http://localhost:8082/advertisements/status adresine get iste??i at??ld??????nda passive durumdaki t??m ilanlar listelenir.
- ??lgili ilan??n id'si PathVariable olarak ilgili adrese eklenip put iste??i g??nderilirse ilan aktif duruma ge??er.
- Bu durumda advertisement mikroservisi, message mikroservisine ilgili ilan??n bilgilerini g??nderir.

##					Message

- advertisement mikroservisinden g??nderilen ilan bilgileri al??nd??????nda veriler queue'ya eklenir.
- Queue message consume edildi??inde 'active' duruma ge??en ilan verileri ile bir rapor olu??turulur ve database'e eklenir.
