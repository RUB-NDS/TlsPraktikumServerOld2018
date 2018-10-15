package de.rub.nds.praktikum.messages.extensions;

import de.rub.nds.praktikum.messages.Parser;

public class SupportedGroupsExtensionParser extends Parser<SupportedGroupsExtension> {

    public SupportedGroupsExtensionParser(byte[] array) {
        super(array);
    }

    @Override
    public SupportedGroupsExtension parse() {
        throw new UnsupportedOperationException("Add code here");
    }

}
