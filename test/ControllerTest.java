import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.status;
import static play.test.Helpers.stop;
import java.util.HashMap;
import java.util.Map;
import models.Address;
import models.Product;
import models.Tag;
import models.Warehouse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;





public class ControllerTest {
  private FakeApplication application;
  
  @Before
  public void startApp() {
    application = fakeApplication(inMemoryDatabase());
    start(application);
  }
  
  @After
  public void stopApp(){
    stop(application);
  }
  
  @Test
  public void testProductController(){
    //test GET /product on an empty database
    Result result = callAction(controllers.routes.ref.Product.index());
    assertTrue("Empty products", contentAsString(result).contains("No products"));
    
    //test GET /product on a database containing a single product.
    String productId = "Product-01";
    Product product = new Product(productId, "French press", "Coffee Maker");
    product.save();
    result = callAction(controllers.routes.ref.Product.index());
    assertTrue("One product", contentAsString(result).contains(productId));
    
    //test GET /product/Product-01
    result = callAction(controllers.routes.ref.Product.details(productId));
    assertTrue("Product detail", contentAsString(result).contains(productId));
    
    //test GET /product/BadProductId and make sure get a 404
    result = callAction(controllers.routes.ref.Product.details("BadProductId"));
    assertEquals("Product detail (bad)", NOT_FOUND, status(result));
    
    //Test POST /products (with simulated, valid form data)
    Map<String, String> productData = new HashMap<String, String>();
    productData.put("productId", "Product-02");
    productData.put("name", "Baby Gaggia");
    productData.put("description", "Espresso machine");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(productData);
    result = callAction(controllers.routes.ref.Product.newProduct(), request);
    assertEquals("Create new product", OK, status(result));
    
    //Test POST /products (with simulated invalid data)
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Product.newProduct(), request);
    assertEquals("Create bad product fails", BAD_REQUEST, status(result));
    
    //test DELETE /products/Product-01 (a valid productId)
    result = callAction(controllers.routes.ref.Product.delete(productId));
    assertEquals("Delete current product OK", OK, status(result));
    result = callAction(controllers.routes.ref.Product.details(productId));
    assertEquals("Deleted product gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Product.delete(productId));
    assertEquals("Delete missing product also OK", OK, status(result));
    
  } //end test product controller
  
