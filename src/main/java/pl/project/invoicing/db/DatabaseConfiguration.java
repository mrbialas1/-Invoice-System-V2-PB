package pl.project.invoicing.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.project.invoicing.db.file.FileBasedDatabase;
import pl.project.invoicing.db.file.IdProvider;
import pl.project.invoicing.db.memory.InMemoryDatabase;
import pl.project.invoicing.utils.FilesService;
import pl.project.invoicing.utils.JsonService;

@Slf4j
@Configuration
public class DatabaseConfiguration {

  @Bean
  public IdProvider idProvider(
      FilesService filesService,
      @Value("${invoicing-system.database.directory}") String databaseDirectory,
      @Value("${invoicing-system.database.id.file}") String idFile
  ) throws IOException {
    Path idFilePath = Files.createTempFile(databaseDirectory, idFile);
    return new IdProvider(idFilePath, filesService);
  }

  @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "file")
  @Bean
  public Database fileBasedDatabase(
      IdProvider idProvider,
      FilesService filesService,
      JsonService jsonService,
      @Value("${invoicing-system.database.directory}") String databaseDirectory,
      @Value("${invoicing-system.database.invoices.file}") String invoicesFile
  ) throws IOException {
    log.debug("Creating in-file database");
    Path databaseFilePath = Files.createTempFile(databaseDirectory, invoicesFile);
    return new FileBasedDatabase(databaseFilePath, idProvider, filesService, jsonService);
  }

  @ConditionalOnProperty(name = "invoicing-system.database", havingValue = "memory")
  @Bean
  public Database inMemoryDatabase() {
    log.trace("Creating in-memory database");
    log.debug("Creating in-memory database");
    log.info("Creating in-memory database");
    log.warn("Creating in-memory database");
    log.error("Creating in-memory database");
    return new InMemoryDatabase();
  }

}
