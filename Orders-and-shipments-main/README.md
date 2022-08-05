# Orders-and-shipments

The first project deals with ordering a package by the customer.
In the order the customer writes his details and the details of the package for delivery.
The details are stored in the FIREBASE database and are handled by the courier in the second application.
The second project deals with the handling of orders by the courier. To log in, you must register for the system by email and password.
You can register by clicking on register (the email and password do not have to be real,
the main thing is that the format of the email will be preserved and the password will have at least 6 characters).
After entering the courier will check the shipments in his area by longitude and latitude and check that the shipment is within 1 range of the courier's location.
You can take an item, update that the package was sent and see the entire history of the courier and the details sent by him.
The second application is built in MVVM architecture 
