package de.studiocode.invui.window.impl.merged.split;

import de.studiocode.invui.gui.GUI;
import de.studiocode.invui.gui.SlotElement;
import de.studiocode.invui.util.Pair;
import de.studiocode.invui.util.SlotUtils;
import de.studiocode.invui.window.Window;
import de.studiocode.invui.window.impl.merged.MergedWindow;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * A {@link Window} where top and player {@link Inventory} are affected by different {@link GUI}s.
 */
public abstract class SplitWindow extends MergedWindow {
    
    private final GUI upperGui;
    private final GUI lowerGui;
    
    public SplitWindow(Player player, BaseComponent[] title, GUI upperGui, GUI lowerGui, Inventory upperInventory, boolean initItems, boolean closeable, boolean removeOnClose) {
        super(player, title, upperGui.getSize() + lowerGui.getSize(), upperInventory, closeable, removeOnClose);
        this.upperGui = upperGui;
        this.lowerGui = lowerGui;
        
        upperGui.addParent(this);
        lowerGui.addParent(this);
        if (initItems) initUpperItems();
    }
    
    @Override
    public void handleSlotElementUpdate(GUI child, int slotIndex) {
        redrawItem(child == upperGui ? slotIndex : upperGui.getSize() + slotIndex,
            child.getSlotElement(slotIndex), true);
    }
    
    @Override
    public SlotElement getSlotElement(int index) {
        if (index >= upperGui.getSize()) return lowerGui.getSlotElement(index - upperGui.getSize());
        else return upperGui.getSlotElement(index);
    }
    
    @Override
    protected Pair<GUI, Integer> getWhereClicked(InventoryClickEvent event) {
        Inventory clicked = event.getClickedInventory();
        if (clicked == getUpperInventory()) {
            return new Pair<>(upperGui, event.getSlot());
        } else {
            int index = SlotUtils.translatePlayerInvToGui(event.getSlot());
            return new Pair<>(lowerGui, index);
        }
    }
    
    @Override
    protected Pair<GUI, Integer> getGuiAt(int index) {
        if (index < upperGui.getSize()) return new Pair<>(upperGui, index);
        else if (index < (upperGui.getSize() + lowerGui.getSize()))
            return new Pair<>(lowerGui, index - upperGui.getSize());
        else return null;
    }
    
    @Override
    public GUI[] getGuis() {
        return new GUI[] {upperGui, lowerGui};
    }
    
}
