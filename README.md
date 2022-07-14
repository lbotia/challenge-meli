# MELI_CHALLENGE

#  Tecnologias Utilizadas

- `Java 1.8`
- `SpringBoot`
- `Mysql`
- `Docker`
- `Docker-compose`



## Como ejecutar con Docker-compose

1. Compilar y Ejecutar
   `docker-compose up --build`

2. Compilar (Opcional)
   `docker-compose --build`

3. Ejecutar (Opcional)
   `docker-compose up`

# Consumo de APIs.
### Servicio Consultar IP
* GET
* URL: `http://localhost:8080/ipCountry/{ip}`
* EJEMPLO: `http://localhost:8080/ipCountry/186.84.88.223`

#### Response
```json
{
   "nameCountry": "Colombia",
   "codeISO": "CO",
   "localMoney": "COP",
   "trm": 4378.576743
}
```

### Servicio Marcar IP lista negra
* GET
* URL: `http://localhost:8080/ipCountry/blackList/{ip}`
* EJEMPLO: `http://localhost:8080/ipCountry/blackList/186.84.88.223`
#### Response
```json
{
   "status": "MARCADO CON EXITO."
}
```
## Ejemplo de consumo de APIs
- Consultar IP marcada en lista negra
#### Response
```json
{
   "status": "CONFLICT",
   "message": "IP se encuentra en lista negra."
}
```

- Si la IP ya se encuentra marcada como lista negra y se intenta marcar de nuevo
#### Response
```json
{
    "status": "CONFLICT",
    "message":"YA SE ENCUENTRA MARCADO."
}
```
- Si el formato de IP no corresponde 
#### Response
```json
{
   "status": "CONFLICT",
    "message":"IP CONSULTADA NO VALIDA."
}
```