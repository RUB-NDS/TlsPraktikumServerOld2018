package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.constants.HandshakeMessageType;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a TLS certificate message. An certificate message
 * consists of of a certificate request context and a list of certificate
 * entries. The certificate request is not necessary for this course and can be
 * 0x00 Each certificate entrie consits of a certificate and an optional list of
 * certificate extensions. Certificate extensions are not necessary for this
 * course.
 *
 */
public class CertificateMessage extends HandshakeMessage {

    //TODO change into list of certificate entries, nobody understands this otherwise
    private List<byte[]> certificateList;

    /**
     *
     * @param certificateList
     */
    public CertificateMessage(List<byte[]> certificateList) {
        super(HandshakeMessageType.CERTIFICATE.getValue());
        this.certificateList = certificateList;
    }

    /**
     *
     * @return
     */
    public List<byte[]> getCertificateList() {
        return Collections.unmodifiableList(certificateList);
    }

    /**
     *
     * @param certificateList
     */
    public void setCertificateList(List<byte[]> certificateList) {
        this.certificateList = certificateList;
    }
}
