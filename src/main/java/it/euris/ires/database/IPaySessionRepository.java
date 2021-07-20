package it.euris.ires.database;

import it.euris.ires.entity.PaySession;
import java.util.Optional;
import java.util.UUID;

public interface IPaySessionRepository {

  Optional<PaySession> findById(UUID uuid);

  void save(PaySession paySession) throws IllegalAccessException;
}
