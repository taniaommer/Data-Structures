package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
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

            else if(x.compareTo("restock") == 0){   // restock querry

                int id = StdIn.readInt();
                int restock = StdIn.readInt();
                warehouse.restockProduct(id, restock);
            }

            else if(x.compareTo("purchase") == 0){   // purchase querry

                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
    
                warehouse.purchaseProduct(id, day, amount);
            }
            
            else{   // delete querry

                int id_delete = StdIn.readInt();
                warehouse.deleteProduct(id_delete);
            }

        }

        StdOut.println(warehouse);

	// Use this file to test all methods
    }
}
