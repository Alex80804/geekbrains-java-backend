package lesson6.tests;

import lesson6.db.dao.CategoriesMapper;
import lesson6.db.dao.ProductsMapper;
import lesson6.db.model.Categories;
import lesson6.db.model.Products;
import lesson6.db.model.ProductsExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        SqlSessionFactory sqlSessionFactory;

        String resource = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

        SqlSession session = sqlSessionFactory.openSession();

        ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);

        CategoriesMapper categoriesMapper = session.getMapper(CategoriesMapper.class);

        // 1. Добавить в БД 3 продукта и 1 категорию
        productsMapper.insert(new Products("New Product 1", 100, 1));
        productsMapper.insert(new Products("New Product 2", 200, 1));
        productsMapper.insert(new Products("New Product 3", 300, 2));

        categoriesMapper.insert(new Categories("New Category 1"));

        session.commit();


        // 2. Найти все продукты 1 категории
        ProductsExample productsOf1CategoryCriteria = new ProductsExample();
        productsOf1CategoryCriteria.createCriteria().andCategory_idEqualTo(1);

        List<Products> productsOf1CategoryResult = productsMapper.selectByExample(productsOf1CategoryCriteria);

        System.out.println("Продукты 1 категории: " + productsOf1CategoryResult);

        // 3. Найти все продукты дешевле 1000
        ProductsExample productsLessThan1000Criteria = new ProductsExample();
        productsLessThan1000Criteria.createCriteria().andPriceLessThan(1000);

        List<Products> productsLessThan1000Result = productsMapper.selectByExample(productsLessThan1000Criteria);

        System.out.println("Продукты дешевле 1000: " + productsLessThan1000Result);

        // 4. Найти все продукты от a до h
        ProductsExample productsBetweenAHCriteria = new ProductsExample();
        productsBetweenAHCriteria.createCriteria().andTitleBetween("A", "I");

        List<Products> productsBetweenAHResult = productsMapper.selectByExample(productsBetweenAHCriteria);

        System.out.println("Продукты от a до h: " + productsBetweenAHResult);

    }
}
