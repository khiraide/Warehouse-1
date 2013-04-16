package models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class StockItem extends Model{
  private static final long serialVersionUID = 1177134038703219710L;
  @Id
  private Long primaryKey;
  @Required
  private String stockItemId;
  @Required
  private long quantity;
  @Required
  @ManyToOne(cascade=CascadeType.ALL)
  private Warehouse warehouse;
  @Required
  @ManyToOne(cascade=CascadeType.ALL)
  private Product product;
  
  public StockItem(String stockItemId, String warehouseId, String productId, long quantity){
    this.stockItemId = stockItemId;
    List<Warehouse> warehouses = Warehouse.find().where().eq("warehouseId", warehouseId).findList();
    if (!warehouses.isEmpty()) {
      this.warehouse = warehouses.get(0);
    }
    else {
      this.warehouse = null;
    }
    List<Product> products = Product.find().where().eq("productId", productId).findList();
    if (!products.isEmpty()) {
      this.product = products.get(0);
    }
    else {
      this.product = null;
    }
    this.quantity = quantity;
  }
  
  /**
   * StockItem requires an existing warehouse and product.
   * @return null if ok, error string if not ok.
   */
  public String validate(){
    String output = null;
    //if the warehouse doesn't exist
    if (!Warehouse.find().findList().contains(warehouse)) {
      output = "Warehouse doesn't exist.";
    }
    if (!Product.find().findList().contains(product)) {
      if (output == null) {
        output = "Product doesn't exist";
      }
      else {
        output.concat(" Product doesn't exist");
      }
    }
    return output;
  }
  
  public static Finder<Long, StockItem> find(){
    return new Finder<Long, StockItem>(Long.class, StockItem.class);
  }
  
  public String toString(){
    return String.format("[StockItem %s %d %s %s]", stockItemId, quantity, warehouse, product);
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
   * @return the stockItemId
   */
  public String getStockItemId() {
    return stockItemId;
  }

  /**
   * @param stockItemId the stockItemId to set
   */
  public void setStockItemId(String stockItemId) {
    this.stockItemId = stockItemId;
  }

  /**
   * @return the quantity
   */
  public long getQuantity() {
    return quantity;
  }

  /**
   * @param quantity the quantity to set
   */
  public void setQuantity(long quantity) {
    this.quantity = quantity;
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

  /**
   * @return the product
   */
  public Product getProduct() {
    return product;
  }

  /**
   * @param product the product to set
   */
  public void setProduct(Product product) {
    this.product = product;
  }
  
  
  
  
}
