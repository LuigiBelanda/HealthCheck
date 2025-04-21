package com.healtcheck.labeng.architecture;

import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.model.*;
import com.structurizr.view.*;
import com.structurizr.io.json.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.Objects;

/**
 * Esta classe gera a documentação de arquitetura usando o modelo C4 para o HealthCheck API.
 * Pode ser executada como uma aplicação Java separada ou como parte do seu build.
 */
public class ArchitectureDocumentation {

    private static final String WORKSPACE_ID = "101482"; // ID do workspace no Structurizr
    private static final String API_KEY = "fa659815-b7f6-4522-a037-131871c36f45"; // API Key
    private static final String API_SECRET = "1bcedae8-df16-4299-aeb7-b5b7e2efed5d"; // API Secret

    public static void main(String[] args) throws Exception {
        // Criar um workspace para conter o modelo e as visualizações
        Workspace workspace = new Workspace("HealthCheck API",
                "Sistema para registro e visualização de casos de doenças por agentes de saúde");

        Model model = workspace.getModel();
        ViewSet views = workspace.getViews();

        // Definir pessoas (atores) e sistemas externos
        Person healthcheckUser = model.addPerson("Agente de Saúde",
                "Profissional que registra e busca casos de doenças");

        SoftwareSystem geoapifySystem = model.addSoftwareSystem("API Geoapify",
                "Serviço de geocodificação para converter endereços em coordenadas geográficas");
        geoapifySystem.setLocation(Location.External);

        // Definir o sistema principal
        SoftwareSystem healthcheckSystem = model.addSoftwareSystem("HealthCheck API System",
                "Permite aos agentes de saúde registrar e visualizar casos de doenças em diferentes localidades");
        healthcheckUser.uses(healthcheckSystem, "Registra e busca casos");

        // Definir os containers (componentes de alto nível) no sistema
        Container apiApplication = healthcheckSystem.addContainer("HealthCheck API Application",
                "API REST que fornece funcionalidades para registro e busca de casos de doenças",
                "Spring Boot 3.2.5, Java 17");

        Container database = healthcheckSystem.addContainer("Database",
                "Armazena informações sobre agentes e casos", "Banco de dados relacional");

        apiApplication.uses(database, "Lê e escreve dados");
        apiApplication.uses(geoapifySystem, "Obtém coordenadas geográficas", "HTTP REST API");
        healthcheckUser.uses(apiApplication, "Acessa por meio de", "HTTP REST");

        // Definir os componentes dentro da API
        Component agentController = apiApplication.addComponent("Agent Controller",
                "Gerencia requisições relacionadas aos agentes de saúde", "Spring REST Controller");

        Component caseController = apiApplication.addComponent("Case Controller",
                "Gerencia requisições relacionadas aos casos de doenças", "Spring REST Controller");

        Component mapController = apiApplication.addComponent("Map Controller",
                "Fornece dados para visualização georreferenciada dos casos", "Spring REST Controller");

        Component agentService = apiApplication.addComponent("Agent Service",
                "Lógica de negócio para operações com agentes", "Spring Service");

        Component caseService = apiApplication.addComponent("Case Service",
                "Lógica de negócio para operações com casos", "Spring Service");

        Component geocodingService = apiApplication.addComponent("Geocoding Service",
                "Serviço para obtenção de coordenadas geográficas", "Spring Service");

        Component agentRepository = apiApplication.addComponent("Agent Repository",
                "Acesso a dados de agentes", "Spring Data JPA Repository");

        Component caseRepository = apiApplication.addComponent("Case Repository",
                "Acesso a dados de casos", "Spring Data JPA Repository");

        // Definir relacionamentos entre componentes
        agentController.uses(agentService, "Invoca");
        caseController.uses(caseService, "Invoca");
        mapController.uses(caseRepository, "Consulta");

        agentService.uses(agentRepository, "Utiliza");
        caseService.uses(caseRepository, "Utiliza");
        caseService.uses(geocodingService, "Solicita geocodificação");

        agentRepository.uses(database, "Lê e escreve dados de agentes");
        caseRepository.uses(database, "Lê e escreve dados de casos");

        geocodingService.uses(geoapifySystem, "Consulta", "HTTP REST");

        healthcheckUser.uses(agentController, "Autenticação e gerenciamento de perfil");
        healthcheckUser.uses(caseController, "Registro e consulta de casos");
        healthcheckUser.uses(mapController, "Visualização de casos no mapa");

        // Criar visualizações (diagramas)
        createSystemContextView(views, healthcheckSystem);
        createContainerView(views, healthcheckSystem, geoapifySystem);
        createComponentView(views, apiApplication);

        // Adicionar estilos
        applyStyles(views);

        // Upload para o serviço Structurizr usando as credenciais configuradas
        uploadWorkspaceToStructurizr(workspace);

        // Exportar para arquivo local como backup
        // exportToLocalFile(workspace);
    }

