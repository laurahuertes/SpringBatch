package es.neesis.demospringbatch.writer;

import es.neesis.demospringbatch.model.Persona;
import lombok.Value;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class PersonaWriter implements ItemWriter<Persona> {

    private final DataSource dataSource;
    private ExecutionContext executionContext;
    private static final String CSV_FILE_PATH = "personas.csv";// Ruta del archivo CSV


    public PersonaWriter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.executionContext = stepExecution.getJobExecution().getExecutionContext();
    }
    @Override
    public void write(List<? extends Persona> list) throws Exception {

        //ClassPathResource outputPath = new ClassPathResource("personas.csv");

        String outputFilePath = "C:\\Users\\laura.huertes\\Downloads\\SpringBatchDemo\\src\\main\\resources\\personas.csv";
        File outputFile = new File(outputFilePath);

        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Error creating the CSV file", e);
            }
        }
        FileWriter fileWriter = new FileWriter(outputFile, true);
        try (BufferedWriter writer = new BufferedWriter(fileWriter)){

            if (outputFile.length() == 0) {
                writer.write("id,nombre,apellido,dni");
                writer.newLine();
            }

            List<Persona> personas = list.stream().collect(Collectors.toList());

            for (Persona persona : personas) {
                writer.write(persona.getIdPersona() + "," +
                        persona.getNombre() + "," +
                        persona.getApellido() + "," +
                        persona.getDni());
                writer.newLine();  // Saltar a la siguiente línea
            }

            List<Persona> personasWritten = (List<Persona>) this.executionContext.get("personasWriten");
            if (personasWritten == null) {
                personasWritten = personas;
            } else {
                personasWritten.addAll(personas);
            }

            this.executionContext.put("personasWriten", personasWritten);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error writing to CSV file", e);
        }

    }
}
