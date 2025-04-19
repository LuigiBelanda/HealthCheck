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
import org.springframework.beans.factory.annotation.Value;
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

// Essa anotação define que essa classe é um serviço do Spring
@Service
public class CaseServiceImpl implements CaseService {

    // URL base da API Geoapify para obter coordenadas geográficas
    private static final String GEOAPIFY_API_URL = "https://api.geoapify.com/v1/geocode/search";

    // Injeção do repositório de casos (para salvar e buscar no banco)
    @Autowired
    private CaseRepository caseRepository;

    // Injeção do repositório de agentes (para verificar se o agente existe)
    @Autowired
    private AgentRepository agentRepository;

    // Utilizado para interpretar o JSON de resposta da API (Geoapify)
    @Autowired
    private ObjectMapper objectMapper;

    // A chave da API Geoapify é lida do arquivo application.properties
    @Value("${geoapify.api.key}")
    private String geoapifyApiKey;

    // Método que registra um novo caso
    @Override
    public Case register(CaseRegisterDTO caseRegisterDTO) {
        // Busca o agente no banco pelo ID
        Agent agent = agentRepository.findById(caseRegisterDTO.getAgentId())
                .orElseThrow(() -> new ResourceNotFoundException("Agente não encontrado"));

        // Verifica se o agente está tentando registrar um caso fora da cidade dele
        if (!agent.getCity().equalsIgnoreCase(caseRegisterDTO.getCity())) {
            throw new UnauthorizedOperationException("O agente só pode registrar casos em sua própria cidade: " + agent.getCity());
        }

        // Criação do objeto Case e preenchimento dos dados básicos
        Case newCase = new Case();
        newCase.setDisease(caseRegisterDTO.getDisease());
        newCase.setStreet(caseRegisterDTO.getStreet());
        newCase.setNumber(caseRegisterDTO.getNumber());
        newCase.setComplement(caseRegisterDTO.getComplement());
        newCase.setNeighborhood(caseRegisterDTO.getNeighborhood());
        newCase.setCity(caseRegisterDTO.getCity());
        newCase.setState(caseRegisterDTO.getState());
        newCase.setZipCode(caseRegisterDTO.getZipCode());
        newCase.setRegistrationDate(LocalDateTime.now());
        newCase.setAgent(agent); // associa o agente que registrou

        // Tenta obter a latitude e longitude com base no endereço usando a API
        try {
            String fullAddress = String.format("%s, %s, %s, %s, %s, %s",
                    newCase.getStreet(),
                    newCase.getNumber(),
                    newCase.getNeighborhood(),
                    newCase.getCity(),
                    newCase.getState() + ", Brazil",
                    newCase.getZipCode());

            GeoCoordinates coordinates = getCoordinatesFromAddress(fullAddress);

            // Se conseguir as coordenadas, define no caso
            if (coordinates != null) {
                newCase.setLatitude(String.valueOf(coordinates.latitude));
                newCase.setLongitude(String.valueOf(coordinates.longitude));
            }
        } catch (Exception e) {
            // Se der erro, apenas exibe no console e continua o cadastro
            System.err.println("Erro ao obter coordenadas: " + e.getMessage());
        }

        // Salva e retorna o novo caso registrado
        return caseRepository.save(newCase);
    }

    /**
     * Método privado que consulta a API da Geoapify para obter coordenadas
     * com base no endereço completo.
     */
    private GeoCoordinates getCoordinatesFromAddress(String address) throws IOException {
        // Codifica o endereço para que ele possa ser usado na URL
        String encodedAddress = UriUtils.encodeQueryParam(address, StandardCharsets.UTF_8);
        String urlString = String.format("%s?text=%s&apiKey=%s", GEOAPIFY_API_URL, encodedAddress, geoapifyApiKey);

        // Abre a conexão com a API (HTTP GET)
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Se a resposta for 200 OK, lê o JSON de resposta
                String response = readResponse(connection);

                // Converte a string JSON para um objeto manipulável
                JsonNode jsonNode = objectMapper.readTree(response);
                JsonNode features = jsonNode.get("features");

                // Se houver resultados, extrai latitude e longitude
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
            connection.disconnect(); // Fecha a conexão
        }

        return null; // Se não conseguir, retorna null
    }

    /**
     * Lê a resposta da API como uma string
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

    /**
     * Classe interna para representar as coordenadas geográficas
     */
    private static class GeoCoordinates {
        private final double latitude;
        private final double longitude;

        public GeoCoordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    // Métodos para buscar casos filtrando por cidade, bairro e/ou doença

    @Override
    public List<CaseSearchRequestDTO> findByCity(String city) {
        List<Case> cases = caseRepository.findByCity(city);
        return convertToDTOList(cases); // converte para DTO antes de retornar
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

    /**
     * Método auxiliar que converte a lista de entidades `Case`
     * para uma lista de DTOs que serão devolvidas pela API
     */
    private List<CaseSearchRequestDTO> convertToDTOList(List<Case> cases) {
        return cases.stream()
                .map(CaseSearchRequestDTO::new) // usa o construtor do DTO
                .collect(Collectors.toList());
    }
}
