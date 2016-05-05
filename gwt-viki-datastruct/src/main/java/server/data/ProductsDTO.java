package server.data;

/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2016 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
