package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import play.db.ebean.Model;


@Entity
public class Warehouse extends Model{

  private static final long serialVersionUID = 8030237455264439124L;
  @Id
  public long id;
  public String name;
  @OneToMany(mappedBy="warehouse", cascade=CascadeType.ALL)
  public List<StockItem> stockItems = new ArrayList<>();
  @OneToOne(cascade=CascadeType.ALL)
  public Address address;
  
  public Warehouse(String name, Address address){
    this.name = name;
    this.address = address;
  }
  
  public static Finder<Long, Warehouse> find(){
    return new Finder<Long, Warehouse>(Long.class, Warehouse.class);
  }
  
}
