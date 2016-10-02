package courseassignment.nitin.personal;

/**
 * Created by nitin on 10/2/2016.
 */
public class PhoneBook {

    private int id;
    private String fullName;
    private String phoneNo;
    private String address;

    public PhoneBook(int id,String fullName,String phoneNo,String address){
        this.id = id;
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    public PhoneBook(String fullName,String phoneNo,String address){
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getAddress() {
        return address;
    }


}
