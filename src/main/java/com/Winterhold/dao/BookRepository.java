package com.Winterhold.dao;

import com.Winterhold.dto.DropdownListDTO;
import com.Winterhold.dto.book.BookDTO;
import com.Winterhold.dto.book.BookDetailDTO;
import com.Winterhold.dto.book.CreateBookDTO;
import com.Winterhold.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,String> {
    @Query("""
            SELECT new com.Winterhold.dto.book.BookDTO(
                bo.code,
                bo.title,
                au.title,
                au.firstName,
                au.lastName,
                bo.isBorrowed,
                bo.releaseDate,
                bo.totalPage,
                bo.summary      
            )
            FROM Book as bo
            INNER JOIN bo.category AS cat
            INNER JOIN bo.author AS au
            WHERE (bo.categoryName LIKE %:categoryName% AND bo.title LIKE %:title%) AND
                    (au.firstName LIKE %:name% OR au.lastName LIKE %:name%) AND
                    ((bo.isBorrowed = false AND :avail = 'on') OR                   
                    ( (bo.isBorrowed = true OR bo.isBorrowed = false) AND :avail= 'off')) AND
                    (bo.isReturn = false)
            """)
    public List<BookDTO> getList(Pageable pagging,@Param("categoryName") String categoryName,@Param("title") String bookTitle, @Param("name") String authorName, @Param("avail")String isAvail);

    @Query("""
            SELECT COUNT(1)
            FROM Book as bo
            INNER JOIN bo.category AS cat
            INNER JOIN bo.author AS au
            WHERE (bo.categoryName LIKE %:categoryName% AND bo.title LIKE %:title%) AND
                    (au.firstName LIKE %:name% OR au.lastName LIKE %:name%) AND
                    ((bo.isBorrowed = false AND :avail = 'on') OR                   
                    ( (bo.isBorrowed = true OR bo.isBorrowed = false) AND :avail= 'off'))AND
                    (bo.isReturn = false)
            """)
    public Long getCount(@Param("categoryName") String categoryName,@Param("title") String bookTitle, @Param("name") String authorName, @Param("avail")String isAvail);

    @Query("""
            SELECT new com.Winterhold.dto.DropdownListDTO(
                au.id,
                CONCAT(au.title,'. ',au.firstName,' ',au.lastName)
            )
            FROM Author AS au
            """)
    public List<DropdownListDTO> getAuthorDDL();

    @Query("""
            SELECT new com.Winterhold.dto.book.CreateBookDTO(
                bo.code,
                bo.title,
                bo.categoryName,
                bo.authorId,
                bo.releaseDate,
                bo.totalPage,
                bo.summary
            )
            FROM Book AS bo
            WHERE bo.code = :bookCode
            """)
    public CreateBookDTO getBookDTO(String bookCode);

    @Query("""
            SELECT bo.summary
            FROM Book AS bo
            WHERE bo.code = :bookCode
            """)
    public String getSummary(@Param("bookCode") String bookCode);

    @Query("""
            SELECT new com.Winterhold.dto.book.BookDetailDTO(
            bo,
            cat,
            au
            )
            FROM Book AS bo
            INNER JOIN bo.author AS au
            INNER JOIN bo.category AS cat
            WHERE bo.code = :bookCode
            """)
    public BookDetailDTO getBook(@Param("bookCode") String bookCode);

    @Query("""
            SELECT COUNT(1)
            FROM Book AS bo
            WHERE bo.code = :bookCode
            """)
    public Long uniqueBookCode(@Param("bookCode") String bookCode);
}
