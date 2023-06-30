package controller;

import dao.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import services.CustomerService;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
    @Test
    public void shouldSaveCustomer() {
        CustomerService service = Mockito.mock(CustomerService.class);
        CustomerController controller = new CustomerController(service);

        Customer customer = new Customer();
        customer.setCustomerRef("test");

        Mockito.when(service.saveCustomer(Mockito.any(Customer.class))).thenReturn(customer);

        ResponseEntity<Customer> response = controller.addCustomer(new Customer());

        assertEquals("test", response.getBody().getCustomerRef());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void shouldGetCustomer() {
        CustomerService service = Mockito.mock(CustomerService.class);
        CustomerController controller = new CustomerController(service);

        Customer customer = new Customer();
        customer.setCustomerRef("test");

        Mockito.when(service.getCustomer("test")).thenReturn(customer);

        ResponseEntity<Customer> response = controller.getCustomer("test");

        assertEquals("test", response.getBody().getCustomerRef());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnNotFoundForUnknownCustomer() {
        CustomerService service = Mockito.mock(CustomerService.class);
        CustomerController controller = new CustomerController(service);

        Mockito.when(service.getCustomer("unknown")).thenReturn(null);

        ResponseEntity<Customer> response = controller.getCustomer("unknown");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
