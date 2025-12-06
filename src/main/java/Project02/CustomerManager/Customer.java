package Project02.CustomerManager;

/**
 * ClassName: Customer
 * Description:
 * 
 * Customer为实体对象，用来封装客户信息
 * 
 * {@code @Author} Liang-ht
 * {@code @Create} 2025-12-02 17:19:50
 */
public class Customer {
    private String name;
    private String sex;
    private int age;
    private String tele;
    private String mail;

    @Override
    public String toString() {
        return name + "\t" + sex + "\t" + age + "\t" + tele + "\t\t" + mail;
    }
    public String toStringForCsv(){
        return name + "," + sex + "," + age + "," + tele + "," + mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public boolean setSex(String sex) {
        this.sex = sex;
        return true;
    }

    public int getAge() {
        return age;
    }

    public boolean setAge(int age) {
        this.age = age;
        return true;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Customer(String name, String sex, int age, String tele, String mail) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.tele = tele;
        this.mail = mail;
    }

}
