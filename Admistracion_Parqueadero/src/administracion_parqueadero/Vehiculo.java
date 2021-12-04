package administracion_parqueadero;

public class Vehiculo {

    private String placa;
    private String marca;
    private String ref;
    private int espacioParqueo;
    private String horaLlegada;
    private String horaSalida;
    
    public Vehiculo(String placa, String marca, String ref,int espacioParqueo) {
        this.placa = placa;
        this.marca = marca;
        this.ref = ref;
        this.espacioParqueo = espacioParqueo;
    }
    
    
    public int getEspacioParqueo() {
        return espacioParqueo;
    }

    public void setEspacioParqueo(int espacioParqueo) {
        this.espacioParqueo = espacioParqueo;
    }
    
    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }
    
    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
