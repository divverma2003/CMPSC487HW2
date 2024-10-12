This webapp uses Springboot MVC to structure a microapplication that processes the relevant GET and POST requests to a local MySQL database. Since this database is local, you'll need to configure your own 
personal database in "application.properties" to enjoy the full functionality of this web app.

Functions:
The "stakeholder" has the ability to add cars to their inventory, and edit and delete them. These forms are supported with input validation--to avoid null attributes being added to the database.
Upon deletion, corresponding "reservations" will be deleted via cascade to maintain integrity constraints. The stakeholder may also add reservations, as long as there's enough inventory. The cost of the "car" object is user mutatable, however, reservation costs are dependent on the car cost and will be assigned to the stakeholder booking the reservation. Finally, the stakeholder may also view every reservation and inventory item.
