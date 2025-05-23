package es.neesis.demospringbatch.tasklet;

import es.neesis.demospringbatch.model.UserEntity;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowUserTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println("PRINTANDO USUARIOS");
        List<UserEntity> usuarios = (List<UserEntity>) chunkContext.getStepContext().getJobExecutionContext().get("users");

        usuarios.forEach(user -> System.out.println(user.toString()));

        System.out.println("YA SE HAN PRINTADO LOS USUARIOS");
        return RepeatStatus.FINISHED;
    }
}
