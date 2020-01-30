import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PDFOpener extends JFrame {
	public static JFrame frame;
	public static JTextField field;
	public static String code = "";
	public static char c;
	public static int i = 0;
	private final static String PDFPath = "/J:/PDF/";
	
	public PDFOpener() {
		frame = new JFrame();
		frame.setSize(200,100);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		field = new JTextField();
		frame.add(field, BorderLayout.CENTER);
	}
	public static void main(String[] args) {
		new PDFOpener();
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		field.setText("");
		
		field.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				((JTextField) e.getSource()).selectAll();
			}

			public void focusLost(FocusEvent e) {
				((JTextField) e.getSource()).selectAll();
			}
		});
		
		field.addKeyListener(new KeyAdapter() {
		      public void keyReleased(KeyEvent e) {
		    	  c = e.getKeyChar();
		    	  Wpisz();
		      }

		      public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if ( c == KeyEvent.VK_ENTER) {
						String code = field.getText() + ".pdf";
						code = code.replace(" ", "");
						try {
							File pdfFile = new File(PDFPath + code);
							if (Desktop.isDesktopSupported() && pdfFile.exists()) {
								Desktop.getDesktop().open(pdfFile);
								field.setText("");
							} else {
								if (i <= 0) {
								JOptionPane.showMessageDialog(null, "Nie ma takiego PDF");
								i = 1;
								}
								field.setText("");
							}
						} catch (Exception ex) {
							ex.printStackTrace();	
						}
						finally {
							i = 0;
						}
					}
		      }
		      public void keyPressed(KeyEvent e) {
		      }
		    });
	}

	public static void Wpisz() {
  	  code += "" + c;
  	  Czekaj();
	}
	
	public static void Czekaj() {
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	if (!field.getText().equals("")) {
		        		Szukaj();
		        		field.setText("");
		            	}
		            }
		        }, 
		        1500 
		);
	}
	
	public static void Szukaj() {
  	  try {
				code = field.getText() + ".pdf";
				code = code.replace(" ", "");
				File pdfFile = new File(PDFPath + code);
				if (Desktop.isDesktopSupported() && pdfFile.exists()) {
					Desktop.getDesktop().open(pdfFile);
				} else {
					if (i == 0) {
						i = 1;
					JOptionPane.showMessageDialog(null, "Nie ma takiego PDF");
					}
					field.setText("");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				field.setText("");
				i = 0;
			}
	}
}
