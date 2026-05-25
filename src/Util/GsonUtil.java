package Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Entity.*;
import com.google.gson.*;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.lang.reflect.Type;

public class GsonUtil {

    private static final RuntimeTypeAdapterFactory<Usuario> usuarioAdapter =
            RuntimeTypeAdapterFactory
                    .of(Usuario.class, "tipo")
                    .registerSubtype(DiscenteDiretor.class, "DISCENTE_DIRETOR")
                    .registerSubtype(Discente.class, "DISCENTE")
                    .registerSubtype(Coordenador.class, "COORDENADOR")
                    .registerSubtype(Docente.class, "DOCENTE");

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapterFactory(usuarioAdapter)
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                @Override
                public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
                }
            })
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
                    return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
                }
            })
            .create();

    private GsonUtil() {}
}