package com.grupo1.pidh.utils.converter;

import com.grupo1.pidh.utils.enums.DiaSemana;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DiaSemanaConverter implements AttributeConverter<List<DiaSemana>, String> {

    private static final String SEPARADOR = ",";

    //Convierte List<DiaSemana> a String tipo "LUNES,MARTES" antes de guardarlo en la base de datos
    @Override
    public String convertToDatabaseColumn(List<DiaSemana> diaSemanas) {
        if (diaSemanas == null || diaSemanas.isEmpty()){
            return null;
        }
        return diaSemanas.stream()
                .map(Enum::name)
                .collect(Collectors.joining(SEPARADOR));
    }

    //Cuando recupera info de la BD, convierte "LUNES,MARTES" de nuevo en List<DiaSemana>
    @Override
    public List<DiaSemana> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()){
            return null;
        }
        return Arrays.stream(dbData.split(SEPARADOR))
                .map(DiaSemana::valueOf)
                .collect(Collectors.toList());
    }
}
