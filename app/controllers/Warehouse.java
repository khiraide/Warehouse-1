
package controllers;

import java.util.List;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import static play.data.Form.form;


public class Warehouse extends Controller{
  
  public static Result index() {
    List<models.Warehouse> warehouses = models.Warehouse.find().findList();
    return ok(warehouses.isEmpty() ? "No warehouses" : warehouses.toString());
  }
  
  public static Result details(String warehouseId) {
    models.Warehouse warehouse = models.Warehouse.find().where().eq("warehouseId", warehouseId).findUnique();
    return (warehouse == null) ? notFound("No warehouse found") : ok(warehouse.toString());
  }
  
  public static Result newWarehouse() {
    Form<models.Warehouse> warehouseForm = form(models.Warehouse.class).bindFromRequest();
    //validate
    if (warehouseForm.hasErrors()) {
      return badRequest("ware house requires id, name and an address." + warehouseForm.errors().toString());
    }
    //passed validation, ok
    models.Warehouse warehouse = warehouseForm.get();
    warehouse.save();
    return ok(warehouse.toString());
  }
  
  public static Result delete(String warehouseId) {
    models.Warehouse warehouse = models.Warehouse.find().where().eq("warehouseId", warehouseId).findUnique();
    if (warehouse != null) {
      warehouse.delete();
    }
    return ok();
  }
  
  
}
