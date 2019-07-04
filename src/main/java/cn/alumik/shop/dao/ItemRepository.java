package cn.alumik.shop.dao;

import cn.alumik.shop.entity.Category;
import cn.alumik.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query(value = "select case when sell is null then 0 " +
            "else sell end sell, id, id_category,  " +
            "name, pic, detail, price, stock, seller, " +
            "created_at, modified_at, available from " +
            "(select count(*) as sell, id_item from " +
            "transaction group by id_item ) as a " +
            "right outer join item on a.id_item = item.id " +
            "where name like %:name%",
        nativeQuery = true)
    Page<Object []> findAllByNameContainsSell(@Param("name") String name, Pageable pageable);
}
