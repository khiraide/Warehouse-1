

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import models.Address;
import models.Product;
import models.StockItem;
import models.Tag;
import models.Warehouse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.FakeApplication;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;


public class ModelTest {
  private FakeApplication application;
  
  @Before
  public void startApp(){
    application = fakeApplication(inMemoryDatabase());
    start(application);
  }
  
  @After
  public void stopApp(){
    stop(application);
  }
  
  @Test
  public void testModel(){
    //create 1 tag that is associated with 1 product
    Tag tag = new Tag("Tag");
    Product product = new Product("Product", "Description");
    product.tags.add(tag);
    tag.products.add(product);
    
    //create 1 warehouse with 1 stockitem for 1 product
    Address address = new Address("Somewhere in Hawaii, maybe.");
    Warehouse warehouse = new Warehouse("Warehouse", address);
    StockItem stockitem = new StockItem(warehouse, product, 100);
    warehouse.stockItems.add(stockitem);
    stockitem.warehouse = warehouse;
    address.warehouse = warehouse;
    
    //persist the sample model by saving all entities and relationships
    warehouse.save();
    tag.save();
    product.save();
    stockitem.save();
    address.save();
    
    //retrieve the entire model from the database
    List<Warehouse> warehouses = Warehouse.find().findList();
    List<Tag> tags = Tag.find().findList();
    List<Product> products = Product.find().findList();
    List<StockItem> stockitems = StockItem.find().findList();
    List<Address> addresses = Address.find().findList();
    
    //check that we've recovered all entities
    assertEquals("Checking warehouse", warehouses.size(), 1);
    assertEquals("Address-Warehouse", addresses.get(0).warehouse, warehouses.get(0));
    assertEquals("Warehouse-Address", warehouses.get(0).address, addresses.get(0));
    assertEquals("StockItem-Warehouse", stockitems.get(0).warehouse, warehouses.get(0));
    assertEquals("Product-StockItem", products.get(0).stockitems.get(0), stockitems.get(0));
    assertEquals("StockItem-Product", stockitems.get(0).product, products.get(0));
    assertEquals("Product-Tag", products.get(0).tags.get(0), tags.get(0));
    assertEquals("Tag-Product", tags.get(0).products.get(0), products.get(0));
    
    //Some code to illustrate model manipulation with ORM
    //Start in java. delete the tag from the (original) product instance.
    product.tags.clear();
    //persist the revised product instance
    product.save();
    //FYI: this does not change our previously retrieved instance from the database.
    assertTrue("Previously retrieved product still has tag", !products.get(0).tags.isEmpty());
    //But of course it does change a freshly retrieved product instance
    assertTrue("Fresh product has no tag", Product.find().findList().get(0).tags.isEmpty());
    //Note: freshly retrieved tag does not point to any product
    assertTrue("Fresh tag has no products", Tag.find().findList().get(0).products.isEmpty());
    //we can now delete this tag from the database if want
    tag.delete();
    assertTrue("No more tags in database", Tag.find().findList().isEmpty());
  }
  
  
} //end test
