package org.cosm.curie.repositorios;

import org.cosm.curie.entidades.PasswordResetToken;
import org.cosm.curie.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    PasswordResetToken findByToken(String token);



}