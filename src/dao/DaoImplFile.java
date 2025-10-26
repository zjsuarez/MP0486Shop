package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import model.Amount;
import model.Product;

public class DaoImplFile implements Dao{
	
	
	public ArrayList<Product> getInventory(){
		ArrayList<Product> lista = new ArrayList<>();
		
		// locate file, path and name
				File f = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.txt");
				
				try {			
					// wrap in proper classes
					FileReader fr;
					fr = new FileReader(f);				
					BufferedReader br = new BufferedReader(fr);
					
					// read first line
					String line = br.readLine();
					
					// process and read next line until end of file
					while (line != null) {
						// split in sections
						String[] sections = line.split(";");
						
						String name = "";
						double wholesalerPrice=0.0;
						int stock = 0;
						
						// read each sections
						for (int i = 0; i < sections.length; i++) {
							// split data in key(0) and value(1) 
							String[] data = sections[i].split(":");
							
							switch (i) {
							case 0:
								// format product name
								name = data[1];
								break;
								
							case 1:
								// format price
								wholesalerPrice = Double.parseDouble(data[1]);
								break;
								
							case 2:
								// format stock
								stock = Integer.parseInt(data[1]);
								break;
								
							default:
								break;
							}
						}
						// add product to inventory
						lista.add(new Product(name, new Amount(wholesalerPrice), true, stock));
						
						// read next line
						line = br.readLine();
					}
					fr.close();
					br.close();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return lista;
	}
	
	public boolean writeInventory(ArrayList<Product> productlist) {
		
		LocalDate myObj = LocalDate.now();
		String fileName = "inventory_" + myObj.toString() + ".txt";
		
		// locate file, path and name
		File f = new File(System.getProperty("user.dir") + File.separator + "files" + File.separator + fileName);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
			
			for (int i = 0; i < productlist.size(); i++) {
				writer.write("Product:"+productlist.get(i).getName()+";");
				writer.write("Wholesaler Price:"+productlist.get(i).getWholesalerPrice().toNormal()+";");
				writer.write("Stock:"+productlist.get(i).getStock()+";");
				writer.newLine();
				writer.write("Total number of products:"+productlist.size());
			}
			
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
}
