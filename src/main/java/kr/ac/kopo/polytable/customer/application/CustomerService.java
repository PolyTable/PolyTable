package kr.ac.kopo.polytable.customer.application;

import kr.ac.kopo.polytable.customer.dto.CustomerResponseDto;
import kr.ac.kopo.polytable.customer.error.CustomerNotFoundException;
import kr.ac.kopo.polytable.customer.error.DuplicatePhoneNumberException;
import kr.ac.kopo.polytable.customer.model.Customer;
import kr.ac.kopo.polytable.customer.model.CustomerRepository;
import kr.ac.kopo.polytable.modelmapper.CustomModelMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomModelMapper customModelMapper;

    @Transactional
    public Long create(Customer request) {
        Customer findCustomer = customerRepository.findByPhone(request.getPhone());

        if (findCustomer != null) {
            throw new DuplicatePhoneNumberException();
        }
        return customerRepository.save(request).getId();
    }

    @Transactional
    public void increaseVisitCount(String phone) {
        Customer findCustomer = customerRepository.findByPhone(phone);

        if (findCustomer == null) {
            throw new CustomerNotFoundException();
        }

        findCustomer.increaseVisitCount();
    }

    @Transactional
    public void decreaseVisitCount(String phone) {
        Customer findCustomer = customerRepository.findByPhone(phone);

        if (findCustomer == null) {
            throw new CustomerNotFoundException();
        }

        findCustomer.decreaseVisitCount();
    }

    @Transactional
    public void decreaseCancelCount(String phone) {
        Customer findCustomer = customerRepository.findByPhone(phone);

        if (findCustomer == null) {
            throw new CustomerNotFoundException();
        }

        findCustomer.decreaseCancelCount();
    }

    @Transactional
    public void decreaseNoShowCount(String phone) {
        Customer findCustomer = customerRepository.findByPhone(phone);

        if (findCustomer == null) {
            throw new CustomerNotFoundException();
        }

        findCustomer.decreaseNoShowCount();
    }

    @Transactional(readOnly = true)
    public CustomerResponseDto getCustomerByPhoneNumber(String phone) {
        Customer findCustomer = customerRepository.findByPhone(phone);

        if (findCustomer == null) {
            throw new CustomerNotFoundException();
        }

        ModelMapper mapper = customModelMapper.standardMapper();
        return mapper.map(findCustomer, CustomerResponseDto.class);
    }




}
