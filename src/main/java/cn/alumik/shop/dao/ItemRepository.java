package cn.alumik.shop.dao;

import cn.alumik.shop.entity.Category;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query(value = "select case when sell is null then 0 " +
            "else sell end sell, id, id_category,  " +
            "name, pic, detail, price, stock, seller, " +
            "created_at, modified_at, available from " +
            "(select count(*) as sell, id_item from " +
            "transaction group by id_item ) as a " +
            "right outer join item on a.id_item = item.id " +
            "where name like %:name% and stock > 0 and " +
            "available <> 0",
        countQuery = "select count(*) from (select case " +
                "when sell is null then 0 " +
                "else sell end sell, id, id_category,  " +
                "name, pic, detail, price, stock, seller, " +
                "created_at, modified_at, available from " +
                "(select count(*) as sell, id_item from " +
                "transaction group by id_item ) as a " +
                "right outer join item on a.id_item = item.id " +
                "where name like %:name% and stock > 0 and " +
                "available <> 0) as ai",
        nativeQuery = true)
    Page<Object []> findAllByNameContainsSell(@Param("name") String name, Pageable pageable);

    @Query(value = "select case when sell is null then 0 " +
            "else sell end sell, id, id_category,  " +
            "name, pic, detail, price, stock, seller, " +
            "created_at, modified_at, available from " +
            "(select count(*) as sell, id_item from " +
            "transaction group by id_item ) as a " +
            "right outer join item on a.id_item = item.id " +
            "where name like %:name% and stock > 0 and " +
            "available <> 0 order by rand() limit :size",
            nativeQuery = true)
    List<Object[]> findAllByNameContainsSellOrderByRand(@Param("name") String name, @Param("size") int size);

    @Query(value = "select case when sell is null then 0 " +
            "else sell end sell, id, id_category,  " +
            "name, pic, detail, price, stock, seller, " +
            "created_at, modified_at, available from " +
            "(select count(*) as sell, id_item from " +
            "transaction group by id_item ) as a " +
            "right outer join item on a.id_item = item.id " +
            "where name like %:name% and stock > 0 and " +
            "available <> 0 and id_category = :categoryId",
            countQuery = "select count(*) from (select case " +
                    "when sell is null then 0 " +
                    "else sell end sell, id, id_category,  " +
                    "name, pic, detail, price, stock, seller, " +
                    "created_at, modified_at, available from " +
                    "(select count(*) as sell, id_item from " +
                    "transaction group by id_item ) as a " +
                    "right outer join item on a.id_item = item.id " +
                    "where name like %:name% and stock > 0 and " +
                    "available <> 0 and id_category = :categoryId) as ai",
            nativeQuery = true)
    Page<Object[]> findAllByNameContainsAndCategory_IdAndSell(@Param("name") String name, @Param("categoryId") int categoryId, Pageable pageable);

    Page<Item> findAllBySeller(User user, Pageable pageable);

    Page<Item> findAllByCategory_IdAndNameContainsAndSeller_UsernameContainsAndAvailable(Integer categoryId, String name, String sellerName, Boolean available, Pageable pageable);

    Page<Item> findAllByCategory_IdAndNameContainsAndSeller_UsernameContains(Integer categoryId, String name, String sellerName, Pageable pageable);

    Page<Item> findAllByNameContainsAndSeller_UsernameContainsAndAvailable(String name, String sellerName, Boolean b, Pageable pageable);

    Page<Item> findAllByNameContainsAndSeller_UsernameContains(String name, String sellerName, Pageable pageable);

    Page<Item> findAllByFavoriteBy(User user, Pageable pageable);
}
