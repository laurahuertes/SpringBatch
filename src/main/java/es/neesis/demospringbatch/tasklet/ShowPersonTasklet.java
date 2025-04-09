package es.neesis.demospringbatch.tasklet;

import es.neesis.demospringbatch.model.Persona;
import es.neesis.demospringbatch.model.UserEntity;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowPersonTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        List<Persona> personas = (List<Persona>) chunkContext.getStepContext().getJobExecutionContext().get("personasWriten");

        personas.forEach(persona -> System.out.println("Persona añadida: " + persona.getNombre()));
        System.out.println("SE HAN PRINTADO LAS PERSONAS");

        return RepeatStatus.FINISHED;

    }
}
