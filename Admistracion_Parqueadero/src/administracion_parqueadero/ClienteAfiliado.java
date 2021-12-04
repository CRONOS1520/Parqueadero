package administracion_parqueadero;

public class ClienteAfiliado extends Usuario{

    private int id;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private int espacioParqueo;
    private boolean mensualidad;
    private Vehiculo vehiculo;

    public ClienteAfiliado(int id, String nombre, String apellido, String fechaNacimiento, String placa, String marca, String ref, int espacioParqueo) {
        super(id, nombre, apellido, fechaNacimiento);
        this.vehiculo = new Vehiculo(placa,marca,ref,espacioParqueo);
        this.espacioParqueo = espacioParqueo;
        this.mensualidad = true;
    }
    
    public void pagarMensualidad(){
        this.mensualidad = true;
    }
    
    public void setEspacioParqueo(int espacioParqueo){
        this.espacioParqueo = espacioParqueo;
    }
    
    public int getEspacioParqueo(){
        return this.espacioParqueo;
    }
    
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}
