package com.Winterhold.service.absract;

import com.Winterhold.dto.AllDataDTO;
import com.Winterhold.dto.DropdownListDTO;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.book.BookDTO;
import com.Winterhold.dto.book.BookDetailDTO;
import com.Winterhold.dto.book.CreateBookDTO;
import com.Winterhold.dto.book.UpdateBookDTO;

import java.util.List;

public interface BookService {
    public AllDataDTO getAllData(Integer page,String categoryName , String bookName,String authorName,String isAvail);
    public List<BookDTO> getList(Integer page,String categoryName, String bookName,String authorName,String isAvail);
    public Long getCount(String categoryName,String bookName,String authorName,String isAvail);
    public Long getPageSize(String categoryName,String bookName,String authorName,String isAvail);

    public List<DropdownListDTO> getAuthorDDL();
    public void insert(CreateBookDTO dto, String categoryName);
    public void update(UpdateBookDTO dto, String categoryName);
    public CreateBookDTO getBookDTO(String bookCode);

    public String getSummary(String bookCode);

    public JsonResultDTO delete(String bookCode);
    public BookDetailDTO getBook(String bookCode);

    public JsonResultDTO saveRest(CreateBookDTO dto);
    public JsonResultDTO updateRest(UpdateBookDTO dto);
    public JsonResultDTO updateSummary(String bookCode,String summary);

    public Boolean uniqueBookCode(String bookCode);
}
