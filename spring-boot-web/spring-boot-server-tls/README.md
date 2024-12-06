# spring-boot-tls

Secure communication plays an important role in modern applications. Communication between client and server over plain
HTTP is not secure. For a production-ready application, we should enable HTTPS via the TLS (Transport Layer Security)
protocol in our application. In this tutorial, we’ll discuss how to enable TLS technology in a Spring Boot application.

## TLS Protocol

TLS provides protection for data in transit between client and server and is a key component of the HTTPS protocol. The
Secure Sockets Layer (SSL) and TLS are often used interchangeably,
but [they aren’t the same](https://www.baeldung.com/cs/ssl-vs-tls). In fact, TLS is the successor of SSL. TLS can be
implemented either one-way or two-way.

- **One-Way TLS**
    - In one-way TLS, only the client verifies the server to ensure that it receives data from the trusted server. For
      implementing one-way TLS, the server shares its public certificate with the clients.
- **Two-Way TLS**
    - In two-way TLS or Mutual TLS (mTLS), both the client and server authenticate each other to ensure that both
      parties involved in the communication are trusted. For implementing mTLS, both parties share their public
      certificates with each other.

## Configuring TLS in Spring Boot

We’ll use either of the following certificate formats:

- PKCS12: Public Key Cryptographic Standards is a password protected format that can contain multiple certificates and keys; it’s an industry-wide used format.
- JKS: Java KeyStore is similar to PKCS12; it’s a proprietary format and is limited to the Java environment.


### Generating a Key Pair

To enable TLS, we need to create a public/private key pair.

```bash 
keytool -genkeypair -alias test -keyalg RSA -validity 3650 \
    -storetype PKCS12 -keystore keystore.p12 -storepass changeit \
    -dname "CN=localhost,OU=Unit,O=Organization,L=City,S=State,C=CN" 
```

### Extract a Self-signed Certificate from the Keystore

```bash
keytool -export -alias test -keystore keystore.p12 -rfc -file public.cert
```

### Export public key

```bash
keytool -list -rfc --keystore keystore.p12 -storepass changeit | \
    openssl x509 -inform pem -pubkey > keystore.pub
```

### Export private key

```bash
keytool -importkeystore -srckeystore keystore.p12 -srcstorepass changeit \
    -destkeystore keystore.p12 -deststoretype PKCS12 \
    -deststorepass changeit -destkeypass changeit

openssl pkcs12 -in keystore.p12 -nodes -nocerts -out keystore.priv
```

### Configuring TLS in Spring

Let’s start by configuring one-way TLS. We configure the TLS related properties in the `application.properties` file:

````properties
server.ssl.enabled=true
server.ssl.key-store-type=PKCS12
server.ssl.key-store=classpath:keystore/keystore.p12
server.ssl.key-store-password=changeit
server.ssl.protocol=TLS
server.ssl.enabled-protocols=TLSv1.2
````

### Configuring mTLS in Spring

For enabling `mTLS`, we use the client-auth attribute with the need value:

```properties
server.ssl.client-auth=need
server.ssl.trust-store=classpath:keystore/truststore.p12
server.ssl.trust-store-password=changeit
```

## Test

For invoking the REST API, we’ll use the curl tool:

```bash
curl -v http://localhost:8443/
```

Since we didn’t specify https, it will output an error:

```
Bad Request
This combination of host and port requires TLS.
```

This issue is solved by using the https protocol:

```bash
curl -v https://localhost:8443/
```

However, this gives us another error:

```bash
SSL certificate problem: self signed certificate
```

This happens when we’re using a self-signed certificate. To fix this, we must use the server certificate in the client
request. First, we’ll copy the server certificate `keystore.cer` from the server keystore file.

Then we’ll use the server certificate in the curl request along with the `–cacert` option:

```bash
curl --cacert public.cert https://localhost:8443/
```