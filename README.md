## Mitä Retrofit tekee
Retrofit on kirjasto, joka hoitaa HTTP-pyynnöt (esim. GET, POST) ja muuntaa palvelimen vastaukset olioiksi, jotta niitä on helppo käyttää sovelluksessa.

## HTTP-pyyntöjen hallinta
Teet interface-rajapinnan, jossa määrittelet endpointit @GET tai @POST.
Retrofit muodostaa oikean URL-osoitteen, lähettää pyynnön ja palauttaa vastauksen määrittelemässäsi muodossa.

## Miten JSON muutetaan dataluokiksi
API palauttaa JSON-muotoista dataa. Retrofit lukee JSONin ja mapittaa sen Kotlin data class -rakenteisiin kenttien nimien perusteella.

## Gson hoitaa muunnoksen taustalla
Gson on JSON-kirjasto, joka lukee JSON-tekstin ja luo siitä olion. Sinun ei tarvitse käsitellä JSON-stringiä itse.

## Miten coroutines toimii tässä
Kutsu suoritetaan taustasäikeessä ilman että UI jumittuu.
Kun vastaus saadaan, coroutine palaa pääsäikeelle ja voit päivittää tilan.

## Miten UI-tila toimii
ViewModel sisältää WeatherUiState-olion.
Kun ViewModel päivittää tilan (esim. Loading → Success), Compose huomaa muutoksen ja piirtää UI:n uudelleen automaattisesti.

## Miten API-key on tallennettu
API-key laitetaan local.properties-tiedostoon.
Se siirretään BuildConfig-muuttujaan Gradlen kautta.
Retrofit lukee sen BuildConfig-arvosta ja lisää sen API-kutsuun.

## Week 6 (Room)

### Mitä Room tekee tässä projektissa?
Room toimii lokaalina tietokantana ja välimuistina säätiedoille.
Sen avulla viimeisin haettu säätieto tallentuu laitteelle ja se näkyy myös ilman uutta API-kutsua.

### Projektin rakenne
Entity
- WeatherEntity määrittelee tietokantataulun rakenteen. Se sisältää kaupungin nimen, kuvauksen,
lämpötilan, ikonin ja timestampin.
DAO
- WeatherDao sisältää SQL-kyselyt.
- 
Database
- AppDatabase yhdistää Entityn ja DAO:n. Se myös vastaa Room-tietokannan instanssin luomisesta.

Repository
- Yhdistää API:n ja Roomin.
- Sisältää välimuistilogiikan.

ViewModel
- Välittää datan UI:lle.
- Käynnistää API-haun tarvittaessa.
- UI kuuntelee Roomista tulevaa Flow-dataa.

UI
- Näyttää säätiedot.
- Päivittyyy automaattisesti Roomin datan muuttuessa.

## Miten datavirta kulkee?
UI → ViewModel → Repository → (Room + API)
Room → ViewModel → UI

## Miten välimuistilogiikka toimii?
Repository tarkistaa, onko viimeisimmän säätiedon timestamp yli 30min vanha.
Jos data on alle 30min vanha:
- Käytetään Roomissa olevaa dataa.
- Ei tehdä API-kutsua.

Jos data on yli 30min vanha:
-Tehdään uusi API-kutsu.
-Päivitetään Room.
-UI päivittyy automaattisesti.