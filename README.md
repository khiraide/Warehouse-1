This is an example application used to test the modeling of Play data storage.

As seen in the below Entity relation diagram, there are five tables:![Select](https://raw.github.com/kowasu/Warehouse/master/images/ERD%20for%20warehouse.png)

The design behind this model is a warehouse which stocks items.

There is a 1:1 relationship between warehouse and address because a specific warehouse can only occupy one address, just as an address should only relate to one warehouse.

Warehouses can stock multiple items. But a particular stocked items only relates to one warehouse. After it doesn't make sense that one warehouse have access to another's stock.

The stockitem is for a specific product. The reason that a product can be associated with multiple stockitems is because of different warehouses. Each warehouse will track their own stock which means that they might hold the same product, but have different amounts or prices.

A product can have many tags for description. While specific tag might have many products using it in their description.