    private static void createSystemContextView(ViewSet views, SoftwareSystem system) {
        SystemContextView contextView = views.createSystemContextView(system,
                "SystemContext", "Visão de contexto do sistema HealthCheck API");
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();
        contextView.enableAutomaticLayout();
    }

    private static void createContainerView(ViewSet views, SoftwareSystem system, SoftwareSystem externalSystem) {
        ContainerView containerView = views.createContainerView(system,
                "Containers", "Visão dos containers do sistema HealthCheck API");
        containerView.addAllContainers();
        containerView.addAllPeople();
        containerView.add(externalSystem); // Método correto para adicionar o sistema externo
        containerView.enableAutomaticLayout();
    }

    private static void createComponentView(ViewSet views, Container container) {
        ComponentView componentView = views.createComponentView(container,
                "Components", "Visão dos componentes da API HealthCheck");
        componentView.addAllComponents();
        componentView.add(Objects.requireNonNull(container.getSoftwareSystem().getModel().getPersonWithName("Agente de Saúde")));
        componentView.add(Objects.requireNonNull(container.getSoftwareSystem().getModel().getSoftwareSystemWithName("API Geoapify")));
        componentView.enableAutomaticLayout();
    }

    private static void applyStyles(ViewSet views) {
        Styles styles = views.getConfiguration().getStyles();

        styles.addElementStyle("Person").shape(Shape.Person).background("#08427B").color("#FFFFFF");
        styles.addElementStyle("Software System").background("#1168BD").color("#FFFFFF");
        styles.addElementStyle("Container").background("#438DD5").color("#FFFFFF");
        styles.addElementStyle("Component").background("#85BBF0").color("#000000");

        styles.addElementStyle("Database").shape(Shape.Cylinder);
    }

    private static void uploadWorkspaceToStructurizr(Workspace workspace) throws Exception {
        System.out.println("Iniciando upload do workspace para Structurizr...");

        // Criar o cliente Structurizr com as credenciais fornecidas
        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);

        // Configurar opções adicionais, se necessário
        // structurizrClient.setMergeFromRemote(true); // Para mesclar com o workspace existente

        try {
            // Fazer o upload do workspace
            structurizrClient.putWorkspace(Long.parseLong(WORKSPACE_ID), workspace);
            System.out.println("Upload para Structurizr concluído com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao fazer upload para Structurizr: " + e.getMessage());
            e.printStackTrace();

            // Em caso de falha, garantir que pelo menos exportamos localmente
            System.out.println("Tentando salvar localmente como fallback...");
            // exportToLocalFile(workspace);
        }
    }

    /*
    private static void exportToLocalFile(Workspace workspace) {
        try {
            // Criar diretório de saída se não existir
            File outputDir = new File("./labeng/labeng/src/main/java/com/healtcheck/labeng/architecture");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Exportar como JSON para uso com Structurizr Lite
            File jsonFile = new File(outputDir, "healthcheck-architecture.json");
            try (FileWriter fileWriter = new FileWriter(jsonFile)) {
                JsonWriter jsonWriter = new JsonWriter(true);
                jsonWriter.write(workspace, fileWriter);
            }

            System.out.println("Workspace exportado para: " + jsonFile.getAbsolutePath());
            System.out.println("Este arquivo pode ser importado no Structurizr Lite ou usado como backup.");

        } catch (Exception e) {
            System.err.println("Erro ao exportar workspace para arquivo local: " + e.getMessage());
            e.printStackTrace();
        }
    }
    */
}