package warehouse;

public class Restock {
    public static void main(String[] args) {
        
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        Warehouse warehouse = new Warehouse();
        int queries = StdIn.readInt();

        for(int i = 0; i < queries; i++){

            String x = StdIn.readString();
            if(x.compareTo("add") == 0){    // add querry

                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();

                warehouse.addProduct(id, name, stock, day, demand);

            }
            else{   // restock querry

                int id = StdIn.readInt();
                int restock = StdIn.readInt();
                warehouse.restockProduct(id, restock);

            }

        }        
        
        StdOut.println(warehouse);

	// Uset his file to test restock
    }
}
