package africa.semicolon.soroSoke.data.repositories;

import africa.semicolon.soroSoke.data.models.Atiku;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AtikuRepository extends MongoRepository<Atiku, String> {
    Atiku getAtikuByTitleIgnoreCase(String title);
}
