# Silnik Transakcyjny Rynku Kapitałowego

Projekt został zrealizowany zgodnie z zasadami Java Code Conventions.

## Specyfikacja formatu pliku portfela (portfolio.txt)

Stan portfela zapisywany jest w formacie tekstowym z użyciem separatora `|`. Plik składa się z trzech typów rekordów:

1. **Gotówka (Nagłówek):**
   `CASH|kwota`
   
2. **Aktywo:**
   Definiuje instrument finansowy. Linie `LOT` występujące po nim należą do tego aktywa.
   `ASSET|TYP|SYMBOL|NAZWA|ŁĄCZNA_ILOŚĆ|PARAMETR_DODATKOWY`
   * *Parametr dodatkowy:* Prowizja (SHARE/COMMODITY) lub Spread (CURRENCY).

3. **Partia Zakupu (Purchase Lot):**
   Historia transakcji kupna dla powyższego aktywa.
   `LOT|DATA_ISO_8601|ILOŚĆ|CENA_ZAKUPU`

### Przykładowy zapis:

CASH|15000.50
ASSET|SHARE|AAPL|Apple Inc.|15.0|5.0
LOT|2023-10-01T10:00:00|10.0|145.0
LOT|2023-11-15T12:30:00|5.0|155.0
ASSET|CURRENCY|USD|Dolar|1000.0|0.02
LOT|2023-09-01T09:00:00|1000.0|4.20
