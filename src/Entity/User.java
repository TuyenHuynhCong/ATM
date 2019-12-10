/////////////////////////////////////////////////////////////////////////////
//
// © 2019 andro Japan. All right reserved.
//
/////////////////////////////////////////////////////////////////////////////

package Entity;

/**
 * [OVERVIEW] XXXXX.
 *
 * @author: tuyenhc
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2019/12/09      tuyenhc       Create new
*/
public class User {
    
    private Integer id ;
    private String fullName;
    private Integer age;
    private Integer amount ;
    private Integer password;
    private String role;
    /**
     * 
     */
    public User() {
        super();
    }
   
    /**
     * @param maSoThe
     * @param fullName
     * @param age
     * @param amount
     * @param password
     * @param role
     */
    public User(Integer id, String fullName, Integer age, Integer amount, Integer password, String role) {
        super();
        id = id;
        this.fullName = fullName;
        this.age = age;
        this.amount = amount;
        this.password = password;
        this.role = role;
    }

   
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }
    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }
    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }
    /**
     * @return the amount
     */
    public Integer getAmount() {
        return amount;
    }
    /**
     * @param amount the amount to set
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    /**
     * @return the password
     */
    public Integer getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(Integer password) {
        this.password = password;
    }
    
    
    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(" Mã số thẻ : "+id);
        string.append(" \nTên : "+fullName);
        string.append(" \nTuổi : "+age);
        string.append(" \nTổng tiền : "+amount);
        string.append(" \npassword :" + password);
        string.append(" \nrole :" + role);
        // TODO Auto-generated method stub
        return string.toString();
    }
    
 
}
