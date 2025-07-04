# Arduino Ethernet LED Controller (Web & Android)

Αυτό το project επιτρέπει τον **έλεγχο ενός LED** συνδεδεμένου σε **Arduino Mega 2560** με **Ethernet Shield (HanRun W5100)**, μέσω ενός **Web server** και **Android app** που στέλνουν HTTP αιτήματα.

---

## Υλικά / Hardware

- Arduino Mega 2560  
- Ethernet Shield (HanRun W5100 ή συμβατό)  
- LED  
- Αντιστάτης (220Ω ή 330Ω)  
- Android κινητό με σύνδεση στο ίδιο δίκτυο  
- Καλώδιο Ethernet  
- Τροφοδοσία για το Arduino (μέσω USB ή εξωτερική)

---

## Συνδεσμολογία

| Arduino Pin | Συσκευή |
|-------------|----------|
| D9          | Θετικό πόδι LED (μέσω αντίστασης)  
| GND         | Αρνητικό LED

Το Ethernet Shield συνδέεται πάνω στο Arduino Mega κατευθείαν (stacked).

---

## Android App (Java)

Η εφαρμογή αποτελείται από 2 κουμπιά:
- **LED ON** ➜ στέλνει αίτημα στο `/​?button1on`
- **LED OFF** ➜ στέλνει αίτημα στο `/​?button2off`

### Παράδειγμα αιτήματος:
```
GET http://anduinoIP/?button1on
```

### Τεχνολογίες:
- Java (Android)
- AsyncTask για HTTP αιτήματα

---

## Arduino Sketch

Το Arduino δημιουργεί έναν HTTP web server στη θύρα `0` και "ακούει" αιτήματα από το Android app.

### Λειτουργίες:
- `/​?button1on` ➜ ενεργοποιεί το LED (pin 9)
- `/​?button2off` ➜ απενεργοποιεί το LED

Η απάντηση περιέχει και ένα απλό web UI με κουμπιά για έλεγχο μέσω browser.

---

## Ρυθμίσεις Δικτύου

Η IP του Arduino ορίζεται **σταθερά**:
```cpp
IPAddress ip(0, 0, 0, 0); // arduinoIP
```
>Φρόντισε να είναι **στο ίδιο δίκτυο** με το κινητό Android.

---

## Build Οδηγίες

### Arduino
1. Άνοιξε το Arduino IDE
2. Κάνε επικόλληση τον κώδικα του sketch
3. Σύνδεσε το Arduino και ανέβασε τον κώδικα

### Android App
1. Άνοιξε το Android Studio
2. Ρύθμισε την IP στα αρχεία `MainActivity.java` και `network_security_config.xml`:
```java
private static final String SERVER_URL = "http:// arduinoIP";
```
```xml
 <domain includeSubdomains="true"> arduinoIP </domain>
```

3. Τρέξε την εφαρμογή σε Android κινητό ή emulator που έχει πρόσβαση στο ίδιο δίκτυο

---

## Screenshots

- Android app με 2 κουμπιά (LED ON / OFF)
  
  ![android_buttons](https://github.com/user-attachments/assets/f49389f1-cec6-4035-ac4d-fd784366d127)

- Web interface από τον Arduino με ίδιο έλεγχο
  
  ![web_buttons](https://github.com/user-attachments/assets/8db40c17-0329-4097-9d0c-c4c74ae52558)

---

## Έλεγχος

Μπορείς να ελέγξεις το LED:
- Μέσω Android εφαρμογής
- Μέσω browser, πηγαίνοντας στο:  `http:// arduinoIP`
