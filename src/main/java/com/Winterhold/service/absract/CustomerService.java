package com.Winterhold.service.absract;

import com.Winterhold.dto.AllDataDTO;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.customer.CreateCustomerDTO;
import com.Winterhold.dto.customer.CustomerDTO;
import com.Winterhold.dto.customer.CustomerDetailDTO;
import com.Winterhold.dto.customer.UpdateCustomerDTO;

import java.util.List;

public interface CustomerService {
    public AllDataDTO getAllData(Integer page, String searchNumber,String searchName,String expired);
    public List<CustomerDTO> getList(Integer page, String searchNumber, String searchName, String expired);
    public Long getCount(String searchNumber,String searchName,String expired);
    public Long getPageSize(String searchNumber,String searchName,String expired);

    public void save(CreateCustomerDTO dto);
    public void update(UpdateCustomerDTO dto);
    public CreateCustomerDTO getCustomerDTO(String membershipNumber);
    public JsonResultDTO setExtend(String membershipNumber);
    public JsonResultDTO delete(String membershipNumber);
    public CustomerDTO getCustomer(String customerNumber);
    public CustomerDetailDTO getCustomerDetail(String customerNumber);

    public JsonResultDTO saveRest(CreateCustomerDTO dto);
    public JsonResultDTO updateRest(UpdateCustomerDTO dto);
    public JsonResultDTO updateAddress(String membership,String address);

    public Boolean uniqueMembership(String membership);
}

