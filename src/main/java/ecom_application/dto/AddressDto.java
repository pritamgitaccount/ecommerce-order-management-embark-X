package ecom_application.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"street", "city", "state", "country", "zipCode"})
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
