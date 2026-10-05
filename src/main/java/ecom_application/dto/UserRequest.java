package ecom_application.dto;

import lombok.Data;

/**
 * Request DTO containing user details submitted to the API.
 */
@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AddressDto address;
}
