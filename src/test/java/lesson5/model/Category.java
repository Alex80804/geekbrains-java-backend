package lesson5.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Category {
    public long id;
    public List<Product> products;
    public String title;
}
