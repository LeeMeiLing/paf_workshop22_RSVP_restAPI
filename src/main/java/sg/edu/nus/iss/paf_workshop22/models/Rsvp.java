package sg.edu.nus.iss.paf_workshop22.models;

import java.sql.Date;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rsvp {
    
    @Id
    private Integer id;

    @NotNull
    @Size(min = 3, max = 150, message = "Fullname must be between 3 and 150 characters")
    private String fullname;

    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email only accepts maximum 150 characters")
    private String email;

    private String phone;

    private Date confirmationDate;

    private String comments;
    
}

