package de.rub.nds.praktikum;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Collection;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.crypto.tls.Certificate;
import org.bouncycastle.crypto.tls.TlsUtils;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

public class Main {

    public static void main(String args[]) throws IOException, FileNotFoundException, CertificateException {
        Certificate cert = readCertificate(new FileInputStream(new File("cert.pem")));
        PrivateKey key = readPrivateKey(new FileInputStream(new File("key.pem")));

        TlsServerSocket serverSocket = new TlsServerSocket(4433, cert, key);

        while (true) {
            Socket socket = serverSocket.accept();
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        socket.getOutputStream().write("Greetings!".getBytes());
                        socket.getOutputStream().flush();
                        int read;
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        do {
                            read = socket.getInputStream().read();
                            stream.write(read);
                        } while (read != 10);
                        socket.close();
                        System.out.println(new String(stream.toByteArray()));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    public static PrivateKey readPrivateKey(InputStream stream) throws IOException {
        InputStreamReader reader = new InputStreamReader(stream);
        try (PEMParser parser = new PEMParser(reader)) {
            Object obj = parser.readObject();
            if (obj instanceof PEMKeyPair) {
                PEMKeyPair pair = (PEMKeyPair) obj;
                obj = pair.getPrivateKeyInfo();
            } else if (obj instanceof ASN1ObjectIdentifier) {
                obj = parser.readObject();
                PEMKeyPair pair = (PEMKeyPair) obj;
                obj = pair.getPrivateKeyInfo();
            }
            PrivateKeyInfo privKeyInfo = (PrivateKeyInfo) obj;
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            return converter.getPrivateKey(privKeyInfo);
        } catch (Exception E) {
            throw new IOException("Could not read private key", E);
        } finally {
            stream.close();
            reader.close();
        }
    }

    public static Certificate readCertificate(InputStream stream) throws FileNotFoundException, CertificateException,
            IOException {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends java.security.cert.Certificate> certs = certFactory.generateCertificates(stream);
        java.security.cert.Certificate sunCert = (java.security.cert.Certificate) certs.toArray()[0];
        byte[] certBytes = sunCert.getEncoded();
        ASN1Primitive asn1Cert = TlsUtils.readASN1Object(certBytes);
        org.bouncycastle.asn1.x509.Certificate cert = org.bouncycastle.asn1.x509.Certificate.getInstance(asn1Cert);
        org.bouncycastle.asn1.x509.Certificate[] certs2 = new org.bouncycastle.asn1.x509.Certificate[1];
        certs2[0] = cert;
        org.bouncycastle.crypto.tls.Certificate tlsCerts = new org.bouncycastle.crypto.tls.Certificate(certs2);
        return tlsCerts;
    }
}
