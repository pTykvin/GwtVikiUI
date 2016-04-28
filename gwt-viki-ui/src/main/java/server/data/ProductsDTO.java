package server.data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import shared.Product;

public class ProductsDTO {

    public static ProductsDTO instance = new ProductsDTO();

    private Random random = new Random();

    private Map<String, Product> list = new HashMap<>();

    private ProductsDTO() {
        {
            Product product = new Product();
            product.setItem("1");
            product.setPrice(random.nextInt(50000));
            product.setProduct("Булочка с маком");
            add(product);
        }
        {
            Product product = new Product();
            product.setItem("2");
            product.setPrice(random.nextInt(50000));
            product.setProduct("Малый пакет");
            add(product);
        }
        {
            Product product = new Product();
            product.setItem("3");
            product.setPrice(random.nextInt(50000));
            product.setProduct("Хлеб бородинский");
            add(product);
        }
        {
            Product product = new Product();
            product.setItem("4");
            product.setPrice(random.nextInt(50000));
            product.setProduct("Спички");
            add(product);
        }
        {
            Product product = new Product();
            product.setItem("5");
            product.setPrice(random.nextInt(50000));
            product.setProduct("Куриное филе");
            add(product);
        }
        {
            Product product = new Product();
            product.setItem("6");
            product.setPrice(random.nextInt(50000));
            product.setProduct("Водка журавли");
            add(product);
        }
    }

    public static String encode(String src) {
        return new String(src.getBytes(), Charset.forName(StandardCharsets.UTF_8.name()));
    }

    private void add(Product product) {
        list.put(product.getItem(), product);
    }

    public Product get(String item) {
        return list.get(item);
    }

    public void init() {
        list.values().forEach(p -> p.setCount(0));
    }

    public Collection<Product> getAll() {
        return list.values();
    }
}
