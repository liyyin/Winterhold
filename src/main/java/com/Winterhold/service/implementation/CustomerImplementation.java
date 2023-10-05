package com.Winterhold.service.implementation;

import com.Winterhold.dao.CustomerRepository;
import com.Winterhold.dto.AllDataDTO;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.customer.CreateCustomerDTO;
import com.Winterhold.dto.customer.CustomerDTO;
import com.Winterhold.dto.customer.CustomerDetailDTO;
import com.Winterhold.dto.customer.UpdateCustomerDTO;
import com.Winterhold.entity.Customer;
import com.Winterhold.service.absract.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerImplementation implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public AllDataDTO getAllData(Integer page, String searchNumber, String searchName, String expired) {
        var data = getList(page,searchNumber,searchName,expired);
        List<Object> data2 = new ArrayList<>();
        data2.addAll(data);
        var currentPage = (long)page;
        var totalPage = getPageSize(searchNumber, searchName, expired);
        var customerData = new AllDataDTO(data2,currentPage,totalPage,searchName,searchNumber,expired);
        return customerData;
    }

    @Override
    public List<CustomerDTO> getList(Integer page, String searchNumber, String searchName, String expired) {
        Pageable pagging = PageRequest.of(page-1,10, Sort.by("id"));
        return customerRepository.getList(pagging,searchNumber,searchName,expired);
    }

    @Override
    public Long getCount(String searchNumber, String searchName, String expired) {
        return customerRepository.getCount(searchNumber, searchName,expired);
    }

    @Override
    public Long getPageSize(String searchNumber, String searchName, String expired) {
        var totalData = (double)getCount(searchNumber, searchName, expired);
        var totalPage = (long)Math.ceil(totalData/10);
        return totalPage ;
    }

    @Override
    public void save(CreateCustomerDTO dto) {
        Customer customer = new Customer(dto.getMembershipNumber(), dto.getFirstName(),dto.getLastName() ,dto.getBirthDate(),dto.getGender(),dto.getPhone(),dto.getAddress(),LocalDate.now().plusYears(2));
        customer.setIsDelete(false);
        customerRepository.save(customer);
    }

    @Override
    public void update(UpdateCustomerDTO dto){
        Customer customer = customerRepository.getReferenceById(dto.getMembershipNumber());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setBirthDate(dto.getBirthDate());
        customer.setGender(dto.getGender());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        customerRepository.save(customer);
    }

    @Override
    public CreateCustomerDTO getCustomerDTO(String membershipNumber) {
        var entity = customerRepository.getReferenceById(membershipNumber);
        CreateCustomerDTO dto = new CreateCustomerDTO(entity.getMembershipNumber(), entity.getFirstName(), entity.getLastName(),entity.getBirthDate(), entity.getGender(), entity.getPhone(), entity.getAddress());
        return dto;
    }

    @Override
    public JsonResultDTO setExtend(String membershipNumber) {
        Customer customer = customerRepository.getReferenceById(membershipNumber);
        try{
            if (customer !=null){
                var tanggalExpire = customer.getMemberShipExpireDate();
                customer.setMemberShipExpireDate(tanggalExpire.plusYears(2));
                customerRepository.save(customer);
                return new JsonResultDTO(true,String.format("extend berhasil di membership %s",membershipNumber));
            }else {
                return new JsonResultDTO(false,String .format("tidak ada customer dengan membershipNumber %s",membershipNumber));
            }
        }catch (Exception ex){
            return new JsonResultDTO(false,ex.getMessage());
        }
    }

    @Override
    public JsonResultDTO delete(String membershipNumber) {
        var customer = customerRepository.getReferenceById(membershipNumber);
        try{
            if (customer != null){
                customer.setIsDelete(true);
                customerRepository.save(customer);
                return new JsonResultDTO(true,String.format("customer %s telah di delete",membershipNumber));
            }else {
                return new JsonResultDTO(false,String.format("customer %s tidak ditemukan",membershipNumber));
            }
        }catch (Exception ex){
            return new JsonResultDTO(false,"Internal Server Error");
        }
    }

    @Override
    public CustomerDTO getCustomer(String customerNumber) {
        return customerRepository.getCustomer(customerNumber);
    }

    @Override
    public CustomerDetailDTO getCustomerDetail(String customerNumber) {
        return customerRepository.getCustomerDetail(customerNumber);
    }

    @Override
    public JsonResultDTO saveRest(CreateCustomerDTO dto) {
        try {
            Customer customer = new Customer(dto.getMembershipNumber(), dto.getFirstName(),dto.getLastName() ,dto.getBirthDate(),dto.getGender(),dto.getPhone(),dto.getAddress(),LocalDate.now().plusYears(2));
            customer.setIsDelete(false);
            customer = customerRepository.save(customer);
            return new JsonResultDTO("Berhasil Insert",true,customer);
        }catch (Exception ex){
            return new JsonResultDTO(false,"Gagal melakukan Insert");
        }
    }

    @Override
    public JsonResultDTO updateRest(UpdateCustomerDTO dto) {
        try {
            Customer customer = customerRepository.getReferenceById(dto.getMembershipNumber());
            if (customer !=null){
                customer.setFirstName(dto.getFirstName());
                customer.setLastName(dto.getLastName());
                customer.setBirthDate(dto.getBirthDate());
                customer.setGender(dto.getGender());
                customer.setPhone(dto.getPhone());
                customer.setAddress(dto.getAddress());
                customer = customerRepository.save(customer);
                return new JsonResultDTO("Berhasil Update data",true,customer);
            }else {
                return new JsonResultDTO(false,"customer tidak di temukan");
            }
        }catch (Exception ex){
            return new JsonResultDTO(false,"Gagal melakukan Update");
        }
    }

    @Override
    public JsonResultDTO updateAddress(String membership, String address) {
        var data = customerRepository.getReferenceById(membership);
        if (data == null){
            return new JsonResultDTO(false,"customer tidak ditemukan");
        }else {
            data.setAddress(address);
            data = customerRepository.save(data);
            return new JsonResultDTO("Berhasil update summary",true,data);
        }
    }

    @Override
    public Boolean uniqueMembership(String membership) {
        boolean result;
        if (customerRepository.uniqueMembership(membership) > 0){
            result = true;
        }else {
            result = false;
        }
        return result;
    }

}
