package kr.ac.kopo.polytable.customer.application;

import kr.ac.kopo.polytable.customer.dto.CustomerSaveRequestDto;
import kr.ac.kopo.polytable.customer.error.CustomerNotFoundException;
import kr.ac.kopo.polytable.customer.error.DuplicatePhoneNumberException;
import kr.ac.kopo.polytable.customer.model.Customer;
import kr.ac.kopo.polytable.customer.model.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public void createCustomer(CustomerSaveRequestDto request) {
        Customer findCustomer = customerRepository.findByPhone(request.getPhone());

        if (findCustomer != null) {
            throw new DuplicatePhoneNumberException();
        }

        Customer newCustomer = Customer.builder()
                .phone(request.getPhone())
                .customerName(request.getCustomerName())
                .build();

        customerRepository.save(newCustomer);
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
    public Customer getCustomerByPhoneNumber(String phone) {
        Customer findCustomer = customerRepository.findByPhone(phone);

        if (findCustomer == null) {
            throw new CustomerNotFoundException();
        }

        return findCustomer;
    }




}
