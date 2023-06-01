package com.marvelstudio.lalalibrary.Configurations;

import com.marvelstudio.lalalibrary.Entity.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestDataConfig implements RepositoryRestConfigurer {
    private String allowedOrigins = "http://localhost:3000";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration repositoryRestConfiguration, CorsRegistry corsRegistry) {
        HttpMethod[] unsupportedActions = {
                HttpMethod.POST,
                HttpMethod.PATCH,
                HttpMethod.DELETE,
                HttpMethod.PUT
        };

        /*
         * This code block is responsible for exposing the IDs of Book entities.
         * It ensures that the IDs of Book entities are included in the API responses.
         * By calling repositoryRestConfiguration.exposeIdsFor(Book.class),
         * we instruct Spring Data REST to expose the IDs for the Book entity.
         */
        repositoryRestConfiguration.exposeIdsFor(Book.class);

        // Disable unsupported HTTP methods for Book entities
        disableHttpMethods(Book.class, repositoryRestConfiguration, unsupportedActions, corsRegistry);

        // CORS Configurations
        corsRegistry.addMapping(repositoryRestConfiguration.getBasePath() + "/**")
                .allowedOrigins(allowedOrigins);
    }

    private void disableHttpMethods(Class<Book> bookClass, RepositoryRestConfiguration repositoryRestConfiguration, HttpMethod[] unsupportedActions, CorsRegistry corsRegistry) {
        /*
         * This method is responsible for disabling unsupported HTTP methods (DELETE, POST, PUT)
         * for Book entities.
         * The repositoryRestConfiguration.getExposureConfiguration() retrieves the exposure configuration
         * for the repository.
         * By calling withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions)),
         * we disable the specified HTTP methods for the Book entity.
         */
        repositoryRestConfiguration.getExposureConfiguration()
                .forDomainType(bookClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions)).withCollectionExposure((metdata, httpMethods) ->
                        httpMethods.disable(unsupportedActions));
    }

}
