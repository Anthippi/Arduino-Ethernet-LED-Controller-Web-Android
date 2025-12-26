# Arduino Ethernet LED Controller (Web & Android)

This project allows you to **control an LED** connected to an **Arduino Mega 2560** with an **Ethernet Shield (HanRun W5100)** via a **Web server** and an **Android app** that send HTTP requests.

---

## Components / Hardware

- Arduino Mega 2560  
- Ethernet Shield (HanRun W5100 or compatible)  
- LED  
- Resistor (220Ω or 330Ω)  
- Android phone connected to the same network  
- Ethernet cable  
- Power supply for Arduino (via USB or external)

---

## Wiring

| Arduino Pin | Device                     |
|-------------|----------------------------|
| D9          | LED positive leg (via resistor) |
| GND         | LED negative leg           |

The Ethernet Shield is stacked directly on top of the Arduino Mega.

<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/71a4a3c5-73b9-4e80-8830-3d7a65a2af2f" />


---

## Android App (Java)

The app contains two buttons:
- **LED ON** ➜ sends request to `/​?button1on`
- **LED OFF** ➜ sends request to `/​?button2off`

### Example Request:
```
GET http://arduinoIP/?button1on
```

### Technologies Used:
- Java (Android)
- `AsyncTask` for sending HTTP requests

---

## Arduino Sketch

The Arduino creates a basic HTTP web server on port `0` and listens for requests from the Android app.

### Functions:
- `/​?button1on` ➜ turns the LED ON (pin 9)
- `/​?button2off` ➜ turns the LED OFF

The response includes a simple web UI with control buttons for use in a browser.

---

## Network Configuration

Set a **static IP address** for the Arduino:
```cpp
IPAddress ip(0, 0, 0, 0); // arduinoIP
```
> Make sure it's on the same network as your Android phone.

## Build Instructions

### Arduino
1. Open Arduino IDE
2. Paste the sketch code
3. Connect your Arduino and upload the code

### Android App
1. Open Android Studio
2. Set the Arduino IP in both `MainActivity.java` and `network_security_config.xml`:
```java
private static final String SERVER_URL = "http:// arduinoIP";
```
```xml
<domain includeSubdomains="true"> arduinoIP </domain>
```
3. Run the app on an Android phone or emulator connected to the same network
---

## Screenshots
- Android app with 2 buttons (LED ON / OFF)
  
   ![android_buttons](https://github.com/user-attachments/assets/f49389f1-cec6-4035-ac4d-fd784366d127)
- Web interface served by the Arduino
  
   ![web_buttons](https://github.com/user-attachments/assets/8db40c17-0329-4097-9d0c-c4c74ae52558)

## LED Control Options
You can control the LED:
- Via the Android app
- Via a web browser by navigating to: `http://arduinoIP`
