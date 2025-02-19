package de.studiocode.invui.window;

import de.studiocode.invui.gui.GUI;
import de.studiocode.invui.gui.GUIParent;
import de.studiocode.invui.item.Item;
import de.studiocode.invui.item.ItemProvider;
import de.studiocode.invui.virtualinventory.VirtualInventory;
import de.studiocode.invui.window.impl.merged.MergedWindow;
import de.studiocode.invui.window.impl.merged.combined.SimpleCombinedWindow;
import de.studiocode.invui.window.impl.merged.split.SimpleSplitWindow;
import de.studiocode.invui.window.impl.single.SimpleWindow;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * A window is the way to show a player a GUI.
 * Windows can only have one viewer.
 *
 * @see SimpleWindow
 * @see SimpleCombinedWindow
 * @see SimpleSplitWindow
 */
public interface Window extends GUIParent {
    
    /**
     * Gets the underlying {@link Inventory}s.
     *
     * @return The underlying {@link Inventory}s.
     */
    Inventory[] getInventories();
    
    /**
     * Gets the underlying {@link GUI}s.
     *
     * @return The underlying {@link GUI}s.
     */
    GUI[] getGuis();
    
    /**
     * A method called by the {@link WindowManager} to notify the {@link Window}
     * that one of its {@link Item}s has been clicked.
     *
     * @param event The {@link InventoryClickEvent} associated with this action.
     */
    void handleClick(InventoryClickEvent event);
    
    /**
     * A method called by the {@link WindowManager} to notify the {@link Window}
     * that {@link ItemStack}s have been dragged inside it.
     *
     * @param event The {@link InventoryDragEvent} associated with this action.
     */
    void handleDrag(InventoryDragEvent event);
    
    /**
     * A method called by the {@link WindowManager} to notify the {@link Window}
     * that {@link ItemStack}s have been shift-clicked from the lower
     * {@link Inventory} to this {@link Window}
     *
     * @param event The {@link InventoryClickEvent} associated with this action.
     */
    void handleItemShift(InventoryClickEvent event);
    
    /**
     * A method called by the {@link WindowManager} to notify the {@link Window}
     * that a {@link Player} is trying to collect {@link ItemStack} to the cursor
     * by double-clicking in the player inventory.
     * This method is not called when the player inventory is also part of the
     * {@link Window}. ({@link MergedWindow})
     *
     * @param event The {@link InventoryClickEvent} associated with this action.
     */
    void handleCursorCollect(InventoryClickEvent event);
    
    /**
     * A method called by the {@link WindowManager} to notify the {@link Window}
     * that its underlying {@link Inventory} is being opened.
     *
     * @param event The {@link InventoryOpenEvent} associated with this action.
     */
    void handleOpen(InventoryOpenEvent event);
    
    /**
     * A method called by the {@link WindowManager} to notify the {@link Window}
     * that its underlying {@link Inventory} is being closed.
     *
     * @param player The {@link Player} who closed this inventory.
     */
    void handleClose(Player player);
    
    /**
     * A method called by the {@link WindowManager} to notify the {@link Window}
     * that it's viewer has died.
     *
     * @param event The {@link PlayerDeathEvent} associated with this action.
     */
    void handleViewerDeath(PlayerDeathEvent event);
    
    /**
     * A method called by the {@link Item} itself to notify the {@link Window}
     * that its {@link ItemProvider} has been updated and the {@link ItemStack}
     * in the {@link Inventory} should be replaced.
     *
     * @param item The {@link Item} whose {@link ItemProvider} has been updated.
     */
    void handleItemProviderUpdate(Item item);
    
    /**
     * A method called by the {@link VirtualInventory} to notify the
     * {@link Window} that one if it's contents has been updated and the {@link ItemStack}'s
     * displayed in the {@link Inventory} should be replaced.
     *
     * @param virtualInventory The {@link VirtualInventory}
     */
    void handleVirtualInventoryUpdate(VirtualInventory virtualInventory);
    
    /**
     * Adds a close handler that will be called when this window gets closed.
     *
     * @param closeHandler The close handler to add
     */
    void addCloseHandler(Runnable closeHandler);
    
    /**
     * Removes a close handler that has been added previously.
     *
     * @param closeHandler The close handler to remove
     */
    void removeCloseHandler(Runnable closeHandler);
    
    /**
     * Removes the {@link Window} from the {@link WindowManager} list.
     * If this method is called, the {@link Window} can't be shown again.
     *
     * @param closeForViewer If the underlying {@link Inventory} should be closed for the viewer.
     */
    void close(boolean closeForViewer);
    
    /**
     * Gets if the {@link Window} is closed and can't be shown again.
     *
     * @return If the {@link Window} is closed.
     */
    boolean isClosed();
    
    /**
     * Closes the underlying {@link Inventory} for its viewer.
     */
    void closeForViewer();
    
    /**
     * Shows the window to the player.
     */
    void show();
    
    /**
     * Gets if the player is able to close the {@link Inventory}.
     *
     * @return If the player is able to close the {@link Inventory}.
     */
    boolean isCloseable();
    
    /**
     * Sets if the player should be able to close the {@link Inventory}.
     *
     * @param closeable If the player should be able to close the {@link Inventory}.
     */
    void setCloseable(boolean closeable);
    
    /**
     * Changes the title of the {@link Inventory}.
     *
     * @param title The new title
     */
    void changeTitle(@NotNull BaseComponent[] title);
    
    /**
     * Changes the title of the {@link Inventory}.
     *
     * @param title The new title
     */
    void changeTitle(@NotNull String title);
    
    /**
     * Gets the viewer of this {@link Window}
     *
     * @return The viewer of this window.
     */
    Player getViewer();
    
    /**
     * Gets the current {@link Player} that is viewing this
     * {@link Window} or null of there isn't one.
     *
     * @return The current viewer of this {@link Window} (can be null)
     */
    Player getCurrentViewer();
    
    /**
     * Gets the viewer's {@link UUID}
     *
     * @return The viewer's {@link UUID}
     */
    UUID getViewerUUID();
    
}
