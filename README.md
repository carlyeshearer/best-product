# best-product
The Best Product application will be used by everyday shoppers who wish to find the product that best fits their needs. 
The application allows users to input items and create several lists to compare items based on different criteria such as name, price, quality, and availability. 
The software will be used anywhere when a person wants to compare prices for items. 
Users will want to use this software over existing processes because it compares items on multiple criteria in a simple, readable format. 
It also allows them to assign a personal rating to an item if they have purchased it.

The Best Product application has been updated to include a LinkedList structure instead of an ArrayList structure to store a user’s items. This change was implemented to allow the list to be accessed forwards and backwards while maintaining the insertion order of the items. A new method has been added to allow the user to search for an item. This method uses the MyHashMap class to create a map structure with a key of the item’s name and the item as the value. The method returns the item associated with the name key in the map. 

The Best Product application has been updated to include an AVL tree which is used to efficiently search for an item by price. A new class called AVLTree has been created to implement this. The MyHashMap class uses hashing to efficiently search for an entry in the map structure used to search an item by name. Several methods have also been simplified to lambda expressions for better readability and clarity. 

The Best Product application has been updated to include concurrent processing so that multiple method operations can be performed on the list of items concurrently. Each method is synchronized to ensure thread safety. An autosave feature has also been added to ensure user data is saved if the program crashes or the user forgets to save before closing the application.

Link to YouTube demonstration: https://youtu.be/gJarcuHFews 
