package ecom_application.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ecom_application.role.UserRole;
import lombok.Data;

/**
 * Response DTO containing the user details exposed by the API.
 */
@Data
@JsonPropertyOrder({"id", "firstName", "lastName", "email", "phone", "role", "address"})
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDto address;
}
