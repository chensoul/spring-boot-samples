# spring-boot-spring-tls

## Enabling Mutual TLS (mTLS)

We will use the [OpenSSL](https://www.openssl.org/) command line tool to generate the certificates.

1. **Generate CA**

First of all, we need a certificate authority (CA) that both the client and the server will trust. We generate these
using openssl.

```bash
mkdir -p tls/ca
openssl req -new -x509 -nodes -days 365 -subj '/CN=my-ca' -keyout tls/ca/ca.key -out tls/ca/ca.crt
```

This now puts a private key in ca.key and a certificate in ca.crt on our filesystem. We can inspect these a little
further with the following.

```bash
openssl x509 --in tls/ca/ca.crt -text --noout
```

Looking at the output, we see some interesting things about our CA certificate. Most importantly the X509v3 Basic
Constraints value is set CA:TRUE, telling us that this certificate can be used to sign other certificates (like CA
certificates can).

2. **Generate Server key and certificate**

The server now needs a key and certificate. Key generation is simple, as usual:

```bash
mkdir -p tls/server
openssl genrsa -out tls/server/tls.key 2048
```

We need to create a certificate that has been signed by our CA. This means we need to generate a certificate signing
request, which is then used to produce the signed certificate.

```bash
openssl req -new -key tls/server/tls.key -subj '/CN=localhost' -out tls/server/tls.csr
```

This gives us a signing request for the domain of localhost as mentioned in the -subj parameter. This signing request
now gets used by the CA to generate the certificate.

```bash
openssl x509 -req -in tls/server/tls.csr -CA tls/ca/ca.crt -CAkey tls/ca/ca.key -CAcreateserial -days 365 -out tls/server/tls.crt
```

Inspecting the server certificate, you can see that it’s quite a bit simpler than the CA certificate. We’re only able to
use this certificate for the subject that we nominated; localhost.

3. **Generate Client key and certificate**

The generation of the client certificates is very much the same as the server.

```bash
mkdir -p tls/client
# create a key
openssl genrsa -out tls/client/tls.key 2048

# generate a signing certificate
openssl req -new -key tls/client/tls.key -subj '/CN=my-client' -out tls/client/tls.csr

# create a certificate signed by the CA
openssl x509 -req -in tls/client/tls.csr -CA tls/ca/ca.crt -CAkey tls/ca/ca.key -CAcreateserial -days 365 -out tls/client/tls.crt
```

The subject in this case is my-client.

The `-CAcreateserial` number also ensures that we have unique serial numbers between the server and client certificates.
Again, this can be verified when you inspect the certificate.

## Test

1. **Update application.yml**

```yaml
server.ssl:
  bundle: server
  client-auth: NEED

spring.ssl:
  bundle:
    pem:
      server:
        keystore:
          certificate: classpath:tls/server/tls.crt
          private-key: classpath:tls/server/tls.key
        truststore:
          certificate: classpath:tls/ca/ca.crt
```

2. **Run Config Server**

Run the Config Server application:

```bash
java -jar target/spring-boot-spring-tls-0.0.1-SNAPSHOT.jar 
```

3. **Test with certificates and keys**

```bash
curl \
    --cacert tls/ca/ca.crt \
    --cert tls/client/tls.crt \
    --key tls/client/tls.key \
    https://localhost:8080
```