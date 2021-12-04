package administracion_parqueadero;

public class ClienteNoAfiliado extends Usuario{
    
    private int id;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private int espacioParqueo;
    private Vehiculo vehiculo;
    
    public ClienteNoAfiliado(int id, String nombre, String apellido, String fechaNacimiento, String placa, String marca, String ref, int espacioParqueo) {
        super(id, nombre, apellido, fechaNacimiento);
        this.vehiculo = new Vehiculo(placa,marca,ref,espacioParqueo);
        this.espacioParqueo = espacioParqueo;
    }
    
    public void setEspacioParqueo(int espacioParqueo){
        this.espacioParqueo = espacioParqueo;
    }
    
    public int getEspacioParqueo(){
        return this.espacioParqueo;
    }
}
