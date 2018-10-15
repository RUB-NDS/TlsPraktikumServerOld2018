package de.rub.nds.praktikum.messages.extensions;

import de.rub.nds.praktikum.constants.ExtensionType;
import de.rub.nds.praktikum.constants.NamedGroup;
import de.rub.nds.praktikum.messages.Serializer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a supported groups extension. The supported groups
 * extension is used to inform the other party about all supported named groups
 *
 */
public class SupportedGroupsExtension extends Extension {

    private final List<NamedGroup> namedGroupList;

    /**
     * Constructor
     *
     * @param namedGroupList a list of supported named groups
     */
    public SupportedGroupsExtension(List<NamedGroup> namedGroupList) {
        super(ExtensionType.SUPPORTED_GROUPS);
        this.namedGroupList = namedGroupList;
    }

    /**
     * Returns the list of named groups
     *
     * @return the list of named groups
     */
    public List<NamedGroup> getNamedGroupList() {
        return Collections.unmodifiableList(namedGroupList);
    }

    /**
     * Returns a supported groups serializer
     *
     * @return a supported groups serializer
     */
    @Override
    public Serializer getSerializer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
