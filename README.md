# Item Management (InventoryIQ)
## Goals
The goal for my application is to make the users inventory processing easier. This includes having simple and efficient processes and interfaces for the application. The application would be accessible through the user’s phone, so there wouldn’t be a need for searching for documents or pen and paper.
## Requirements
This application will require a database with at least two tables, one for the items and one for the usernames and passwords. It will also require two screens. There will be a screen for logging into or registering for the application, and a screen to display all inventory items in a grid.  There will also be three mechanisms. One to add or remove items, one to increase or decrease the amount of an item, and a notification to the user when an item is out of stock. This UI allows the users to clearly identify the item they were looking for, control of the items, and easy update of low quantity items. I believe my designs were successful. 
## Process
Normally, I would start with evaluating my technology options, however we were given the sole options of Android Studio and SQLite, so I didn’t have to worry about that part of my process. I first identified which portions of my app would be talking to each other. For instance, I identified that the username and password fields would need to compare the users input against records in the SQLite database. 
## Testing
Following identifying those connections, I built my app in sections and completed unit tests on the portions developed. I followed those up with functional testing and ensuring I met the business requirements defined. I will continue to utilize this type of process, but I will be adding integration testing to make sure my integrations connect correctly. 
## Component Success
I had to innovate some to make the item creation fragment. I was considering using another full screen but felt that I should try to do a UI piece that most users are familiar with. This also happened to be the component of my application that I was most happy with. I had to debug this component many times to get the data to properly add a new item, to get the fragment xml to show properly, and even to get the screen behind the component to not show. I used all my combined skills, knowledge, and UI experience to produce the component I am most proud to have made. 

