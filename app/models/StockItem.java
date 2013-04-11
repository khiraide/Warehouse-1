package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.db.ebean.Model;

@Entity
public class StockItem extends Model{

  private static final long serialVersionUID = 1177134038703219710L;
  @Id
  public long id;
  public long quantity;
  @ManyToOne(cascade=CascadeType.ALL)
  public Warehouse warehouse;
  @ManyToOne(cascade=CascadeType.ALL)
  public Product product;
  
  public StockItem(Warehouse warehouse, Product product, long quantity){
    this.warehouse = warehouse;
    this.product = product;
    this.quantity = quantity;
  }
  
  public static Finder<Long, StockItem> find(){
    return new Finder<Long, StockItem>(Long.class, StockItem.class);
  }

}
