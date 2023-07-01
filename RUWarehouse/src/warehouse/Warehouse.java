package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */ 
public class Warehouse {
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {

        Product product = new Product(id, name, stock, day, demand);
        int index = id % 10;
        sectors[index].add(product);

    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {

        int index = id % 10;
        int size = sectors[index].getSize();
        sectors[index].swim(size);

    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {

        int index = id % 10;
        if(sectors[index].getSize() == 5){ // sector at full capacity

            sectors[index].swap(1, 5);
            sectors[index].deleteLast();
            sectors[index].sink(1);

           }      
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        
        int index = id % 10;
        int size = sectors[index].getSize();

        if(size != 0){
            for(int i = 1; i <= size; i++){
                if(sectors[index].get(i).getId() == id){
                    sectors[index].get(i).updateStock(amount);
                }
            }
        }

    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        
        int index = id % 10;
        int size = sectors[index].getSize();

        if(size != 0){

            for(int i = 1; i <= size; i++){
                if(sectors[index].get(i).getId() == id){

                    sectors[index].swap(i, size);
                    sectors[index].deleteLast();
                    sectors[index].sink(i);
                    return;

                }
            }
        }

    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        
        int index = id % 10;
        int size = sectors[index].getSize();

        for(int i = 1; i <= size; i++){

            if(sectors[index].get(i).getId() == id){
                if(amount <= sectors[index].get(i).getStock()){

                    sectors[index].get(i).setLastPurchaseDay(day);
                    sectors[index].get(i).updateStock(-1 * amount);
                    sectors[index].get(i).updateDemand(amount);
                    sectors[index].sink(i);

                }
            }
        }
    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
    
        int index = id % 10;
        int size = sectors[index].getSize();

        if(size < 5){
            addProduct(id, name, stock, day, demand);
        }
        else{
            boolean add = false;
            index = (id++) % 10;    // go to next sector
            
            while(add == false){

                if(index == id % 10){    // sector you started at
                    addProduct(id, name, stock, day, demand);
                    add = true;
                }
                else{
                    size = sectors[index].getSize();
                    
                    if(size < 5){
                        Product new_product = new Product(id, name, stock, day, demand);
                        sectors[index].add(new_product);
                        fixHeap(index);
                        add = true;
                    }
                }

                id++;
                index = id % 10;
            }
        }
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }
        
        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */ 
    public Sector[] getSectors () {
        return sectors;
    }
}
