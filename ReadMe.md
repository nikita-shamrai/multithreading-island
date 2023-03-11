# multithreading-island
This project is a **console MultiThreading Java application**, that emulates the life on an island.

As in real life, there are predators, herbivores and plants. 
In every iteration of cycle animals move from cell to cell and if there is another type of animal on the cell they will fight.
If there is same type of animal - they will try to multiply. 

If there is a plant and animal is hungry - it will eat the plant.
There are lots of conditions that emulates the real life. 
Animals and plants are on different threads, and they exist independently of each other.

# How It Works

![tamagocci_rush](tamagocci_rush.gif)

In configuration.yaml file you can set the initial settings such as: 
Field size, quantity of plants, quantity of animals of each type and their properties, etc.

**Run the Main.class and enjoy the show**