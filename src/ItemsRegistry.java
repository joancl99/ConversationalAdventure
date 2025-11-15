import java.util.HashMap;
import java.util.Map;

public class ItemsRegistry 
{
    private static final Map<String, ItemType> registry = new HashMap<>();

    static 
    {
        // Register the items auto
        for (Items item : Items.values()) 
        {
            registry.put(item.getItemName(), item);
        }
    }

    public static ItemType getItemByName(String name) 
    {
        return registry.get(name);
    }
}