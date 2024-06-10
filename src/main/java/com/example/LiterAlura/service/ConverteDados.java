package com.example.LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
//            if (classe == DadosLivro.class) {
            JsonNode node = mapper.readTree(json);
            var s = node.get("results").get(0);
            return mapper.treeToValue(s, classe);
//            } else if (classe == DadosAutor.class) {
//                JsonNode node = mapper.readTree(json);
//                var s = node.get("results").get(0).get("authors").get(0);
//                return mapper.treeToValue(s, classe);
//            } else {
//                return mapper.readValue(json, classe);
//            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
