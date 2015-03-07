package com.alex.vkdemo.dummy;

import com.alex.vkdemo.model.Item;
import com.alex.vkdemo.model.NewsDesc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class VkNewsItem {

    /**
     * An array of sample (dummy) items.
     */
    public static List<VkNewsItem> ITEMS = new ArrayList<VkNewsItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, VkNewsItem> ITEM_MAP = new HashMap<String, VkNewsItem>();
    public final Item item;

    public int id;
    static {
        // Add 3 sample items.
        addItem(new VkNewsItem(1, "Item 1"));
        addItem(new VkNewsItem(2, "Item 2"));
        addItem(new VkNewsItem(3, "Item 3"));
    }

    private NewsDesc description;

    public VkNewsItem(int s, String s1) {
        this.id=s;

        item = null;
    }

    public VkNewsItem() {

        item = null;
    }
    public VkNewsItem(Item item) {
        this.item=item;
    }

    private static void addItem(VkNewsItem item) {
        ITEMS.add(item);

    }

    public void setDescription(NewsDesc description) {
        this.description = description;
    }

    public NewsDesc getDescription() {
        return description;
    }

    public com.alex.vkdemo.model.Attachment getImage(int position) {

        if (item!=null)
            return item.getImage(position);
        return null;
    }

    public int getImageCount() {

        if (item==null)
            return 0;
       return item.getImageCount();
    }

    /**
     * A dummy item representing a piece of content.
     */

}
