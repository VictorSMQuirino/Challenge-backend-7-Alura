package alura.api.challenge.repository;

import alura.api.challenge.domain.model.Destino;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinoRepository extends JpaRepository<Destino, Long> {
    Page<Destino> findAllByAtivoTrue(Pageable page);

    Destino getReferenceByIdAndAtivoTrue(Long id);

    Page<Destino> findAllByNomeContainingAndAtivoTrue(String busca, Pageable page);
}
