package com.wms.picking_packing_service.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pickingPackingServiceAPI() {
        Server server = new Server();
                server.setUrl("/");
                server.setDescription("Picking & Packing Service");

        Contact contact = new Contact();
        contact.setName("WMS Development Team");
        contact.setEmail("support@wms.com");

        Info info = new Info()
                .title("Picking & Packing Service API")
                .version("1.0.0")
                .description("API for managing picking and packing operations in the Warehouse Management System. " +
                        "This service handles order fulfillment workflows including picking items from inventory, " +
                        "packing orders, and coordinating with Order, Inventory, and Worker services.")
                .contact(contact);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
