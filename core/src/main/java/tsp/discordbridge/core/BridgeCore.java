package tsp.discordbridge.core;

import tsp.discordbridge.core.manager.DataManager;
import tsp.discordbridge.core.manager.LinkageManager;

/**
 * This is the core of the plugin, each module implements this in their own way.
 *
 * @author TheSilentPro (Silent)
 */
public interface BridgeCore {

    DataManager getDataManager();

    LinkageManager getLinkageManager();

}
