# jobsApi
Több forrásból adatokat gyűjtő álláskereső API

### Indítás:
src/main/java/com/example/jobsapi/JobsApiApplication.java

### Tesztek:
src/test/java/com/example/jobsapi/HttpRequestTest.java

### Részletes leírás:
1. Az alkalmazás biztosítson lehetőséget kliensalkalmazások regisztrációjára (POST /clients). A
kliens átadja a nevét (validáció: max 100 karakter), e-mail címét (validáció: érvényes email
cím formátum, bármilyen regexp használatával, valamint egyediség ellenőrzése). A
responseban a szerver egy api kulcsot ad vissza UUID formátumban.

2. Az alkalmazás biztosítson lehetőséget állások létrehozására (POST /positions). A kliens
átadja az állás megnevezését(validáció: max 50 karakter), a munkavégzés földrajzi
helyét(validáció: max 50 karakter). A szerver első lépésben ellenőrzi az api kulcs
érvényességét. Nem érvényes api kulcs esetén hibaüzenettel tér vissza. A szerver mentse el
az állást, majd térjen vissza egy URL-lel a responseban, hogy milyen oldalon érhető el a
pozició.

3. Az alkalmazás biztosítson lehetőséget állások keresésére (GET /positions). A kliens
átadja a keresett keywordöt (pl.: "finance", validáció: max 50 karakter) valamint a
lokációt (pl.: "london", validáció: max 50 karakter). A szerver első lépésben ellenőrzi az
api kulcs érvényességét. Nem érvényes api kulcs esetén hibaüzenettel tér vissza.
Érvényes api kulcs esetén az átadott adatokkal bekérdez a következő helyekre:
  - "External" Job API (http://localhost:8080/positions/search)
  - Adatbázisban tárolt állások

4. A fenti adatokat össze kell fésülni és a kliens alkalmazásnak visszaadni a következő
adatokat:
  - Job title - az állás megnevezése
  - Location - a munkavégzés földrajzi helye
  - URL - a hírdetéshez tartozó URL
