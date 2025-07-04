#include <SPI.h>
#include <Ethernet.h>

byte mac[] = { 0, 0, 0, 0, 0, 0 };
IPAddress ip(0, 0, 0, 0);
EthernetServer server(0);
String readString;
const int ledPin = 9;

void setup() {
  Serial.begin(9600);
  pinMode(ledPin, OUTPUT);
  Ethernet.begin(mac, ip);

  server.begin();
  Serial.print("Server is at: ");
  Serial.println(Ethernet.localIP());
}

void loop() {
  EthernetClient client = server.available();
  if (client) {
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();

        if (readString.length() < 100) {
          readString += c;
        }

        if (c == '\n') {
          Serial.println(readString);
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");
          client.println();
          client.println("<!DOCTYPE html>");
          client.println("<html>");
          client.println("<head>");
          client.println("<title>LED Webserver</title>");
          client.println("<script>");
          client.println("function sendRequest(cmd) {");
          client.println("  var xhttp = new XMLHttpRequest();");
          client.println("  xhttp.open('GET', '/?' + cmd, true);");
          client.println("  xhttp.send();");
          client.println("}");
          client.println("</script>");
          client.println("</head>");
          client.println("<body>");
          client.println("<h2>LED Control</h2>");
          client.println("<button onclick=\"sendRequest('button1on')\">LED ON</button>");
          client.println("<button onclick=\"sendRequest('button2off')\">LED OFF</button>");
          client.println("</body>");
          client.println("</html>");

          delay(1);
          client.stop();

          if (readString.indexOf("?button1on") > 0) {
            digitalWrite(ledPin, HIGH);
          }

          if (readString.indexOf("?button2off") > 0) {
            digitalWrite(ledPin, LOW);
          }
          readString = "";
        }
      }
    }
  }
}
