package devo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingRequest {

    private String firstname;
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lasttname) {
        this.lastname = lasttname;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    public boolean getDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }

    public Bookingdtes getBookingdates() {
        return bookingdtes;
    }

    public void setBookingdates(Bookingdtes bookingdtes) {
        this.bookingdtes = bookingdtes;
    }

    private int totalprice;
    private boolean depositpaid;
    private String additionalneeds;
    private Bookingdtes bookingdtes;

    @JsonCreator
    public BookingRequest(@JsonProperty(value = "firstname",required = true) String firstname,
                   @JsonProperty(value = "lastname",required = true) String lastname,
                   @JsonProperty(value = "totalprice",required = true) int totalprice,
                   @JsonProperty(value = "depositpaid",required = true) boolean depositpaid,
                   @JsonProperty(value = "bookingdates",required = true) Bookingdtes bookingdtes,
                   @JsonProperty(value = "additionalneeds",required = true) String additionalneeds) throws ParseException {

        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.additionalneeds = additionalneeds;
        this.bookingdtes = bookingdtes;
    }

}
