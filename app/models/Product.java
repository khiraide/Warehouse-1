package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


@Entity
public class Product extends Model{
  private static final long serialVersionUID = 1765615104131291592L;
  @Id
  private Long primayKey;
  @Required
  private String productId;
  @Required
  private String name;
  @Required
  private String description;
  
  @ManyToMany(cascade=CascadeType.ALL)
  private List<Tag> tags = new ArrayList<>();
  @OneToMany(mappedBy="product", cascade=CascadeType.ALL)
  private List<StockItem> stockItems = new ArrayList<>();
  
  public Product(String productId, String name, String description){
    this.productId = productId;
    this.name = name;
    this.description = description;
  }
  
  public static Finder<Long, Product> find(){
    return new Finder<Long, Product>(Long.class, Product.class);
  }
  
  public String toString(){
    return String.format("[Product %s %s %s]", productId, name, description);
  }

  /**
   * @return the primayKey
   */
  public Long getPrimayKey() {
    return primayKey;
  }

  /**
   * @param primayKey the primayKey to set
   */
  public void setPrimayKey(Long primayKey) {
    this.primayKey = primayKey;
  }

  /**
   * @return the productId
   */
  public String getProductId() {
    return productId;
  }

  /**
   * @param productId the productId to set
   */
  public void setProductId(String productId) {
    this.productId = productId;
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
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the tags
   */
  public List<Tag> getTags() {
    return tags;
  }

  /**
   * @param tags the tags to set
   */
  public void setTags(List<Tag> tags) {
    this.tags = tags;
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
  
  
  
  
  /*
  //public String getPK() ???
  public String getProductId() { return productId; }
  public String getName(){ return name; }
  public String getDescription() { return description; }
  public List<Tag> getTags() { return tags; }
  public List<StockItem> getStockItems() { return stockItems; }
  
  public void setProductId(String pid) { this.productId = pid; }
  public void setName(String name) { this.productId = name; }
  public void setDescription(String desc) { this.productId = desc; }
  public void setTags(List<Tag> tList) { this.tags = tList; }
  public void setStockItems(List<StockItem> siList) { this.stockItems = siList; }
  */
  
}
