package server.data;

import java.util.HashMap;
import java.util.Map;

import shared.Product;

public class TilesDTO {

    public static TilesDTO instance = new TilesDTO();

    private Map<Integer, Product> tiles = new HashMap<>();

    private TilesDTO() {
        ProductsDTO instance = ProductsDTO.instance;
        tiles.put(0, instance.get("1"));
        tiles.put(2, instance.get("2"));
        tiles.put(3, instance.get("3"));
        tiles.put(4, instance.get("4"));
        tiles.put(7, instance.get("5"));
        tiles.put(9, instance.get("6"));
    }

    public Map<Integer, Product> getTiles() {
        return tiles;
    }
}
