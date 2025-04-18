package com.healtcheck.labeng.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healtcheck.labeng.dtos.CaseRegisterDTO;
import com.healtcheck.labeng.dtos.CaseSearchRequestDTO;
import com.healtcheck.labeng.entities.Agent;
import com.healtcheck.labeng.entities.Case;
import com.healtcheck.labeng.exceptions.ResourceNotFoundException;
import com.healtcheck.labeng.exceptions.UnauthorizedOperationException;
import com.healtcheck.labeng.repositories.AgentRepository;
import com.healtcheck.labeng.repositories.CaseRepository;
import com.healtcheck.labeng.services.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


@Service
public class CaseServiceImpl implements CaseService {
    private static final String GEOAPIFY_API_KEY = "9ddf24376acc4b098bfe7c1515e311d5";
    private static final String GEOAPIFY_API_URL = "https://api.geoapify.com/v1/geocode/search";

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Case register(CaseRegisterDTO caseRegisterDTO) {
        // Verificar se o agente existe
        Agent agent = agentRepository.findById(caseRegisterDTO.getAgentId())
                .orElseThrow(() -> new ResourceNotFoundException("Agente não encontrado"));

        // Verificar se o agente é da mesma cidade do caso a ser registrado
        if (!agent.getCity().equalsIgnoreCase(caseRegisterDTO.getCity())) {
            throw new UnauthorizedOperationException("O agente só pode registrar casos em sua própria cidade: " + agent.getCity());
        }

        // Criar um novo caso
        Case newCase = new Case();
        newCase.setDisease(caseRegisterDTO.getDisease());

        // Definir os campos de endereço separados
        newCase.setStreet(caseRegisterDTO.getStreet());
        newCase.setNumber(caseRegisterDTO.getNumber());
        newCase.setComplement(caseRegisterDTO.getComplement());
        newCase.setNeighborhood(caseRegisterDTO.getNeighborhood());
        newCase.setCity(caseRegisterDTO.getCity());
        newCase.setState(caseRegisterDTO.getState());
        newCase.setZipCode(caseRegisterDTO.getZipCode());

        newCase.setRegistrationDate(LocalDateTime.now());
        newCase.setAgent(agent);

        // Obter as coordenadas geográficas do endereço
        try {
            // Estrutura o endereço completo
            String fullAddress = String.format("%s, %s, %s, %s, %s, %s",
                    newCase.getStreet(),
                    newCase.getNumber(),
                    newCase.getNeighborhood(),
                    newCase.getCity(),
                    newCase.getState() + ", Brazil",
                    newCase.getZipCode());

            // Obter coordenadas
            GeoCoordinates coordinates = getCoordinatesFromAddress(fullAddress);

            // Definir latitude e longitude
            if (coordinates != null) {
                newCase.setLatitude(String.valueOf(coordinates.latitude));
                newCase.setLongitude(String.valueOf(coordinates.longitude));
            }
        } catch (Exception e) {
            // Registrar o erro, mas continuar com o cadastro mesmo sem as coordenadas
            System.err.println("Erro ao obter coordenadas: " + e.getMessage());
        }

        // Salvar e retornar o caso
        return caseRepository.save(newCase);
    }

    /**
     * Obtém as coordenadas geográficas a partir de um endereço usando a API Geoapify
     */
    private GeoCoordinates getCoordinatesFromAddress(String address) throws IOException {
        // Codifica o endereço para URL
        String encodedAddress = UriUtils.encodeQueryParam(address, StandardCharsets.UTF_8);
        String urlString = String.format("%s?text=%s&apiKey=%s", GEOAPIFY_API_URL, encodedAddress, GEOAPIFY_API_KEY);

        // Cria a conexão HTTP
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lê a resposta
                String response = readResponse(connection);

                // Parse do JSON de resposta
                JsonNode jsonNode = objectMapper.readTree(response);
                JsonNode features = jsonNode.get("features");

                if (features != null && features.isArray() && !features.isEmpty()) {
                    JsonNode firstResult = features.get(0);
                    JsonNode geometry = firstResult.get("geometry");

                    if (geometry != null && geometry.has("coordinates")) {
                        JsonNode coordinates = geometry.get("coordinates");
                        double longitude = coordinates.get(0).asDouble();
                        double latitude = coordinates.get(1).asDouble();

                        return new GeoCoordinates(latitude, longitude);
                    }
                }
            } else {
                System.err.println("Erro na chamada da API: " + responseCode);
            }
        } finally {
            connection.disconnect();
        }

        return null;
    }

    /**
     * Lê a resposta HTTP
     */
    private String readResponse(HttpURLConnection connection) throws IOException {
        StringBuilder response = new StringBuilder();
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
        }
        return response.toString();
    }

    // Classe auxiliar para representar coordenadas
    private static class GeoCoordinates {
        private final double latitude;
        private final double longitude;

        public GeoCoordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    @Override
    public List<CaseSearchRequestDTO> findByCity(String city) {
        List<Case> cases = caseRepository.findByCity(city);
        return convertToDTOList(cases);
    }

    @Override
    public List<CaseSearchRequestDTO> findByCityAndNeighborhood(String city, String neighborhood) {
        List<Case> cases = caseRepository.findByCityAndNeighborhood(city, neighborhood);
        return convertToDTOList(cases);
    }

    @Override
    public List<CaseSearchRequestDTO> findByCityAndDisease(String city, String disease) {
        List<Case> cases = caseRepository.findByCityAndDisease(city, disease);
        return convertToDTOList(cases);
    }

    @Override
    public List<CaseSearchRequestDTO> findByCityAndNeighborhoodAndDisease(String city, String neighborhood, String disease) {
        List<Case> cases = caseRepository.findByCityAndNeighborhoodAndDisease(city, neighborhood, disease);
        return convertToDTOList(cases);
    }

    // Metodo auxiliar para converter List<Case> para List<CaseSearchRequestDTO>
    private List<CaseSearchRequestDTO> convertToDTOList(List<Case> cases) {
        return cases.stream()
                .map(CaseSearchRequestDTO::new)
                .collect(Collectors.toList());
    }
}
