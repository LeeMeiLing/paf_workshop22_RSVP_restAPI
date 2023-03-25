package sg.edu.nus.iss.paf_workshop22.models;

import java.sql.Date;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
public class Rsvp {
    
    // @Id
    private Integer id;

    @NotNull
    @Size(min = 3, max = 150, message = "Fullname must be between 3 and 150 characters")
    private String fullname;

    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email only accepts maximum 150 characters")
    private String email;

    private String phone;

    private Date confirmationDate; // no need @DateTimeFormat as default string of pattern"yyyy-MM-dd" can parse into sql date

    private String comments;

    public Rsvp() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Rsvp [id=" + id + ", fullname=" + fullname + ", email=" + email + ", phone=" + phone
                + ", confirmationDate=" + confirmationDate + ", comments=" + comments + "]";
    }

    
}

