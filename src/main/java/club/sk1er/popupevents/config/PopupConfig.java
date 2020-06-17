package club.sk1er.popupevents.config;

import club.sk1er.vigilance.Vigilant;
import club.sk1er.vigilance.data.Property;
import club.sk1er.vigilance.data.PropertyType;

import java.io.File;

public class PopupConfig extends Vigilant {

    @Property(
        type = PropertyType.SWITCH, name = "Ping On Request",
        category = "Popup Events", subcategory = "General",
        description = "Receive a ding noise when someone sends you a request."
    )
    public static boolean pingRequest = true;

    @Property(
        type = PropertyType.SWITCH, name = "Friend Requests",
        category = "Popup Events", subcategory = "Request Types",
        description = "Receive popups for Friend Requests."
    )
    public static boolean friendRequestsRequest = true; // lol

    @Property(
        type = PropertyType.SWITCH, name = "Party Invites",
        category = "Popup Events", subcategory = "Request Types",
        description = "Receive popups for Party Requests."
    )
    public static boolean partyInviteRequest = true;

    @Property(
        type = PropertyType.SWITCH, name = "Guild Invites",
        category = "Popup Events", subcategory = "Request Types",
        description = "Receive popups for Guild Invites."
    )
    public static boolean guildInviteRequest = true;

    @Property(
        type = PropertyType.SWITCH, name = "Guild Party Invites",
        category = "Popup Events", subcategory = "Request Types",
        description = "Receive popups for Guild Party Invites."
    )
    public static boolean guildPartyInviteRequest = true;

    @Property(
        type = PropertyType.SWITCH, name = "Skyblock Trade Requests",
        category = "Popup Events", subcategory = "Request Types",
        description = "Receive popups for Skyblock Trade Requests."
    )
    public static boolean skyblockTradeRequest = true;

    @Property(
        type = PropertyType.SWITCH, name = "Duel Requests",
        category = "Popup Events", subcategory = "Request Types",
        description = "Receive popups for Duel Requests."
    )
    public static boolean duelRequests = true;

    public PopupConfig() {
        super(new File("./config/popup_events.toml"));
        initialize();
    }
}
