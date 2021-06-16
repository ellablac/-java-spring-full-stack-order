db = connect("169.57.110.110:30017");

use admin
db.auth('admin', 'pa55w0rd')

db = db.getSiblingDB('Test')