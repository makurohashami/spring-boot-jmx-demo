<h1 align="center">Spring Boot JMX Demo</h1>

---

### About

---

Demo application that demonstrates the features of JMX in combination with Spring Boot

### Run

---

To use client with server please add following VM run options to server app:

```
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.port=9010
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
```
