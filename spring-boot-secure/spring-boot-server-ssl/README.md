# spring-boot-ssl

## Generating a Self-Signed Certificate

We’ll use either of the following certificate formats:

- PKCS12: Public Key Cryptographic Standards is a password protected format that can contain multiple certificates and keys; it’s an industry-wide used format.
- JKS: Java KeyStore is similar to PKCS12; it’s a proprietary format and is limited to the Java environment.

### Generating a Keystore

We can use the following command to generate our PKCS12 keystore format:

```bash 
keytool -genkeypair -alias test -keyalg RSA -validity 3650 \
    -storetype PKCS12 -keystore keystore.p12 -storepass changeit \
    -dname "CN=localhost,OU=Unit,O=Organization,L=City,S=State,C=CN" 
```

### Extract a Self-signed Certificate from the Keystore

```bash
keytool -export -alias test -keystore keystore.p12 -rfc -file public.cert
```

### ### Configuring ssl in Spring

Config application.properties file:

````properties
server.ssl.enabled=true
server.ssl.key-store-type=PKCS12
server.ssl.key-store=classpath:keystore/keystore.p12
server.ssl.key-store-password=changeit
server.ssl.key-alias=test
````

### Invoking an HTTPS URL from client

Define new properties for the trust store details in application.properties file

```properties
trust.store=classpath:keystore/keystore.p12
trust.store.password=changeit
```

Then we need to prepare an SSLContext with the trust store and create a customized RestTemplate:

```java
RestTemplate restTemplate() throws Exception {
    SSLContext sslContext = new SSLContextBuilder()
      .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
      .build();
    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
    HttpClient httpClient = HttpClients.custom()
      .setSSLSocketFactory(socketFactory)
      .build();
    HttpComponentsClientHttpRequestFactory factory = 
      new HttpComponentsClientHttpRequestFactory(httpClient);
    return new RestTemplate(factory);
}
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
curl failed to verify the legitimacy of the server and therefore could not
establish a secure connection to it. To learn more about this situation and
how to fix it, please visit the web page mentioned above.
```

This happens when we’re using a self-signed certificate. To fix this, we must use the server certificate in the client
request. First, we’ll copy the server certificate `keystore.cer` from the server keystore file.

Then we’ll use the server certificate in the curl request along with the `–cacert` option:

```bash
curl --cacert public.cert https://localhost:8443/
```