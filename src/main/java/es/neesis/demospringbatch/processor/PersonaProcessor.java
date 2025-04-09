package es.neesis.demospringbatch.processor;

import es.neesis.demospringbatch.dto.User;
import es.neesis.demospringbatch.model.Persona;
import org.springframework.batch.item.ItemProcessor;

public class PersonaProcessor implements ItemProcessor<User, Persona> {

    @Override
    public Persona process(User user) {
        int id = Integer.parseInt(user.getId());
        return new Persona(id,user.getName(), user.getSurname(),"12345678A");
    }
}
