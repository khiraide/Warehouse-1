package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


@Entity
public class Address extends Model{

  private static final long serialVersionUID = -5860583881661552944L;
  @Id
  private long primaryKey;
  @Required
  private String addressId;
  @Required
  private String address;
  
  @OneToOne(mappedBy="address",cascade=CascadeType.ALL)
  private Warehouse warehouse;
  
  public Address(String addressId, String address){
    this.addressId = addressId;
    this.address = address;
  }
  
  public static Finder<Long, Address> find(){
    return new Finder<Long, Address>(Long.class, Address.class);
  }
  
  public String toString(){
    return String.format("[Address %s %s]", addressId, address);
  }

  /**
   * @return the primaryKey
   */
  public long getPrimaryKey() {
    return primaryKey;
  }

  /**
   * @param primaryKey the primaryKey to set
   */
  public void setPrimaryKey(long primaryKey) {
    this.primaryKey = primaryKey;
  }

  /**
   * @return the addressId
   */
  public String getAddressId() {
    return addressId;
  }

  /**
   * @param addressId the addressId to set
   */
  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @return the warehouse
   */
  public Warehouse getWarehouse() {
    return warehouse;
  }

  /**
   * @param warehouse the warehouse to set
   */
  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }
  
  
  
} //end class
