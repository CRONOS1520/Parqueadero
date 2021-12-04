package administracion_parqueadero;

import java.io.BufferedReader;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Operador extends Usuario {

    private BufferedReader lector;
    private String linea;
    private String partes[];

    public Operador(int id, String nombre, String apellido, String fechaNacimiento) {
        super(id, nombre, apellido, fechaNacimiento);

    }
    
    //Registramos la salida del vehiculo en el archivo csv
    public Vehiculo[] registrarVehiculoSalida() throws ParseException {
        int valorAPagar = 0;
        
        Vehiculo[] arregloSalida = cargarRegistroSalida();
        ClienteAfiliado[] arregloAfiliados = cargarAfiliados();
        Vehiculo[] arregloEntrada = cargarRegistroEntrada();
        
        Vehiculo[] newArreglo = new Vehiculo[arregloSalida.length + 1];
        
        String placa = JOptionPane.showInputDialog(null, "Ingrese la placa del vehiculo");
        String salida = JOptionPane.showInputDialog(null, "Ingrese la hora de salida (MM/DD/YYYY HH:MM:SS)");
        
        for (int i = 0; i < arregloAfiliados.length; i++) {
            String aux = arregloAfiliados[i].getVehiculo().getPlaca();
            if((aux.compareTo(placa)) == 0){
                for (int j = 0; j <= newArreglo.length - 1; j++) {
                    if(j == newArreglo.length - 1 ){
                        newArreglo[j] = arregloAfiliados[i].getVehiculo();
                        newArreglo[j].setHoraSalida(salida);
                    }else{
                        newArreglo[j] = arregloAfiliados[i].getVehiculo();
                    }
                }
                escribirRegistroSalida(newArreglo);
                return newArreglo;
            }
        }
        
        for (int i = 0; i < arregloSalida.length; i++) {
            newArreglo[i] = arregloSalida[i];
        }
        
        for (int i = 0; i < arregloEntrada.length; i++) {
            if((arregloEntrada[i].getPlaca().compareTo(placa)) == 0){
                for (int j = 0; j <= newArreglo.length - 1; j++) {
                    if(j == newArreglo.length - 1 ){
                        newArreglo[j] = arregloEntrada[i];
                        newArreglo[j].setHoraSalida(salida);
                        
                        valorAPagar = 3500 * obtenerTiempoHoras(newArreglo[j].getHoraLlegada(), newArreglo[j].getHoraSalida());
                        JOptionPane.showMessageDialog(null, "Valor a pagar: " + valorAPagar);
                    }
                }
            }
        }
        escribirRegistroSalida(newArreglo);
        return newArreglo;
    }

    private void escribirRegistroSalida(Vehiculo[] arreglo){
        
        try {
            PrintWriter pw = new PrintWriter(new File("vehiculoSalida.csv"));
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i <= arreglo.length - 1; i++) {
                sb.append(arreglo[i].getPlaca());
                sb.append(",");
                sb.append(arreglo[i].getHoraSalida());
                sb.append("\n");
            }

            pw.write(sb.toString());
            pw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public Vehiculo[] cargarRegistroSalida(){
        Vehiculo[] arregloEntrada = cargarRegistroEntrada();
        ClienteAfiliado[] afiliados = cargarAfiliados();
        
        try {
            
            lector = new BufferedReader(new FileReader("vehiculoSalida.csv"));
            int contadorUsers = 0;
            while (lector.readLine() != null) {
                contadorUsers = contadorUsers + 1;
            }
            lector.close();
            Vehiculo registro[] = new Vehiculo[contadorUsers];
            int contadorAux = 0;
            lector = new BufferedReader(new FileReader("vehiculoSalida.csv"));
            
            for (int j = 1; j <= contadorUsers; j++) {
                linea = lector.readLine();
                partes = linea.split(",");
                String placa = partes[0];
                String horaSalida = partes[1];
                
                
                for (int i = 0; i < afiliados.length; i++) {
                    if((arregloEntrada[i].getPlaca().compareTo(placa)) == 0){
                        registro[contadorAux] = new Vehiculo(placa,arregloEntrada[i].getMarca(), arregloEntrada[i].getRef(), arregloEntrada[i].getEspacioParqueo());
                        registro[contadorAux].setHoraLlegada(arregloEntrada[i].getHoraLlegada());
                        registro[contadorAux].setHoraSalida(horaSalida);
                    }
                }
                
                contadorAux = contadorAux + 1;
            }
            lector.close();

            return registro;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " Lectura del archivo salida");
            return null;
        }
        
    }
    
    //Registramos la entrada del vehiculo
    public Vehiculo[] registrarVehiculoEntrada(int puesto) {
        boolean esAfiliado = false;
        int pos = 0;
        
        Vehiculo[] arregloEntrada = cargarRegistroEntrada();
        Vehiculo[] newArreglo;
        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el Id de cliente"));
        String entrada = JOptionPane.showInputDialog(null, "Ingrese la hora de entrada (MM/DD/YYYY HH:MM:SS)");
        ClienteAfiliado[] arreglo = cargarAfiliados();
        
        for (int i = 0; i <= arreglo.length - 1; i++) {
            if(arreglo[i].getId() == id){
                esAfiliado = true;
                pos = i;
                continue;
            }
        }
        
        newArreglo = new Vehiculo[arregloEntrada.length +1];
        
        if(esAfiliado){
            for (int i = 0; i <= newArreglo.length - 1; i++) {
                if(i == newArreglo.length - 1 ){
                    newArreglo[i] = new Vehiculo(arreglo[pos].getVehiculo().getPlaca(),
                                                    arreglo[pos].getVehiculo().getMarca(),
                                                        arreglo[pos].getVehiculo().getRef(),
                                                            arreglo[pos].getEspacioParqueo());
                    newArreglo[i].setHoraLlegada(entrada);
                }else{
                    newArreglo[i] = arregloEntrada[i];
                }
            }
        }else{
            String placa = JOptionPane.showInputDialog(null, "Ingrese la placa del vehiculo");
            String marca = JOptionPane.showInputDialog(null, "Ingrese la marca del vehiculo");
            String ref = JOptionPane.showInputDialog(null, "Ingrese la referencia del vehiculo");
            
            for (int i = 0; i <= newArreglo.length - 1; i++) {
                if(i == newArreglo.length - 1 ){
                    newArreglo[i] = new Vehiculo(placa, marca, ref, puesto);
                    newArreglo[i].setHoraLlegada(entrada);
                }else{
                    newArreglo[i] = arregloEntrada[i];
                }
            }
            JOptionPane.showMessageDialog(null, placa + "\n" +
                                                        marca +"\n"+
                                                            ref + "\n"+
                                                                newArreglo[newArreglo.length-1].getHoraLlegada()+"\n"+
                                                                    Integer.toString(puesto));
        }
        
        escribirRegistroEntrada(newArreglo);
        
        return newArreglo;
    }
    
    public void escribirRegistroEntrada(Vehiculo[] arreglo){
        try {
            PrintWriter pw = new PrintWriter(new File("vehiculoEntrada.csv"));
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i <= arreglo.length - 1; i++) {
                sb.append(String.valueOf(arreglo[i].getPlaca()));
                sb.append(",");
                sb.append(arreglo[i].getMarca());
                sb.append(",");
                sb.append(arreglo[i].getRef());
                sb.append(",");
                sb.append(arreglo[i].getHoraLlegada());
                sb.append(",");
                sb.append(String.valueOf(arreglo[i].getEspacioParqueo()));
                sb.append("\n");
            }

            pw.write(sb.toString());
            pw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public Vehiculo[] cargarRegistroEntrada(){
        try {
            lector = new BufferedReader(new FileReader("vehiculoEntrada.csv"));
            int contadorUsers = 0;
            while (lector.readLine() != null) {
                contadorUsers++;
            }
            lector.close();
            Vehiculo registro[] = new Vehiculo[contadorUsers];
            int contadorAux = 0;
            lector = new BufferedReader(new FileReader("vehiculoEntrada.csv"));
            for (int j = 1; j <= contadorUsers; j++) {
                linea = lector.readLine();
                partes = linea.split(",");
                String placa = partes[0];
                String marca = partes[1];
                String ref = partes[2];
                int parqueo = Integer.parseInt(partes[4]);
                

                registro[contadorAux] = new Vehiculo(placa, marca, ref, parqueo);
                registro[contadorAux].setHoraLlegada(partes[3]);
                
                contadorAux = contadorAux + 1;
            }
            lector.close();

            return registro;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " Lectura del archivo entrada");
            return null;
        }
        
    }
    
    //Creamos los clientes añadidos
    public ClienteAfiliado[] crearClienteAfiliado() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el Id de cliente"));
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre de cliente");
        String apellido = JOptionPane.showInputDialog(null, "Ingrese el apellido de cliente");
        String fecha = JOptionPane.showInputDialog(null, "Ingrese la fecha de nacimiento de cliente");
        String placa = JOptionPane.showInputDialog(null, "Ingrese la placa del vehiculo");
        String marca = JOptionPane.showInputDialog(null, "Ingrese la marca del vehiculo");
        String ref = JOptionPane.showInputDialog(null, "Ingrese la referencia del vehiculo");
        int puesto = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el numero de puesto"));

        ClienteAfiliado[] arreglo = cargarAfiliados();
        ClienteAfiliado[] newArreglo = new ClienteAfiliado[arreglo.length +1];
        
        for (int i = 0; i <= newArreglo.length - 1; i++) {
            if(i == newArreglo.length - 1 ){
                newArreglo[i] = new ClienteAfiliado(id,nombre,apellido,fecha,placa,marca,ref,puesto);
            }else{
                newArreglo[i] = arreglo[i];
            }
        }
        
        escribirArchivoAfilado(newArreglo);
        
        return newArreglo;
    }

    private void escribirArchivoAfilado(ClienteAfiliado[] arreglo) {
        try {
            PrintWriter pw = new PrintWriter(new File("usuarios.csv"));
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i <= arreglo.length - 1; i++) {
                sb.append(String.valueOf(arreglo[i].getId()));
                sb.append(",");
                sb.append(arreglo[i].getNombre());
                sb.append(",");
                sb.append(arreglo[i].getApellido());
                sb.append(",");
                sb.append(arreglo[i].getFechaNacimiento());
                sb.append(",");
                sb.append(arreglo[i].getVehiculo().getPlaca());
                sb.append(",");
                sb.append(arreglo[i].getVehiculo().getMarca());
                sb.append(",");
                sb.append(arreglo[i].getVehiculo().getRef());
                sb.append(",");
                sb.append(String.valueOf(arreglo[i].getEspacioParqueo()));
                sb.append("\n");
            }

            pw.write(sb.toString());
            pw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public ClienteAfiliado[] cargarAfiliados() {
        try {
            lector = new BufferedReader(new FileReader("usuarios.csv"));
            int contadorUsers = 0;
            while (lector.readLine() != null) {
                contadorUsers = contadorUsers + 1;
            }
            lector.close();
            ClienteAfiliado afiliados[] = new ClienteAfiliado[contadorUsers];
            int contadorAux = 0;
            lector = new BufferedReader(new FileReader("usuarios.csv"));
            for (int j = 1; j <= contadorUsers; j++) {
                linea = lector.readLine();
                partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                String apellido = partes[2];
                String fechaNacimiento = partes[3];
                String placa = partes[4];
                String marca = partes[5];
                String ref = partes[6];
                int espacioParqueo = Integer.parseInt(partes[7]);

                afiliados[contadorAux] = new ClienteAfiliado(id, nombre, apellido, fechaNacimiento,
                        placa, marca, ref, espacioParqueo);

                contadorAux = contadorAux + 1;
            }
            lector.close();

            return afiliados;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " Lectura del archivo");
            return null;
        }
    }

    public Vehiculo[] obtenerFecha(){
        Vehiculo[] arreglo = cargarRegistroEntrada();
        Vehiculo[] aux;
        int[] posicion;
        int contador = 0;
        
        String fecha = JOptionPane.showInputDialog(null, "Ingrese la fecha de entrada (MM/DD/YYYY)");
        
        for (int i = 0; i < arreglo.length; i++) {
            String[] fechaEntrada = arreglo[i].getHoraLlegada().split(" ");
            if(fechaEntrada[0].compareTo(fecha) == 0){
                contador++;
            } 
        }
        
        posicion = new int[contador];
        aux = new Vehiculo[contador];
        contador = 0;
        
        for (int i = 0; i < arreglo.length; i++) {
            String[] fechaEntrada = arreglo[i].getHoraLlegada().split(" ");
            if(fechaEntrada[0].compareTo(fecha) == 0){
                posicion[contador] = i;
                contador++;
            } 
        }
        
        for (int i = 0; i < posicion.length; i++) {
            aux[i] = arreglo[posicion[i]];
        }
        
        return aux;
    }
    
    private int obtenerTiempoHoras(String entrada, String salida) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.ENGLISH);
        Date firstDate = sdf.parse(entrada);
        Date secondDate = sdf.parse(salida);

        long diff = secondDate.getTime() - firstDate.getTime();

        TimeUnit time = TimeUnit.SECONDS;
        long difference = time.convert(diff, TimeUnit.MILLISECONDS);
        long extra = difference % 3600;
        if (extra > 0) {
            difference -= extra;
            difference += 3600;
        }
        difference /= 3600;
        return (int) difference;
    }
}
