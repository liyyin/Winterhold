package com.Winterhold.dao;

import com.Winterhold.dto.category.CategoryDTO;
import com.Winterhold.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category,String> {
    @Query("""
            SELECT new com.Winterhold.dto.category.CategoryDTO(
                cat.name,
                cat.floor,
                cat.isle,
                cat.bay,
                COUNT(bo.categoryName)
            )
            FROM Category as cat
            LEFT JOIN cat.book As bo
            WHERE cat.name LIKE %:name% AND cat.isDelete = false
            GROUP BY cat.name,
                    cat.floor,
                    cat.isle,
                    cat.bay
            """)
    public List<CategoryDTO> getList(Pageable pagging, @Param("name") String searchName);

    @Query("""
            SELECT Count(1)
            FROM Category as cat
            WHERE cat.name LIKE %:name% AND cat.isDelete = false
            """)
    public Long getCount(@Param("name") String searchName);

    @Query("""
            SELECT COUNT(1)
            FROM Category as cat
            where cat.name = :categoryName
            """)
    public Long uniqueCategoryName(@Param("categoryName")String categoryName);

    @Query(value = """
            EXEC getAllDataCategory @searchName = :categoryName
            """, nativeQuery = true)
    List<Category> getAllDataCategory(@Param("categoryName") String name);
}
