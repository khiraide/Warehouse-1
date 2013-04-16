package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


@Entity
public class Warehouse extends Model{
  private static final long serialVersionUID = 8030237455264439124L;
  @Id
  private Long primaryKey;
  @Required
  private String warehouseId;
  @Required
  private String name;
  @OneToMany(mappedBy="warehouse", cascade=CascadeType.ALL)
  private List<StockItem> stockItems = new ArrayList<>();
  //@Required
  @OneToOne(cascade=CascadeType.ALL)
  private Address address;
  @Transient
  private String addressId;
  
  public Warehouse(String warehouseId, String name, String addressId){
    this.warehouseId = warehouseId;
    this.name = name;
    this.addressId = addressId;
    this.address = Address.find().where().eq("addressId", addressId).findUnique();
  }
  
  /**
   * Needs an existing address for creation.
   * @return null if ok, error string if not ok.
   */
  public String validate() {
    Address address = Address.find().where().eq("addressId", addressId).findUnique();
    return (address == null) ? "Error: Address with ID " + addressId + " does not exist." : null;
  }
  
  public static Finder<Long, Warehouse> find(){
    return new Finder<Long, Warehouse>(Long.class, Warehouse.class);
  }
  
  public String toString(){
    return String.format("[Warehouse %s %s %s]", warehouseId, name, address);
  }

  /**
   * @return the primaryKey
   */
  public Long getPrimaryKey() {
    return primaryKey;
  }

  /**
   * @param primaryKey the primaryKey to set
   */
  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  /**
   * @return the warehouseId
   */
  public String getWarehouseId() {
    return warehouseId;
  }

  /**
   * @param warehouseId the warehouseId to set
   */
  public void setWarehouseId(String warehouseId) {
    this.warehouseId = warehouseId;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the stockItems
   */
  public List<StockItem> getStockItems() {
    return stockItems;
  }

  /**
   * @param stockItems the stockItems to set
   */
  public void setStockItems(List<StockItem> stockItems) {
    this.stockItems = stockItems;
  }

  /**
   * @return the address
   */
  public Address getAddress() {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(Address address) {
    this.address = address;
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
  
  
  
}
