package Project02.CustomerManager;


/**
 * ClassName: CustomerList
 * Description: 
 * 
 * CustomerList为Customer对象的管理模块，内部用数组管理一组Customer对象，
 * 并提供相应的添加、修改、删除和遍历方法，供CustomerView调用
 * 
 * {@code @Author} Liang-ht
 * {@code @Create} 2025-12-02 17:19:59
 */
public class CustomerList {
    Linklist<Customer> customerLinkedList = new Linklist<Customer>();
    /**
     * 添加用户信息，在末尾
     * @param cust : 要添加的用户
     */
    public void addCustomer(Customer cust){
        customerLinkedList.addlast(cust);
    }

    /**
     * 删除用户信息，指定位置
     * @param index : 要删除的用户所在位置
     */
    public void removeCustomer(int index){
        customerLinkedList.removeNode(index);
    }

   
    public Customer getCust(int index){
        return customerLinkedList.getNode(index);
    }

    public void printCustomerInfo(){
            customerLinkedList.printCustomerInfoInList();
    }

}
