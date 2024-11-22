package api.veterinary.system.utils;

public enum Constants {

    INVALID_INPUTS("Campos invalidos, favor de verificar"),
    REGISTER_EXISTS("El registro ya tiene el mismo nombre, ingresa otro nombre porfavor"),
    REGISTER_NOT_EXISTS("El registro no existe, verifica porfavor"),
    REGISTER_CREATED("El registro se creo correctamente"),
    REGISTER_UPDATE("La información del registro se actualizo correctamente"),
    REGISTER_DELETE("El registro se ha eliminado correctamente"),
    LIST_EMPTY("No tiene registros en almacenados"),
    LIST_NOT_EMPTY("Listado de registros"),
    REGISTER_INFORMATION("Información recupera")

    ;

    private String value;

    Constants(String value){this.value = value;}

    public String getValue(){return  value;}
}