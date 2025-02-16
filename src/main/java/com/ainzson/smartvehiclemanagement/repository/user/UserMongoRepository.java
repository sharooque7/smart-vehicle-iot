package com.ainzson.smartvehiclemanagement.repository.user;

import com.ainzson.smartvehiclemanagement.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("mongo") // Active only in MongoDB profile
public interface UserMongoRepository extends MongoRepository<User, String>, UserRepository {
}
