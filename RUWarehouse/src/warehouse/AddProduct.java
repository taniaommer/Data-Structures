package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {

        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        Warehouse warehouse = new Warehouse();
        int num = StdIn.readInt();

        for(int i = 0; i < num; i++){
            
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int stock =StdIn.readInt();
            int demand = StdIn.readInt();
            
            warehouse.addProduct(id, name, stock, day, demand);

        }

        StdOut.println(warehouse);

	// Use this file to test addProduct
    }
}
