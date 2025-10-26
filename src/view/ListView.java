package view;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.DefaultTableModel;

import main.Shop;
import model.Product;

public class ListView extends javax.swing.JFrame {

	Shop shop;
	
    public ListView(Shop shop) {
        initComponents();
        loadTable(shop);
        this.shop = shop;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });


        jTextField1.setText("Buscar...");
        jTextField1.setToolTipText("Buscar");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });



        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setText("Inventario");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
            	"ID", "Name", "Public Price", "Wholesaler Price", "Stock"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 110, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 864, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(73, 73, 73))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE))
                .addContainerGap(104, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        
                                   

    
    
    
    
    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {                                        
        if(jTextField1.getText().equals("Buscar...")) {
        jTextField1.setText("");
        }
    }                                       

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {                                      
        if(jTextField1.getText().equals("")) {
        jTextField1.setText("Buscar...");
        }
    }                                     

    private void formMouseClicked(java.awt.event.MouseEvent evt) {                                  
        this.requestFocus();
    }                                 
                                   
    
    
    
    
    
    
    
    /*ACTUALIZAR TABLA CUANDO EL USUARIO ESCRIBA EN EL TEXTFIELD*/
    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {                                        
       DefaultTableModel modelo = new DefaultTableModel();
       modelo.setColumnIdentifiers(new String[]{"ID", "Name", "Public Price", "Wholesaler Price", "Stock"});
       
       
    
       String text = jTextField1.getText();
        
       ArrayList <Product> productlist = shop.getInventory();
       
       int count = 0;
       
       for (int i = 0; i < productlist.size(); i++) {
    	   
    	   Product producto = productlist.get(i);
            
    	   if (producto.getName().toLowerCase().contains(text.toLowerCase())) {
               Object [] nuevaFila = {producto.getId(),producto.getName(),producto.getPublicPrice(),producto.getWholesalerPrice(),producto.getStock()};
               modelo.addRow(nuevaFila);
               count++;

           }
        }
        
        
        jTable1.setModel(modelo);

    }                                            
                                  

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    
    
   public void loadTable(Shop shop){
       
	   ArrayList <Product> productlist = shop.getInventory();
       
       DefaultTableModel modelo = new DefaultTableModel();
       modelo.setColumnIdentifiers(new String[]{"ID", "Name", "Public Price", "Wholesaler Price", "Stock"});
        
       for (int i = 0; i < productlist.size(); i++) {

    	   Product producto = productlist.get(i);
            
    	   Object [] nuevaFila = {producto.getId(),producto.getName(),producto.getPublicPrice(),producto.getWholesalerPrice(),producto.getStock()};
           modelo.addRow(nuevaFila);
            
        }
        
        jTable1.setModel(modelo);
   }
   
  

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration                   
}
