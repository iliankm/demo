package com.iliankm.demo.entity.repository.service.customer;

import com.iliankm.demo.dto.CustomerDto;
import com.iliankm.demo.entity.Customer;
import com.iliankm.demo.entity.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Customer service implementation.
 */
@Service
class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Autowired constructor for DI.
     *
     * @param customerRepository {@link CustomerRepository} dependency
     */
    CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public static void main(String[] args) {

        Converter<Customer, CustomerDto> conv = new CustomerToCustomerDtoConverter();
        System.out.println("source type = " + conv.getSourceType().getName());
        System.out.println("dest type = " + conv.getDestinationType().getName());

        MergeConverter<CustomerDto, Customer> conv2 = new CustomerDtoToCustomerMergeConverter();
        System.out.println("source type = " + conv2.getSourceType().getName());
        System.out.println("dest type = " + conv2.getDestinationType().getName());

        BigDecimal f = Function.<String>identity()
                .andThen(Integer::valueOf)
                .andThen(i -> i.toString() + ".000")
                .andThen(BigDecimal::new)
                .apply("1");
        var b = new byte[]{1, 2};
        var s = new String(b, StandardCharsets.UTF_16);
        var b1 = s.getBytes(StandardCharsets.UTF_16);
        System.out.println(Arrays.equals(b, b1));
    }
}

interface SourceAndDestination<S, D> {
    default Class<S> getSourceType() {
        return (Class<S>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    default Class<D> getDestinationType() {
        return (Class<D>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
    }
}

interface Converter<S, D> extends SourceAndDestination<S, D> {
    D convert(S source);
}

interface MergeConverter<S, D> extends SourceAndDestination<S, D> {
    D convert(S source, D dest);
}

@Service
class ConverterService {
    @Autowired
    List<Converter> converters;
    @Autowired
    List<MergeConverter> mergeConverters;

    public <S, D> D convert(S source, Class<D> destType) {
        return (D) converters.stream().filter(c ->
                        c.getSourceType().getName().equals(source.getClass().getName())
                                && c.getDestinationType().getName().equals(destType.getName()))
                .findFirst()
                .orElseThrow()
                .convert(source);
    }

    public <S, D> D convert(S source, D dest) {
        return (D) mergeConverters.stream().filter(c ->
                        c.getSourceType().getName().equals(source.getClass().getName())
                                && c.getDestinationType().getName().equals(dest.getClass().getName()))
                .findFirst()
                .orElseThrow()
                .convert(source, dest);
    }
}

@Component
class CustomerToCustomerDtoConverter implements Converter<Customer, CustomerDto> {
    @Override
    public CustomerDto convert(Customer source) {
        return null;
    }
}

@Component
class CustomerDtoToCustomerMergeConverter implements MergeConverter<CustomerDto, Customer> {
    @Override
    public Customer convert(CustomerDto source, Customer dest) {
        return null;
    }
}

