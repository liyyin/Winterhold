package com.Winterhold.service.absract;

import com.Winterhold.dto.AllDataDTO;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.author.AuthorDTO;
import com.Winterhold.dto.author.CreateUpdateAuthorDTO;
import com.Winterhold.dto.book.BookDTO;

import java.util.List;

public interface AuthorService {
    public AllDataDTO getAllData(Integer page,String searchName);
    public List<AuthorDTO> getList(Integer page, String searchName);
    public Long getCount(String searchName);
    public Long getPageSize(String searchName);

    public AuthorDTO getDataAuthor(Long id);
    public List<BookDTO> getListBookByIdAuthor(Long id);

    public void save(CreateUpdateAuthorDTO dto);
    public CreateUpdateAuthorDTO getAuthorDTO(Long id);
    public JsonResultDTO delete(Long id);

    public JsonResultDTO saveRest(CreateUpdateAuthorDTO dto);
    public JsonResultDTO updateRest(CreateUpdateAuthorDTO dto);
    public JsonResultDTO updateSummary(Long id,String summary);
    public JsonResultDTO deleteRest(Long id);
}
