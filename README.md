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
