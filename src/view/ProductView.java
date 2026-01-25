package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Product;
import model.Amount;
import utils.Constants;

public class ProductView extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private Shop shop;
	private int option;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField textFieldName;
	private JTextField textFieldStock;
	private JTextField textFieldPrice;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			ProductView dialog = new ProductView();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public ProductView(Shop shop, int option) {
		this.shop = shop;
		this.option = option;
		
		// main configuration dialog
		switch (option) {
		case Constants.OPTION_ADD_PRODUCT:
			setTitle("Añadir Producto");			
			break;
		case Constants.OPTION_ADD_STOCK:
			setTitle("Añadir Stock");			
			break;
		case Constants.OPTION_REMOVE_PRODUCT:
			setTitle("Eliminar Producto");			
			break;		

		default:
			break;
		}
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		// name section
		JLabel lblName = new JLabel("Nombre producto:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(33, 10, 119, 19);
		contentPanel.add(lblName);
		textFieldName = new JTextField();
		textFieldName.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldName.setBounds(169, 10, 136, 25);
		contentPanel.add(textFieldName);
		textFieldName.setColumns(10);		
		
		// stock section
		JLabel lblStock = new JLabel("Stock producto:");
		lblStock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStock.setBounds(33, 50, 119, 19);
		contentPanel.add(lblStock);
		textFieldStock = new JTextField();
		textFieldStock.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldStock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldStock.setBounds(169, 50, 136, 25);
		contentPanel.add(textFieldStock);
		textFieldStock.setColumns(10);
		if (option == Constants.OPTION_ADD_PRODUCT || option == Constants.OPTION_ADD_STOCK) {
			lblStock.setVisible(true);
			textFieldStock.setVisible(true);			
		}else {
			lblStock.setVisible(false);
			textFieldStock.setVisible(false);
		}
		
		// price section
		JLabel lblPrice = new JLabel("Precio producto:");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrice.setBounds(33, 90, 119, 19);
		contentPanel.add(lblPrice);
		textFieldPrice = new JTextField();
		textFieldPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldPrice.setBounds(169, 90, 136, 25);
		contentPanel.add(textFieldPrice);
		textFieldPrice.setColumns(10);
		if (option == Constants.OPTION_ADD_PRODUCT) {
			lblPrice.setVisible(true);
			textFieldPrice.setVisible(true);			
		}else {
			lblPrice.setVisible(false);
			textFieldPrice.setVisible(false);
		}
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(this);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(this);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == okButton) {
			Product product;
			switch (this.option) {
			case Constants.OPTION_ADD_PRODUCT:
				// check product does not exist
				product = shop.findProduct(textFieldName.getText());
				
				if (product != null) {
					JOptionPane.showMessageDialog(null, "Producto ya existe ", "Error",
							JOptionPane.ERROR_MESSAGE);
					
				} else {
						try {
							product = new Product(0,textFieldName.getText(), 
									Double.parseDouble(textFieldPrice.getText()) ,
									true,
									Integer.parseInt(textFieldStock.getText()));
		                    if (shop.addProduct(product)) {
		                        JOptionPane.showMessageDialog(null, "Producto añadido ", "Information",
		                                JOptionPane.INFORMATION_MESSAGE);
		                        dispose(); 
		                    } else {
		                        JOptionPane.showMessageDialog(null, "ERROR AL AÑADIR: No se pudo guardar en BD", "Information",
		                                JOptionPane.WARNING_MESSAGE);
		                    }
		                } catch (NumberFormatException ex) {
		                    JOptionPane.showMessageDialog(null, "ERROR: El precio o stock deben ser números", "Error",
		                            JOptionPane.ERROR_MESSAGE);
		                } catch (Exception ex) {
		                    JOptionPane.showMessageDialog(null, "ERROR DESCONOCIDO", "Error",
		                            JOptionPane.ERROR_MESSAGE);
		                    ex.printStackTrace();
		                }
		            }
				
				break;
				
			case Constants.OPTION_ADD_STOCK:
			    product = shop.findProduct(textFieldName.getText());
			    
			    if (product == null) {
			        JOptionPane.showMessageDialog(null, "Producto no existe ", "Error",
			                JOptionPane.ERROR_MESSAGE);
			        
			    } else {
			        try {
			            int stockToAdd = Integer.parseInt(textFieldStock.getText());
			            int currentStock = product.getStock();
			            
			            product.setStock(currentStock + stockToAdd);
			            
			            if (shop.updateProduct(product)) {
			                JOptionPane.showMessageDialog(null, "Stock actualizado ", "Information",
			                        JOptionPane.INFORMATION_MESSAGE);
			                dispose(); 
			            } else {
			                JOptionPane.showMessageDialog(null, "Error al actualizar en BD", "Error",
			                        JOptionPane.ERROR_MESSAGE);
			            }

			        } catch(NumberFormatException e2) {
			            JOptionPane.showMessageDialog(null, "Formato inválido (solo números enteros)", "Error",
			                    JOptionPane.ERROR_MESSAGE);
			        } catch(Exception e3) {
			             JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado", "Error",
			                    JOptionPane.ERROR_MESSAGE);
			        }
			    }
			    break;
				
			case Constants.OPTION_REMOVE_PRODUCT:

			    product = shop.findProduct(textFieldName.getText());
			    
			    if (product == null) {
			        JOptionPane.showMessageDialog(null, "Producto no existe ", "Error",
			                JOptionPane.ERROR_MESSAGE);
			        
			    } else {
			        boolean eliminado = shop.removeProduct(product);
			        
			        if (eliminado) {
			            JOptionPane.showMessageDialog(null, "Producto eliminado", "Information",
			                    JOptionPane.INFORMATION_MESSAGE);
			            dispose();  
			        } else {
			            JOptionPane.showMessageDialog(null, "Error al eliminar el producto de la base de datos", "Error",
			                    JOptionPane.ERROR_MESSAGE);
			        }
			    }
			    
			    break;

			default:
				break;
			}
			
		}
		
		if (e.getSource() == cancelButton) {
			// release current screen
			dispose();			
		}		
	}

}
