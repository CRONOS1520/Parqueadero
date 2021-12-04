package administracion_parqueadero;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Parqueadero extends JFrame implements MouseListener{
    
    private JPanel contentPane;
    private Operador operador;
    private ClienteAfiliado[] clientesAfiliados;
    private Vehiculo[] entrada;
    private Vehiculo[] salida;
    private int[] espacio;
    private int X,Y;
    private int motos = 0;
    private int autoMoviles = 0;
    private boolean grafica = false;
    

    public Parqueadero() {
        super("Parqueadero");
        this.operador = new Operador(0000, null, null, null);
        this.clientesAfiliados = operador.cargarAfiliados();
        this.entrada = operador.cargarRegistroEntrada();
        this.salida = operador.cargarRegistroSalida();
        this.espacio = new int[30];
        for (int i = 0; i < espacio.length; i++) {
            espacio[i] = 0;
        }
        contentPane = new JPanel();
        
        
        setBounds(0, 0, 700, 700);
        setContentPane(contentPane);
        setBackground(Color.white);
        setLocationRelativeTo(null);
        addMouseListener((MouseListener) this); 
        setVisible( true );
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public void paint(Graphics g){
        int contador = 1;
        boolean imprimir = true;
        g.setColor(Color.BLACK);
        g.drawRect(35, 600, 100, 50);
        g.drawString("Sacar Vehiculo", 45, 630);
        
        g.drawRect(565, 600, 100, 50);
        g.drawString("Obtener grafico", 575, 630);
        
        g.drawRect(400, 600, 100, 50);
        g.drawString("Agregar Afiliado", 410, 630);
        
        g.drawRect(250, 600, 100, 50);
        g.drawString("Limpiar", 280, 630);
        
        g.drawString("M", 75, 40);
        g.drawRect(35, 45, 80, 510);
        
        if(!grafica){
            g.setColor(Color.WHITE);
            g.fillRect(150, 150, 200, 200);
        }
        for(int i = 50; i<=500;i+=50){g.setColor(Color.BLACK);
                    g.drawString(" ", 50+25, i + 24);
                    g.drawString("L", 50+25, i + 24);
                    g.setColor (Color.GREEN);
            for (int j = 0; j < clientesAfiliados.length; j++) {
                if(clientesAfiliados[j].getEspacioParqueo() == contador){
                    espacio[contador-1] = 1;
                    g.setColor(Color.WHITE);
                    g.fillRect(70, i+15,15,15);
                    g.setColor(Color.BLACK);
                    g.drawString("R", 50+25, i + 24);
                    g.setColor (Color.GRAY);
                    continue;
                }
            }
            for (int j = 0; j < entrada.length; j++) {
                if(entrada[j].getEspacioParqueo() == contador){
                    for (int k = 0; k < salida.length; k++) {
                        if((entrada[j].getPlaca().compareTo(salida[k].getPlaca())) == 0){
                            imprimir = false;
                        }
                    }
                    if(imprimir){
                        espacio[contador-1] = 2;
                        g.setColor(Color.WHITE);
                        g.fillRect(70, i+15,15,15);
                        g.setColor(Color.BLACK);
                        g.drawString("NL", 50+25, i + 23);
                        g.setColor (Color.BLUE);
                        continue;
                    }
                    
                }
                imprimir = true;
            }
            g.drawRect (50, i, 50, 50);
            contador++;
        }
        contador = 11;
        
        g.setColor(Color.BLACK);
        g.drawString("V", 400, 40);
        g.drawRect(135, 45, 530, 510);
        for(int i = 50; i<=500;i+=50){
            for(int j = 150; j<=600;j+=50){
                if((i == 50) || (i == 500)){
                    g.setColor(Color.WHITE);
                    g.fillRect(j+20, i+15,20,20);
                    g.setColor(Color.BLACK);
                    g.drawString("L", j+25, i + 24);
                    g.setColor (Color.GREEN);
                    for (int p = 0; p < clientesAfiliados.length; p++) {
                        if(clientesAfiliados[p].getEspacioParqueo() == contador){
                            espacio[contador-1] =1;
                            g.setColor(Color.WHITE);
                            g.fillRect(j+20, i+15,15,15);
                            g.setColor(Color.BLACK);
                            g.drawString("R", j+25, i + 24);
                            g.setColor (Color.GRAY);
                            continue;
                        }
                    }
                    for (int h = 0; h < entrada.length; h++) {
                        if(entrada[h].getEspacioParqueo() == contador){
                            for (int k = 0; k < salida.length; k++) {
                                if((salida[k].getPlaca().compareTo(entrada[h].getPlaca())) == 0){
                                    imprimir = false;
                                }
                            }
                            if(imprimir){
                                espacio[contador-1]=2;
                                g.setColor(Color.WHITE);
                                g.fillRect(j+20, i+15,20,20);
                                g.setColor(Color.BLACK);
                                g.drawString("NL", j+24, i + 24);
                                g.setColor (Color.BLUE);
                                continue;
                            }
                            imprimir = true;
                        }
                    }
                    g.drawRect (j, i, 50, 50);
                    contador++;
                }
            }
        }
        
        if(grafica){
            g.setColor(Color.WHITE);
            g.fillRect(150, 150, 200, 200);
            g.setColor(Color.red);
            g.drawString("Motos: "  , 200, 200);
            g.drawString(String.valueOf(motos) , 213, 210);
            g.fillRect(250, 175, 25*motos, 50);
            g.setColor(Color.GREEN);
            g.drawString("Carros: "  , 200, 300);
            g.drawString(String.valueOf(autoMoviles) , 213, 310);
            g.fillRect(250, 275, 25*autoMoviles, 50);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int posicion = 0;
        X = e.getX()/50;
        Y = e.getY()/50;
        Vehiculo[] aux;
        
        if(X == 1 && Y <= 10){
            posicion = Y;
        }
        if(Y == 1 && X >= 3 && X <= 12){
            posicion = X + 8;
        }
        if(Y == 10 && X >= 3 && X <= 12){
            posicion = X + 18;
        }
        
        if(X >= 1 && X<=2 && Y==12){
            try {
                salida = operador.registrarVehiculoSalida();
            } catch (ParseException ex) {
                Logger.getLogger(Parqueadero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if((X == 5 || X == 6) && Y ==12){
            grafica = false;
            repaint();
        }else if((X == 8 || X == 9) && Y ==12){
            clientesAfiliados = operador.crearClienteAfiliado();
        }else if(X >= 11 && X<=12 && Y==12){
            motos = 0;
            autoMoviles = 0;
            Vehiculo[] cantidadFecha = operador.obtenerFecha();
            
            for (int i = 0; i < cantidadFecha.length; i++) {
                if(cantidadFecha[i].getRef().compareTo("Moto") == 0){
                    motos++;
                }else{
                    autoMoviles++;
                }  
            }
            grafica = true;
            
        }else if(espacio[posicion - 1] == 1){
            if(JOptionPane.showConfirmDialog(null, "¿Intoducir vehiculo?") == 0){
                for (int i = 0; i < clientesAfiliados.length; i++) {
                    if(clientesAfiliados[i].getEspacioParqueo() == posicion){
                        aux = new Vehiculo[entrada.length+1];
                        for (int j = 0; j < aux.length; j++) {
                            if(j == aux.length-1){
                                aux[j] = clientesAfiliados[i].getVehiculo();
                                aux[j].setHoraLlegada(JOptionPane.showInputDialog(null, "Ingrese la hora de entrada (MM/DD/YYYY HH:MM:SS)"));              
                            }else{
                                aux[j] = entrada[j];
                            }
                        }
                    entrada = aux;
                    }
                }
                operador.escribirRegistroEntrada(entrada);
                espacio[posicion-1]=2;
            }
        }else if(espacio[posicion-1] == 0){
            if(JOptionPane.showConfirmDialog(null, "¿Intoducir vehiculo?") == 0){
                entrada = operador.registrarVehiculoEntrada(posicion);
            }
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
