package com.Winterhold.dao;

import com.Winterhold.dto.author.AuthorDTO;
import com.Winterhold.dto.book.BookDTO;
import com.Winterhold.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query("""
            SELECT new com.Winterhold.dto.author.AuthorDTO(
                au.id,
                au.title,
                au.firstName,
                au.lastName,
                au.birthDate,
                au.deceasedDate,
                au.education
            )
            FROM Author as au
            WHERE au.firstName LIKE %:name% OR au.lastName LIKE %:name% AND au.isRemove = false
            """)
    public List<AuthorDTO> getList(Pageable pagging, @Param("name") String searchName);

    @Query("""
            SELECT Count(1)
            FROM Author as au
            WHERE au.title LIKE %:name% OR au.firstName LIKE %:name% OR au.lastName LIKE %:name% AND au.isRemove = false
            """)
    public Long getCount(@Param("name") String searchName);

    @Query("""
            SELECT  new com.Winterhold.dto.author.AuthorDTO(
                au.id,
                au.title,
                au.firstName,
                au.lastName,
                au.birthDate,
                au.deceasedDate,
                au.education,
                au.summary
            )
            FROM Author as au
            WHERE au.id = :id
            """)
    public AuthorDTO getAuthor(@Param("id") Long id);

    @Query("""
            SELECT new com.Winterhold.dto.book.BookDTO(
                bo.code,
                bo.categoryName,
                bo.title,
                bo.isBorrowed,
                bo.releaseDate,
                bo.totalPage
            )
            FROM Author AS au
            INNER JOIN au.book as bo
            WHERE au.id = :id
            """)
    public List<BookDTO> getListBookByIdAuthor(Long id);

    @Query("""
            SELECT COUNT(au.id)
            FROM Author AS au
            INNER JOIN au.book
            WHERE au.id = :id
            """)
    public Long getCountOnBook(@Param("id") Long id);
}
