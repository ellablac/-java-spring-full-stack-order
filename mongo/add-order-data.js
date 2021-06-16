db = connect("169.57.110.110:30017");

use Order
db.auth('orderdbuser', 'orderdbuserpwd')

db.Order.insertOne({"orderNumber": "ORDER-123", "customerNumber": "CUST456", "orderPrice": "31.85", "orderMenuItems": [ {"menuItemNumber": "MENU112", "quantityOfMenuItem": "2", "menuName": "Salmon", "price": "12.95" }, {"menuItemNumber": "MENU554", "quantityOfMenuItem": "1", "menuName": "Salad", "price": "5.95" }] })
