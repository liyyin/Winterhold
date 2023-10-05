package com.Winterhold.service.implementation;

import com.Winterhold.dao.BookRepository;
import com.Winterhold.dto.AllDataDTO;
import com.Winterhold.dto.DropdownListDTO;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.book.BookDTO;
import com.Winterhold.dto.book.BookDetailDTO;
import com.Winterhold.dto.book.CreateBookDTO;
import com.Winterhold.dto.book.UpdateBookDTO;
import com.Winterhold.entity.Book;
import com.Winterhold.service.absract.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookImplementation implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public AllDataDTO getAllData(Integer page, String categoryName, String bookName, String authorName, String isAvail) {
        var data = getList(page,categoryName,bookName,authorName,isAvail);
        List<Object> data2 = new ArrayList<>();
        data2.addAll(data);
        var currentPage = (long)page;
        var totalPage = getPageSize(categoryName, bookName, authorName, isAvail);
        var bookData = new AllDataDTO(data2,currentPage,totalPage,categoryName,bookName,authorName,isAvail);
        return bookData;
    }

    @Override
    public List<BookDTO> getList(Integer page, String categoryName, String bookName, String authorName, String isAvail) {
        Pageable pagging = PageRequest.of(page-1,10, Sort.by("id"));
        return bookRepository.getList(pagging,categoryName,bookName,authorName,isAvail);
    }

    @Override
    public Long getCount(String categoryName, String bookName, String authorName, String isAvail) {
        return bookRepository.getCount(categoryName, bookName, authorName,isAvail);
    }

    @Override
    public Long getPageSize(String categoryName, String bookName, String authorName, String isAvail) {
        var totalData = (double)getCount(categoryName, bookName, authorName, isAvail);
        var totalPage = (long)Math.ceil(totalData/10);
        return totalPage;
    }

    @Override
    public List<DropdownListDTO>getAuthorDDL() {
        return bookRepository.getAuthorDDL();
    }

    @Override
    public void insert(CreateBookDTO dto, String categoryName) {
        Book book = new Book(dto.getCode(),dto.getTitle(), categoryName, dto.getAuthor(),false,dto.getSummary(),dto.getReleaseDate(),dto.getTotalPage());
        book.setIsReturn(false);
        bookRepository.save(book);
    }
    @Override
    public void update(UpdateBookDTO dto, String categoryName) {
        Book book = new Book(dto.getCode(),dto.getTitle(), categoryName, dto.getAuthor(),false,dto.getSummary(),dto.getReleaseDate(),dto.getTotalPage());
        book.setIsReturn(false);
        bookRepository.save(book);
    }

    @Override
    public CreateBookDTO getBookDTO(String bookCode) {
        return bookRepository.getBookDTO(bookCode);
    }

    @Override
    public String getSummary(String bookCode) {
        return bookRepository.getSummary(bookCode);
    }

    @Override
    public JsonResultDTO delete(String bookCode) {
        var book = bookRepository.getReferenceById(bookCode);
        try{
            if (book != null){
                if (!book.getIsBorrowed()){
                    book.setIsReturn(true);
                    bookRepository.save(book);
                    return new JsonResultDTO(true,String.format("buku dengan code %s telah di delete",bookCode));
                }else {
                    return new JsonResultDTO(false,String.format("buku dengan code %s sedang di pinjam",bookCode));
                }
            }else {
                return new JsonResultDTO(false,String.format("buku dengan code %s tidak ditemukan",bookCode));
            }
        }catch (Exception ex){
            return new JsonResultDTO(false,"Internal Server Error");
        }
    }

    @Override
    public BookDetailDTO getBook(String bookCode) {
        return bookRepository.getBook(bookCode);
    }

    @Override
    public JsonResultDTO saveRest(CreateBookDTO dto) {
        try{
            Book book = new Book(dto.getCode(),dto.getTitle(), dto.getCategoryName(), dto.getAuthor(),false,dto.getSummary(),dto.getReleaseDate(),dto.getTotalPage());
            book.setIsReturn(false);
            book = bookRepository.save(book);
            return new JsonResultDTO("berhasil insert",true,book);
        }catch (Exception ex){
            return new JsonResultDTO(false,"Gagal melakukan Insert");
        }
    }

    @Override
    public JsonResultDTO updateRest(UpdateBookDTO dto) {
        try {
            Book book = bookRepository.getReferenceById(dto.getCode());
            if (book !=null){
                book.setTitle(dto.getTitle());
                book.setCategoryName(dto.getCategoryName());
                book.setAuthorId(dto.getAuthor());
                book.setReleaseDate(dto.getReleaseDate());
                book.setTotalPage(dto.getTotalPage());
                book.setSummary(dto.getSummary());
                book = bookRepository.save(book);
                return new JsonResultDTO("Berhasil Update data",true,book);
            }else {
                return new JsonResultDTO(false,"Buku tidak di temukan");
            }
        }catch (Exception ex){
            return new JsonResultDTO(false,"Gagal melakukan Update");
        }
    }

    @Override
    public JsonResultDTO updateSummary(String bookCode, String summary) {
        var data = bookRepository.getReferenceById(bookCode);
        if (data == null){
            return new JsonResultDTO(false,"Buku tidak ditemukan");
        }else {
            data.setSummary(summary);
            data = bookRepository.save(data);
            return new JsonResultDTO("Berhasil update summary",true,data);
        }
    }

    @Override
    public Boolean uniqueBookCode(String bookCode) {
        boolean result;
        if (bookRepository.uniqueBookCode(bookCode) > 0){
            result = true;
        }else {
            result = false;
        }
        return result;
    }
}
