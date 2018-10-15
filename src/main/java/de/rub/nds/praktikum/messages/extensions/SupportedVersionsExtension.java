package de.rub.nds.praktikum.messages.extensions;

import de.rub.nds.praktikum.constants.ExtensionType;
import de.rub.nds.praktikum.constants.ProtocolVersion;
import de.rub.nds.praktikum.messages.Serializer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a SupportedVersions extension. The supported version
 * extension is used in tls 1.3 by the client to transmit all client supported
 * versions and used by the server to perform the acutal version selection. This
 * is done since some implementations reject client hello messages which promote
 * a higher version that 0x0303. Note that this extension looks slightly for cl
 *
 */
public class SupportedVersionsExtension extends Extension {

    private final List<ProtocolVersion> supportedVersions;

    /**
     * Constructor
     *
     * @param supportedVersions a List of supported versions which should be
     * transmitted
     */
    public SupportedVersionsExtension(List<ProtocolVersion> supportedVersions) {
        super(ExtensionType.SUPPORTED_VERSIONS);
        this.supportedVersions = supportedVersions;
    }

    public SupportedVersionsExtension(ProtocolVersion supportedVersion) {
        super(ExtensionType.SUPPORTED_VERSIONS);
        this.supportedVersions = new LinkedList<>();
        this.supportedVersions.add(supportedVersion);
    }

    /**
     * Returns the list of supported versions
     *
     * @return the list of supported versions
     */
    public List<ProtocolVersion> getSupportedVersions() {
        return Collections.unmodifiableList(supportedVersions);
    }

    /**
     * Returns a supported versions extension serializer
     *
     * @return a supported versions extension serializer
     */
    @Override
    public Serializer getSerializer() {
        return new SupportedVersionsExtensionSerializer(this);
    }
}
