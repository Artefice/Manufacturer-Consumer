package Main;

import Enums.Party;
import Enums.Size;

public class Request {
    private Party party;
    private Size size;

    public Request(Party party, Size size) {
        this.party = party;
        this.size = size;
    }

    public Party getParty() {
        return party;
    }

    public Size getSize() {
        return size;
    }
}