  @Test
  public void testTagController() {
    //test GET /tags on an empty database
    Result result = callAction(controllers.routes.ref.Tag.index());
    assertTrue("empty tags", contentAsString(result).contains("No Tags"));
    
    //test GET /tag on a database containing a single tag
    String tagId = "Tag-01";
    Tag tag = new Tag(tagId);
    tag.save();
    result = callAction(controllers.routes.ref.Tag.index());
    assertTrue("One tag", contentAsString(result).contains(tagId));
    
    //Test GET /tags/Tag-01
    result = callAction(controllers.routes.ref.Tag.details(tagId));
    assertTrue("Tag detail", contentAsString(result).contains(tagId));
    
    //test GET /tags/BadTagId and make sure get a 404
    result = callAction(controllers.routes.ref.Tag.details("BadTagId"));
    assertEquals("Tag detail (bad)", NOT_FOUND, status(result));
    
    //Test POST /tags (with simulated, valid form data)
    Map<String, String> tagData = new HashMap<String, String>();
    tagData.put("tagId", "Tag-02");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(tagData);
    result = callAction(controllers.routes.ref.Tag.newTag(), request);
    assertEquals("Create new tag", OK, status(result));
    
    //Test POST /tags (with invalid tag: tags cannot be named "Tag").
    request = fakeRequest();
    tagData.put("tagId", "Tag");
    request.withFormUrlEncodedBody(tagData);
    result = callAction(controllers.routes.ref.Tag.newTag(), request);
    assertEquals("Create bad tag fails", BAD_REQUEST, status(result));
    
    //Test DELETE /tags/Tag-01 (a valid tag)
    result = callAction(controllers.routes.ref.Tag.delete(tagId));
    assertEquals("Delete current tag OK", OK, status(result));
    result = callAction(controllers.routes.ref.Tag.details(tagId));
    assertEquals("Delete tag gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Tag.delete(tagId));
    assertEquals("Delete missing tag also OK", OK, status(result));
  }
  
  @Test
  public void testWarehouseController() {
    //test GET on empty
    Result result = callAction(controllers.routes.ref.Warehouse.index());
    assertTrue("Empty warehouses", contentAsString(result).contains("No warehouses"));
    
    //test GET on a database containing a single warehouse
    //NOTE: warehouses need an existing address inorder to be made.
    String addr1Id = "Address-01";
    models.Address addr = new models.Address(addr1Id, "Hawaii street");
    addr.save();
    String warehouseId = "Warehouse-01";
    models.Warehouse warehouse = new models.Warehouse(warehouseId, "Costgo", addr1Id);
    warehouse.save();
    result = callAction(controllers.routes.ref.Warehouse.index());
    assertTrue("One warehouse item", contentAsString(result).contains(warehouseId));
    
    //test GET /warehouses/Warehouse-01
    result = callAction(controllers.routes.ref.Warehouse.details(warehouseId));
    assertTrue("Warehouse detail", contentAsString(result).contains(warehouseId));
    
    //test Get /warehouses/BadId
    result = callAction(controllers.routes.ref.Warehouse.details("BadId"));
    assertEquals("Warehouse detail (bad)", NOT_FOUND, status(result));
    
    //Test POST /warehouses (with simulated, valid form data)
    //Note: addresses and warehouse have 1:1 cardinality, need to make new address.
    String addrId2 = "Address-02";
    models.Address addr2 = new models.Address(addrId2, "Also Hawaii street");
    addr2.save();
    Map<String, String> warehouseData = new HashMap<String,String>();
    warehouseData.put("warehouseId", "Warehouse-02");
    warehouseData.put("name", "Costgo-02");
    warehouseData.put("addressId", addrId2);
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(warehouseData);
    result = callAction(controllers.routes.ref.Warehouse.newWarehouse(), request);
    //assertEquals("Created new warehouse" + contentAsString(result), 000, status(result));
    assertEquals("Created new warehouse" + contentAsString(result), OK, status(result));
    
    //Test POST /warehouse with invalid address
    request = fakeRequest();
    warehouseData.put("address", "This address doesn't exsit");
    request.withFormUrlEncodedBody(warehouseData);
    result = callAction(controllers.routes.ref.Warehouse.newWarehouse(), request);
    assertEquals("Create bad warehouse fails (non-existent address)", BAD_REQUEST, status(result));
    //Test POST /warehouse with missing name
    Map<String, String> warehouseData2 = new HashMap<String,String>();
    warehouseData2.put("warehouseId", "Warehouse-02");
    warehouseData2.put("address", addrId2);
    request = fakeRequest();
    request.withFormUrlEncodedBody(warehouseData2);
    result = callAction(controllers.routes.ref.Warehouse.newWarehouse(), request);
    assertEquals("Create bad warehouse fails (missing name)", BAD_REQUEST, status(result));
    
    //Test DELETE /warehouse/Warehouse-01 (a valid warehouse)
    result = callAction(controllers.routes.ref.Warehouse.delete(warehouseId));
    assertEquals("Delete the warehouse OK", OK, status(result));
    result = callAction(controllers.routes.ref.Warehouse.details(warehouseId));
    assertEquals("Warehouse is not retrievable", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Warehouse.delete(warehouseId));
    assertEquals("Delete missing warehouse also OK", OK, status(result));
    
    
  }
  
  @Test
  public void testStockItemController() {
    //test GET on empty
    Result result = callAction(controllers.routes.ref.StockItem.index());
    assertTrue("Empty stock items", contentAsString(result).contains("No stockItems"));
    
    //test GET on a database containing a single stockItem.
    //NOTE: need existing product and warehouse to make a stock item.
    //ALSO NOTE: a warehouse needs an existing address
    String addr1Id = "Address-01";
    models.Address addr = new models.Address(addr1Id, "Hawaii street location");
    addr.save();
    String whId = "Warehouse-01";
    models.Warehouse wh = new models.Warehouse(whId, "Costgo", addr1Id);
    wh.save();
    String prodId = "Product-01";
    models.Product prod = new models.Product(prodId, "French Press", "Coffee machine");
    prod.save();
    String stockItemId = "StockItem-01";
    models.StockItem stockItem = new models.StockItem(stockItemId, whId, prodId, 10);
    stockItem.save();
    result = callAction(controllers.routes.ref.StockItem.details(stockItemId));
    assertTrue("One stock item", contentAsString(result).contains(stockItemId));

    
  }

}
